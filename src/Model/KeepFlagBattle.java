package Model;

import java.lang.reflect.Array;
import java.util.List;

public class KeepFlagBattle extends Battle {
    private Flag singleFlag;
    private static final int DURATION_TO_WIN = 7;

    public KeepFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        singleFlag = Flag.insertFlagsInBattleGround(this,1)[0];
    }

    @Override
    protected void collectFlag(int playerIndex, Warrior warrior, int flagX, int flagY) {
        warrior.setCollectedFlag(((Flag) battleGround.getGround().get(flagY).get(flagX)));
        battleGround.getGround().get(flagY).set(flagX, null);
    }

    @Override
    public int endGame() {
        if (singleFlag.getKeptDuration() >= DURATION_TO_WIN) {
            if (singleFlag.getOwner() == players[0]) {
                players[0].setBudget(players[0].getBudget() + reward);
                return FIRST_PLAYER_WIN;
            }
            else if (singleFlag.getOwner() == players[1]) {
                players[1].setBudget(players[0].getBudget() + reward);
                return SECOND_PLAYER_WIN;
            }
        }
        if (singleFlag.getOwner() != null)
            singleFlag.setKeptDuration(singleFlag.getKeptDuration() + 1);
        return UNFINISHED_GAME;
    }

    public Flag getSingleFlag() {
        return singleFlag;
    }

    public static void gameInfo(){}
    public static void help(){}
    //public int endGame(){}

}
