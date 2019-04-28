package Model;

import java.util.ArrayList;

public class Minion extends Card{
    private int range;
    private int HP;
    private int AP;
    private int LifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private AttackType attackType;
    private ArrayList<Card> attackedCards = new ArrayList<>();
    private ArrayList<Integer>  multiplicityOfEachAttackedCard = new ArrayList<>();
    public enum ActivateTimeOfSpecialPower{
        NOTHING,ON_RESPAWN,PASSIVE,ON_DEATH,ON_ATTACK,ON_DEFEND,COMBO
    }
    private ActivateTimeOfSpecialPower activateTimeOfSpecialPower;
    private boolean isDisarm=true;
    private boolean isPoision=true;

    public boolean isDisarm() {
        return isDisarm;
    }

    public void setDisarm(boolean disarm) {
        isDisarm = disarm;
    }

    public boolean isPoision() {
        return isPoision;
    }

    public void setPoision(boolean poision) {
        isPoision = poision;
    }

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType, ActivateTimeOfSpecialPower activateTimeOfSpecialPower, String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setxInGround(0);
        this.setyInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.range = range;
        this.AP = AP;
        this.HP = HP;
        this.setMP(MP);
        this.attackType = attackType;
        this.activateTimeOfSpecialPower=activateTimeOfSpecialPower;
    }

    public ArrayList<Card> getAttackedCards() {
        return attackedCards;
    }

    public void setAttackedCards(ArrayList<Card> attackedCards) {
        this.attackedCards = attackedCards;
    }

    public ArrayList<Integer> getMultiplicityOfEachAttackedCard() {
        return multiplicityOfEachAttackedCard;
    }

    public void setMultiplicityOfEachAttackedCard(ArrayList<Integer> multiplicityOfEachAttackedCard) {
        this.multiplicityOfEachAttackedCard = multiplicityOfEachAttackedCard;
    }

    public ActivateTimeOfSpecialPower getActivateTimeOfSpecialPower() {
        return activateTimeOfSpecialPower;
    }

    public void setActivateTimeOfSpecialPower(ActivateTimeOfSpecialPower activateTimeOfSpecialPower) {
        this.activateTimeOfSpecialPower = activateTimeOfSpecialPower;
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


    public int getHP() {
        return HP;
    }

    public void changeHP(int valueOfChange) {
        this.HP += valueOfChange;
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
