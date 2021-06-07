package com.simulation;

import com.simulation.SimulationConstants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmyUnitTest {

    @Test
    void resetArmyStats() {
        ArmyUnit.resetArmyStats();
        Assertions.assertEquals(0, ArmyUnit.getNumOfAliveUnits());
        Assertions.assertEquals(0, ArmyUnit.getNumOfALiveInfantry());
        Assertions.assertEquals(0, ArmyUnit.getNumOfALiveTanks());
        Assertions.assertEquals(0, ArmyUnit.getNumOfAliveTeam1());
        Assertions.assertEquals(0, ArmyUnit.getNumOfAliveTeam2());
        Assertions.assertEquals(0, ArmyUnit.getNumOfBattles());
        Assertions.assertEquals(0, ArmyUnit.getNumOfAttacks());
        Assertions.assertEquals(0, ArmyUnit.getDeadArmy());
    }

    @Test
    void takeSupplies() {
        int addHp = 1, addAmmo = 1;
        Map map = new Map(2);
        ArmyUnit armyUnit = new ArmyUnit(0, "infantry", map, SimulationConstants.INFANTRY_MOVE, 1);
        Supplies supplies = new Supplies(0,0);
        armyUnit.setSupplies(supplies);
        armyUnit.takeSupplies(addHp,addAmmo);

        Assertions.assertEquals(Math.min(addHp, armyUnit.maxHp), armyUnit.getSupplies().hp);
        Assertions.assertEquals(Math.min(addAmmo, armyUnit.maxAmmo), armyUnit.getSupplies().ammo);

    }

    @Test
    void move() {
    }

    @Test
    void takeHit() {
        int damage = 10;
        Map map = new Map(2);
        ArmyUnit armyUnit = new ArmyUnit(0, "infantry", map, SimulationConstants.INFANTRY_MOVE, 1);

        armyUnit.takeHit(damage);

        Assertions.assertEquals(Math.max(SimulationConstants.INFANTRY_HP - damage, 0), armyUnit.getSupplies().hp);
    }
}