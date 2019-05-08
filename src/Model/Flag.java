package Model;

import java.time.Year;
import static Model.Buffer.randomNumberGenerator;

public class Flag extends Asset {
    private int keptDuration = 0;

    public Flag(){
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
                if (flag != null && flag.getXInGround() == randomX || flag.getYInGround() == randomY)
                    continue outer;
            }
            break outer;
        }
        xInGround = randomX;
        yInGround = randomY;
        owner = null;
    }

    public int getKeptDuration() {
        return keptDuration;
    }

    public void setKeptDuration(int keptDuration) {
        this.keptDuration = keptDuration;
    }
}
