package org.example.Homework6;

import java.time.Duration;
import java.time.LocalDateTime;

    public class HumanReadableTimestampDuration implements HumanReadableTimestamp {

        @Override
        public String getTimestamp(LocalDateTime eventTimestamp) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(eventTimestamp, now);

            long minutes = duration.toMinutes();
            long hours = duration.toHours();
            long days = duration.toDays();

            if (minutes < 60) {
                return formatMinutes(minutes);
            } else if (hours < 24) {
                return formatHours(hours);
            } else if (days == 1) {
                return "опубликовано вчера";
            } else {
                return formatDays(days);
            }
        }

        private String formatMinutes(long minutes) {
            if (minutes % 10 == 1 && minutes % 100 != 11) {
                return "опубликовано " + minutes + " минуту назад";
            } else if (minutes % 10 >= 2 && minutes % 10 <= 4 && (minutes % 100 < 10 || minutes % 100 >= 20)) {
                return "опубликовано " + minutes + " минуты назад";
            } else {
                return "опубликовано " + minutes + " минут назад";
            }
        }

        private String formatHours(long hours) {
            if (hours % 10 == 1 && hours % 100 != 11) {
                return "опубликовано " + hours + " час назад";
            } else if (hours % 10 >= 2 && hours % 10 <= 4 && (hours % 100 < 10 || hours % 100 >= 20)) {
                return "опубликовано " + hours + " часа назад";
            } else {
                return "опубликовано " + hours + " часов назад";
            }
        }

        private String formatDays(long days) {
            if (days % 10 == 1 && days % 100 != 11) {
                return "опубликовано " + days + " день назад";
            } else if (days % 10 >= 2 && days % 10 <= 4 && (days % 100 < 10 || days % 100 >= 20)) {
                return "опубликовано " + days + " дня назад";
            } else {
                return "опубликовано " + days + " дней назад";
            }
        }
    }
