package Model;

import java.util.ArrayList;

public class Minion extends Warrior{
    private ArrayList<Card> attackedCards = new ArrayList<>();
    private ArrayList<Integer>  multiplicityOfEachAttackedCard = new ArrayList<>();
    public enum ActivateTimeOfSpecialPower{
        NOTHING,ON_RESPAWN,PASSIVE,ON_DEATH,ON_ATTACK,ON_DEFEND,COMBO
    }
    private ActivateTimeOfSpecialPower activateTimeOfSpecialPower;
    private boolean isDisarm=true;
    private boolean isPoison =true;

    public boolean isDisarm() {
        return isDisarm;
    }

    public void setDisarm(boolean disarm) {
        isDisarm = disarm;
    }

    public boolean isPoision() {
        return isPoison;
    }

    public void setPoision(boolean poision) {
        isPoison = poision;
    }

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType, ActivateTimeOfSpecialPower activateTimeOfSpecialPower, String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setXInGround(0);
        this.setYInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.setMP(MP);
        this.activateTimeOfSpecialPower=activateTimeOfSpecialPower;
    }

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType) {
        super(name, desc, price, ID, AP, HP, range, attackType, false);
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





}
