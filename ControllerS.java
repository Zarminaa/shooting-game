package com.example.demo;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerS implements Initializable {

    // Declaration of JavaFX elements from the FXML file
    @FXML
    private AnchorPane NewPane;
    @FXML
    private ImageView Arena;
    @FXML
    private ImageView Player1; // Represents the image view of Player 1
    @FXML
    private ImageView Player2; // Represents the image view of Player 2
    @FXML
    private Line Division; // Represents the division line separating players
    @FXML
    private ImageView bullet1; // Represents the image view of bullet 1
    @FXML
    private ImageView bullet2; // Represents the image view of bullet 2
    @FXML
    private ProgressBar HP1;
    @FXML
    private ProgressBar HP2;
    @FXML
    private ImageView Victory;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Text Win1;
    @FXML
    private Text Win2;

    // Speed at which players will move
    double playerSpeed = 5.0; // Stores the speed at which players move

    // Width and height of the scene
    double sceneWidth; // Stores the width of the scene
    double sceneHeight; // Stores the height of the scene

    // Initialize method from the Initializable interface
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Allow players to gain focus
        Player1.setFocusTraversable(true);
        Player2.setFocusTraversable(true);

        // Platform.runLater is used to ensure that these operations are executed on the JavaFX Application Thread.
        // This is necessary since the code is dealing with JavaFX UI elements.
        Platform.runLater(() -> {
            // Calculate initial offsets for bullets relative to players
            double bullet1OffsetX = bullet1.getLayoutX() - Player1.getLayoutX();
            double bullet1OffsetY = bullet1.getLayoutY() - Player1.getLayoutY();

            double bullet2OffsetX = bullet2.getLayoutX() - Player2.getLayoutX();
            double bullet2OffsetY = bullet2.getLayoutY() - Player2.getLayoutY();

            // Bind bullets to players with initial offsets
            // These listeners ensure that the bullets move along with the players as they change position.
            // It establishes a relationship between player positions and bullet positions.
            Player1.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                bullet1.setLayoutX(newValue.doubleValue() + bullet1OffsetX);
                // Update the layoutX of bullet1 based on the change in player's layoutX
            });
            Player1.layoutYProperty().addListener((observable, oldValue, newValue) -> {
                bullet1.setLayoutY(newValue.doubleValue() + bullet1OffsetY);
                // Update the layoutY of bullet1 based on the change in player's layoutY
            });

            Player2.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                bullet2.setLayoutX(newValue.doubleValue() + bullet2OffsetX);
                // Update the layoutX of bullet2 based on the change in player's layoutX
            });
            Player2.layoutYProperty().addListener((observable, oldValue, newValue) -> {
                bullet2.setLayoutY(newValue.doubleValue() + bullet2OffsetY);
                // Update the layoutY of bullet2 based on the change in player's layoutY
            });
        });

        // Get the width and height of the scene
        sceneWidth = Player1.getParent().getBoundsInLocal().getWidth();
        sceneHeight = Player1.getParent().getBoundsInLocal().getHeight();

        // Set event handlers for key presses and releases
        Player1.setOnKeyPressed(this::handleKeyPress);
        Player1.setOnKeyReleased(this::handleKeyRelease);
        Player2.setOnKeyPressed(this::handleKeyPress);
        Player2.setOnKeyReleased(this::handleKeyRelease);

        // Start the movement update loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update movement for Player 1 and Player 2
                movePlayer(Player1);
                movePlayer(Player2);
            }
        };

        timer.start(); // Start the timer
    }

    // Array to keep track of which keys are currently pressed
    private boolean[] keys = new boolean[KeyCode.values().length]; // Stores boolean values for each key code

    // Event handler for key presses
    private void handleKeyPress(KeyEvent event) {
        keys[event.getCode().ordinal()] = true; // Sets the corresponding key to true in the array

        // Check if specific keys are pressed for shooting bullets
        if (event.getCode() == KeyCode.Q) {
            moveBullet(bullet1); // Move bullet1 if Q is pressed
        }
        if (event.getCode() == KeyCode.NUMPAD0) {
            moveBullet(bullet2); // Move bullet2 if NUMPAD0 is pressed
        }
    }

    // Event handler for key releases
    private void handleKeyRelease(KeyEvent event) {
        keys[event.getCode().ordinal()] = false; // Sets the corresponding key to false in the array
    }

    // Method to move players
    private void movePlayer(ImageView player) {
        double deltaX = 0; // Stores the change in x-coordinate for the player
        double deltaY = 0; // Stores the change in y-coordinate for the player

        double playerX = player.getLayoutX(); // Current x-coordinate of the player
        double playerY = player.getLayoutY(); // Current y-coordinate of the player

        double playerWidth = player.getBoundsInParent().getWidth(); // Width of the player
        double playerHeight = player.getBoundsInParent().getHeight(); // Height of the player

        // Update movement based on pressed keys for Player 1 and Player 2
        if (player == Player1) {
            if (keys[KeyCode.A.ordinal()]) deltaX -= playerSpeed; // Move left
            if (keys[KeyCode.D.ordinal()]) deltaX += playerSpeed; // Move right
            if (keys[KeyCode.W.ordinal()]) deltaY -= playerSpeed; // Move up
            if (keys[KeyCode.S.ordinal()]) deltaY += playerSpeed; // Move down
        } else if (player == Player2) {
            if (keys[KeyCode.LEFT.ordinal()]) deltaX -= playerSpeed; // Move left
            if (keys[KeyCode.RIGHT.ordinal()]) deltaX += playerSpeed; // Move right
            if (keys[KeyCode.UP.ordinal()]) deltaY -= playerSpeed; // Move up
            if (keys[KeyCode.DOWN.ordinal()]) deltaY += playerSpeed; // Move down
        }

        // Calculate new position for the player while keeping it within the scene bounds
        double newX = keepInScene(playerX + deltaX, 0, sceneWidth - playerWidth); // New x-coordinate
        double newY = keepInScene(playerY + deltaY, 0, sceneHeight - playerHeight); // New y-coordinate

        // Set the new position for the player
        player.setLayoutX(newX);
        player.setLayoutY(newY);

        // Check collision with the division line and revert to previous position if collided
        if (player.getBoundsInParent().intersects(Division.getBoundsInParent())) {
            player.setLayoutX(playerX);
            player.setLayoutY(playerY);
        }
    }

    // Method to keep a value within a specified range
    private double keepInScene(double value, double min, double max) {
        return Math.max(min, Math.min(max, value)); // Ensures value is within min and max bounds
    }

    // Flags to track if transitions are in progress for each bullet
    private boolean isTransitionInProgressBullet1 = false;
    private boolean isTransitionInProgressBullet2 = false;

    // Method to move bullets
    private void moveBullet(ImageView bullet) {
        // Check if a transition is already in progress for the specific bullet
        if ((bullet == bullet1 && isTransitionInProgressBullet1) || (bullet == bullet2 && isTransitionInProgressBullet2)) {
            return; // Exit the method without starting a new transition
        }

        // Set the flag to indicate that a transition is now in progress for the specific bullet
        if (bullet == bullet1) {
            isTransitionInProgressBullet1 = true;

        } else if (bullet == bullet2) {
            isTransitionInProgressBullet2 = true;
        }

        // Store the initial position of the bullet
        double initialX = bullet.getTranslateX();

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), bullet);
        transition.setCycleCount(1);

        // Set the direction and distance for the bullet based on its type
        if (bullet == bullet1) {
            transition.setToX(935); // Move bullet1 to the right
        } else if (bullet == bullet2) {
            transition.setToX(-935); // Move bullet2 to the left
        }

        // Event handler to reset bullet position after transition completes
        transition.setOnFinished(event -> {

            checkBulletCollision( bullet);

            bullet.setTranslateX(initialX);

            // Reset the flag since transition is completed for the specific bullet
            if (bullet == bullet1) {
                isTransitionInProgressBullet1 = false;
            } else if (bullet == bullet2) {
                isTransitionInProgressBullet2 = false;
            }


        });
//System.out.println(bullet.getBoundsInLocal());
        transition.play(); // Start the transition animation

    }

    private void checkBulletCollision(ImageView bullet) {
        Platform.runLater(() -> {
            Bounds bulletBounds = bullet.getBoundsInParent();
            Bounds player1Bounds = Player1.getBoundsInParent();
            Bounds player2Bounds = Player2.getBoundsInParent();

            if (bulletBounds.intersects(player1Bounds)) {
                double currentProgress = HP1.getProgress();
                double newProgress = Math.max(0, currentProgress - 1);
                System.out.println("The new HP1 is: " + newProgress);
                HP1.setProgress(newProgress);
                Victory();
            }

            else if (bulletBounds.intersects(player2Bounds)) {
                double currentProgress = HP2.getProgress();
                double newProgress = Math.max(0, currentProgress - 1);
                System.out.println("The new HP2 is: " + newProgress);
                HP2.setProgress(newProgress);
                Victory();
            }
        });
    }



    // Victory message display
    private void Victory() {
        if (HP1.getProgress() == 0) {
            Victory.setVisible(true);
            Win1.setVisible(true);
        } else if (HP2.getProgress() == 0) {
            Victory.setVisible(true);
            Win2.setVisible(true);
        }
    }
}

