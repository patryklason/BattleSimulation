package com.simulation;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;


/**
 * @version 1.0.4
 * @author Patryk Lasoń, Hubert Bełkot
 *
 * @ TODO: 24.05.2021 save some important facts to file 
 *
 *<p>Simple war simulation. There are infantry, tank, mobile base, base and trap units which can interact with others. Each unit
 * has it's own features. The program sets the startup parameters from user input and runs simulation by initializing each unit's
 * movement. The simulation ends within set number of iterations. </p>
 */

public class Simulation {
    static int size = 100;
    static int iterations = 100;
    static int infantry = 100;   //values for each team
    static int tanks = 100;
    static int mobiles = 100;
    static int bases = 100;
    static int traps = 100;

    public static void main(String[] args) {
        File file = new File("Results.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
                System.out.println("Plik został utworzony");
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
        if (file.canWrite()){
            try {
                FileWriter fileWriter = new FileWriter(file,false);
                Formatter formatter = new Formatter(fileWriter);
                formatter.format("%s \r\n", "Symulacja wykonana dla danych początkowych:");

                setStartupParams();

                System.out.println("Symulacja się rozpoczęła!");

                Map map = new Map(size);

                UnitCreator unitCreator = new UnitCreator(map, infantry, tanks, mobiles,bases,traps);
                final List<Unit> unitList = unitCreator.getUnitList();

                formatter.format("%s \r\n", "Czas rozpoczęcia: " + LocalDateTime.now());
                formatter.format("%s \r\n", "Rozmiar boku mapy: " + size);
                formatter.format("%s \r\n", "Ilość iteracji: " + iterations);
                formatter.format("%s \r\n", "Liczba jednostek piechoty(dla jednej drużyny): " + infantry);
                formatter.format("%s \r\n", "Liczba czołgów(dla jednej drużyny): " + tanks);
                formatter.format("%s \r\n", "Liczba mobilnych baz: " + mobiles);
                formatter.format("%s \r\n", "Liczba głównych baz: " + bases);
                formatter.format("%s \r\n\r\n", "Ilość pułapek: " + traps);


                for(int i = 0; i < iterations; ++i) {
                    //System.out.println("====================== ITERACJA: " + (i+1) + " ======================");
                    for (Unit unit : unitList) {
                        if (unit instanceof ArmyUnit) {
                            ((ArmyUnit) unit).move(map, unitCreator);
                        }
                        else if(unit instanceof MovingBase){
                        ((MovingBase) unit).move(map, unitCreator);
                        }

                        if(ArmyUnit.getDeadArmy()==1) {
                           formatter.format("%s \r\n", "Jednostka "+ unit.id+" piechoty została zabita w " + (i + 1)+" iteracji");
                                ArmyUnit.setDeadArmy(0);}
                        else if(ArmyUnit.getDeadArmy()==2){
                                formatter.format("%s \r\n", "Czołg "+ unit.id+" został zniszczony w " + (i + 1)+" iteracji");
                                ArmyUnit.setDeadArmy(0);}
                        else if(Trap.getDeadTrap()==3){
                            formatter.format("%s \r\n", "Pułapka "+ unit.id+" została zniszczona w " + (i + 1)+" iteracji");
                            Trap.setDeadTrap(0);
                        }
                    }
                }

                System.out.println();
                System.out.println();
                System.out.println("Jednostek żywych: " + ArmyUnit.getNumOfAliveUnits());
                System.out.println("Żywej piechoty: " + ArmyUnit.getNumOfALiveInfantry());
                System.out.println("Działających czołgów: " + ArmyUnit.getNumOfALiveTanks());
                System.out.println("Śmierci jednostek: " + ((infantry + tanks) * 2 - ArmyUnit.getNumOfAliveUnits()));
                System.out.println("Stoczonych bitw: " + ArmyUnit.getNumOfBattles());
                System.out.println("Wykonanych ataków: " + ArmyUnit.getNumOfAttacks());

                int team1 = ArmyUnit.getNumOfAliveTeam1();
                int team2 = ArmyUnit.getNumOfAliveTeam2();
                String winner;

                if(team1 > team2){
                    System.out.println("Drużyna 1 wygrała (" + team1 + " do " + team2+ ")");
                    winner="Wygrała drużyna 1";}
                else if(team2 > team1){
                    System.out.println("Drużyna 2 wygrała (" + team2 + " do " + team1 + ")");
                    winner="Wygrała drużyna 2";}
                else{
                    System.out.println("Remis (" + team1 + " to " + team2 + ")");
                    winner="Remis";}

                formatter.format("%s \r\n"," ");
                formatter.format("%s \r\n", "Wynik symulacji dla powyższych danych:");
                formatter.format("%s \r\n","Jednostek żywych: " + ArmyUnit.getNumOfAliveUnits());
                formatter.format("%s \r\n", "Żywej piechoty: " + ArmyUnit.getNumOfALiveInfantry());
                formatter.format("%s \r\n", "Działających czołgów: " + ArmyUnit.getNumOfALiveTanks());
                formatter.format("%s \r\n", "Śmierci jednostek: " + ((infantry + tanks) * 2 - ArmyUnit.getNumOfAliveUnits()));
                formatter.format("%s \r\n", "Stoczonych bitw: " + ArmyUnit.getNumOfBattles());
                formatter.format("%s \r\n", "Wykonanych ataków: " + ArmyUnit.getNumOfAttacks());
                formatter.format("%s \r\n", "Rezultat symulacji: " + winner + " (" + Math.max(team1, team2) + " do " + Math.min(team1, team2) + ")");

                formatter.close();
                fileWriter.close();

            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
        Desktop desktop = Desktop.getDesktop();
        if(Desktop.isDesktopSupported()) {
            try {
                desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Błąd otwierania pliku wynikowego!");
            }
        }
        else
            System.out.println("Twój system nie wspiera automatycznego otwarcia pliku! Możesz go znaleźć w folderze" +
                    "programu.");

    }

    static void setStartupParams(){
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        do {
            System.out.print("Proszę podać rozmiar boku mapy (np. dla wartosci 10, mapa to 10x10; max 1000): ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać rozmiar boku mapy (np. dla wartosci 10, mapa to 10x10; max 1000): ");
            }
            int x = scanner.nextInt();
            if (x > 0 && x <= 1000) {
                size = x;
                success = true;
            }
            else {
                System.out.println("Podano nieprawdiłowy rozmiar (1-1000)!");
                scanner.nextLine();
            }
        }while(!success);

        success = false;

        do{
            System.out.print("Proszę podać ilość iteracji (max 1 000 000): ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość iteracji (max 1 000 000): ");
            }
            int x = scanner.nextInt();
            if(x > 0 && x <= 1000000){
                iterations = x;
                success = true;
            }
            else
                System.out.println("Podano nieprawidłową liczbę iteracji (1-1 000 000)!");
        }while(!success);

        success = false;

        do{
            System.out.print("Proszę podać ilość jednostek piechoty (dla jednej drużyny): ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość jednostek piechoty (dla jednej drużyny): ");
            }
            int x = scanner.nextInt();
            infantry = Math.max(x, 0);

            System.out.print("Proszę podać ilość czołgów (dla jednej drużyny): ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość czołgów (dla jednej drużyny): ");
            }
            x = scanner.nextInt();
            tanks = Math.max(x, 0);

            System.out.print("Proszę podać ilość mobilnych baz: ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość mobilnych baz: ");
            }
            x = scanner.nextInt();
            mobiles = Math.max(x, 0);

            System.out.print("Proszę podać ilość głównych baz: ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość głównych baz: ");
            }
            x = scanner.nextInt();
            bases = Math.max(x, 0);

            System.out.print("Proszę podać ilość pułapek: ");
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Wprowadzono nieprawidłową wartość!");
                System.out.print("Proszę podać ilość pułapek: ");
            }
            x = scanner.nextInt();
            traps = Math.max(x, 0);

            int numOfWantedArmy = infantry*2 + tanks*2;
            int numOfWantedNeutrals = mobiles + bases + traps;
            if(numOfWantedArmy > size * size){
                System.out.println("Nie można utworzyć " + numOfWantedArmy + " jednostek wojskowych na mapie " +
                        "o rozmiarze " + size*size);
            }
            else if(numOfWantedNeutrals > size* size){
                System.out.println("Nie można utworzyć " + numOfWantedNeutrals + " jednostek neutralnych na mapie " +
                        "o rozmiarze " + size*size);
            }
            else
                success = true;
        }while(!success);
    }
}
