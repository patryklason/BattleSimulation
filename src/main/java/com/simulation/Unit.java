package com.simulation;

import java.util.Random;

/**
 * Base class for all the Units. It stores data such as field, id and type.
 */
abstract class Unit {
    /**
     * field that unit currently occupies
     */
    protected Field field = null;
    /**
     * unique id of unit
     */
    final int id;
    /**
     * type of unit
     */
    final String type;

    /**
     *
     * @param id id of unit
     * @param type type of unit
     * @param map map the unit will spawn on
     */
    public Unit(int id, String type, Map map){
        Random random = new Random();
        int rnd;
        int rnd1;

        this.id = id;
        this.type = type;

        do {
            rnd = random.nextInt(map.size);
            rnd1 = random.nextInt(map.size);
            Field fieldVar = map.getMapList().get(rnd).get(rnd1);
            if(type.equals("infantry") || type.equals("tank")) {
                if (fieldVar.getTakenByArmy() == -1) {
                    field = fieldVar;
                    field.setTakenByArmy(id);
                }
            }
            else if(type.equals("base") || type.equals("trap") || type.equals("movingBase")){
                if(fieldVar.getTakenByNeutral() == -1) {
                    field = fieldVar;
                    field.setTakenByNeutral(id);
                }
            }
            else
                System.out.println("Couldn't found suitable constructor for " + type);
        }while(field == null);
    }

    @Override
    public String toString(){
        return type + ", " + id + " pos[" + field.pos_x + "," + field.pos_y + "]" + "\n";
    }
}
