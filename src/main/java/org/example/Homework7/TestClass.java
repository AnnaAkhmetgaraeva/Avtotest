package org.example.Homework7;

public class TestClass {
    public static void main(String[] args) {
        PlayerService service = new PlayerServiceJSON();

        //Создание игрока игрока
        int playerId = service.createPlayer("Test1");
        System.out.println("Создан игрок с ID: " + playerId);

        //Получение игрока по id
        Player player = service.getPlayerById(playerId);
        System.out.println("Найден игрок: " + player);

        //Добавление очков пользователю
       int newPoints = service.addPoints(playerId, 30);
       System.out.println("Добавлено 30 очков. Счет: " + newPoints);

        //Проверка обновления данных
       Player updatedPlayer = service.getPlayerById(playerId);
       System.out.println("Обновленные данные: " + updatedPlayer);
    }
}
