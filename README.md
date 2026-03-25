# data_management_system_VideoGameCollectionManager_Phase2-CLI

## Overview
The Video Game Manager is a Java console-based application that allows users to manage a collection of video games using a text file for storage. The program supports adding, removing, updating, displaying, and tracking games in a simple menu-driven interface.

This project was developed for **CEN 3024C – Software Development I** as part of the **DMS Project Phase 2** assignment.

---

# Features
- Load game data from a `.txt` file
- Display all stored games
- Add a new game with input validation
- Remove a game by ID
- Update any field of a game
- Generate a backlog report for unfinished games
- Save all changes back to the text file
- JUnit testing for core system features

---

# Game Data Stored
Each game record contains:
- Game ID
- Title
- Platform
- Genre
- Purchase Price
- Hours Played
- Completion Status
- Release Year

---

# Project Files
- `Game.java` – represents a video game object
- `GameManager.java` – manages game records and file operations
- `VGMApp.java` – contains the main user interface and program flow
- `GameManagerTest.java` – contains JUnit test cases
- `videogamesdata.txt` – sample data file used by the application

---

# Improvements in Phase 2
This phase focused on improving usability, validation, and testing based on instructor feedback.

### Fixes made:
- Input validation now happens as each field is entered
- Duplicate game IDs are caught immediately
- Invalid IDs during update are re-prompted until valid
- Users can now update **any field** of a game
- Program handles invalid menu and input values without crashing
- JUnit tests were added for major functions

---

# Technologies Used
- Java
- IntelliJ IDEA
- JUnit 5
- Text file storage

---

# How to Run
1. Open the project in **IntelliJ IDEA**
2. Make sure your data file (such as `videogamesdata.txt`) is in the project folder
3. Run `VGMApp.java`
4. Enter the `.txt` file name when prompted
5. Use the menu options to manage game records

---

# Running the Tests
1. Open the project in IntelliJ
2. Navigate to `GameManagerTest.java`
3. Right-click the file
4. Select **Run 'GameManagerTest'**

The tests verify:
- File loading
- Adding a game
- Removing a game
- Updating game fields
- Finding a game
- Backlog report generation

---

# Example Data Format
Each line in the text file should follow this format:

```text
1,Minecraft,PC,Sandbox,29.99,120.5,true,2011
2,Elden Ring,PS5,RPG,59.99,45.0,false,2022
3,Stardew Valley,Switch,Simulation,14.99,80.0,false,2016
