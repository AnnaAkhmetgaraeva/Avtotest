package org.example.Certification;


import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод длины забора
        System.out.print("Введите длину забора в см: ");
        double fenceLength = scanner.nextDouble();

        // Вводимые парметры надписи
        int letters = 15;
        int spaces = 3;

        // Расчет длины
        double lettersLength = Math.ceil(letters / 3.0) * 62;
        double spacesLength = spaces * 12;
        double totalLength = lettersLength + spacesLength;

        // Проверка и результат
        if (fenceLength >= totalLength) {
            System.out.println("Надпись поместится!");
            System.out.printf("Необходимая длина: %.2f см%n", totalLength);
        } else {
            System.out.println("Надпись не поместится");
            System.out.printf("Необходимо ещё %.2f см%n", totalLength - fenceLength);
        }
    }
}