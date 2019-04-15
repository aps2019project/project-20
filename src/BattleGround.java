import java.util.ArrayList;
enum Effect{
    POISON,
    FIRE,
    HOLY;
}
public class BattleGround {
    private ArrayList<ArrayList<Integer>> ground;
    private ArrayList <Effect> effect;
    private ArrayList<ArrayList<Integer>> effectposition;
    private ArrayList<Item> groundItem;
}

