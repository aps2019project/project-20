package Model;

import java.util.ArrayList;

public class BattleGround {
    enum Effect{
        POISON,
        FIRE,
        HOLY;
    }
    private ArrayList<ArrayList<Integer>> ground;
    private ArrayList <Effect> effect;
    private ArrayList<ArrayList<Integer>> effectposition;
    private ArrayList<Item> groundItem;
}

