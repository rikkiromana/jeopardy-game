# Jeopardy Game

## Overview
Welcome to the Jeopardy Game! This is a GUI-based Jeopardy game developed using JavaFX, where players can select questions from different categories and earn points by answering them correctly.

## How to Run the Game
Before running the game, ensure you have the following prerequisites installed on your system:

- Java Development Kit (JDK) version 8 or later
- JavaFX SDK (included with JDK 8 and 11, or available as a separate package for later versions)

### Running the Game
1. Clone or download the project repository to your local machine.

2. Open a terminal or command prompt and navigate to the root directory of the project.

3. Compile the Java files using the following command:<br>
javac --module-path /path/to/javafx-sdk-VERSION/lib --add-modules javafx.controls,javafx.fxml *.java
Note: Replace `/path/to/javafx-sdk-VERSION` with the actual path to the JavaFX SDK on your system.

4. Once the Java files are successfully compiled, run the game using the following command:<br>
java --module-path /path/to/javafx-sdk-VERSION/lib --add-modules javafx.controls,javafx.fxml App
Again, replace `/path/to/javafx-sdk-VERSION` with the actual path to the JavaFX SDK on your system.

## Game Concept and Rules
**Concept**: Creating a GUI-based Jeopardy game with JavaFX where players can select questions from different categories and earn points by answering them correctly.

**Rules**:
- The game will have a set of predefined categories, each with multiple questions of varying point values.
- The game board will display categories and point values like the Jeopardy TV show.
- Two Players will take turns selecting a category and point value.
- Once a question is selected, a new window will open with the trivia question.
- The player must respond with their answer.
- If the player answers correctly, they earn the selected points. Otherwise, points are deducted.
- The game continues until all questions have been answered or until a specific number of rounds.
- The player with the highest score at the end of the game wins.

## How to Play
1. The game will start with a welcome screen where you can click the "Start Game" button to begin.

2. The main game board will be displayed, showing different categories and point values. Each player takes turns selecting a category and point value by clicking on the corresponding button.

3. After selecting a question, a new window will open with the trivia question. The player must respond with their answer before the timer runs out.

4. If the player answers correctly, they earn the selected points. Otherwise, points are deducted.

5. The game continues until all questions have been answered or after a specific number of rounds.

6. At the end of the game, the final scores will be displayed, and the winner will be announced.

7. To play again, you can click the "Play Again" button on the ending page.

## Game Customization
- You can customize the categories and questions by modifying the `initializeGame()` method in the `App.java` file. Simply change the category names, questions, and answers to suit your preferences.

## Styling and UI Enhancements
- The game board, trivia question window, buttons, and other UI components have been designed to be visually appealing and user-friendly.


Enjoy the Jeopardy Game and have fun testing your knowledge! If you encounter any issues or have suggestions for improvements, feel free to reach out. Happy playing!
