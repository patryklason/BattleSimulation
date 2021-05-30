package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * Moving base moves on the map and delivers supplies to ArmyUnits (if has any usesLeft).
 * It can be destroyed by trap or resupplied by main base.
 */
class MovingBase extends MovingUnit{
    final int ammoToGive = MOVING_BASE_AMMO;
    private int usesLeft = MOVING_BASE_USES;
    private static int deadMovingBase;

    public MovingBase(int id, Map map, int movement){
        super(id, "movingBase", map, movement);
    }

    public void setUsesLeft(int amount){
        usesLeft = amount;
    }

    public static int getDeadMovingBase() { return deadMovingBase; }

    public static void setDeadMovingBase(int deadMovingBase) { MovingBase.deadMovingBase = deadMovingBase; }

    /**
     * Gives specific amount of ammo to alive ArmyUnit (if ArmyUnit has not full ammo). Each gift lowers the usesLeft by 1.
     * @param armyUnit armyUnit that will be resupplied
     */
    void resupply(ArmyUnit armyUnit){
        if (!armyUnit.getAlive())
            return;
        if(usesLeft <= 0)
            return;
        armyUnit.takeSupplies(0, ammoToGive);
        usesLeft--;
    }

    /**
     * makes movingBase move on the map and interact with other units. If the field is free, it takes the field.
     * @param map map on which the moving base will move
     * @param uc list of Units
     */
    void move(Map map, UnitCreator uc){
        Field newField = super.findNewField(map);
        if (newField == null)
            return;
        if(newField.getTakenByNeutral() == -1)
            takeField(newField);
        else if(newField.getTakenByNeutral() > -1){
            Unit unit = uc.getUnitList().get(newField.getTakenByNeutral());
            if(unit instanceof Trap){
                Trap trap = (Trap) unit;
                trap.attack(this);
            }
            if(unit instanceof Base){
                Base base = (Base) unit;
                base.resupply(this);
            }
        }
        if(newField.getTakenByArmy() > -1){
            ArmyUnit armyUnit = (ArmyUnit) uc.getUnitList().get(newField.getTakenByArmy());
            resupply(armyUnit);
        }
    }
    private void takeField(Field newField){
        if(newField.getTakenByNeutral() != -1)
            return;
        field.setTakenByNeutral(-1);
        field = newField;
        field.setTakenByNeutral(id);
    }

    /**
     * sets usesLeft to 0 and sets the field free
     */
    void die(){
        usesLeft = 0;
        field.setTakenByNeutral(-1);
        deadMovingBase=4;
    }
}
