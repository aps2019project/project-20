package Model;

public class Spell extends Card {
    private int squareSideLength;
    private TargetType targetType;

    public enum TargetType {
        ENEMY, PLAYER, CELLS, WHOLE_OF_GROUND
    }

    public Spell(String name, String desc, int price, int ID, int MP, TargetType targetType) {
        super(name, desc, price, ID, MP, true);
        this.targetType = targetType;
        this.squareSideLength = 0;
    }

    public Spell(String name, String desc, int price, int ID, int MP, TargetType targetType, int squareSideLength) {
        super(name, desc, price, ID, MP, true);
        this.setXInGround(0);
        this.setYInGround(0);
        this.targetType = targetType;
        this.squareSideLength = squareSideLength;
    }

    public int getSquareSideLength() {
        return squareSideLength;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}
