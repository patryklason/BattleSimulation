package com.simulation;


/**
 * @version 1.0.1
 * @author Patryk Lason, Hubert Belkot
 */
class Trap extends Unit{
    final int damage = 10;
    private int usesLeft = 1;

    public Trap(int id, Map map){
        super(id, "trap", map);
    }

    public int getUsesLeft() {
        return usesLeft;
    }
}
