package com.simulation;


/**
 * @version 1.0.1
 * @author Patryk Lason, Hubert Belkot
 */
class Base extends Unit{
    int healPoints = 10;
    int ammoToGive = 5;
    public Base(int id, Map map){
        super(id, "base", map);
    }
}
