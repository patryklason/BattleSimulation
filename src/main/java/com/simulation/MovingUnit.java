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
     * @return returns field that unit will interact with or null if fails to find one
     * <p>Finding new field is divided into 4 methods - each is responsible for finding a field in specific direction.
     * This method randomly calls out one of those direction-methods in loop. The loop ends if the new field was found.
     * To make the movement even more random, new field is found in range (1 - unit_movement).</p>
     */
    Field findNewField(Map map){
        Field newField = null;
        Random rnd = new Random();

        do {
            int moveDir = rnd.nextInt(4);
            int rndMovement = rnd.nextInt(movement) + 1;


            switch (moveDir) {
                case 0 -> newField = findFieldUp(map, field.pos_x, field.pos_y, rndMovement);
                case 1 -> newField = findFieldDown(map, field.pos_x, field.pos_y, rndMovement);
                case 2 -> newField = findFieldRight(map, field.pos_x, field.pos_y, rndMovement);
                case 3 -> newField = findFieldLeft(map, field.pos_x, field.pos_y, rndMovement);
            }
        }while(field==null);
        return newField;
    }

    /**
     *
     * @param map map on which the new field will be found
     * @param curPos_x current position of unit in x-axis (horizontal)
     * @param curPos_y current position of unit in y-axis (vertical)
     * @param rndMovement randomly generated integer in range (1 - unit_movement)
     * @return found field or null if field cannot be found in that direction
     *
     * <p>method tries to find field in specific direction based on current unit's position and rndMovement</p>
     */
    private Field findFieldUp(Map map, int curPos_x, int curPos_y, int rndMovement){
        if(curPos_y + rndMovement >= 0 && curPos_y + rndMovement < map.size)
            return map.getMapList().get(curPos_x).get(curPos_y + rndMovement);
        return null;
    }
    private Field findFieldDown(Map map, int curPos_x, int curPos_y, int rndMovement){
        if(curPos_y - rndMovement >= 0 && curPos_y - rndMovement < map.size)
            return map.getMapList().get(curPos_x).get(curPos_y - rndMovement);
        return null;
    }
    private Field findFieldRight(Map map, int curPos_x, int curPos_y, int rndMovement){
        if(curPos_x + rndMovement >= 0 && curPos_x + rndMovement < map.size)
            return map.getMapList().get(curPos_x + rndMovement).get(curPos_y);
        return null;
    }
    private Field findFieldLeft(Map map, int curPos_x, int curPos_y, int rndMovement){
        if(curPos_x - rndMovement >= 0 && curPos_x - rndMovement < map.size)
            return map.getMapList().get(curPos_x - rndMovement).get(curPos_y);
        return null;
    }
}
