import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private static final int NUM_CATEGORIES = 2;
    private static final int NUM_QUESTIONS_PER_CATEGORY = 3;
    private static final int MAX_ROUNDS = 3;
    private static final int ROUND_TIME_LIMIT_SECONDS = 15;

    private Question[][] questions;
    private boolean[][] questionAnswered;
    private int[] categoryPoints;
    private int currentRound;
    private int currentPlayer;
    private GridPane gridPane;
    private BorderPane borderPane;
    private VBox welcomePageLayout;
    private Label titleLabel;
    private Button startButton;
    private VBox triviaQuestionPane;
    private Label questionLabel;
    private Button[] answerButtons;
    private HBox pointsPane;
    private Label[] playerPointLabels;
    private Stage primaryStage;
    private VBox endingPageLayout;
    private Label winnerLabel;
    private Button playAgainButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeGame();

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jeopardy Game");

        createWelcomePage();
        createEndingPage(); // Call the method to create the ending page layout

        primaryStage.show();
    }

    private void initializeGame() {
        // Initialize the questions and their corresponding points
        questions = new Question[NUM_CATEGORIES][NUM_QUESTIONS_PER_CATEGORY];
        questions[0][0] = new Question("Capital Cities", "What is the capital of France?", new String[]{"London", "Paris", "Berlin", "Rome"}, 1, 100);
        questions[0][1] = new Question("Capital Cities", "What is the capital of Japan?", new String[]{"Beijing", "Tokyo", "Seoul", "Bangkok"}, 1, 200);
        questions[0][2] = new Question("Capital Cities", "What is the capital of Brazil?", new String[]{"Buenos Aires", "Brasilia", "Santiago", "Lima"}, 1, 300);

        questions[1][0] = new Question("Animals", "What is the largest land animal?", new String[]{"African Elephant", "Giraffe", "Hippopotamus", "Rhinoceros"}, 0, 100);
        questions[1][1] = new Question("Animals", "What animal is known as the 'king of the jungle'?", new String[]{"Tiger", "Lion", "Leopard", "Cheetah"}, 1, 200);
        questions[1][2] = new Question("Animals", "What flightless bird is native to Antarctica?", new String[]{"Ostrich", "Emperor Penguin", "Kiwi", "Cassowary"}, 1, 300);

        // Initialize the questionAnswered array to keep track of whether a question has been answered.
        questionAnswered = new boolean[NUM_CATEGORIES][NUM_QUESTIONS_PER_CATEGORY];

        // Initialize the categoryPoints array to store the points earned in each category.
        categoryPoints = new int[NUM_CATEGORIES];

        currentRound = 1;
        currentPlayer = 0;
    }

    private void createWelcomePage() {
        titleLabel = new Label("Welcome to Jeopardy!");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 24px; -fx-padding: 10px 20px;");

        startButton.setOnAction(e -> {
            borderPane.getChildren().removeAll(welcomePageLayout);
            createGameBoard();
        });

        welcomePageLayout = new VBox(20);
        welcomePageLayout.getChildren().addAll(titleLabel, startButton);
        welcomePageLayout.setPadding(new Insets(150, 0, 0, 200));

        borderPane.setCenter(welcomePageLayout);
    }

    private void createGameBoard() {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #0054A4;");

        // Create labels for categories
        Label[] categoryLabels = new Label[NUM_CATEGORIES];
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            categoryLabels[i] = new Label("Category " + (i + 1) + ": " + questions[i][0].getCategory());
            categoryLabels[i].setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");
            gridPane.add(categoryLabels[i], i, 0);
        }

        for (int i = 0; i < NUM_CATEGORIES; i++) {
            for (int j = 0; j < NUM_QUESTIONS_PER_CATEGORY; j++) {
                Button questionButton = createQuestionButton(i, j);
                gridPane.add(questionButton, i, j + 1); // Start from row 1 to skip category labels
            }
        }

        // Create a label to display the current round
        Label roundLabel = new Label("Round: " + currentRound);
        roundLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create a label to display the current player
        Label currentPlayerLabel = new Label("Current Player: " + (currentPlayer + 1));
        currentPlayerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Create a label to display the player points
        pointsPane = new HBox(20);
        pointsPane.setPadding(new Insets(10));
        playerPointLabels = new Label[NUM_CATEGORIES];
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            playerPointLabels[i] = new Label("Player " + (i + 1) + ": " + categoryPoints[i] + " points");
            playerPointLabels[i].setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
            pointsPane.getChildren().add(playerPointLabels[i]);
        }

        // Create a VBox to hold the game board, points information, and current player label
        VBox gameBoardLayout = new VBox(20);
        gameBoardLayout.getChildren().addAll(roundLabel, currentPlayerLabel, gridPane, pointsPane);

        // Set the game board layout to the center of the borderPane
        borderPane.setCenter(gameBoardLayout);
    }

    private Button createQuestionButton(int category, int questionNumber) {
        Button questionButton = new Button("Question " + (questionNumber + 1) + " - " + questions[category][questionNumber].getPoints() + " points");
        questionButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-color: white;");
        questionButton.setOnAction(e -> handleQuestionButtonClick(category, questionNumber));
        return questionButton;
    }

    private void handleQuestionButtonClick(int category, int questionNumber) {
        if (!questionAnswered[category][questionNumber]) {
            Question question = questions[category][questionNumber];
            questionAnswered[category][questionNumber] = true;

            // Open a new window with the trivia question
            openTriviaQuestionWindow(question);
        }
    }

    private void openTriviaQuestionWindow(Question question) {
        // Create a new VBox to display the trivia question
        triviaQuestionPane = new VBox(20);
        triviaQuestionPane.setPadding(new Insets(50));
        triviaQuestionPane.setStyle("-fx-background-color: #f0f0f0;");
    
        // Add the question text label to the VBox
        questionLabel = new Label("Question: " + question.getQuestionText());
        questionLabel.setStyle("-fx-font-size: 20px;");
    
        // Create a label to display the current round
        Label roundLabel = new Label("Round: " + currentRound);
        roundLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
    
        // Create a label to display the current player
        Label currentPlayerLabel = new Label("Current Player: " + (currentPlayer + 1));
        currentPlayerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
    
        // Create a label to display the player points
        pointsPane = new HBox(20);
        pointsPane.setPadding(new Insets(10));
        playerPointLabels = new Label[NUM_CATEGORIES];
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            playerPointLabels[i] = new Label("Player " + (i + 1) + ": " + categoryPoints[i] + " points");
            playerPointLabels[i].setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
            pointsPane.getChildren().add(playerPointLabels[i]);
        }
    
        // Add the roundLabel, currentPlayerLabel, player points, and questionLabel to the VBox
        triviaQuestionPane.getChildren().addAll(roundLabel, currentPlayerLabel, pointsPane, questionLabel);
    
        // Create buttons for the answers
        answerButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button(question.getChoices()[i]);
            answerButtons[i].setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
            // Set event handler to check if the answer is correct
            int buttonIndex = i;
            answerButtons[i].setOnAction(e -> handleAnswerButtonClick(buttonIndex, question));
            triviaQuestionPane.getChildren().add(answerButtons[i]);
        }
    
        borderPane.setCenter(triviaQuestionPane);
    }

    private void handleAnswerButtonClick(int answerIndex, Question question) {
        if (!question.isAnsweredCorrectly()) {
            questionAnswered[currentPlayer][answerIndex] = true;

            if (question.isCorrectAnswer(answerIndex)) {
                question.markAnsweredCorrectly();
                categoryPoints[currentPlayer] += question.getPoints();
                updatePointsPane();

                // Show a correct answer message
                showAlert("Correct!", "You earned " + question.getPoints() + " points.");
            } else {
                // Incorrect answer
                question.markAnsweredIncorrectly();
                categoryPoints[currentPlayer] -= question.getPoints();
                updatePointsPane();

                // Show an incorrect answer message
                showAlert("Incorrect!", "You lost " + question.getPoints() + " points.");
            }
        }

        // Check if all questions are answered or maximum rounds reached to end the game
        if (isAllQuestionsAnswered() || currentRound > MAX_ROUNDS) {
            endGame();
        } else {
            // Switch to the other player
            currentPlayer = (currentPlayer + 1) % NUM_CATEGORIES;

            // Update the current player label
            Label currentPlayerLabel = new Label("Current Player: " + (currentPlayer + 1));
            currentPlayerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
            ((VBox) borderPane.getCenter()).getChildren().set(1, currentPlayerLabel);

            borderPane.setCenter(gridPane);
        }
    }

    private boolean isAllQuestionsAnswered() {
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            for (int j = 0; j < NUM_QUESTIONS_PER_CATEGORY; j++) {
                if (!questionAnswered[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame() {
        // Determine the winner and display the result on the ending page
        int winnerIndex = categoryPoints[0] > categoryPoints[1] ? 0 : 1;
        String winnerMessage = "Player " + (winnerIndex + 1) + " wins with a score of " + categoryPoints[winnerIndex] + " points!";
        winnerLabel.setText(winnerMessage);

        borderPane.setCenter(endingPageLayout);
    }

    private void createEndingPage() {
        winnerLabel = new Label();
        winnerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        playAgainButton.setOnAction(e -> {
            borderPane.getChildren().removeAll(endingPageLayout);
            initializeGame();
            createGameBoard();
            currentRound = 1;
            currentPlayer = 0;
        });

        endingPageLayout = new VBox(20);
        endingPageLayout.getChildren().addAll(winnerLabel, playAgainButton);
        endingPageLayout.setPadding(new Insets(150, 0, 0, 200));
    }

    private void updatePointsPane() {
        // Update the points pane with the latest scores for each player
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            playerPointLabels[i].setText("Player " + (i + 1) + ": " + categoryPoints[i] + " points");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
