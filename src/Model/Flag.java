package Model;

import java.util.ArrayList;
import static Model.Battle.differentRandomNumbersGenerator;
import static Model.Buffer.randomNumberGenerator;

public class Flag extends Asset {
    private int keptDuration = 0;

    public Flag(){
    }


    public Flag(int xInGround,int yInGround){
        this.xInGround = xInGround;
        this.yInGround = yInGround;
    }

    public Flag(Flag[] currentFlags, int cursor) {
        int randomY;
        int randomX;
        outer: while (true) {
            randomY = randomNumberGenerator(BattleGround.getRows());
            if (cursor < CollectFlagBattle.getNumberOfFlags() / 2)
                randomX = randomNumberGenerator(BattleGround.getColumns() / 2);
            else if (cursor > CollectFlagBattle.getNumberOfFlags() / 2)
                randomX =  randomNumberGenerator(BattleGround.getColumns() / 2) + BattleGround.getColumns() / 2 + 1;
            else
                randomX = BattleGround.getColumns() / 2 + 1;
            for (Flag flag: currentFlags) {
                if (flag != null && flag.getXInGround() == randomX && flag.getYInGround() == randomY)
                    continue outer;
            }
            break outer;
        }
        xInGround = randomX;
        yInGround = randomY;
        owner = null;
    }

    public static Flag[] insertFlagsInBattleGround(Battle battle,int numberOfFlags) {
        Flag[] flags = new Flag[numberOfFlags];
        int[] invalidCells =new int[battle.getItemsCoordinates().size() + 2];
        invalidCells[0]=18;
        invalidCells[1]=26;
        for(int i = 0; i < battle.getItemsCoordinates().size(); i++)
            invalidCells[i + 2] = battle.getItemsCoordinates().get(i);
        ArrayList<Integer> temp = differentRandomNumbersGenerator(flags.length, BattleGround.getRows() * BattleGround.getColumns(), invalidCells);
        for (int i = 0; i < flags.length; i++) {
            Flag tempFlag = new Flag(temp.get(i) % BattleGround.getColumns(), temp.get(i) / BattleGround.getColumns());
            flags[i] = tempFlag;
            battle.getBattleGround().getGround().get(flags[i].getYInGround()).set(flags[i].getXInGround(), flags[i]);
        }
        return flags;
    }

    public int getKeptDuration() {
        return keptDuration;
    }

    public void setKeptDuration(int keptDuration) {
        this.keptDuration = keptDuration;
    }

    @Override
    protected void setImageAddresses(String name) {
        //TODO must be implemented.
    }
}
