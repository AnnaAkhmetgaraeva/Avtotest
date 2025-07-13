import org.example.Homework7.PlayerServiceJSON;
import org.example.Homework7.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

public class PositiveTests {
    private PlayerServiceJSON playerService;
    private File testDataFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        testDataFile = tempDir.resolve("test_players.json").toFile();
        playerService = new PlayerServiceJSON(testDataFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (testDataFile.exists()) {
            testDataFile.delete();
        }
    }

    @Test
    @DisplayName("1. Добавить игрока и проверить его наличие в списке")
    void addPlayerAndCheckPresence() {
        // Создаем игрока
        int playerId = playerService.createPlayer("TestPlayer");

        // Получаем список игроков
        Collection<Player> players = playerService.getPlayers();

        // Проверяем, что игрок есть в списке
        assertThat(players)
                .extracting(Player::getId)
                .contains(playerId);

        // Проверяем информацию о игроке
        Player player = playerService.getPlayerById(playerId);
        assertThat(player)
                .isNotNull()
                .hasFieldOrPropertyWithValue("nick", "TestPlayer")
                .hasFieldOrPropertyWithValue("points", 0)
                .hasFieldOrPropertyWithValue("isOnline", true);
    }

    @Test
    @DisplayName("2. Добавить и удалить игрока, проверить отсутствие в списке")
    void addAndDeletePlayer() {
        int playerId = playerService.createPlayer("ToDelete");

        // Удаляем игрока
        Player deletedPlayer = playerService.deletePlayer(playerId);
        assertThat(deletedPlayer).isNotNull();

        // Проверяем, что игрока больше нет
        assertThat(playerService.getPlayerById(playerId)).isNull();
        assertThat(playerService.getPlayers()).doesNotContain(deletedPlayer);
    }

    @Test
    @DisplayName("3. Добавить игрока (когда нет JSON-файла)")
    void addPlayerWhenNoJsonFile() {
        assertThat(testDataFile.exists()).isFalse();

        int playerId = playerService.createPlayer("NewPlayer");
        assertThat(playerId).isPositive();

        assertThat(testDataFile.exists()).isTrue();
    }

    @Test
    @DisplayName("4. Добавить игрока (когда есть JSON-файл)")
    void addPlayerWhenJsonFileExists() {
        // Сначала создаем файл с одним игроком
        playerService.createPlayer("Existing");
        assertThat(testDataFile.exists()).isTrue();

        // Добавляем еще одного игрока
        int newPlayerId = playerService.createPlayer("NewPlayer1");

        // Проверяем, что оба игрока есть в системе
        assertThat(playerService.getPlayers())
                .extracting(Player::getNick)
                .contains("Existing", "NewPlayer1");
    }

    @Test
    @DisplayName("5. Начислить баллы существующему игроку")
    void addPointsToExistingPlayer() {
        int playerId = playerService.createPlayer("PointsPlayer");

        // Начисляем 100 очков
        int newPoints = playerService.addPoints(playerId, 100);
        assertThat(newPoints).isEqualTo(100);

        // Проверяем обновленные данные
        Player player = playerService.getPlayerById(playerId);
        assertThat(player.getPoints()).isEqualTo(100);
    }

    @Test
    @DisplayName("6. Добавить очков поверх существующих")
    void addPointsToExistingPoints() {
        int playerId = playerService.createPlayer("MultiPoints");
        playerService.addPoints(playerId, 50);

        // Добавляем еще 30 очков
        int newPoints = playerService.addPoints(playerId, 30);
        assertThat(newPoints).isEqualTo(80);

        Player player = playerService.getPlayerById(playerId);
        assertThat(player.getPoints()).isEqualTo(80);
    }

    @Test
    @DisplayName("7. Получить игрока по id")
    void getPlayerById() {
        int playerId = playerService.createPlayer("GetByIdPlayer");

        Player player = playerService.getPlayerById(playerId);
        assertThat(player)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", playerId)
                .hasFieldOrPropertyWithValue("nick", "GetByIdPlayer");
    }

    @Test
    @DisplayName("8. Проверить корректность сохранения в файл")
    void checkFileSaving() {
        int playerId1 = playerService.createPlayer("SavePlayer1");
        int playerId2 = playerService.createPlayer("SavePlayer2");

        // Создаем новый сервис с тем же файлом для проверки загрузки
        PlayerServiceJSON newService = new PlayerServiceJSON(testDataFile.getAbsolutePath());

        assertThat(newService.getPlayers())
                .extracting(Player::getId)
                .containsExactlyInAnyOrder(playerId1, playerId2);
    }

    @Test
    @DisplayName("9. Проверить корректность загрузки json-файла")
    void checkJsonFileLoading() {
        // Создаем 3 игроков
        int playerId1 = playerService.createPlayer("LoadPlayer1");
        int playerId2 = playerService.createPlayer("LoadPlayer2");
        int playerId3 = playerService.createPlayer("LoadPlayer3");

        // Создаем новый сервис с тем же файлом
        PlayerServiceJSON newService = new PlayerServiceJSON(testDataFile.getAbsolutePath());

        // Проверяем, что все игроки загрузились
        Collection<Player> loadedPlayers = newService.getPlayers();
        assertThat(loadedPlayers)
                .extracting(Player::getId)
                .containsExactlyInAnyOrder(playerId1, playerId2, playerId3);

        // Проверяем, что данные не повредились
        Player loadedPlayer = newService.getPlayerById(playerId2);
        assertThat(loadedPlayer)
                .hasFieldOrPropertyWithValue("nick", "LoadPlayer2")
                .hasFieldOrPropertyWithValue("points", 0)
                .hasFieldOrPropertyWithValue("isOnline", true);
    }

    @Test
    @DisplayName("10. Проверить уникальность id")
    void checkUniqueIdGeneration() {
        // Создаем 5 игроков
        for (int i = 1; i <= 5; i++) {
            playerService.createPlayer("Player" + i);
        }

        // Удаляем 3-го игрока
        playerService.deletePlayer(3);

        // Добавляем нового игрока
        int newPlayerId = playerService.createPlayer("NewPlayer");

        // Проверяем, что новый ID = 6, а не 3
        assertThat(newPlayerId).isEqualTo(6);
    }

    @Test
    @DisplayName("11. Запросить список игроков (когда нет json-файла)")
    void getPlayersWhenNoJsonFile() {
        assertThat(testDataFile.exists()).isFalse();

        Collection<Player> players = playerService.getPlayers();
        assertThat(players).isEmpty();
    }

    @Test
    @DisplayName("12. Проверить создание игрока с 15 символами")
    void createPlayerWith15Characters() {
        String longNickname = "A".repeat(15);
        int playerId = playerService.createPlayer(longNickname);

        Player player = playerService.getPlayerById(playerId);
        assertThat(player.getNick()).hasSize(15).isEqualTo(longNickname);
    }
}
