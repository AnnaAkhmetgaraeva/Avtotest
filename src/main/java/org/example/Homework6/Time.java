package org.example.Homework6;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Time {
    public static void main(String[] args) {
        // Создаем timestamp (время публикации)
        LocalDate publishDate = LocalDate.now().minusDays(4);  // Вчерашняя дата
        LocalTime publishTime = LocalTime.now().minusHours(5); // 5 часов назад
        LocalDateTime timestamp = LocalDateTime.of(publishDate, publishTime);

        //Форматирование
        HumanReadableTimestamp timestampFormatter = new HumanReadableTimestampDuration();
        String formattedTimestamp = timestampFormatter.getTimestamp(timestamp);

        //Дата и время публикации
        System.out.println("Время публикации: " + timestamp);
        System.out.println(formattedTimestamp);
    }
}