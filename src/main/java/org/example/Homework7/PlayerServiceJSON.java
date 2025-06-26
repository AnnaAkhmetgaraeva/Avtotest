package org.example.Homework7;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerServiceJSON implements PlayerService {
    private Map<Integer, Player> players = new HashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger(1);
    private String dataFile = "players.json";
    private ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public PlayerServiceJSON() {
        loadData();
    }

    private void loadData() {
        try {
            File file = new File(dataFile);
            if (file.exists()) {
                Player[] playersArray = mapper.readValue(file, Player[].class);
                for (Player player : playersArray) {
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
            mapper.writeValue(new File(dataFile), new ArrayList<>(players.values()));
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

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
        Player player = players.get(playerId);
        if (player != null) {
            player.setPoints(player.getPoints() + points);
            saveData();
            return player.getPoints();
        }
        return -1;
    }
}
