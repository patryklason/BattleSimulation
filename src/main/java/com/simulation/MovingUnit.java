package com.simulation;

import java.util.Random;


/**
 * abstract class for ArmyUnit and MovingBase
 * <p>stores information about movement (number of field unit will move on) and
 * methods for initializing movement</p>
 */
abstract class MovingUnit extends Unit{

    int movement;

    public MovingUnit(int id, String type, Map map, int movement) {
        super(id, type, map);
        this.movement = movement;
    }

    abstract void move(Map map, UnitCreator unitCreator);

    /**
     * finds new field that unit will interact with, based on unit's movement
     * @param map map on which the new field will be found
     * @return returns field that unit will interact with or null if cannot find one
     * <p>finding field is divided into two possibilities: horizontal or vertical. Each possibility is randomly
     * selected in findNewField method so that the units can move in more various way</p>
     */
    Field findNewField(Map map){
        Field newField = null;
        Random rnd = new Random();

        do {
            int moveDir = rnd.nextInt(2);

            switch (moveDir) {
                case 0 -> newField = horizontalMove(map, field.pos_x, field.pos_y);
                case 1 -> newField = verticalMove(map, field.pos_x, field.pos_y);
            }
        }while(field==null);
        return newField;
    }

    private Field horizontalMove(Map map, int curPos_x, int curPos_y){
        if(curPos_x + movement >= 0 && curPos_x + movement < map.size)
            return map.getMapList().get(curPos_x + movement).get(curPos_y);
        else if(curPos_x - movement >= 0 && curPos_x - movement < map.size)
            return map.getMapList().get(curPos_x - movement).get(curPos_y);
        return null;
    }

    private Field verticalMove(Map map, int curPos_x, int curPos_y){
        if(curPos_y + movement >= 0 && curPos_y + movement < map.size)
            return map.getMapList().get(curPos_x).get(curPos_y + movement);
        else if(curPos_y - movement >=0 && curPos_y - movement < map.size)
            return map.getMapList().get(curPos_x).get(curPos_y - movement);
        return null;
    }
}
