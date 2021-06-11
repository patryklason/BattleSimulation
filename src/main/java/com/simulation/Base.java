package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * Base is a stationary Unit. Once met, it gives movingBase more uses and armyUnit specific amount of hp and ammo.
 * Base cannot be destroyed.
 */
class Base extends Unit{
    /**
     * hp points the unit will give to armyUnit
     */
    int healPoints = BASE_HEAL;
    /**
     * ammo points the unit will give to armyUnit
     */
    int ammoToGive = BASE_AMMO;

    /**
     * constructor for Base
     * @param id unit's id
     * @param map map the unit will spawn on
     */
    public Base(int id, Map map){
        super(id, "base", map);
    }

    /**
     * gives hp and ammo to armyUnit or gives more uses to mobileBase
     * @param unit unit that will be resupplied
     */
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
