package Model;

import java.util.ArrayList;

public class BattleGround {
    private final static int rows = 5;
    private final static int cols = 9;

    public enum CellsEffect {
        NOTHING,
        POISON,
        FIRE,
        HOLY
    }

    private ArrayList<ArrayList<Asset>> ground = new ArrayList<>();
    private ArrayList<ArrayList<CellsEffect>> effectsPosition = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> effectsLifeTimePosition = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> getEffectsLifeTimePosition() {
        return effectsLifeTimePosition;
    }

    public static int getRows() {
        return rows;
    }

    public static int getCols() {
        return cols;
    }

    public ArrayList<ArrayList<Asset>> getGround() {
        return ground;
    }

    public void setGround(ArrayList<ArrayList<Asset>> ground) {
        this.ground = ground;
    }

    public ArrayList<ArrayList<CellsEffect>> getEffectsPosition() {
        return effectsPosition;
    }

    public void setEffectsPosition(ArrayList<ArrayList<CellsEffect>> effectsPosition) {
        this.effectsPosition = effectsPosition;
    }
}

