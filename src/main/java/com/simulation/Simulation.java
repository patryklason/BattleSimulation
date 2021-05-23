package com.simulation;

import java.util.*;


/**
 * @version 1.0.3
 * @author Patryk Lasoń, Hubert Bełkot
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

        setStartupParams();
        Map map = new Map(size);

        UnitCreator unitCreator = new UnitCreator(map, infantry, tanks, mobiles,bases,traps);
        final List<Unit> unitList = unitCreator.getUnitList();


        for(int i = 0; i < iterations; ++i) {
            System.out.println("====================== ITERACJA: " + (i+1) + " ======================");
            for (Unit unit : unitList) {
                if (unit instanceof ArmyUnit) {
                    ((ArmyUnit) unit).move(map, unitCreator);
                }
                else if(unit instanceof MovingBase){
                ((MovingBase) unit).move(map, unitCreator);
                }
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("Jednostek żywych: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("Żywej piechoty: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("Działających czołgów: " + ArmyUnit.getNumOfALiveTanks());
        System.out.println("Śmierci jednostek: " + ((infantry + tanks) * 2 - ArmyUnit.getNumOfAliveUnits()));
        System.out.println("stoczonych bitw: " + ArmyUnit.getNumOfBattles());
        System.out.println("wykonanych ataków: " + ArmyUnit.getNumOfAttacks());

        int team1 = ArmyUnit.getNumOfAliveTeam1();
        int team2 = ArmyUnit.getNumOfAliveTeam2();

        if(team1 > team2)
            System.out.println("Drużyna 1 wygrała (" + team1 + " do " + team2+ ")");
        else if(team2 > team1)
            System.out.println("Drużyna 2 wygrała (" + team2 + " do " + team1 + ")");
        else
            System.out.println("Remis (" + team1 + " to " + team2 + ")");
    }

    static void setStartupParams(){
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        do {
            System.out.print("Proszę podać rozmiar boku mapy (np. dla wartosci 10, mapa to 10x10; max 1000): ");
            int x = scanner.nextInt();
            if (x > 0 && x <= 1000) {
                size = x;
                success = true;
            }
            else
                System.out.println("Podano nieprawdiłowy rozmiar (1-1000)!");
        }while(!success);

        success = false;

        do{
            System.out.print("Proszę podać ilość iteracji (max 1 000 000): ");
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
            int x = scanner.nextInt();
            infantry = Math.max(x, 0);

            System.out.print("Proszę podać ilość czołgów (dla jednej drużyny): ");
            x = scanner.nextInt();
            tanks = Math.max(x, 0);

            System.out.print("Proszę podać ilość mobilnych baz: ");
            x = scanner.nextInt();
            mobiles = Math.max(x, 0);

            System.out.print("Proszę podać ilość głównych baz: ");
            x = scanner.nextInt();
            bases = Math.max(x, 0);

            System.out.print("Proszę podać ilość pułapek: ");
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
