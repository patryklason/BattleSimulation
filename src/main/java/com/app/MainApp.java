package com.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rootMain = FXMLLoader.load(getClass().getResource("sceneMain.fxml"));

        Scene sceneMain = new Scene(rootMain);

        primaryStage.setTitle("Symulacja Wojska");
        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
