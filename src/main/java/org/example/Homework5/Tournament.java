package org.example.Homework5;

import java.util.HashSet;
import java.util.Set;

public class Tournament {
        private Set<Player> players = new HashSet<>();

        public boolean addPlayer(Player player) {
            return players.add(player);
        }

        public static void main(String[] args) {
            Tournament tournament = new Tournament();

            // Создаем 10 игроков
            Player p1 = new Player(1, "Иван", true);
            Player p2 = new Player(2, "Мария", false);
            Player p3 = new Player(3, "Алексей", false);
            Player p4 = new Player(4, "Елена", false);
            Player p5 = new Player(5, "Дмитрий", true);
            Player p6 = new Player(6, "Ольга", true);
            Player p7 = new Player(7, "Сергей", false);
            Player p8 = new Player(8, "Анна", true);
            Player p9 = new Player(9, "Павел", false);
            Player p10 = new Player(10, "Юлия", true);

            // Добавляем игроков в турнир
            tournament.addPlayer(p1);
            tournament.addPlayer(p2);
            tournament.addPlayer(p3);
            tournament.addPlayer(p4);
            tournament.addPlayer(p5);
            tournament.addPlayer(p6);
            tournament.addPlayer(p7);
            tournament.addPlayer(p8);
            tournament.addPlayer(p9);
            tournament.addPlayer(p10);

            // Пытаемся добавить дубликат
            Player p1Duplicate = new Player(1, "Иван (дубль)", true);
            boolean added = tournament.addPlayer(p1Duplicate);

            System.out.println("Удалось добавить дубликат? " + (added ? "Да" : "Нет"));
        }

}
