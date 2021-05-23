package com.simulation;


import static com.simulation.SimulationConstants.*;


class Trap extends Unit{
    final int damage = TRAP_DMG;
    private int usesLeft = TRAP_USES;

    public Trap(int id, Map map){
        super(id, "trap", map);
    }


    void attack(Unit unit){
        if(unit instanceof MovingBase && usesLeft > 0) {
            MovingBase mb = (MovingBase) unit;
            mb.die();
            usesLeft--;
            System.out.println("Trap destroyed moving base " + mb.id);
        }
        else if(unit instanceof ArmyUnit && usesLeft > 0){
            ArmyUnit armyUnit = (ArmyUnit) unit;
            armyUnit.takeHit(damage);
            usesLeft--;
        }
        if(usesLeft <= 0)
            die();
    }
    private void die(){
        usesLeft = 0;
        field.setTakenByNeutral(-1);
        //System.out.println(type + " died!");
        field = null;
    }
}
