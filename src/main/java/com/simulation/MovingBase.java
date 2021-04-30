package com.simulation;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 */
class MovingBase extends MovingUnit{
    final int ammoToGive = 2;
    private int usesLeft = 5;

    public MovingBase(int id, Map map, int movement){
        super(id, "movingBase", map, movement);
    }

    public int getUsesLeft() {
        return usesLeft;
    }
    public void setUsesLeft(int amount){
        usesLeft = amount;
    }

    void resupply(ArmyUnit armyUnit){
        if (!armyUnit.getAlive())
            return;
        if(usesLeft <= 0)
            return;
        armyUnit.takeSupplies(0, ammoToGive);
        System.out.println("Moving base resupplied " + armyUnit.id + " army unit");
        usesLeft--;
    }

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
                //field.setTakenByNeutral(-1);
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
        Field oldField = field;
        field.setTakenByNeutral(-1);
        field = newField;
        field.setTakenByNeutral(id);
        System.out.println("Moving base " + id + " moved from [" + oldField.pos_x + "," + oldField.pos_y + "] to ["
                + newField.pos_x + "," + newField.pos_y +"]");
    }

    void die(){
        usesLeft = 0;
        field.setTakenByNeutral(-1);
        //field = null;
    }
}
