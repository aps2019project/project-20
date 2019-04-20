package Model;

import java.util.ArrayList;

public class BattleGround {
    enum Effect{
        POISON,
        FIRE,
        HOLY;
    }
    private ArrayList<ArrayList<Asset>> ground;
    private ArrayList<Effect> effects;
    private ArrayList<ArrayList<Integer>> effectsPosition;
// what is this?    private ArrayList<Item> groundItem;
}

