package Model;

public class Hero extends Card{
    private int range;
    private Buffer specialPower;
    private int AP;
    private int HP;
    private Spell spell;
    private int coolDown;
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

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
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
