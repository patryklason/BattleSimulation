package com.simulation;

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
        if(!(isAlive && enemy.isAlive))         //czemu enemy.isAlive dziala? XDD
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
