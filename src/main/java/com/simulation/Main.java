package com.simulation;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int size = 10;
        Map map = new Map(size);
        List<List<Field>> mapList = map.getMapList();


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


        int days = 100;
        for(int i = 0; i < days; ++i){
        for (ArmyUnit armyUnit : armyList) {
            armyUnit.move(map);
        }

        }


        System.out.println(mapList);
    }
}

class Map{
    final int size;
    final private List<List<Field>> mapList = new ArrayList<>();

    public Map(int size){
        this.size = size;
        for(int i = 0; i < size; ++i){
            mapList.add(new ArrayList<>());
            for(int j = 0; j < size; ++j) {
                mapList.get(i).add(new Field(i,j));
            }
        }
    }

    public List<List<Field>> getMapList() {
        return mapList;
    }
}

class Field{
    final int pos_x;
    final int pos_y;
    private boolean isFree;
    private String takenBy;

    public Field(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
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
            takenBy = obj;
    }
    public String getTakenBy() {
        return takenBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return pos_x == field.pos_x && pos_y == field.pos_y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos_x, pos_y);
    }

    @Override
    public String toString() {
        return "Field[" + pos_x + "," + pos_y + "] " + " takenBy: " + getTakenBy() + "\n";
    }
}

abstract class Unit{
    protected Field field = null;
    final String type;

    public Unit(String type, Map map){
        Random random = new Random();
        int rnd;
        int rnd1;

        this.type = type;

        do {
            rnd = random.nextInt(map.size-1);
            rnd1 = random.nextInt(map.size-1);
            Field fieldVar = map.getMapList().get(rnd).get(rnd1);
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
        int currPos_x = field.pos_x;
        int currPos_y = field.pos_y;


        if(currPos_y + movement >= 0 && currPos_y + movement < map.size){
            if(map.getMapList().get(currPos_x).get(currPos_y + movement).getIsFree()){
                freeField(field);
                field = map.getMapList().get(currPos_x).get(currPos_y + movement);
                takeField(field, type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move up!");
        }
        else if(currPos_y - movement >= 0 && currPos_y - movement < map.size){
            if(map.getMapList().get(currPos_x).get(currPos_y - movement).getIsFree()){
                freeField(field);
                field = map.getMapList().get(currPos_x).get(currPos_y - movement);
                takeField(field, type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move down!");
        }
        else if(currPos_x + movement < map.size && currPos_x + movement >= 0) {
            if (map.getMapList().get(currPos_x + movement).get(currPos_y).getIsFree()) {
                freeField(field);
                field = map.getMapList().get(currPos_x + movement).get(currPos_y);
                takeField(field, type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move right!");
        }
        else if(currPos_x - movement < map.size && currPos_x - movement >=0) {
            if (map.getMapList().get(currPos_x - movement).get(currPos_y).getIsFree()) {
                freeField(field);
                field = map.getMapList().get(currPos_x - movement).get(currPos_y);
                takeField(field, type);

                System.out.println("moved");
            }
            else
                System.out.println("cannot move left!");
        }
        else
            System.out.println("Cannot move outside the map!");
    }
    private void freeField(Field field){
        if(field != null) {
            field.setIsFree(true);
            field.setTakenBy("none");
        }
    }
    private void takeField(Field field, String type){
        if(field != null) {
            field.setIsFree(false);
            field.setTakenBy(type);
        }
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
        return type + " alive: " + isAlive + " pos[" + field.pos_x + "," + field.pos_y + "]" + "\n";
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