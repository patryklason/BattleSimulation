package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * Base is a stationary Unit. Once met, it gives movingBase more uses and armyUnit specific amount of hp and ammo.
 * Base cannot be destroyed.
 */
class Base extends Unit{
    int healPoints = BASE_HEAL;
    int ammoToGive = BASE_AMMO;
    public Base(int id, Map map){
        super(id, "base", map);
    }

    void resupply(Unit unit){
        if(unit instanceof MovingBase){
            MovingBase movingBase = (MovingBase) unit;
            movingBase.setUsesLeft(5);
        }
        else if(unit instanceof ArmyUnit){
            ArmyUnit armyUnit = (ArmyUnit) unit;
            armyUnit.takeSupplies(healPoints, ammoToGive);
        }
    }
}
