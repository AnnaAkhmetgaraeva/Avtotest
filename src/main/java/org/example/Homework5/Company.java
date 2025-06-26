package org.example.Homework5;

import java.util.List;

public class Company {
    String name;
    int foundationYear;
    List<Movie> movies;

    public Company(String name, int foundationYear, List<Movie> movies) {
        this.name = name;
        this.foundationYear = foundationYear;
        this.movies = movies;
    }

    public String getMoviesAsString() {
        StringBuilder sb = new StringBuilder();
        for (Movie movie : movies) {
            sb.append(movie.name).append(", ");
        }
        // Удаляем последнюю запятую и пробел
        return !sb.isEmpty() ? sb.substring(0, sb.length() - 2) : "";
    }
}
