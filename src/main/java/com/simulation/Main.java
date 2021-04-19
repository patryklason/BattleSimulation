package com.simulation;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int size = 100;
        Map map = new Map(size);
        List<Field> mapList = map.getMapList();


        int numOfInfantry = 10;
        int numOfTanks = 5;


        List<ArmyUnit> armyList = new ArrayList<>();
        for(int i = 0; i < numOfInfantry; ++i) {
            armyList.add(new ArmyUnit("infantry", map, 2, 5, 5, 1, 5));
            armyList.add(new ArmyUnit("infantry", map, 2, 5, 5, 2, 5));
        }
        for(int i = 0; i < numOfTanks; ++i){
            armyList.add(new ArmyUnit("tank Unit", map, 5, 10, 2, 1, 10));
            armyList.add(new ArmyUnit("tank Unit", map, 5, 10, 2, 2, 10));
        }

        System.out.println(armyList);
        System.out.println(mapList);


        int days = 1;
        //for(int i = 0; i < days; ++i){
        for (ArmyUnit armyUnit : armyList) {
            armyUnit.move(map);
        }

        //}


        System.out.println(mapList);
    }
}

class Map{
    final int size;
    final private List<Field> mapList = new ArrayList<>();

    public Map(int size){
        this.size = size;
        for(int i = 0; i < size; ++i){
            mapList.add(new Field(i));
        }
    }

    public List<Field> getMapList() {
        return mapList;
    }
}

class Field{
    final int position;
    private boolean isFree;
    private String takenBy;

    public Field(int position){
        this.position = position;
        isFree = true;
        takenBy = "none";
    }

    public void setIsFree(boolean isFree){
        this.isFree = isFree;
    }
    public boolean getIsFree(){
        return isFree;
    }

    public void setTakenBy(String obj){
        if(!(obj == null))
            takenBy = new String(obj);
    }
    public String getTakenBy() {
        return takenBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return position == field.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Field: " + position + " takenBy: " + getTakenBy() + "\n";
    }
}

abstract class Unit{
    protected Field field = null;
    final String type;

    public Unit(String type, Map map){
        Random random = new Random();
        int rnd;

        this.type = type;

        do {
            rnd = random.nextInt(map.size-1);
            Field fieldVar = map.getMapList().get(rnd);
            if (fieldVar.getIsFree()) {
                field = fieldVar;
                field.setIsFree(false);
                field.setTakenBy(type);
            }
            else
                System.out.println("Tried to spawn on taken field!");
        }while(field == null);
    }
    public Unit(){
        this("fail", null);
    }
}

abstract class MovingUnit extends Unit{

    int movement;

    public MovingUnit(String type, Map map, int movement) {
        super(type, map);
        this.movement = movement;
    }

    void move(Map map){
        int currPos = field.position;


        if(currPos + movement < map.size && currPos + movement >= 0) {
            if (map.getMapList().get(currPos + movement).getIsFree()) {
                field.setIsFree(true);
                field.setTakenBy("none");
                field = map.getMapList().get(currPos + movement);
                field.setIsFree(false);
                field.setTakenBy(this.type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move forward!");
        }
        else if(currPos - movement < map.size && currPos - movement >=0) {
            if (map.getMapList().get(currPos - movement).getIsFree()) {
                field.setIsFree(true);
                field.setTakenBy("none");
                field = map.getMapList().get(currPos - movement);
                field.setIsFree(false);
                field.setTakenBy(this.type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move backward!");
        }
        else
            System.out.println("Cannot move outside the map!");
    }
}

class ArmyUnit extends MovingUnit{

    Supplies supplies;
    final int maxHp;
    final int maxAmmo;
    int team;
    boolean isAlive;
    int damage;

    private static int numOfAliveUnits;

    public ArmyUnit(String type, Map map, int movement, int hp, int ammo, int team, int damage){
        super(type, map, movement);
        supplies = new Supplies(hp, ammo);

        maxHp = hp;
        maxAmmo = ammo;
        this.team = team;
        isAlive = true;
        this.damage = damage;
    }

    @Override
    public String toString(){
        return type + " alive: " + isAlive + " pos: " + field.position + "\n";
    }

}

class Supplies{
    protected int hp;
    protected int ammo;

    public Supplies(int hp, int ammo){
        this.hp = hp;
        this.ammo = ammo;
    }
}