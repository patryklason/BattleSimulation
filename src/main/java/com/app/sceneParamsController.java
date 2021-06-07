package com.app;
import com.simulation.Simulation;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

public class sceneParamsController implements Initializable {

    @FXML
    private Button paramsBtnBack;
    @FXML
    private Label paramsInfo;
    @FXML
    private Label freeArmyFields;
    @FXML
    private Label freeNeutralFields;


    @FXML
    private Label mapVal;
    @FXML
    private TextField mapInput;
    @FXML
    private Button mapConfirm;

    @FXML
    private Label iterationsVal;
    @FXML
    private TextField iterationsInput;
    @FXML
    private Button iterationsConfirm;

    @FXML
    private Label infantryVal;
    @FXML
    private TextField infantryInput;
    @FXML
    private Button infantryConfirm;

    @FXML
    private Label tanksVal;
    @FXML
    private TextField tanksInput;
    @FXML
    private Button tanksConfirm;

    @FXML
    private Label mobilesVal;
    @FXML
    private TextField mobilesInput;
    @FXML
    private Button mobilesConfirm;

    @FXML
    private Label basesVal;
    @FXML
    private TextField basesInput;
    @FXML
    private Button basesConfirm;

    @FXML
    private Label trapsVal;
    @FXML
    private TextField trapsInput;
    @FXML
    private Button trapsConfirm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paramsBtnBack.setOnAction(event -> changeSceneToMain());
        mapConfirm.setOnAction(event -> changeMapSize());
        iterationsConfirm.setOnAction(event -> changeIterations());
        infantryConfirm.setOnAction(event -> changeInfantry());
        tanksConfirm.setOnAction(event -> changeTanks());
        mobilesConfirm.setOnAction(event -> changeMobiles());
        basesConfirm.setOnAction(event -> changeBases());
        trapsConfirm.setOnAction(event -> changeTraps());

        paramsInfo.setStyle("-fx-text-fill: black");
        paramsInfo.setText("Podaj nowa wartosc, a nastepnie kliknij zmien");

        printParams();

        TextField [] txtFields = new TextField[7];
        txtFields[0] = mapInput;
        txtFields[1] = iterationsInput;
        txtFields[2] = infantryInput;
        txtFields[3] = tanksInput;
        txtFields[4] = mobilesInput;
        txtFields[5] = basesInput;
        txtFields[6] = trapsInput;

        DecimalFormat format = new DecimalFormat( "#.0" );
        for(TextField txt : txtFields){
            txt.setTextFormatter(new TextFormatter<>(c -> {
                if(c.getControlNewText().isEmpty()){
                    return c;
                }

                ParsePosition parsePosition = new ParsePosition( 0 );
                Object object = format.parse( c.getControlNewText(), parsePosition );

                if(object == null || parsePosition.getIndex() < c.getControlNewText().length()){
                    return null;
                }
                else
                    return c;
            }));
        }
    }

    private void printParams(){
        Integer sizeI = Simulation.getSize();
        Integer iterationsI = Simulation.getIterations();
        Integer infantryI = Simulation.getInfantry();
        Integer tanksI = Simulation.getTanks();
        Integer mobilesI = Simulation.getMobiles();
        Integer basesI = Simulation.getBases();
        Integer trapsI = Simulation.getTraps();
        Integer armyFields = sizeI*sizeI - infantryI - tanksI;
        Integer neutralFields = sizeI*sizeI - mobilesI - basesI - trapsI;

        mapVal.setText(sizeI.toString());
        iterationsVal.setText(iterationsI.toString());
        infantryVal.setText(infantryI.toString());
        tanksVal.setText(tanksI.toString());
        mobilesVal.setText(mobilesI.toString());
        basesVal.setText(basesI.toString());
        trapsVal.setText(trapsI.toString());

        freeArmyFields.setText(armyFields.toString());
        freeNeutralFields.setText(neutralFields.toString());
    }

    private void changeMapSize(){
        int oldSize = Simulation.getSize();
        try {
            Integer input = Integer.parseInt(mapInput.getText());
            if(input == oldSize){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana wartosc jest aktualna");
            }
            else if(input >= 10 && input <=1000){
                Simulation.setSize(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Rozmiar mapy pomyslnie zmieniono na " + input.toString());
                mapVal.setText(input.toString());
                if(input < oldSize && (Simulation.getInfantry() + Simulation.getTanks() > input*input
                        || Simulation.getMobiles() + Simulation.getBases() + Simulation.getTraps() > input*input)) {
                    Simulation.recalculateParams();
                }
                printParams();
            }
            else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna wielkosc boku mapy (10 - 1000)");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna wielkosc boku mapy (10 - 1000)");
        }
    }

    private void changeIterations(){
        try {
            Integer input = Integer.parseInt(iterationsInput.getText());

            if(input == Simulation.getIterations()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana wartosc jest aktualna");
            }
            else if (input >= 1 && input <= 100000) {
                Simulation.setIterations(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc iteracji pomyslnie zmieniono na " + input.toString());
                iterationsVal.setText(input.toString());
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe iteracji (1 - 100 000)");
            }
        }catch(Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe iteracji (1 - 100 000)");
        }
    }

    private void changeInfantry(){
        try{
            Integer input = Integer.parseInt(infantryInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getInfantry()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getTanks()){
                Simulation.setInfantry(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc piechoty pomyslnie zmieniono na " + input.toString());
                infantryVal.setText(input.toString());
                printParams();
            }else if(input > size*size - Simulation.getTanks()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba piechoty jest za duza");
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe piechoty");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe piechoty");
        }
    }

    private void changeTanks(){
        try{
            Integer input = Integer.parseInt(tanksInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getTanks()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getInfantry()){
                Simulation.setTanks(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc czolgow pomyslnie zmieniono na " + input.toString());
                tanksVal.setText(input.toString());
                printParams();
            }else if(input > size*size - Simulation.getInfantry()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba czolgow jest za duza");
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe czolgow");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe czolgow");
        }
    }

    private void changeMobiles(){
        try{
            Integer input = Integer.parseInt(mobilesInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getMobiles()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getBases() - Simulation.getTraps()){
                Simulation.setMobiles(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc mobilnych baz pomyslnie zmieniono na " + input.toString());
                mobilesVal.setText(input.toString());
                printParams();
            }else if(input > size*size - Simulation.getBases() - Simulation.getTraps()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba mobilnych baz jest za duza");
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe mobilnych baz");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe mobilnych baz");
        }
    }

    private void changeBases(){
        try{
            Integer input = Integer.parseInt(basesInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getBases()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getMobiles() - Simulation.getTraps()){
                Simulation.setBases(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc baz pomyslnie zmieniono na " + input.toString());
                basesVal.setText(input.toString());
                printParams();
            }else if(input > size*size - Simulation.getMobiles() - Simulation.getTraps()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba baz jest za duza");
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe baz");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe baz");
        }
    }

    private void changeTraps(){
        try{
            Integer input = Integer.parseInt(trapsInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getTraps()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getMobiles() - Simulation.getBases()){
                Simulation.setTraps(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc pulapek pomyslnie zmieniono na " + input.toString());
                trapsVal.setText(input.toString());
                printParams();
            }else if(input > size*size - Simulation.getMobiles() - Simulation.getBases()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba pulapek jest za duza");
            }else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna liczbe pulapek");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna liczbe pulapek");
        }
    }

    @FXML
    private void changeSceneToMain(){
        try{
            Parent rootMain = FXMLLoader.load(getClass().getResource("sceneMain.fxml"));
            Stage stage = (Stage) paramsBtnBack.getScene().getWindow();
            stage.getScene().setRoot(rootMain);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
