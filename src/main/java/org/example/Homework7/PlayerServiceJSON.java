package org.example.Homework7;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerServiceJSON implements PlayerService {
    private Map<Integer, Player> players = new HashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger(1);
    private String dataFile = "players1.json";
    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public PlayerServiceJSON(String dataFile) {
        this.dataFile = dataFile;
        loadData();
    }

    public PlayerServiceJSON() {
    }

    private void loadData() {
        try {
            File file = new File(dataFile);
            if (file.exists() && file.length() > 0) {
                // Читаем как List<Player>
                List<Player> playersList = mapper.readValue(file, new TypeReference<List<Player>>() {});
                for (Player player : playersList) {
                    players.put(player.getId(), player);
                    idGenerator.set(Math.max(idGenerator.get(), player.getId() + 1));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            // Сохраняем как List<Player>
            mapper.writeValue(new File(dataFile), new ArrayList<>(players.values()));
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    // Остальные методы остаются без изменений
    @Override
    public Player getPlayerById(int id) {
        return players.get(id);
    }

    @Override
    public Collection<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    @Override
    public int createPlayer(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be empty");
        }

        if (nickname.length() > 15) {
            throw new IllegalArgumentException("Nickname is too long");
        }

        // Проверка на дубликат
        players.values().stream()
                .filter(p -> p.getNick().equals(nickname))
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Nickname is already in use: " + nickname);
                });

        int newId = idGenerator.getAndIncrement();
        Player player = new Player(newId, nickname, 0, true);
        players.put(newId, player);
        saveData();
        return newId;
    }

    @Override
    public Player deletePlayer(int id) {
        Player removed = players.remove(id);
        if (removed != null) {
            saveData();
        }
        return removed;
    }

    @Override
    public int addPoints(int playerId, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        Player player = players.get(playerId);
        if (player != null) {
            player.setPoints(player.getPoints() + points);
            saveData();
            return player.getPoints();
        }
        return -1;
    }
}