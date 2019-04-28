package Model;

import java.util.ArrayList;

public class Minion extends Warrior{
    private int lifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private ArrayList<Card> attackedCards = new ArrayList<>();
    private ArrayList<Integer>  multiplicityOfEachAttackedCard = new ArrayList<>();
    public enum ActivateTimeOfSpecialPower{
        ON_RESPAWN,PASSIVE,ON_DEATH,ON_ATTACK,ON_DEFEND,COMBO
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

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType, ActivateTimeOfSpecialPower activateTimeOfSpecialPower) {
        super(name, desc, price, ID, AP, HP, range, attackType, true);
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
