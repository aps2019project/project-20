package Model;

import java.util.ArrayList;

public class Minion extends Warrior {
    private ArrayList<Card> attackedCards = new ArrayList<>();
    private ArrayList<Integer> multiplicityOfEachAttackedCard = new ArrayList<>();
    private ActivateTimeOfSpecialPower activateTimeOfSpecialPower;

    public enum ActivateTimeOfSpecialPower {
        ON_SPAWN, PASSIVE, ON_DEATH, ON_ATTACK, ON_DEFEND, COMBO
    }

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType, ActivateTimeOfSpecialPower activateTimeOfSpecialPower) {
        super(name, desc, price, ID, AP, HP, MP, range, true, attackType);
        this.activateTimeOfSpecialPower = activateTimeOfSpecialPower;
    }

    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType) {
        super(name, desc, price, ID, AP, HP, MP, range, false, attackType);
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
