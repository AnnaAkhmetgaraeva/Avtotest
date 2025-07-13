import org.example.Homework7.PlayerServiceJSON;
import org.example.Homework7.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

public class NegativeTests {
    private PlayerServiceJSON playerService;
    private File testDataFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        testDataFile = tempDir.resolve("test_players_negative.json").toFile();
        playerService = new PlayerServiceJSON(testDataFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (testDataFile.exists()) {
            testDataFile.delete();
        }
    }

    @Test
    @DisplayName("1. Удалить игрока, которого нет")
    void deleteNonExistentPlayer() {
        // Создаем несколько игроков (id 1, 2)
        playerService.createPlayer("Player1");
        playerService.createPlayer("Player2");

        // Пытаемся удалить несуществующего игрока
        Player deleted = playerService.deletePlayer(10);
        assertThat(deleted).isNull();

        // Проверяем, что существующие игроки не затронуты
        assertThat(playerService.getPlayers()).hasSize(2);
    }

    @Test
    @DisplayName("2. Создать дубликат (имя уже занято)")
    void createDuplicatePlayer() {
        playerService.createPlayer("UniquePlayer");

        // Пытаемся создать игрока с таким же именем
        assertThatThrownBy(() -> playerService.createPlayer("UniquePlayer"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nickname is already in use");
    }

    @Test
    @DisplayName("3. Получить игрока по несуществующему id")
    void getPlayerByNonExistentId() {
        Player player = playerService.getPlayerById(999);
        assertThat(player).isNull();
    }

    @Test
    @DisplayName("4. Сохранить игрока с пустым ником")
    void createPlayerWithEmptyNick() {
        assertThatThrownBy(() -> playerService.createPlayer(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nickname cannot be empty");

        assertThatThrownBy(() -> playerService.createPlayer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nickname cannot be empty");
    }

    @Test
    @DisplayName("5. Начислить отрицательное число очков")
    void addNegativePoints() {
        int playerId = playerService.createPlayer("PointsPlayer");

        assertThatThrownBy(() -> playerService.addPoints(playerId, -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Points cannot be negative");
    }

    @Test
    @DisplayName("6. Начислить очки несуществующему игроку")
    void addPointsToNonExistentPlayer() {
        int result = playerService.addPoints(999, 100);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("7. Начислить очки без указания id (некорректный id)")
    void addPointsWithInvalidId() {
        // В данном случае метод просто вернет -1, так что тест немного другой
        int result = playerService.addPoints(-1, 100);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("8. Ввести невалидный id (String)")
    void testInvalidIdType() {
        // Этот тест требует изменения интерфейса, так как текущий интерфейс принимает только int
        // Можно протестировать через рефлексию или изменить интерфейс
        // В текущей реализации этот тест не применим
    }

    @Test
    @DisplayName("9. Проверить загрузку системы с другим json-файлом")
    void loadWithDifferentJsonFile(@TempDir Path tempDir) throws Exception {
        // Создаем "битый" JSON-файл
        File badFile = tempDir.resolve("bad_json.json").toFile();
        java.nio.file.Files.write(badFile.toPath(), "{invalid json}".getBytes());

        // Пытаемся загрузить
        PlayerServiceJSON badService = new PlayerServiceJSON(badFile.getAbsolutePath());

        // Проверяем, что система не упала и список игроков пуст
        assertThat(badService.getPlayers()).isEmpty();
    }

    @Test
    @DisplayName("10. Начислить 1.5 балла игроку")
    void addFractionalPoints() {
        // В текущей реализации points - int, так что этот тест не применим
        // Можно добавить проверку, что нельзя передать дробное число
        int playerId = playerService.createPlayer("FractionPlayer");

        // Этот тест проверяет, что система не принимает дробные числа на уровне компиляции
        // playerService.addPoints(playerId, 1.5); // не скомпилируется
    }

    @Test
    @DisplayName("11. Проверить загрузку json-файла с дубликатами")
    void loadJsonWithDuplicates(@TempDir Path tempDir) throws Exception {
        // Правильный формат JSON массива
        String jsonWithDuplicates = """
    [
        {"id":1,"nick":"Player1","points":0,"online":true},
        {"id":1,"nick":"Player1Duplicate","points":10,"online":false}
    ]""";

        File dupFile = tempDir.resolve("duplicates.json").toFile();
        Files.write(dupFile.toPath(), jsonWithDuplicates.getBytes());

        PlayerServiceJSON dupService = new PlayerServiceJSON(dupFile.getAbsolutePath());

        // Проверяем, что остался только последний игрок с ID=1
        assertThat(dupService.getPlayers())
                .hasSize(1)
                .extracting(Player::getNick)
                .containsExactly("Player1Duplicate");

        // Дополнительная проверка данных
        Player player = dupService.getPlayerById(1);
        assertThat(player)
                .isNotNull()
                .hasFieldOrPropertyWithValue("points", 10)
                .hasFieldOrPropertyWithValue("online", false);
    }

    @Test
    @DisplayName("12. Проверить создание игрока с 16 символами")
    void createPlayerWith16Characters() {
        String tooLongNickname = "A".repeat(16);

        assertThatThrownBy(() -> playerService.createPlayer(tooLongNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nickname is too long");
    }
}