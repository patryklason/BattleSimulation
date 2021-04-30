package com.simulation;

import java.util.Random;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 */
abstract class MovingUnit extends Unit{

    int movement;

    public MovingUnit(int id, String type, Map map, int movement) {
        super(id, type, map);
        this.movement = movement;
    }


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
