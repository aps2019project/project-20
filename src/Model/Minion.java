package Model;

public class Minion extends Card{
    private int range;
    private Buffer specialPower;
    private int HP;
    private int AP;
    private AttackType attackType;


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Buffer getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Buffer specialPower) {
        this.specialPower = specialPower;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }



}
