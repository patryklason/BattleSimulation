package com.simulation;

import java.util.*;

/**
 * @version 1.0.0
 * @author Patryk Lason, Hubert Belkot
 *
 * @implNote army units can spawn on traps!
 * @// TODO: 30.04.2021 implement neutral units movement and interaction
 */

public class Simulation {

    public static void main(String[] args) {
        int size = 10;
        Map map = new Map(size);
        final List<List<Field>> mapList = map.getMapList();

        UnitCreator unitCreator = new UnitCreator(map, 8, 5, 3,1,2);
        final List<Unit> unitList = unitCreator.getUnitList();

        System.out.println(unitList);
        System.out.println(mapList);
        System.out.println("Created Units: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("Created Infantry: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("Created Tanks: " + ArmyUnit.getNumOfALiveTanks());


        for(Unit unit : unitList){
            if(unit instanceof ArmyUnit){
                ArmyUnit armyUnit = (ArmyUnit) unit;
                armyUnit.move(map, unitCreator);
            }
            //else if(unit instanceof MovingBase){
                //MovingBase movingBase = (MovingBase) unit;
                //movingBase.move(map, unitCreator);
            //}
        }
        System.out.println("Units left: " + ArmyUnit.getNumOfAliveUnits());
        System.out.println("infantry left: " + ArmyUnit.getNumOfALiveInfantry());
        System.out.println("tanks left: " + ArmyUnit.getNumOfALiveTanks());
        System.out.println("amount of battles: " + ArmyUnit.getNumOfBattles());
        System.out.println("amount of attacks: " + ArmyUnit.getNumOfAttacks());
    }


}
class UnitCreator{
    final private List<Unit> unitList= new ArrayList<>();

    public UnitCreator(Map map, int infantry, int tanks, int mobiles, int bases, int traps){

        for(int i = 0; i < infantry; ++i) {
            unitList.add(new ArmyUnit(unitList.size(),"infantry", map, 2, 1));
            unitList.add(new ArmyUnit(unitList.size(),"infantry", map, 2, 2));
        }
        for(int i = 0; i < tanks; ++i){
            unitList.add(new ArmyUnit(unitList.size(),"tank", map, 5, 1));
            unitList.add(new ArmyUnit(unitList.size(),"tank", map, 5, 2));
        }
        for(int i = 0; i < mobiles; ++i) {
            unitList.add(new MovingBase(unitList.size(), map, 2));
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
    private int takenByArmy;        //free: -1, else: unitList index
    private int takenByNeutral;

    public Field(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        takenByArmy = -1;
        takenByNeutral = -1;
    }


    public void setTakenByArmy(int unitId){
        if(takenByArmy == -1)
            takenByArmy = unitId;
    }
    public int getTakenByArmy() {
        return takenByArmy;
    }

    public void setTakenByNeutral(int unitId) {
        if(takenByNeutral == -1)
        takenByNeutral = unitId;
    }
    public int getTakenByNeutral() {
        return takenByNeutral;
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
        return "Field[" + pos_x + "," + pos_y + "] "
                + " Army: " + (getTakenByArmy()==-1? "no": getTakenByArmy())
                + ", Neutral: " + (getTakenByNeutral()==-1? "no" : getTakenByNeutral() )+ "\n";
    }
}


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

abstract class MovingUnit extends Unit{

    int movement;

    public MovingUnit(int id, String type, Map map, int movement) {
        super(id, type, map);
        this.movement = movement;
    }


    Field findNewField(Map map){
        Field newField = null;

        Random rnd = new Random();
        int moveDir = rnd.nextInt(2);

        switch (moveDir) {
            case 0 -> newField = horizontalMove(map, field.pos_x, field.pos_y);
            case 1 -> newField = verticalMove(map, field.pos_x, field.pos_y);
        }
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



//    void move(Map map) {
//        int currPos_x = field.pos_x;
//        int currPos_y = field.pos_y;
//        int numOfTriesFailed = 0;
//        Random random = new Random();
//
//        do {
//            int moveDir = random.nextInt(4);
//
//            switch (moveDir) {
//                case 0 -> numOfTriesFailed += moveUp(map, currPos_x, currPos_y);
//                case 1 -> numOfTriesFailed += moveDown(map, currPos_x, currPos_y);
//                case 2 -> numOfTriesFailed += moveRight(map, currPos_x, currPos_y);
//                case 3 -> numOfTriesFailed += moveLeft(map, currPos_x, currPos_y);
//            }
//        }while(numOfTriesFailed < 4);
//
//
//    }

//    void verticalMove(Map map, int currPos_x, int currPos_y, int movement){
//        if(currPos_y + movement >= 0 && currPos_y + movement < map.size){
//            Field newField =
//        }
//    }
//    void inspectField(Map map, Field newField){
//        if(newField.getIsFree())
//            move(map);
//        else if ((newField.getTakenBy().equals("infantry") || newField.getTakenBy().equals("tanks")) )
//    }

//    int moveUp(Map map, int currPos_x, int currPos_y){
//        if(currPos_y + movement >= 0 && currPos_y + movement < map.size){
//            if(map.getMapList().get(currPos_x).get(currPos_y + movement).getIsFree()){
//                freeField(field);
//                field = map.getMapList().get(currPos_x).get(currPos_y + movement);
//                takeField(field, type);
//
//                System.out.println("moved up");
//                return 0;
//            }
//            else {
//                System.out.println("cannot move up!");
//                return 1;
//            }
//        }
//        else return 1;
//    }
//    int moveDown(Map map, int currPos_x, int currPos_y){
//        if(currPos_y - movement >= 0 && currPos_y - movement < map.size){
//            if(map.getMapList().get(currPos_x).get(currPos_y - movement).getIsFree()){
//                freeField(field);
//                field = map.getMapList().get(currPos_x).get(currPos_y - movement);
//                takeField(field, type);
//
//                System.out.println("moved down");
//                return 0;
//            }
//            else {
//                System.out.println("cannot move down!");
//                return 1;
//            }
//        }
//        else return 1;
//    }
//    int moveRight(Map map, int currPos_x, int currPos_y){
//        if(currPos_x + movement < map.size && currPos_x + movement >= 0) {
//            if (map.getMapList().get(currPos_x + movement).get(currPos_y).getIsFree()) {
//                freeField(field);
//
//                field = map.getMapList().get(currPos_x + movement).get(currPos_y);
//                takeField(field, type);
//
//                System.out.println("moved right");
//                return 0;
//            }
//            else {
//                System.out.println("cannot move right!");
//                return 1;
//            }
//        }
//        else return 1;
//    }
//    int moveLeft(Map map, int currPos_x, int currPos_y){
//        if(currPos_x - movement < map.size && currPos_x - movement >=0) {
//            if (map.getMapList().get(currPos_x - movement).get(currPos_y).getIsFree()) {
//                freeField(field);
//                field = map.getMapList().get(currPos_x - movement).get(currPos_y);
//                takeField(field, type);
//
//                System.out.println("moved left");
//                return 0;
//            }
//            else {
//                System.out.println("cannot move left!");
//                return 1;
//            }
//        }
//        else return 1;
//    }

//    void move(Map map){
//        int currPos_x = field.pos_x;
//        int currPos_y = field.pos_y;
//
//
//        if(currPos_y + movement >= 0 && currPos_y + movement < map.size){
//            if(map.getMapList().get(currPos_x).get(currPos_y + movement).getIsFree()){
//                freeField(field);
//                field = map.getMapList().get(currPos_x).get(currPos_y + movement);
//                takeField(field, type);
//
//                System.out.println("moved");
//            }
//            else
//                System.out.println("cannot move up!");
//        }
//        else if(currPos_y - movement >= 0 && currPos_y - movement < map.size){
//            if(map.getMapList().get(currPos_x).get(currPos_y - movement).getIsFree()){
//                freeField(field);
//                field = map.getMapList().get(currPos_x).get(currPos_y - movement);
//                takeField(field, type);
//
//                System.out.println("moved");
//            }
//            else
//                System.out.println("cannot move down!");
//        }
//        else if(currPos_x + movement < map.size && currPos_x + movement >= 0) {
//            if (map.getMapList().get(currPos_x + movement).get(currPos_y).getIsFree()) {
//                freeField(field);
//                field = map.getMapList().get(currPos_x + movement).get(currPos_y);
//                takeField(field, type);
//
//                System.out.println("moved");
//            }
//            else
//                System.out.println("cannot move right!");
//        }
//        else if(currPos_x - movement < map.size && currPos_x - movement >=0) {
//            if (map.getMapList().get(currPos_x - movement).get(currPos_y).getIsFree()) {
//                freeField(field);
//                field = map.getMapList().get(currPos_x - movement).get(currPos_y);
//                takeField(field, type);
//
//                System.out.println("moved");
//            }
//            else
//                System.out.println("cannot move left!");
//        }
//        else
//            System.out.println("Cannot move outside the map!");
//    }


//    private void freeField(Field field){
//        if(field != null) {
//            field.setIsFree(true);
//            field.setTakenByArmy("none");
//        }
//    }
//    private void takeField(Field field, String type){
//        if(field != null) {
//            field.setIsFree(false);
//            field.setTakenByArmy(type);
//        }
//    }
}

class ArmyUnit extends MovingUnit{

    private Supplies supplies;
    final int maxHp;
    final int maxAmmo;
    final int team;
    final int damage;
    private boolean isAlive;


    private static int numOfAliveUnits;
    private static int numOfALiveInfantry;
    private static int numOfALiveTanks;
    private static int numOfBattles;
    private static int numOfAttacks;

    public ArmyUnit(int id, String type, Map map, int movement, int team){
        super(id, type, map, movement);

        this.team = team;
        isAlive = true;
        numOfAliveUnits++;


        if(type.equals("infantry")) {
            int hp = 5; int ammo = 10; int damage = 2;
            supplies = new Supplies(hp, ammo);
            maxHp = hp;
            maxAmmo = ammo;
            this.damage = damage;

            numOfALiveInfantry++;
        }
        else if(type.equals("tank")) {
            int hp = 10; int ammo = 5; int damage = 5;
            supplies = new Supplies(hp, ammo);
            maxHp = hp;
            maxAmmo = ammo;
            this.damage = damage;

            numOfALiveTanks++;
        }
        else{
            maxHp = -1;
            maxAmmo = -1;
            damage = -1;
        }

    }

    public static int getNumOfAliveUnits(){
        return numOfAliveUnits;
    }

    public static int getNumOfALiveInfantry() {
        return numOfALiveInfantry;
    }

    public static int getNumOfALiveTanks() {
        return numOfALiveTanks;
    }

    public static int getNumOfBattles() {
        return numOfBattles;
    }

    public static int getNumOfAttacks() {
        return numOfAttacks;
    }

    public void setSupplies(Supplies supplies) {
        this.supplies = supplies;
    }

    public Supplies getSupplies() {
        return supplies;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean getAlive(){
        return isAlive;
    }


    protected void move(Map map, UnitCreator uc){
        Field newField = super.findNewField(map);
        if(newField==null)
            return;
        if(newField.getTakenByArmy()==-1){          //first handle interaction with ArmyUnits
            takeField(newField);
            System.out.println(id + " moved to empty field!");
        }
        else if(newField.getTakenByArmy() > -1){
            ArmyUnit enemy = (ArmyUnit) uc.getUnitList().get(newField.getTakenByArmy());
            if(attack(enemy))
                numOfBattles++;
            enemy.attack(this);
            if(!enemy.isAlive) {
                takeField(newField);
                System.out.println(id + " killed and moved!");
            }
        }
        if(newField.getTakenByNeutral() > -1){      //then handle interaction with NeutralUnits
            Unit neutralUnit = uc.getUnitList().get(newField.getTakenByNeutral());
            if(neutralUnit instanceof Trap){
                System.out.println(id + " met trap");
            }
            else if(neutralUnit instanceof MovingBase){
                System.out.println(id + "met moving base");
            }
            else if(neutralUnit instanceof Base){
                System.out.println(id + " met static base");
            }
        }

    }
    private void takeField(Field newField){
        field.setTakenByArmy(-1);
        field = newField;
        field.setTakenByArmy(id);
    }
    private boolean attack(ArmyUnit enemy){
        if(!(isAlive && enemy.isAlive))
            return false;
        enemy.takeHit(this.damage);
        supplies.ammo--;
        System.out.println(id + " attacked " + enemy.id + " for " + this.damage + " to " + enemy.getSupplies().hp);
        numOfAttacks++;
        return true;
    }
    private void takeHit(int damage){
        if(damage >= supplies.hp) {
            die();
            System.out.println(this.id + " was attacked and died!");
        }
        else{
            supplies.hp-=damage;
            //System.out.println(this.id + " was attacked for " + damage + ", now hp: " + supplies.hp);
        }
    }
    private void die(){
        if(!isAlive)
            return;
        supplies.hp = 0;
        supplies.ammo = 0;
        isAlive = false;
        field.setTakenByArmy(-1);
        if(type.equals("infantry")) {
            numOfALiveInfantry--;
            numOfAliveUnits--;
        }
        if(type.equals("tank")) {
            numOfALiveTanks--;
            numOfAliveUnits--;
        }
    }

    @Override
    public String toString(){
        return type + ", " + id + " alive: " + isAlive + " pos[" + field.pos_x + "," + field.pos_y + "]" + "\n";
    }

}

class Supplies{
    int hp;
    int ammo;

    public Supplies(int hp, int ammo){
        this.hp = hp;
        this.ammo = ammo;
    }
}

class MovingBase extends MovingUnit{
    final int ammoToGive = 2;
    private int UsesLeft = 5;

    public MovingBase(int id, Map map, int movement){
        super(id, "movingBase", map, movement);
    }

    public int getUsesLeft() {
        return UsesLeft;
    }
}

class Base extends Unit{
    int healPoints = 10;
    int ammoToGive = 5;
    public Base(int id, Map map){
        super(id, "base", map);
    }
}

class Trap extends Unit{
    final int damage = 10;
    private int usesLeft = 1;

    public Trap(int id, Map map){
        super(id, "trap", map);
    }

    public int getUsesLeft() {
        return usesLeft;
    }
}