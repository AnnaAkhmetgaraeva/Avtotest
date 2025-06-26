package org.example.Certification;

import java.util.Scanner;
import java.util.Random;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int userScore = 0;
        int computerScore = 0;
        final int ROUNDS = 5;

        System.out.println("Давайте играть в Камень-Ножницы-Бумагу!");
        System.out.println("Введите К (камень), Н (ножницы) или Б (бумага)");
        System.out.println("Игра состоит из " + ROUNDS + " раундов.\n");

        for (int round = 1; round <= ROUNDS; round++) {
            System.out.println("--- Раунд " + round + " ---");

            // Ход пользователя
            System.out.print("Ваш выбор (К/Н/Б): ");
            String userInput = scanner.nextLine().toUpperCase();
            char userChoice;

            try {
                userChoice = validateInput(userInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                round--; // Повторяем раунд
                continue;
            }

            // Ход компьютера
            char computerChoice = getComputerChoice(random);
            System.out.println("Компьютер выбрал: " + getFullName(computerChoice));

            // Определение победителя и начисление баллов
            int roundPoints = determineWinner(userChoice, computerChoice);

            if (roundPoints > 0) {
                System.out.println("Вы выиграли раунд! +" + roundPoints + " баллов");
                userScore += roundPoints;
            } else if (roundPoints < 0) {
                System.out.println("Компьютер выиграл раунд! +" + (-roundPoints) + " баллов");
                computerScore += -roundPoints;
            } else {
                System.out.println("Ничья в этом раунде!");
            }

            System.out.println("Текущий счет: Вы " + userScore + " - " + computerScore + " Компьютер\n");
        }

        // Итог игры
        System.out.println("=== Игра окончена ===");
        System.out.println("Финальный счет: Вы " + userScore + " - " + computerScore + " Компьютер");

        if (userScore > computerScore) {
            System.out.println("Поздравляем! Вы победили!");
        } else if (computerScore > userScore) {
            System.out.println("Компьютер победил. Попробуйте еще раз!");
        } else {
            System.out.println("Ничья! Игра закончилась с равным счетом.");
        }
    }

    private static char validateInput(String input) throws IllegalArgumentException {
        if (input.length() != 1 || "КНБ".indexOf(input.charAt(0)) == -1) {
            throw new IllegalArgumentException("Пожалуйста, введите К, Н или Б");
        }
        return input.charAt(0);
    }

    private static char getComputerChoice(Random random) {
        int choice = random.nextInt(3);
        return switch (choice) {
            case 0 -> 'К';
            case 1 -> 'Н';
            case 2 -> 'Б';
            default -> throw new IllegalStateException("Недопустимый выбор компьютера");
        };
    }

    private static String getFullName(char choice) {
        return switch (choice) {
            case 'К' -> "Камень";
            case 'Н' -> "Ножницы";
            case 'Б' -> "Бумага";
            default -> "Неизвестно";
        };
    }

    private static int determineWinner(char user, char computer) {
        if (user == computer) {
            return 0; // Ничья
        }

        // Правила победы и баллы
        if ((user == 'К' && computer == 'Н') ||
                (user == 'Н' && computer == 'Б') ||
                (user == 'Б' && computer == 'К')) {

            return getPoints(user); // Победа пользователя
        } else {
            return -getPoints(computer); // Победа компьютера (отрицательное значение)
        }
    }

    private static int getPoints(char choice) {
        return switch (choice) {
            case 'К' -> 1;
            case 'Н' -> 2;
            case 'Б' -> 5;
            default -> 0;
        };
    }
}
