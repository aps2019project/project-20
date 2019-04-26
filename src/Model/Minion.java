package Model;

import java.util.ArrayList;

public class Minion extends Card {
    private int range;
    private int HP;
    private int AP;
    private AttackType attackType;
    private ArrayList<Card> attackedCards = new ArrayList<>();
    private ArrayList<Integer> multiplicityOfEachAttackedCard = new ArrayList<>();


    public Minion(String name, String desc, int price, int ID, int range, int AP, int HP, int MP, AttackType attackType, String action) {
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
        this.attackType = attackType;
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
