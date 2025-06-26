import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

public class Task2 {
    public static void main(String[] args) {
        LocalDate lastWateringDate = LocalDate.of(2025, 6, 23);
        WateringScheduler scheduler = new WateringScheduler();
        scheduler.printNextWateringInfo(lastWateringDate);
    }
}

class WateringScheduler {
    private final HumiditySensor sensor;

    public WateringScheduler() {
        this.sensor = new HumiditySensor();
    }

    public void printNextWateringInfo(LocalDate lastWateringDate) {
        LocalDate today = LocalDate.now();
        int humidity = sensor.getHumidity();
        Month currentMonth = today.getMonth();

        System.out.println("--- Проверка полива кактуса ---");
        System.out.println("Последний полив: " + lastWateringDate);
        System.out.println("Текущая влажность: " + humidity + "%");

        if (isWinter(currentMonth)) {
            System.out.println("Сейчас зима — полив раз в месяц.");
            System.out.println("Следующий полив: " + lastWateringDate.plusMonths(1));
        } else if (isSpringOrAutumn(currentMonth)) {
            System.out.println("Сейчас весна/осень — полив раз в неделю.");
            System.out.println("Следующий полив: " + lastWateringDate.plusWeeks(1));
        } else {
            System.out.println("Сейчас лето — полив по влажности (но не чаще 1 раза в 2 дня).");
            if (humidity < 30) {
                LocalDate nextWateringDate = lastWateringDate.plusDays(2);
                if (today.isAfter(nextWateringDate) || today.isEqual(nextWateringDate)) {
                    System.out.println("❗ Кактус нужно полить сегодня! (влажность " + humidity + "%)");
                } else {
                    System.out.println("Следующий полив: " + nextWateringDate);
                }
            } else {
                System.out.println("Полив не требуется. Проверить влажность завтра.");
            }
        }
    }

    private boolean isWinter(Month month) {
        return month == Month.DECEMBER || month == Month.JANUARY || month == Month.FEBRUARY;
    }

    private boolean isSpringOrAutumn(Month month) {
        return month == Month.MARCH || month == Month.APRIL || month == Month.MAY ||
                month == Month.SEPTEMBER || month == Month.OCTOBER || month == Month.NOVEMBER;
    }
}

class HumiditySensor {
    private final Random random = new Random();

    public int getHumidity() {
        return random.nextInt(101); // 0-100%
    }
}