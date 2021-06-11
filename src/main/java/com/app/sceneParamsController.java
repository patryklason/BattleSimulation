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

/**
 * processes user changes of simulation parameters
 */
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

    /**
     * initializes Params scene
     * @param location default JavaFX argument
     * @param resources default JavaFX argument
     */
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

    /**
     * prints current simulation parameters values
     */
    private void printParams(){
        int sizeI = Simulation.getSize();
        int iterationsI = Simulation.getIterations();
        int infantryI = Simulation.getInfantry();
        int tanksI = Simulation.getTanks();
        int mobilesI = Simulation.getMobiles();
        int basesI = Simulation.getBases();
        int trapsI = Simulation.getTraps();
        int armyFields = sizeI*sizeI - infantryI - tanksI;
        int neutralFields = sizeI*sizeI - mobilesI - basesI - trapsI;

        mapVal.setText(Integer.toString(sizeI));
        iterationsVal.setText(Integer.toString(iterationsI));
        infantryVal.setText(Integer.toString(infantryI));
        tanksVal.setText(Integer.toString(tanksI));
        mobilesVal.setText(Integer.toString(mobilesI));
        basesVal.setText(Integer.toString(basesI));
        trapsVal.setText(Integer.toString(trapsI));

        freeArmyFields.setText(Integer.toString(armyFields));
        freeNeutralFields.setText(Integer.toString(neutralFields));
    }

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the mapSize value if it does.
     */
    private void changeMapSize(){
        int oldSize = Simulation.getSize();
        try {
            int input = Integer.parseInt(mapInput.getText());

            if(input % 2 == 1){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Wartosc musi byc parzysta!");
                return;
            }
            if(input == oldSize){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana wartosc jest aktualna");
            }
            else if(input >= 10 && input <=1000){
                Simulation.setSize(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Rozmiar mapy pomyslnie zmieniono na " + input);
                mapVal.setText(Integer.toString(input));
                if(input < oldSize && (Simulation.getInfantry()+Simulation.getTanks() > input*input))
                    Simulation.recalculateArmyParams();
                if(input < oldSize && (Simulation.getMobiles() + Simulation.getBases()
                        + Simulation.getTraps() > input*input))
                    Simulation.recalculateNeutralParams();

                printParams();
            }
            else{
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podano niepoprawna wielkosc boku mapy (10 - 1000)");
            }
        }catch (Exception e){
            e.printStackTrace();
            paramsInfo.setStyle("-fx-text-fill: red");
            paramsInfo.setText("Podano niepoprawna wartosc boku mapy (10 - 1000, l. parzysta)");
        }
    }

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the iterations value if it does.
     */
    private void changeIterations(){
        try {
            int input = Integer.parseInt(iterationsInput.getText());

            if(input == Simulation.getIterations()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana wartosc jest aktualna");
            }
            else if (input >= 1 && input <= 100000) {
                Simulation.setIterations(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc iteracji pomyslnie zmieniono na " + input);
                iterationsVal.setText(Integer.toString(input));
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

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the infantry value if it does.
     */
    private void changeInfantry(){
        try{
            int input = Integer.parseInt(infantryInput.getText());
            int size = Simulation.getSize();

            if(input % 2 == 1){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Wartosc musi byc parzysta!");
                return;
            }
            if(input == Simulation.getInfantry()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getTanks()){
                Simulation.setInfantry(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc piechoty pomyslnie zmieniono na " + input);
                infantryVal.setText(Integer.toString(input));
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

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the tanks value if it does.
     */
    private void changeTanks(){
        try{
            int input = Integer.parseInt(tanksInput.getText());
            int size = Simulation.getSize();

            if(input % 2 == 1){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Wartosc musi byc parzysta!");
                return;
            }
            if(input == Simulation.getTanks()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getInfantry()){
                Simulation.setTanks(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc czolgow pomyslnie zmieniono na " + input);
                tanksVal.setText(Integer.toString(input));
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

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the mobiles value if it does.
     */
    private void changeMobiles(){
        try{
            int input = Integer.parseInt(mobilesInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getMobiles()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getBases() - Simulation.getTraps()){
                Simulation.setMobiles(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc mobilnych baz pomyslnie zmieniono na " + input);
                mobilesVal.setText(Integer.toString(input));
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

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the bases value if it does.
     */
    private void changeBases(){
        try{
            int input = Integer.parseInt(basesInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getBases()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getMobiles() - Simulation.getTraps()){
                Simulation.setBases(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc baz pomyslnie zmieniono na " + input);
                basesVal.setText(Integer.toString(input));
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

    /**
     * hadles user interaction with the app. Checks if the user input matches specific conditions and
     * sets the traps value if it does.
     */
    private void changeTraps(){
        try{
            int input = Integer.parseInt(trapsInput.getText());
            int size = Simulation.getSize();
            if(input == Simulation.getTraps()){
                paramsInfo.setStyle("-fx-text-fill: red");
                paramsInfo.setText("Podana liczba jest aktualna");
            }
            else if(input >= 0 && input <= size*size - Simulation.getMobiles() - Simulation.getBases()){
                Simulation.setTraps(input);
                paramsInfo.setStyle("-fx-text-fill: green");
                paramsInfo.setText("Ilosc pulapek pomyslnie zmieniono na " + input);
                trapsVal.setText(Integer.toString(input));
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

    /**
     * changes current scene
     */
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
