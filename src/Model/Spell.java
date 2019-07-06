package Model;

public class Spell extends Card {
    private int squareSideLength;
    private TargetType targetType;
    private String actionBarImageAddress;
    private String activeImageAddress;
    private String effectImageAddress;

    public enum TargetType {
        ENEMY, PLAYER, CELLS, WHOLE_OF_GROUND
    }

    public Spell(String name, String desc, int price, int ID, int MP, TargetType targetType) {
        super(name, desc, price, ID, MP, true);
        this.targetType = targetType;
        this.squareSideLength = 1;
        setImageAddresses(name);
    }

    public Spell(String name, String desc, int price, int ID, int MP, TargetType targetType, int squareSideLength) {
        this(name, desc, price, ID, MP, targetType);
        this.squareSideLength = squareSideLength;
    }

    public Spell(String name, String desc, int price, int ID, int MP, TargetType targetType, int squareSideLength, BufferOfSpells buff, boolean isTargetFriend) {
        super(name, desc, price, ID, MP, true, buff, isTargetFriend);
        this.targetType = targetType;
        this.squareSideLength = squareSideLength;
        setImageAddresses(name);
    }

    public int getSquareSideLength() {
        return squareSideLength;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public String getActionBarImageAddress() {
        return actionBarImageAddress;
    }

    public String getActiveImageAddress() {
        return activeImageAddress;
    }

    public String getEffectImageAddress() {
        return effectImageAddress;
    }

    @Override
    protected void setImageAddresses(String name) {
        actionBarImageAddress = "file:images/cards/spell/" + name + "/" + name + "_actionbar.gif";
        activeImageAddress = "file:images/cards/spell/" + name + "/" + name + "_active.gif";
        effectImageAddress = "file:images/cards/spell/" + name + "/" + name + "_effect.gif";
    }
}
