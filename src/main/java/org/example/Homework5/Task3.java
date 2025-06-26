package org.example.Homework5;

import java.util.ArrayList;
import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        List<Movie> Films = new ArrayList<>();
        //Список любимых фильмов
        Films.add(new Movie("Волк с Уолл-стрит", 8.2, "Биография", "США", false));
        Films.add(new Movie("Один дома", 7.7, "Комедия", "США", false));
        Films.add(new Movie("Гарри Поттер и философский камень", 7.6, "Фэнтези", "Великобритания", false));

        // Вывод фильма с индексом 1
        System.out.println(Films.get(1));
    }
}
