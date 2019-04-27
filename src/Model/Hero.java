package Model;

public class Hero extends Card{
    private int range;
    // extending 10 heroes
    // private Spell specialPower;
    private int AP;
    private int HP;
    private int coolDown;
    private AttackType attackType;

    public Hero(String name, int price, int ID, int range, int AP, int HP, boolean doesHaveAction, AttackType attackType) {
        super(name, name, price, ID, doesHaveAction);
        this.setRange(range);
        this.setAP(AP);
        this.changeHP(HP);
        this.setAttackType(attackType);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, int MP, int coolDown, AttackType attackType) {
        this(name, price, ID, range, AP, HP, true, attackType);
        this.setMP(MP);
        this.setCoolDown(coolDown);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, AttackType attackType) {
        this(name, price, ID, range, AP, HP, false, attackType);
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

    public void changeHP(int valueOfChange) {
        this.HP += valueOfChange;
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
