package Model;

import java.util.HashMap;
import static Model.Spell.State.*;

public class Spell extends Card {
    private int squareSideLength;
    private TargetType targetType;
    private HashMap<State, String> imageAddresses = new HashMap<>();

    public enum TargetType {
        ENEMY, PLAYER, CELLS, WHOLE_OF_GROUND
    }

    public enum State {
        in_deck, actionbar, active, effect
    }

    public Spell(String name,String desc, int price, int ID,int MP, TargetType targetType) {
        super(name, desc, price, ID, MP, true);
        this.targetType = targetType;
        this.squareSideLength = 0;
        setImageAddresses(name);
    }

    public Spell(String name,String desc, int price, int ID,int MP, TargetType targetType,int squareSideLength) {
        super(name, desc, price, ID, MP, true);
        this.setXInGround(0);
        this.setYInGround(0);
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

    public HashMap<State, String> getImageAddresses() {
        return imageAddresses;
    }

    @Override
    protected void setImageAddresses(String name) {
        this.imageAddresses.put(in_deck, "file:images/cards/spell/" + name + "/" + name + ".png");
        for (State state : State.values()) {
            if (state != in_deck)
                this.imageAddresses.put(state, "file:images/cards/spell/" + name + "/" + name + "_" + state.toString() + ".gif");
        }
    }
}
