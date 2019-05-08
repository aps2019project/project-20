package Model;

import Exceptions.AssetNotFoundException;

import java.util.ArrayList;

public class BattleGround {
    private final static int ROWS = 5;
    private final static int COLUMNS = 9;
    // How to fix the error. We need to input effectLifetime of any member of CellsEffect.
    public enum CellsEffect {
        NOTHING, POISON, FIRE, HOLY;
    }


    private ArrayList<ArrayList<Asset>> ground;
    private ArrayList<ArrayList<CellsEffect>> effectsPosition = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> effectsLifeTimePosition = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> getEffectsLifeTimePosition() {
        return effectsLifeTimePosition;
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getColumns() {
        return COLUMNS;
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

    public Asset searchAssetInBattleGround(Account owner,String cardName){
        for (ArrayList<Asset> assets : ground) {
            for (Asset asset : assets) {
                if(asset.getName().equals(cardName) && owner==asset.getOwner()){
                    return asset;
                }
            }
        }
        throw new AssetNotFoundException();
    }
}

