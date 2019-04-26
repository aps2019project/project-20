package Model;

public class Hero extends Card{
    private int range;
    // extending 10 heroes
   // private Spell specialPower;
    private int AP;
    private int LifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private int HP;
    private int coolDown;
    private AttackType attackType;

    public Hero(String name,String desc, int price, int ID,int range, int AP, int HP,int MP, int coolDown, AttackType attackType,String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setXInGround(0);
        this.setYInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.range = range;
        this.AP = AP;
        this.HP = HP;
        this.setMP(MP);
        this.coolDown = coolDown;
        this.attackType = attackType;
    }
    public int getAmountOfChangedAP() {
        return amountOfChangedAP;
    }

    public void setAmountOfChangedAP(int amountOfChangedAP) {
        this.amountOfChangedAP = amountOfChangedAP;
    }
    public int getLifeTimeChangedAP() {
        return LifeTimeChangedAP;
    }

    public void setLifeTimeChangedAP(int lifeTimeChangedAP) {
        LifeTimeChangedAP = lifeTimeChangedAP;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
//
//    public Spell getSpecialPower() {
//        return specialPower;
//    }
//
//    public void setSpecialPower(Spell specialPower) {
//        this.specialPower = specialPower;
//    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }
}
