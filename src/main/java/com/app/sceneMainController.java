package com.app;

import com.simulation.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * processes user interaction with main menu
 */
public class sceneMainController implements Initializable {

    @FXML
    private Button mainBtnStart;
    @FXML
    private Button mainBtnParams;
    @FXML
    private Button mainBtnOptions;

    /**
     * initializes Main scene
     * @param location default JavaFX argument
     * @param resources default JavaFX argument
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainBtnStart.setOnAction(event -> { Simulation.simulation(); changeSceneToProgress();});
        mainBtnOptions.setOnAction(event -> changeSceneToOptions());
        mainBtnParams.setOnAction(event -> changeSceneToParams());

    }

    /**
     * changes current scene
     */
    @FXML
    private void changeSceneToProgress(){
        try{
            Parent rootProgress = FXMLLoader.load(getClass().getResource("sceneProgress.fxml"));
            Stage stage = (Stage) mainBtnStart.getScene().getWindow();
            stage.getScene().setRoot(rootProgress);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * changes current scene
     */
    @FXML
    private void changeSceneToOptions(){
        try {
            Parent rootOptions = FXMLLoader.load(getClass().getResource("sceneOptions.fxml"));
            Stage stage = (Stage) mainBtnOptions.getScene().getWindow();
            stage.getScene().setRoot(rootOptions);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * changes current scene
     */
    @FXML
    private void changeSceneToParams(){
        try {
            Parent rootParams = FXMLLoader.load(getClass().getResource("sceneParams.fxml"));
            Stage stage = (Stage) mainBtnParams.getScene().getWindow();
            stage.getScene().setRoot(rootParams);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
