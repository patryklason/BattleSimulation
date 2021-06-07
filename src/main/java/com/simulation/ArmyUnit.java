package com.simulation;


import static com.simulation.SimulationConstants.*;

/**
 * ArmyUnit contains infantry and tanks, which have different parameters values such as maxHp, hp, maxAmmo, ammo, team.
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
    private static int deadArmy;

    /**
     * resets the stats to start new simulation
     */
    public static void resetArmyStats(){
        numOfAliveUnits = 0;
        numOfALiveInfantry = 0;
        numOfALiveTanks = 0;
        numOfAliveTeam1 = 0;
        numOfAliveTeam2 = 0;
        numOfBattles = 0;
        numOfAttacks = 0;
        deadArmy = 0;
    }

    public ArmyUnit(int id, String type, Map map, int movement, int team){
        super(id, type, map, movement);

        isAlive = true;
        this.team = team;

        numOfAliveUnits++;
        if(team == 1) numOfAliveTeam1++;
        else if(team == 2) numOfAliveTeam2++;

        if(type.equals("infantry")) {
            int hp = INFANTRY_HP, ammo = INFANTRY_AMMO;
            supplies = new Supplies(hp, ammo);
            maxHp = hp;
            maxAmmo = ammo;
            this.damage = INFANTRY_DMG;

            numOfALiveInfantry++;
        }
        else if(type.equals("tank")) {
            int hp = TANK_HP, ammo = TANK_AMMO;
            supplies = new Supplies(hp, ammo);
            maxHp = hp;
            maxAmmo = ammo;
            this.damage = TANK_DMG;

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

    public static int getDeadArmy() { return deadArmy; }

    public static void setDeadArmy(int deadArmy) { ArmyUnit.deadArmy = deadArmy; }

    public Supplies getSupplies() {
        return supplies;
    }

    public boolean getAlive(){
        return isAlive;
    }

    /**
     * take supplies from base or moving base
     * <p>
     *     if hp is bigger than max hp, then unit's hp will be set to max (same refers
     *     to ammo)
     * </p>
     * @param hp - hp to be added
     * @param ammo - hp to be added
     */
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

    /**
     * Allows ArmyUnit to move and interact with other Units. If the new searched field is free, the unit will take it.
     * If it is not free, it will trigger different interactions based on type of unit that occupies the field.
     * @param map map on which ArmyUnit will move
     * @param uc list of units
     */
    @Override
    void move(Map map, UnitCreator uc){
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
                if (attack(enemy))
                    numOfBattles++;
                enemy.attack(this);
                if (!enemy.isAlive) {
                    takeField(newField);
                }

        }
        if(newField.getTakenByNeutral() > -1){      //then handle interaction with NeutralUnits
            Unit neutralUnit = uc.getUnitList().get(newField.getTakenByNeutral());

            if(neutralUnit instanceof Trap){
                ((Trap) neutralUnit).attack(this);
            }
            else if(neutralUnit instanceof MovingBase){
                ((MovingBase) neutralUnit).resupply(this);
            }
            else if(neutralUnit instanceof Base){
                ((Base) neutralUnit).resupply(this);
            }
        }
    }

    private void takeField(Field newField){
        if (newField.getTakenByArmy() != -1)
            return;
        field.setTakenByArmy(-1);
        field = newField;
        field.setTakenByArmy(id);
    }

    /**
     * Allows ArmyUnit to attack other ArmyUnit. The attack will fail if any of units is already dead, the units are in the
     * same team or unit has not enough ammo to attack.
     * @param enemy ArmyUnit that will be attacked.
     * @return if the attack was successful
     */
    private boolean attack(ArmyUnit enemy){
        if(!(isAlive && enemy.isAlive))        //czemu enemy.isAlive dziala? XDD
            return false;
        if(enemy.team == this.team)
            return false;
        if(supplies.ammo <= 0){
            return false;
        }
        enemy.takeHit(this.damage);
        supplies.ammo--;
        numOfAttacks++;
        return true;
    }

    /**
     * Lowers unit hp based on damage value or makes unit die if damage is sufficient.
     * @param damage value subtracted from unit hp.
     */
    void takeHit(int damage){
        if(!isAlive)
            return;
        if(damage >= supplies.hp) {
            die();
        }
        else{
            supplies.hp-=damage;
        }
    }

    /**
     * sets the unit dead and sets the field free. Changes death stats.
     */
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
            deadArmy=1;
        }
        else if(type.equals("tank")) {
            numOfALiveTanks--;
            numOfAliveUnits--;
            deadArmy=2;
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
