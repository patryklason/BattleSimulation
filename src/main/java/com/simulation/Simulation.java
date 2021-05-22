package com.simulation;

import java.util.*;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 *
 * @implNote army units can spawn on traps!
 *
 */

public class Simulation {

    public static void main(String[] args) {
        int size = 100;
        Map map = new Map(size);
        final List<List<Field>> mapList = map.getMapList();

        int iterations = 1000;
        int infantry = 4000;   //values for each team
        int tanks = 1000;
        int mobiles = 1000;
        int bases = 500;
        int traps = 1000;
        int numOfWantedArmy = infantry*2 + tanks*2;
        int numOfWantedNeutrals = mobiles + bases + traps;
        if(numOfWantedArmy > map.size* map.size){
            System.out.println("Cannot spawn " + numOfWantedArmy + " army units on "
                    + map.size + "x" + map.size + " map!");
            return;
        }
        else if(numOfWantedNeutrals > map.size* map.size){
            System.out.println("Cannot spawn " + numOfWantedNeutrals + " neutral units on "
                    + map.size + "x" + map.size + " map!");
            return;
        }

        UnitCreator unitCreator = new UnitCreator(map, infantry, tanks, mobiles,bases,traps);
        final List<Unit> unitList = unitCreator.getUnitList();

        //System.out.println(unitList);
        //System.out.println(mapList);

        System.out.println("Created Units: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("Created Infantry: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("Created Tanks: " + ArmyUnit.getNumOfALiveTanks());


        for(int i = 0; i < iterations; ++i) {
            System.out.println("==================================== ITERATION: " + (i+1) + "======================");
            for (Unit unit : unitList) {
                if (unit instanceof ArmyUnit) {
                    ArmyUnit armyUnit = (ArmyUnit) unit;
                    armyUnit.move(map, unitCreator);
                }
                else if(unit instanceof MovingBase){
                MovingBase movingBase = (MovingBase) unit;
                movingBase.move(map, unitCreator);
                }
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Units left: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("infantry left: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("tanks left: " + ArmyUnit.getNumOfALiveTanks());
        System.out.println("Army units deaths: " + ((infantry + tanks) * 2 - ArmyUnit.getNumOfAliveUnits()));
        System.out.println("amount of battles: " + ArmyUnit.getNumOfBattles());
        System.out.println("amount of attacks: " + ArmyUnit.getNumOfAttacks());

        int team1 = ArmyUnit.getNumOfAliveTeam1();
        int team2 = ArmyUnit.getNumOfAliveTeam2();

        if(team1 > team2)
            System.out.println("Team 1 won the war (" + team1 + " to " + team2+ ")");
        else if(team2 > team1)
            System.out.println("Team 2 won the war (" + team2 + " to " + team1 + ")");
        else
            System.out.println("draw! (" + team1 + " to " + team2 + ")");
    }
}
