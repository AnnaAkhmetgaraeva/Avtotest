package org.example.Homework5;

import java.util.*;


public class Task6 {
    public static void main(String[] args) {
        Map<Player, Integer> leaderBoard = new HashMap<>();

        // Добавляем игроков
        for (int i = 1; i <= 10; i++) {
            leaderBoard.put(new Player(i, "Player_" + i, i % 2 == 0), 0);
        }

        // Назначаем очки (исправленный вариант)
        updateScore(leaderBoard, 4, 10);
        updateScore(leaderBoard, 7, 12);
        updateScore(leaderBoard, 8, 11);
        updateScore(leaderBoard, 9, 13);
        updateScore(leaderBoard, 10, 5);

        // Сортируем и выводим результаты
        List<Map.Entry<Player, Integer>> results = new ArrayList<>(leaderBoard.entrySet());
        results.sort((o1, o2) -> o2.getValue() - o1.getValue());

        System.out.println("ТОП-3 ИГРОКОВ:");
        for (int i = 0; i < 3 && i < results.size(); i++) {
            Map.Entry<Player, Integer> entry = results.get(i);
            System.out.printf("%d. %-15s %d очков%n",
                    i + 1,
                    entry.getKey().getNickname(),
                    entry.getValue());
        }
    }

    private static void updateScore(Map<Player, Integer> map, int playerId, int score) {
        for (Player player : map.keySet()) {
            if (player.getId() == playerId) {
                map.put(player, score);
                return;
            }
        }
        System.out.println("Игрок с ID " + playerId + " не найден!");
    }
}