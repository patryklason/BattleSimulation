package com.simulation;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 */
class Trap extends Unit{
    final int damage = 10;
    private int usesLeft = 1;

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
            if(!armyUnit.getAlive())
                System.out.println("Trap killed " + armyUnit.id);
            else
                System.out.println("Trap dealt " + damage + " to " + id);
            usesLeft--;
        }
        if(usesLeft <= 0)
            die();
    }
    private void die(){
        usesLeft = 0;
        field.setTakenByNeutral(-1);
        field = null;
    }
}
