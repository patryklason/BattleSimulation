package com.simulation;

import java.util.Random;

abstract class Unit {
    protected Field field = null;
    final int id;
    final String type;

    public Unit(int id, String type, Map map){
        Random random = new Random();
        int rnd;
        int rnd1;

        this.id = id;
        this.type = type;

        do {
            rnd = random.nextInt(map.size-1);
            rnd1 = random.nextInt(map.size-1);
            Field fieldVar = map.getMapList().get(rnd).get(rnd1);
            if(type.equals("infantry") || type.equals("tank")) {
                if (fieldVar.getTakenByArmy() == -1) {
                    field = fieldVar;
                    field.setTakenByArmy(id);
                } else
                    System.out.println("Tried to spawn on taken field!");
            }
            else if(type.equals("base") || type.equals("trap") || type.equals("movingBase")){
                if(fieldVar.getTakenByNeutral() == -1) {
                    field = fieldVar;
                    field.setTakenByNeutral(id);
                }
                else
                    System.out.println("static object tried to spawn on taken field");
            }
            else
                System.out.println("Couldnt found suitable constructor for " + type);
        }while(field == null);
    }

    @Override
    public String toString(){
        return type + ", " + id + " pos[" + field.pos_x + "," + field.pos_y + "]" + "\n";
    }
}
