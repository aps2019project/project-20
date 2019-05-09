package Model;

import Exceptions.AssetNotFoundException;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import static Model.BattleGround.CellsEffect.*;

public class BattleGround {
    private final static int ROWS = 5;
    private final static int COLUMNS = 9;
    private ArrayList<ArrayList<Asset>> ground = new ArrayList<>();
    private ArrayList<ArrayList<CellsEffect>> effectsPosition = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> effectsLifeTimePosition = new ArrayList<>();

    public enum CellsEffect {
        NOTHING, POISON, FIRE, HOLY;
    }

    public BattleGround() {
        for (int i = 0; i < ROWS; i++){
            ground.add(new ArrayList<>());
            effectsPosition.add(new ArrayList<>());
            effectsLifeTimePosition.add(new ArrayList<>());
            for (int j = 0; j < COLUMNS; j++) {
                ground.get(i).add(null);
                effectsPosition.get(i).add(NOTHING);
                effectsLifeTimePosition.get(i).add(-1);
            }
        }
    }

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
                if(asset!=null && asset.getName().equals(cardName) && owner==asset.getOwner()){
                    return asset;
                }
            }
        }
        throw new AssetNotFoundException();
    }
}

