package com.simulation;

import java.util.ArrayList;
import java.util.List;

import static com.simulation.SimulationConstants.*;

/**
 * Creates list of Units to manage them better
 */
class UnitCreator{
    final private List<Unit> unitList= new ArrayList<>();

    public UnitCreator(Map map, int infantry, int tanks, int mobiles, int bases, int traps){

        for(int i = 0; i < infantry; ++i) {
            unitList.add(new ArmyUnit(unitList.size(),"infantry", map, INFANTRY_MOVE, 1));
            unitList.add(new ArmyUnit(unitList.size(),"infantry", map, INFANTRY_MOVE, 2));
        }
        for(int i = 0; i < tanks; ++i){
            unitList.add(new ArmyUnit(unitList.size(),"tank", map, TANK_MOVE, 1));
            unitList.add(new ArmyUnit(unitList.size(),"tank", map, TANK_MOVE, 2));
        }
        for(int i = 0; i < mobiles; ++i) {
            unitList.add(new MovingBase(unitList.size(), map, MOVING_BASE_MOVE));
        }
        for(int i = 0; i < bases; ++i)
            unitList.add(new Base(unitList.size(), map));
        for(int i = 0; i < traps; ++i)
            unitList.add(new Trap(unitList.size(), map));

    }

    public List<Unit> getUnitList() {
        return unitList;
    }

}
