package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * Traps are spawned at the start of the simulation. If ArmyUnit steps onto field with trap, it gets damage. If the mobile
 * base meets the trap, it is destroyed instantly.
 */
class Trap extends Unit{
    final int damage = TRAP_DMG;
    private int usesLeft = TRAP_USES;

    private static int deadTrap;

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
