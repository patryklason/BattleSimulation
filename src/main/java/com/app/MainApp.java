package com.app;

import com.simulation.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * main class for application
 */
public class MainApp extends Application{

    /**
     * loads application with visible frame and stages
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rootMain = FXMLLoader.load(getClass().getResource("sceneMain.fxml"));

        Scene sceneMain = new Scene(rootMain);

        sceneMain.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Symulacja Wojny");
        primaryStage.getIcons().add(new Image("https://www.forbot.pl/forum/upload_img/obrazki/IMG_4e1f007b384ab9814.jpg")); // XD
        primaryStage.setScene(sceneMain);
        primaryStage.show();
        primaryStage.setResizable(false);

        Simulation.recalculateParams();
    }

    /**
     * starts application
     * @param args default Java argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}
