package org.example.Homework5;

import java.util.ArrayList;
import java.util.List;

public class Task4 {
    public static void main(String[] args) {
        //Список кинокомпаний
        List<Company> companies = new ArrayList<>();
        // Кинокомпания Warner Bros.
        List<Movie> warnerMovies = new ArrayList<>();
        warnerMovies.add(new Movie("Гарри Поттер и философский камень", 7.6, "Фэнтези", "Великобритания/США", false));
        warnerMovies.add(new Movie("Темный рыцарь", 9.0, "Боевик", "США", true));
        warnerMovies.add(new Movie("Матрица", 8.7, "Фантастика", "США", true));
        companies.add(new Company("Warner Bros.", 1923, warnerMovies));

        // Кинокомпания Disney
        List<Movie> disneyMovies = new ArrayList<>();
        disneyMovies.add(new Movie("Король Лев", 8.5, "Мультфильм", "США", true));
        disneyMovies.add(new Movie("Пираты Карибского моря", 8.0, "Приключения", "США", false));
        disneyMovies.add(new Movie("Звездные войны: Пробуждение силы", 7.8, "Фантастика", "США", false));
        companies.add(new Company("Disney", 1923, disneyMovies));

        // Кинокомпания Universal Pictures
        List<Movie> universalMovies = new ArrayList<>();
        universalMovies.add(new Movie("Один дома", 7.7, "Комедия", "США", false));
        universalMovies.add(new Movie("Форсаж", 6.8, "Боевик", "США", false));
        universalMovies.add(new Movie("Парк Юрского периода", 8.1, "Фантастика", "США", true));
        companies.add(new Company("Universal Pictures", 1912, universalMovies));

        //Информация о кинокомпаниях с фильмами
        for (Company company : companies) {
            System.out.println(company.name + " (" + company.foundationYear + "): " + company.getMoviesAsString());
        }
    }
}
