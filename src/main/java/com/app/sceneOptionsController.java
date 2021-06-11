package com.app;

import com.simulation.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * processes user options choices. User can decide what will be saved to result file
 */
public class sceneOptionsController implements Initializable {

    @FXML
    private Button optionsBtnBack;
    @FXML
    private CheckBox fileInfantry;
    @FXML
    private CheckBox fileTanks;
    @FXML
    private CheckBox fileTraps;
    @FXML
    private CheckBox fileMobiles;
    @FXML
    private CheckBox fileStats;

    /**
     * initializes Options scene
     * @param location default JavaFX argument
     * @param resources default JavaFX argument
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optionsBtnBack.setOnAction(event -> changeSceneToMain());
        fileInfantry.setOnAction(event -> setCheckBox(fileInfantry, 0));
        fileTanks.setOnAction(event -> setCheckBox(fileTanks, 1));
        fileTraps.setOnAction(event -> setCheckBox(fileTraps, 2));
        fileMobiles.setOnAction(event -> setCheckBox(fileMobiles, 3));
        fileStats.setOnAction(event -> setCheckBox(fileStats, 4));

        printCheckBoxes();
    }

    /**
     * sets the opposite value of specific file saving option. (True -> False, False -> True)
     * @param cb specific checkBox
     * @param i index of option that will be changed
     */
    private void setCheckBox(CheckBox cb, int i ){
        if(cb.isSelected()) {
            Simulation.setuFileChoice(true, i);
        }
        else if(!cb.isSelected()) {
            Simulation.setuFileChoice(false, i);
        }
    }

    /**
     * prints current values of parameters and checkboxes for that parameters
     * Used to refresh the visible values
     */
    private void printCheckBoxes(){
        boolean [] options = new boolean[5];
        for(int i = 0; i < 5; ++i)
            options[i] = Simulation.getuFileChoice(i);

        fileInfantry.setSelected(options[0]);
        fileTanks.setSelected(options[1]);
        fileTraps.setSelected(options[2]);
        fileMobiles.setSelected(options[3]);
        fileStats.setSelected(options[4]);
    }

    /**
     * changes current scene
     */
    @FXML
    private void changeSceneToMain(){
        try{
            Parent rootMain = FXMLLoader.load(getClass().getResource("sceneMain.fxml"));
            Stage stage = (Stage) optionsBtnBack.getScene().getWindow();
            stage.getScene().setRoot(rootMain);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
