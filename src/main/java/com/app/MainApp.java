package com.app;

import com.simulation.Simulation;
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

        sceneMain.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Symulacja Wojny");
        primaryStage.setScene(sceneMain);
        primaryStage.show();

        Simulation.recalculateParams();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
