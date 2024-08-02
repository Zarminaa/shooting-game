package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            URL fxmlUrl = getClass().getResource("Sample.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find Sample.fxml");
            }
            FXMLLoader loader1 = new FXMLLoader(fxmlUrl);
            Parent root1 = loader1.load();

            // Set the FXML file's root as the content of the scene
            Scene scene1 = new Scene(root1);

            // Set the custom icon
            URL iconUrl = getClass().getResource("rifle.png");
            if (iconUrl == null) {
                throw new IOException("Cannot find rifle.png");
            }
            primaryStage.getIcons().add(new Image(iconUrl.openStream()));

            primaryStage.setResizable(false);
            primaryStage.setScene(scene1);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading resources: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
