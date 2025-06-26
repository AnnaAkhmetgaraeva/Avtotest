package org.example.Homework5;

import java.util.ArrayList;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        //Создание списка задач
        List<String> toDoList = new ArrayList<>();
        toDoList.add("Умыться");
        toDoList.add("Позавтракать");
        toDoList.add("Одеться");
        toDoList.add("Погулять с собакой");
        toDoList.add("Идти на работу");
        //Вывод задач по порядку с индексом
        for (int i = 0; i < toDoList.size(); i++) {
    System.out.println("Задача " + (i+1) + ": " + toDoList.get(i));
        }
    }
}
