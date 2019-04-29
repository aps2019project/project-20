package Model;

import java.util.ArrayList;

public class Warrior extends Card {
    private int range;
    private int HP;
    private int AP;
    private int lifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private AttackType attackType;

    private ArrayList<BufferOfSpells> bufferEffected = new ArrayList<>();

    public Warrior(){}

    public Warrior(String name, String desc, int price, int ID, int AP, int HP, int range, AttackType attackType, boolean doesHaveAction) {
        super(name, desc, price, ID, doesHaveAction);
        this.AP = AP;
        this.HP = HP;
        this.range = range;
        this.attackType = attackType;
    }

    public ArrayList<BufferOfSpells> getBufferEffected() {
        return bufferEffected;
    }

    public void setBufferEffected(ArrayList<BufferOfSpells> bufferEffected) {
        this.bufferEffected = bufferEffected;
    }

    public int getRange() {
        return range;
    }

    public int getHP() {
        return HP;
    }

    public int getAP() {
        return AP;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void changeAP(int valueOfChange) {
        this.AP += valueOfChange;
    }

    public void changeHP(int valueOfChange) {
        this.HP += valueOfChange;
    }
    public int getAmountOfChangedAP() {
        return amountOfChangedAP;
    }

    public void setAmountOfChangedAP(int amountOfChangedAP) {
        this.amountOfChangedAP = amountOfChangedAP;
    }

    public int getLifeTimeChangedAP() {
        return lifeTimeChangedAP;
    }

    public void setLifeTimeChangedAP(int lifeTimeChangedAP) {
        this.lifeTimeChangedAP = lifeTimeChangedAP;
    }

}