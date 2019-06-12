package Model;

import Exceptions.AssetNotFoundException;
import java.util.ArrayList;
import static Model.BufferOfSpells.Type.*;

public class BattleGround {
    private final static int ROWS = 5;
    private final static int COLUMNS = 9;
    private ArrayList<ArrayList<Asset>> ground = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<CellEffect>>> effectsPosition = new ArrayList<>();
//    private ArrayList<ArrayList<Integer>> effectsLifeTimePosition = new ArrayList<>();

    public enum CellEffect {
        POISON, FIRE, HOLY;
        private int effectLifeTime = -1;

        public CellEffect setEffectLifeTime(int effectLifeTime) {
            this.effectLifeTime = effectLifeTime;
            return this;
        }

        public int getEffectLifeTime() {
            return effectLifeTime;
        }
    }

    public BattleGround() {
        for (int i = 0; i < ROWS; i++) {
            ground.add(new ArrayList<>());
            effectsPosition.add(new ArrayList<>());
//            effectsLifeTimePosition.add(new ArrayList<>());
            for (int j = 0; j < COLUMNS; j++) {
                ground.get(i).add(null);
                effectsPosition.get(i).add(new ArrayList<>());
//                effectsPosition.get(i).get(j).add(NOTHING);
//                effectsLifeTimePosition.get(i).add(-1);
            }
        }
    }
    
    public void applyCellsEffect(Account opponent) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                ArrayList<CellEffect> cellEffectsOfCell = effectsPosition.get(i).get(j);
                for (CellEffect cellEffect: cellEffectsOfCell) {
//                    if (cellEffect == NOTHING)
//                        continue;
                    if (ground.get(i).get(j) instanceof Warrior && ground.get(i).get(j).getOwner() == opponent && cellEffect.getEffectLifeTime() > 0) {
                        Warrior targetWarrior = (Warrior) ground.get(i).get(j);
                        cellEffect.setEffectLifeTime(cellEffect.getEffectLifeTime() - 1);
                        switch (cellEffect) {
                            case FIRE:
                                targetWarrior.changeHP(-2);
                                break;
                            case HOLY:
                                targetWarrior.getBufferEffected().add(new BufferOfSpells(1, HOLY_BUFF));
                                break;
                            case POISON:
                                targetWarrior.getBufferEffected().add(new BufferOfSpells(3, POISON_BUFF));
                        }
                    }
                }
            }
        }
    }

//    public ArrayList<ArrayList<Integer>> getEffectsLifeTimePosition() {
//        return effectsLifeTimePosition;
//    }

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

    public ArrayList<ArrayList<ArrayList<CellEffect>>> getEffectsPosition() {
        return effectsPosition;
    }

//    public void setEffectsPosition(ArrayList<ArrayList<CellEffect>> effectsPosition) {
//        this.effectsPosition = effectsPosition;
//    }

    public Asset searchAssetInBattleGround(Account owner, String cardName) {
        for (ArrayList<Asset> assets : ground) {
            for (Asset asset : assets) {
                if (asset != null && !(asset instanceof Flag) && asset.getName().equals(cardName) && owner == asset.getOwner())
                    return asset;
            }
        }
        throw new AssetNotFoundException();
    }
}

