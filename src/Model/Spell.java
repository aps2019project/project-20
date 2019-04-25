package Model;

import java.util.ArrayList;

public class Spell extends Card {
    private int squareSideLength;

    public enum TargetType {
        ENEMY, PLAYER, CELLS
    }

    private TargetType targetType;

    public Spell(String name,String desc, int price, int ID,int MP, TargetType targetType,String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setxInGround(0);
        this.setyInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.setMP(MP);
        this.targetType = targetType;
    }

}
