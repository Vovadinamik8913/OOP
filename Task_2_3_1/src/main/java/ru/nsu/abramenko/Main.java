package ru.nsu.abramenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("snake/view/game.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.lookup("#gameCanvas").requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
