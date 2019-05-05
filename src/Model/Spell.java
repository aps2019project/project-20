package Model;

public class Spell extends Card {
    private int squareSideLength;

    public enum TargetType {
        ENEMY, PLAYER, CELLS, WHOLE_OF_GROUND
    }

    private TargetType targetType;

    public Spell(String name,String desc, int price, int ID,int MP, TargetType targetType,String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setXInGround(0);
        this.setYInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.setMP(MP);
        this.targetType = targetType;
        this.squareSideLength = 0;
    }

    public Spell(String name,String desc, int price, int ID,int MP, TargetType targetType,int squareSideLength,String action) {
        this.setName(name);
        this.setDesc(desc);
        this.setPrice(price);
        this.setID(ID);
        this.setXInGround(0);
        this.setYInGround(0);
        this.setOwner(null);
        this.setAction(action);
        this.setMP(MP);
        this.targetType = targetType;
        this.squareSideLength = squareSideLength;
    }

}
