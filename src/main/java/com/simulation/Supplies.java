package com.simulation;

/**
 * Stores data about hp and ammo of each ArmyUnit.
 */
class Supplies{
    /**
     * health points
     */
    int hp;
    /**
     * ammunition points
     */
    int ammo;

    /**
     * constructor for Supplies
     * @param hp health points value
     * @param ammo ammunition value
     */
    public Supplies(int hp, int ammo){
        this.hp = hp;
        this.ammo = ammo;
    }
}
