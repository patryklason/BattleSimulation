package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * Traps are spawned at the start of the simulation. If ArmyUnit steps onto field with trap, it gets damage. If the mobile
 * base meets the trap, it is destroyed instantly.
 */
class Trap extends Unit{
    /**
     * damage the trap will deal
     */
    final int damage = TRAP_DMG;
    /**
     * number of uses for Trap
     */
    private int usesLeft = TRAP_USES;
    /**
     * helpful variable to save stats to file
     */
    private static int deadTrap;

    /**
     * constructor for Trap
     * @param id id of unit
     * @param map map the unit will spawn on
     */
    public Trap(int id, Map map){
        super(id, "trap", map);
    }

    public static int getDeadTrap() { return deadTrap; }

    public static void setDeadTrap(int deadTrap) { Trap.deadTrap = deadTrap; }

    /**
     * If the trap has some usesLeft, it makes movingBase die instantly or
     * gives damage to ArmyUnit (If ArmyUnit gets enough damage it will die as well).
     * @param unit unit the trap will attack
     */
    void attack(Unit unit){
        if(unit instanceof MovingBase && usesLeft > 0) {
            MovingBase mb = (MovingBase) unit;
            mb.die();
            usesLeft--;
        }
        else if(unit instanceof ArmyUnit && usesLeft > 0){
            ArmyUnit armyUnit = (ArmyUnit) unit;
            armyUnit.takeHit(damage);
            usesLeft--;
        }
        if(usesLeft <= 0)
            die();
    }

    /**
     * Sets usesLeft to 0 and sets the field free.
     */
    private void die(){
        usesLeft = 0;
        field.setTakenByNeutral(-1);
        deadTrap=3;
        field = null;
    }
}
