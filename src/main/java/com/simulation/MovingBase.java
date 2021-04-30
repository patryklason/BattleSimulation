package com.simulation;

class MovingBase extends MovingUnit{
    final int ammoToGive = 2;
    private int UsesLeft = 5;

    public MovingBase(int id, Map map, int movement){
        super(id, "movingBase", map, movement);
    }

    public int getUsesLeft() {
        return UsesLeft;
    }
}
