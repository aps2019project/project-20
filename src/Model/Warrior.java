package Model;

import java.util.ArrayList;

public abstract class Warrior extends Card {
    private int range;
    private int HP;
    private int AP;
    private boolean isDisarm;
    private boolean isPoison;
    private boolean isStun;
    private boolean isAttackedThisTurn;
    private int lifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private AttackType attackType;
    private ArrayList<BufferOfSpells> bufferEffected = new ArrayList<>();
    private Flag collectedFlag = null;
    private boolean isMovedThisTurn = false;

    public Warrior() {
    }

    public Warrior(String name, String desc, int price, int ID, int AP, int HP, int MP, int range, boolean doesHaveAction, AttackType attackType) {
        super(name, desc, price, ID, MP, doesHaveAction);
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

    public Flag getCollectedFlag() {
        return collectedFlag;
    }

    public void setCollectedFlag(Flag collectedFlag) {
        this.collectedFlag = collectedFlag;
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

    public boolean isStun() {
        return isStun;
    }

    public void setStun(boolean stun) {
        isStun = stun;
    }

    public boolean isDisarm() {
        return isDisarm;
    }

    public void setDisarm(boolean disarm) {
        isDisarm = disarm;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public boolean isAttackedThisTurn() {
        return isAttackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.isAttackedThisTurn = attackedThisTurn;
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

    public boolean isMovedThisTurn() {
        return isMovedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.isMovedThisTurn = movedThisTurn;
    }
}