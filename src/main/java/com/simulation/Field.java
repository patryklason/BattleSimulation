package com.simulation;

import java.util.Objects;


/**
 * Field is a object that can store 1 armyUnit and 1 neutralUnit. (ex. Field can be occupied by Infantry and Base, but
 * cannot be occupied by infantry and tank at the same time).
 */
class Field{
    final int pos_x;
    final int pos_y;
    private int takenByArmy;        //free: -1, else: unitList index
    private int takenByNeutral;

    public Field(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        takenByArmy = -1;
        takenByNeutral = -1;
    }


    public void setTakenByArmy(int unitId){
            takenByArmy = unitId;
    }
    public int getTakenByArmy() {
        return takenByArmy;
    }

    public void setTakenByNeutral(int unitId) {
            takenByNeutral = unitId;
    }
    public int getTakenByNeutral() {
        return takenByNeutral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return pos_x == field.pos_x && pos_y == field.pos_y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos_x, pos_y);
    }

    @Override
    public String toString() {
        return "Field[" + pos_x + "," + pos_y + "] "
                + " Army: " + (getTakenByArmy()==-1? "no": getTakenByArmy())
                + ", Neutral: " + (getTakenByNeutral()==-1? "no" : getTakenByNeutral() )+ "\n";
    }
}
