package com.simulation;

import java.util.*;


/**
 * @version 1.0.1
 * @author Patryk Lason, Hubert Belkot
 *
 * @implNote army units can spawn on traps!
 * @// TODO: 30.04.2021 implement neutral units movement and interaction
 */

public class Simulation {

    public static void main(String[] args) {
        int size = 10;
        Map map = new Map(size);
        final List<List<Field>> mapList = map.getMapList();

        UnitCreator unitCreator = new UnitCreator(map, 8, 5, 3,1,2);
        final List<Unit> unitList = unitCreator.getUnitList();

        System.out.println(unitList);
        System.out.println(mapList);
        System.out.println("Created Units: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("Created Infantry: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("Created Tanks: " + ArmyUnit.getNumOfALiveTanks());


        for(Unit unit : unitList){
            if(unit instanceof ArmyUnit){
                ArmyUnit armyUnit = (ArmyUnit) unit;
                armyUnit.move(map, unitCreator);
            }
            //else if(unit instanceof MovingBase){
                //MovingBase movingBase = (MovingBase) unit;
                //movingBase.move(map, unitCreator);
            //}
        }
        System.out.println("Units left: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("infantry left: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("tanks left: " + ArmyUnit.getNumOfALiveTanks());
        System.out.println("amount of battles: " + ArmyUnit.getNumOfBattles());
        System.out.println("amount of attacks: " + ArmyUnit.getNumOfAttacks());
    }


}
