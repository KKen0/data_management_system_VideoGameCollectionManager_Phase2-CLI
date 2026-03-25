/**
 * Ken Amin
 * CEN 3024C - Software Development I CRN: 23586
 * Date: March 23, 2026
 * Class: VGMApp
 *
 * This class contains the main method and acts as the user interface for the
 * Video Game Manager application. It displays the menu, processes user input,
 * and interacts with the GameManager class to perform operations such as
 * adding, removing, updating, and viewing games.
 */

import java.util.*;
import java.io.File;

public class VGMApp {

    private GameManager manager;
    private Scanner scanner;

    /**
     * Constructor: VGMApp
     * Parameters: none
     * Return: none
     * Purpose: Initializes the scanner and asks the user for the text file name.
     */
    public VGMApp() {
        scanner = new Scanner(System.in);
        String fileName = requestFileName();
        manager = new GameManager(fileName);
    }

    /**
     * Method: requestFileName
     * Parameters: none
     * Return: String
     * Purpose: Prompts the user to enter a valid .txt file name for storing data.
     */
    private String requestFileName() {

        while(true) {

            String fileName = readNonEmptyString("Enter data file name (.txt only): ");

            if(!fileName.toLowerCase().endsWith(".txt")) {
                System.out.println("Only .txt files are allowed.");
                continue;
            }

            File file = new File(fileName);

            if(!file.exists()) {
                System.out.println("File does not exist. Please try again.");
                continue;
            }

            return fileName;
        }
    }

    /**
     * Method: run
     * Parameters: none
     * Return: boolean
     * Purpose: Controls the main program loop and processes user menu selections.
     */
    public boolean run() {

        boolean running = true;

        while(running) {

            displayMenu();
            int choice = readIntInRange("Choose option: ", 1, 6);

            switch(choice) {

                case 1:
                    displayGames();
                    break;

                case 2:
                    addGame();
                    break;

                case 3:
                    removeGame();
                    break;

                case 4:
                    updateGame();
                    break;

                case 5:
                    backlog();
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting program.");
                    break;
            }
        }

        return true;
    }

    /**
     * Method: displayMenu
     * Parameters: none
     * Return: boolean
     * Purpose: Displays the application menu options.
     */
    private boolean displayMenu() {

        System.out.println("\n===== Video Game Manager =====");
        System.out.println("1. Display Games");
        System.out.println("2. Add Game");
        System.out.println("3. Remove Game");
        System.out.println("4. Update Game");
        System.out.println("5. Backlog Report");
        System.out.println("6. Exit");

        return true;
    }

    /**
     * Method: displayGames
     * Parameters: none
     * Return: boolean
     * Purpose: Displays all games currently stored in the system.
     */
    private boolean displayGames() {

        List<Game> games = manager.getAllGames();

        if(games.isEmpty()) {
            System.out.println("No games found.");
            return true;
        }

        for(Game g : games) {
            System.out.println(g);
        }

        return true;
    }

    /**
     * Method: addGame
     * Parameters: none
     * Return: boolean
     * Purpose: Prompts the user for game details and adds a new game.
     */
    private boolean addGame() {

        int id;

        while(true) {
            id = readPositiveInt("Game ID: ");

            if(manager.findGame(id) != null) {
                System.out.println("That ID already exists. Enter a different ID.");
            } else {
                break;
            }
        }

        String title = readNonEmptyString("Title: ");
        String platform = readNonEmptyString("Platform: ");
        String genre = readNonEmptyString("Genre: ");
        double price = readNonNegativeDouble("Purchase Price: ");
        double hours = readNonNegativeDouble("Hours Played: ");
        boolean completed = readBoolean("Completed (true/false): ");
        int year = readYear("Release Year: ");

        Game game = new Game(id, title, platform, genre, price, hours, completed, year);

        if(manager.addGame(game)) {
            System.out.println("Game added.");
            System.out.println(game);
        } else {
            System.out.println("Game could not be added.");
        }

        return true;
    }

    /**
     * Method: removeGame
     * Parameters: none
     * Return: boolean
     * Purpose: Removes a game based on the ID entered by the user.
     */
    private boolean removeGame() {

        int id = promptForExistingGameId("Enter Game ID to remove: ");

        if(manager.removeGame(id)) {
            System.out.println("Game removed.");
        } else {
            System.out.println("Game not found.");
        }

        return true;
    }

    /**
     * Method: updateGame
     * Parameters: none
     * Return: boolean
     * Purpose: Updates any field for a specific game.
     */
    private boolean updateGame() {

        int id = promptForExistingGameId("Enter Game ID to update: ");
        Game game = manager.findGame(id);

        System.out.println("\nSelected Game:");
        System.out.println(game);

        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Title");
        System.out.println("2. Platform");
        System.out.println("3. Genre");
        System.out.println("4. Purchase Price");
        System.out.println("5. Hours Played");
        System.out.println("6. Completed Status");
        System.out.println("7. Release Year");

        int choice = readIntInRange("Choose field: ", 1, 7);
        boolean updated = false;

        switch(choice) {

            case 1:
                updated = manager.updateGameTitle(id, readNonEmptyString("New title: "));
                break;

            case 2:
                updated = manager.updateGamePlatform(id, readNonEmptyString("New platform: "));
                break;

            case 3:
                updated = manager.updateGameGenre(id, readNonEmptyString("New genre: "));
                break;

            case 4:
                updated = manager.updateGamePrice(id, readNonNegativeDouble("New purchase price: "));
                break;

            case 5:
                updated = manager.updateGameHours(id, readNonNegativeDouble("New hours played: "));
                break;

            case 6:
                updated = manager.updateGameCompleted(id, readBoolean("Completed (true/false): "));
                break;

            case 7:
                updated = manager.updateGameYear(id, readYear("New release year: "));
                break;
        }

        if(updated) {
            System.out.println("Game updated.");
            System.out.println(manager.findGame(id));
        } else {
            System.out.println("Update failed.");
        }

        return true;
    }

    /**
     * Method: backlog
     * Parameters: none
     * Return: boolean
     * Purpose: Displays all games that have not yet been completed.
     */
    private boolean backlog() {

        List<Game> backlog = manager.backlogReport();

        if(backlog.isEmpty()) {
            System.out.println("No backlog games.");
            return true;
        }

        for(Game g : backlog) {
            System.out.println(g);
        }

        return true;
    }

    /**
     * Method: promptForExistingGameId
     * Parameters: prompt
     * Return: int
     * Purpose: Repeatedly asks for a game ID until a valid existing ID is entered.
     */
    private int promptForExistingGameId(String prompt) {

        while(true) {
            int id = readPositiveInt(prompt);

            if(manager.findGame(id) != null) {
                return id;
            }

            System.out.println("Game ID not found. Please try again.");
        }
    }

    /**
     * Method: readInt
     * Parameters: msg
     * Return: int
     * Purpose: Reads a valid integer from the user.
     */
    private int readInt(String msg) {

        while(true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch(Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    /**
     * Method: readPositiveInt
     * Parameters: msg
     * Return: int
     * Purpose: Reads a valid positive integer from the user.
     */
    private int readPositiveInt(String msg) {

        while(true) {
            int value = readInt(msg);

            if(value > 0) {
                return value;
            }

            System.out.println("Value must be greater than 0.");
        }
    }

    /**
     * Method: readIntInRange
     * Parameters: msg, min, max
     * Return: int
     * Purpose: Reads an integer within a specific range.
     */
    private int readIntInRange(String msg, int min, int max) {

        while(true) {
            int value = readInt(msg);

            if(value >= min && value <= max) {
                return value;
            }

            System.out.println("Enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Method: readDouble
     * Parameters: msg
     * Return: double
     * Purpose: Reads a valid decimal number from the user.
     */
    private double readDouble(String msg) {

        while(true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(scanner.nextLine().trim());
            }
            catch(Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    /**
     * Method: readNonNegativeDouble
     * Parameters: msg
     * Return: double
     * Purpose: Reads a non-negative decimal number.
     */
    private double readNonNegativeDouble(String msg) {

        while(true) {
            double value = readDouble(msg);

            if(value >= 0) {
                return value;
            }

            System.out.println("Value cannot be negative.");
        }
    }

    /**
     * Method: readNonEmptyString
     * Parameters: msg
     * Return: String
     * Purpose: Reads a non-empty string from the user.
     */
    private String readNonEmptyString(String msg) {

        while(true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if(!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be blank.");
        }
    }

    /**
     * Method: readBoolean
     * Parameters: msg
     * Return: boolean
     * Purpose: Reads true or false from the user.
     */
    private boolean readBoolean(String msg) {

        while(true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(input);
            }

            System.out.println("Enter true or false.");
        }
    }

    /**
     * Method: readYear
     * Parameters: msg
     * Return: int
     * Purpose: Reads a valid release year from the user.
     */
    private int readYear(String msg) {

        while(true) {
            int year = readInt(msg);

            if(year >= 1950 && year <= 2100) {
                return year;
            }

            System.out.println("Enter a valid year between 1950 and 2100.");
        }
    }

    public static void main(String[] args) {
        VGMApp app = new VGMApp();
        app.run();
    }
}