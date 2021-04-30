package com.simulation;

class Base extends Unit{
    int healPoints = 10;
    int ammoToGive = 5;
    public Base(int id, Map map){
        super(id, "base", map);
    }
}
