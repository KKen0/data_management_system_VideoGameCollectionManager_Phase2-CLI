/**
 * Ken Amin
 * CEN 3024C - Software Development I CRN: 23586
 * Date: March 23, 2026
 * Class: GameManager
 *
 * This class manages the collection of Game objects in the Video Game Manager
 * application. It handles loading games from a text file, saving games back to
 * the file, adding new games, removing games, searching for games, updating
 * game information, and generating reports such as the backlog report.
 */

import java.util.*;
import java.io.*;

public class GameManager {

    // List that stores all game objects
    private List<Game> games;
    // Name of the data file
    private String fileName;

    /**
     * Constructor: GameManager
     * Purpose: Initializes the manager and loads games from the file.
     */
    public GameManager(String fileName) {
        this.fileName = fileName;
        games = new ArrayList<>();
        loadGames();
    }

    /**
     * Method: loadGames
     * Purpose: Reads the text file and loads game data into memory.
     * Return: boolean
     */
    private boolean loadGames() {

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                return true;
            }

            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {

                String line = sc.nextLine().trim();

                if(line.isEmpty()) {
                    continue;
                }

                String[] p = line.split(",");

                if(p.length != 8) {
                    continue;
                }

                Game g = new Game(
                        Integer.parseInt(p[0].trim()),
                        p[1].trim(),
                        p[2].trim(),
                        p[3].trim(),
                        Double.parseDouble(p[4].trim()),
                        Double.parseDouble(p[5].trim()),
                        Boolean.parseBoolean(p[6].trim()),
                        Integer.parseInt(p[7].trim())
                );

                games.add(g);
            }

            sc.close();
            return true;

        } catch(Exception e) {
            System.out.println("Error loading file.");
            return false;
        }
    }

    /**
     * Method: saveGames
     * Purpose: Saves all games to the text file.
     * Return: boolean
     */
    private boolean saveGames() {

        try {
            PrintWriter writer = new PrintWriter(fileName);

            for(Game g : games) {
                writer.println(g.toFileString());
            }

            writer.close();
            return true;

        } catch(Exception e) {
            System.out.println("Error saving file.");
            return false;
        }
    }

    /**
     * Method: getAllGames
     * Purpose: Returns the entire game list.
     * Return: List<Game>
     */
    public List<Game> getAllGames() {
        return games;
    }

    /**
     * Method: addGame
     * Purpose: Adds a new game if ID does not already exist.
     * Parameter: game
     * Return: boolean
     */
    public boolean addGame(Game game) {

        if(findGame(game.getGameId()) != null) {
            return false;
        }

        games.add(game);
        saveGames();
        return true;
    }

    /**
     * Method: removeGame
     * Purpose: Removes a game based on ID.
     * Parameter: id
     * Return: boolean
     */
    public boolean removeGame(int id) {

        Iterator<Game> iterator = games.iterator();

        while(iterator.hasNext()) {
            Game g = iterator.next();

            if(g.getGameId() == id) {
                iterator.remove();
                saveGames();
                return true;
            }
        }

        return false;
    }

    /**
     * Method: updateGameTitle
     * Purpose: Updates a game's title.
     * Parameters: id, newTitle
     * Return: boolean
     */
    public boolean updateGameTitle(int id, String newTitle) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updateTitle(newTitle)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGamePlatform
     * Purpose: Updates a game's platform.
     * Parameters: id, newPlatform
     * Return: boolean
     */
    public boolean updateGamePlatform(int id, String newPlatform) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updatePlatform(newPlatform)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGameGenre
     * Purpose: Updates a game's genre.
     * Parameters: id, newGenre
     * Return: boolean
     */
    public boolean updateGameGenre(int id, String newGenre) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updateGenre(newGenre)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGamePrice
     * Purpose: Updates a game's purchase price.
     * Parameters: id, newPrice
     * Return: boolean
     */
    public boolean updateGamePrice(int id, double newPrice) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updatePrice(newPrice)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGameHours
     * Purpose: Updates a game's hours played.
     * Parameters: id, newHours
     * Return: boolean
     */
    public boolean updateGameHours(int id, double newHours) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updateHours(newHours)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGameCompleted
     * Purpose: Updates a game's completion status.
     * Parameters: id, status
     * Return: boolean
     */
    public boolean updateGameCompleted(int id, boolean status) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updateCompleted(status)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: updateGameYear
     * Purpose: Updates a game's release year.
     * Parameters: id, newYear
     * Return: boolean
     */
    public boolean updateGameYear(int id, int newYear) {
        Game game = findGame(id);

        if(game == null) {
            return false;
        }

        if(!game.updateYear(newYear)) {
            return false;
        }

        saveGames();
        return true;
    }

    /**
     * Method: findGame
     * Purpose: Searches for a game using its ID.
     * Parameter: id
     * Return: Game
     */
    public Game findGame(int id) {

        for(Game g : games) {
            if(g.getGameId() == id) {
                return g;
            }
        }

        return null;
    }

    /**
     * Method: backlogReport
     * Purpose: Returns all games that are not completed.
     * Return: List<Game>
     */
    public List<Game> backlogReport() {

        List<Game> backlog = new ArrayList<>();

        for(Game g : games) {
            if(!g.isCompleted()) {
                backlog.add(g);
            }
        }

        return backlog;
    }
}