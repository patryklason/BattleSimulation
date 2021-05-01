package com.simulation;


/**
 * @version 1.0.2
 * @author Patryk Lason, Hubert Belkot
 */
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
    private static int numOfAliveTeam1;
    private static int numOfAliveTeam2;
    private static int numOfBattles;
    private static int numOfAttacks;

    public ArmyUnit(int id, String type, Map map, int movement, int team){
        super(id, type, map, movement);

        this.team = team;
        isAlive = true;
        numOfAliveUnits++;
        if(team == 1)
            numOfAliveTeam1++;
        else if(team == 2)
            numOfAliveTeam2++;

        if(type.equals("infantry")) {
            int hp = 10; int ammo = 20; int damage = 5;
            supplies = new Supplies(hp, ammo);
            maxHp = hp;
            maxAmmo = ammo;
            this.damage = damage;

            numOfALiveInfantry++;
        }
        else if(type.equals("tank")) {
            int hp = 20; int ammo = 10; int damage = 10;
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

    public static int getNumOfAliveTeam1() {
        return numOfAliveTeam1;
    }

    public static int getNumOfAliveTeam2() {
        return numOfAliveTeam2;
    }

    public Supplies getSupplies() {
        return supplies;
    }

    public boolean getAlive(){
        return isAlive;
    }

    void takeSupplies(int hp, int ammo){
        if(this.supplies.hp + hp > maxHp)
            this.supplies.hp = maxHp;
        else
            this.supplies.hp += hp;
        if(this.supplies.ammo + ammo > maxAmmo)
            this.supplies.ammo = maxAmmo;
        else
            this.supplies.ammo += ammo;
    }

    protected void move(Map map, UnitCreator uc){
        if(!isAlive)
            return;

        Field newField = super.findNewField(map);
        if(newField==null)
            return;
        if(newField.getTakenByArmy()==-1){          //first handle interaction with ArmyUnits
            takeField(newField);
        }
        else if(newField.getTakenByArmy() > -1){
            ArmyUnit enemy = (ArmyUnit) uc.getUnitList().get(newField.getTakenByArmy());
            if(enemy.isAlive) {
                if (attack(enemy))
                    numOfBattles++;
                enemy.attack(this);
                if (!enemy.isAlive) {
                    takeField(newField);
                    System.out.println(id + " killed and moved!");
                }
            }
        }
        if(newField.getTakenByNeutral() > -1){      //then handle interaction with NeutralUnits
            Unit neutralUnit = uc.getUnitList().get(newField.getTakenByNeutral());
            if(neutralUnit instanceof Trap){
                Trap trap = (Trap) neutralUnit;
                trap.attack(this);
            }
            else if(neutralUnit instanceof MovingBase){
                MovingBase movingBase = (MovingBase) neutralUnit;
                movingBase.resupply(this);
            }
            else if(neutralUnit instanceof Base){
                Base base = (Base) neutralUnit;
                base.resupply(this);
            }
        }

    }
    private void takeField(Field newField){
        if (newField.getTakenByArmy() != -1)
            return;
        Field oldField = field;
        field.setTakenByArmy(-1);
        field = newField;
        field.setTakenByArmy(id);
        //System.out.println(id + " moved from [" + oldField.pos_x + "," + oldField.pos_y + "] to ["
        //        + newField.pos_x + "," + newField.pos_y +"]");
    }

    private boolean attack(ArmyUnit enemy){
        if(!(isAlive && enemy.isAlive))        //czemu enemy.isAlive dziala? XDD
            return false;
        if(enemy.team == this.team)
            return false;
        if(supplies.ammo <= 0){
            System.out.println(id + " has no ammo to attack " + enemy.id + "!");
            return false;
        }
        enemy.takeHit(this.damage);
        supplies.ammo--;
        System.out.println(id + " attacked " + enemy.id + " for " + this.damage + " to " + enemy.getSupplies().hp);
        numOfAttacks++;
        return true;
    }
    void takeHit(int damage){
        if(!isAlive)
            return;
        if(damage >= supplies.hp) {
            die();
            System.out.println(this.id + " was attacked and died!");
        }
        else{
            supplies.hp-=damage;
            System.out.println(this.id + " was attacked for " + damage + ", now hp: " + supplies.hp);
        }
    }
    private void die(){
        if(!isAlive)
            return;
        supplies.hp = 0;
        supplies.ammo = 0;
        isAlive = false;
        field.setTakenByArmy(-1);
        //field = null;
        if(type.equals("infantry")) {
            numOfALiveInfantry--;
            numOfAliveUnits--;
        }
        else if(type.equals("tank")) {
            numOfALiveTanks--;
            numOfAliveUnits--;
        }
        if(team == 1)
            numOfAliveTeam1--;
        else if(team == 2)
            numOfAliveTeam2--;
    }

    @Override
    public String toString(){
        return type + ", " + id + (isAlive?" alive ":" dead ") + " pos[" + field.pos_x + "," + field.pos_y + "]" + "\n";
    }

}
