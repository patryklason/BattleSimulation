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
 * displays information after simulation finishes
 */
public class sceneProgressController implements Initializable {

    @FXML
    private Button progressBtnBack;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        progressBtnBack.setOnAction(event -> changeSceneToMain());

    }

    private void changeSceneToMain(){
        try{
            Parent rootMain = FXMLLoader.load(getClass().getResource("sceneMain.fxml"));
            Stage stage = (Stage) progressBtnBack.getScene().getWindow();
            stage.getScene().setRoot(rootMain);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
