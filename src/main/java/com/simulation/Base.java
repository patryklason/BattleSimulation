package com.simulation;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 */
class Base extends Unit{
    int healPoints = 10;
    int ammoToGive = 5;
    public Base(int id, Map map){
        super(id, "base", map);
    }

    void resupply(Unit unit){
        if(unit instanceof MovingBase){
            MovingBase movingBase = (MovingBase) unit;
            movingBase.setUsesLeft(5);
            System.out.println("Base resupplied " + movingBase.id + " moving base");
        }
        else if(unit instanceof ArmyUnit){
            ArmyUnit armyUnit = (ArmyUnit) unit;
            armyUnit.takeSupplies(healPoints, ammoToGive);
            System.out.println("Base resupplied " + armyUnit.id +" army unit");
        }
    }
}
