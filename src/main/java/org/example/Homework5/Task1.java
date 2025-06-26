package org.example.Homework5;

public class Task1 {
    public static void main(String[] args) {
        // Создаем двух игроков с одинаковыми значениями полей
        Player player1 = new Player(1, "User", true);
        Player player2 = new Player(1, "User", true);

        // Проверяем равенство ссылок
        System.out.println("player1 == player2: " + (player1 == player2));

        // Проверяем логическое равенство
        System.out.println("player1.equals(player2): " + player1.equals(player2));
    }
}
