/**
 * Kaveen Amin
 * CEN 3024C - Software Development I CRN: 23586
 * Date: March 24, 2026
 * Class: GameManagerTEst
 *
 * This class contains JUnit tests for the GameManager class.
 * It verifies that the system can correctly load file data,
 * add games, remove games, update game information,
 * search for games, and generate the backlog report.
 */


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    // Temporary file used only for unit testing
    private static final String TEST_FILE = "test_games.txt";
    private GameManager manager;

    /**
     * Method: setUp
     * Purpose: Creates a fresh test file before each test and loads it into GameManager.
     * Return: none
     */
    @BeforeEach
    void setUp() throws Exception {
        PrintWriter writer = new PrintWriter(TEST_FILE);
        writer.println("1,Minecraft,PC,Sandbox,29.99,120.5,true,2011");
        writer.println("2,Elden Ring,PS5,RPG,59.99,45.0,false,2022");
        writer.println("3,Stardew Valley,Switch,Simulation,14.99,80.0,false,2016");
        writer.close();

        //Load file into the manager
        manager = new GameManager(TEST_FILE);
    }

    /**
     * Method: tearDown
     * Purpose: Deletes the temporary file after each test finishes.
     * Return: none
     */
    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Method: addGame
     * Purpose: Verifies that a new game can be added successfully
     * Return: None
     */
    @Test
    void addGame() {
        Game newGame = new Game(4, "Cyberpunk 2077", "PC", "RPG", 49.99, 25.0, false, 2020);

        boolean result = manager.addGame(newGame);

        assertTrue(result);
        assertEquals(4, manager.getAllGames().size());
        assertNotNull(manager.findGame(4));
        assertEquals("Cyberpunk 2077", manager.findGame(4).getTitle());
    }

    /**
     * Method: removeGame
     * Purpose: Verifies that an existing game can be removed successfully.
     * Return: none
     */
    @Test
    void removeGame() {
        boolean result = manager.removeGame(2);

        assertTrue(result);
        assertNull(manager.findGame(2));
        assertEquals(2, manager.getAllGames().size());
    }

    /**
     * Method: updateGameTitle
     * Purpose: Verifies that a game's title can be updated successfully.
     * Return: none
     */
    @Test
    void updateGameTitle() {
        boolean result = manager.updateGameTitle(1, "Minecraft Deluxe");

        assertTrue(result);
        assertEquals("Minecraft Deluxe", manager.findGame(1).getTitle());
    }

    /**
     * Method: updateGameHours
     * Purpose: Verifies that a game's hours played can be updated successfully.
     * Return: none
     */
    @Test
    void updateGameHours() {
        boolean result = manager.updateGameHours(2, 100.0);

        assertTrue(result);
        assertEquals(100.0, manager.findGame(2).getHoursPlayed());
    }

    /**
     * Method: updateGameCompleted
     * Purpose: Verifies that a game's completion status can be updated successfully.
     * Return: none
     */
    @Test
    void updateGameCompleted() {
        boolean result = manager.updateGameCompleted(3, true);

        assertTrue(result);
        assertTrue(manager.findGame(3).isCompleted());
    }

    /**
     * Method: findGame
     * Purpose: Verifies that a game can be found by its ID.
     * Return: none
     */
    @Test
    void findGame() {
        Game game = manager.findGame(1);

        assertNotNull(game);
        assertEquals("Minecraft", game.getTitle());
    }

    /**
     * Method: backlogReport
     * Purpose: Verifies that the backlog report returns only games
     * that are not completed.
     * Return: none
     */
    @Test
    void backlogReport() {
        List<Game> backlog = manager.backlogReport();

        assertEquals(2, backlog.size());
        assertEquals(2, backlog.get(0).getGameId());
        assertEquals(3, backlog.get(1).getGameId());
    }

    /**
     * Method: fileLoadsCorrectly
     * Purpose: Verifies that game data is loaded correctly from the file.
     * Return: none
     */
    @Test
    void fileLoadsCorrectly() {
        assertEquals(3, manager.getAllGames().size());
        assertEquals("Minecraft", manager.findGame(1).getTitle());
        assertEquals("Elden Ring", manager.findGame(2).getTitle());
    }
}