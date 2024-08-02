package com.example.demo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private ImageView BigSparkle;
    @FXML
    private ImageView SmallSparkle1;
    @FXML
    private ImageView SmallSparkle2;
    @FXML
    private ImageView SmallSparkle3;
    @FXML
    private ImageView SmallSparkle4;
    @FXML
    private ImageView SmallSparkle5;
    @FXML
    private ImageView LittleRobot;
    @FXML
    private ImageView SpecialSparkle;
    @FXML
    private Button Start;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Set initial visibility of LittleRobot to false
        LittleRobot.setVisible(false);

        // Add mouse event filter to track mouse movements over the scene
        LittleRobot.getParent().addEventFilter(MouseEvent.MOUSE_MOVED, this::onMouseMoved);

        // Create a RotateTransition for BigSparkle
        RotateTransition rotation = new RotateTransition(Duration.seconds(10), BigSparkle);
        rotation.setByAngle(360);
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.play();



        // Create a RotateTransition for SmallSparkles
        RotateTransition rotation1 = new RotateTransition(Duration.seconds(5), SmallSparkle1);
        rotation1.setByAngle(360);
        rotation1.setCycleCount(RotateTransition.INDEFINITE);
        rotation1.play();

        RotateTransition rotation2 = new RotateTransition(Duration.seconds(5), SmallSparkle2);
        rotation2.setByAngle(360);
        rotation2.setCycleCount(RotateTransition.INDEFINITE);
        rotation2.play();

        RotateTransition rotation3 = new RotateTransition(Duration.seconds(5), SmallSparkle3);
        rotation3.setByAngle(360);
        rotation3.setCycleCount(RotateTransition.INDEFINITE);
        rotation3.play();

        RotateTransition rotation4 = new RotateTransition(Duration.seconds(5), SmallSparkle4);
        rotation4.setByAngle(360);
        rotation4.setCycleCount(RotateTransition.INDEFINITE);
        rotation4.play();

        RotateTransition rotation5 = new RotateTransition(Duration.seconds(5), SmallSparkle5);
        rotation5.setByAngle(360);
        rotation5.setCycleCount(RotateTransition.INDEFINITE);
        rotation5.play();
        // Create a RotateTransition for SpecialSparkle
        RotateTransition rotationS = new RotateTransition(Duration.seconds(5), SpecialSparkle);
        rotationS.setByAngle(360);
        rotationS.setCycleCount(RotateTransition.INDEFINITE);
        rotationS.play();


        //the extra miles i will have to take for this stupid special hidden button:(

    }

    private void onMouseMoved(MouseEvent event) {
        // Get the position of the cursor
        double x = event.getX();
        double y = event.getY();

        // Check if the cursor is within the scene
        if (x >= 0 && x <= LittleRobot.getParent().getBoundsInLocal().getWidth() &&
                y >= 0 && y <= LittleRobot.getParent().getBoundsInLocal().getHeight()) {
            // Set the position of the LittleRobot ImageView to match the cursor position
            LittleRobot.setLayoutX(x);
            LittleRobot.setLayoutY(y);

            // Set visibility to true
            LittleRobot.setVisible(true);
        } else {
            // If cursor is not within the scene, hide the LittleRobot ImageView
            LittleRobot.setVisible(false);
        }

    }

    @FXML
    private void handleStartButtonClick(ActionEvent event) {
        // Perform the desired action when the button is clicked

        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewScene.fxml"));
            Parent root = loader.load();

            // Create a new Scene with the loaded root node
            Scene scene2 = new Scene(root);

            // Create a new Stage
            Stage newStage = new Stage();

            // Set the scene to the new stage
            newStage.setScene(scene2);
            newStage.setResizable(false);
            newStage.getIcons().add(new Image(getClass().getResourceAsStream("rifle.png")));

            // Close the current stage
            Stage currentStage = (Stage) BigSparkle.getScene().getWindow();
            currentStage.close();

            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



