package Model;

import java.util.HashMap;
import static Model.Item.State.*;

public class Item extends Asset {
    private HashMap<State, String> imageAddresses = new HashMap<>();

    public enum State {
        in_deck, actionbar, active
    }

    //price=0 ----> item is collectable
    public Item(String name, String desc, int price, int ID) {
        super(name, desc, price, ID, true);
        setImageAddresses(name);
    }

    public Item(String name, String desc, int ID) {
        super(name, desc, 0, ID, true);
        setImageAddresses(name);
    }

    public HashMap<State, String> getImageAddresses() {
        return imageAddresses;
    }

    protected void setImageAddresses(String name) {
        //TODO check the following directory carefully.
        this.imageAddresses.put(in_deck, "file:images/cards/item/" + name + "/" + name + ".png");
        for (State state : State.values()) {
            if (state != in_deck)
                this.imageAddresses.put(state, "file:images/cards/item/" + name + "/" + name + "_" + state.toString() + ".gif");
        }
    }

}
