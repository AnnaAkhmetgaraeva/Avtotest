package org.example.Homework5;

    public class Movie {
        String name;
        double rating;
        String genre;
        String country;
        boolean Oscar;
        int year;

        public Movie(String name, double rating, String genre, String country, boolean oscar) {
            this.name = name;
            this.rating = rating;
            this.genre = genre;
            this.country = country;
            Oscar = oscar;
        }

        @Override
        public String toString() {
            return "Название: " + name +
                    "\nРейтинг: " + rating +
                    "\nЖанр: " + genre +
                    "\nСтрана: " + country +
                    "\nЕсть Оскар: " + (Oscar ? "Да" : "Нет");
        }
    }
