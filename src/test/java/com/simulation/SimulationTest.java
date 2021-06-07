package com.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    void freeArmyFieldsTest() {
        Simulation.tanks=2000;
        Simulation.infantry=8000;
        Simulation.size=100;

        Assertions.assertEquals(0, Simulation.freeArmyFields());
    }

    @Test
    void freeNeutralFieldsTest() {
        Simulation.bases=1000;
        Simulation.traps=3000;
        Simulation.mobiles=3000;
        Simulation.size=100;

        Assertions.assertEquals(3000, Simulation.freeNeutralFields());
    }
}