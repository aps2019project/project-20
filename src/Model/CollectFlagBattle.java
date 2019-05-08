package Model;

import java.util.ArrayList;

public class CollectFlagBattle extends Battle{
    private static int numberOfFlags = 7;
    private Flag[] flags = new Flag[numberOfFlags];

    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        for (int i = 0; i <  numberOfFlags; i++)
            flags[i] = new Flag(flags, i);
    }

    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward, int numberOfFlags) {
        this(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        for (int i = 0; i <  numberOfFlags; i++)
            flags[i] = new Flag(flags, i);
        CollectFlagBattle.numberOfFlags = numberOfFlags;
    }

    @Override
    public int endGame() {
        int numberOfFirstPlayersFlags = 0;
        int numberOfSecondPlayersFlags = 0;
        for (Flag flag: flags){
            if (flag.getOwner() == players[0])
                numberOfFirstPlayersFlags++;
            else if (flag.getOwner() == players[1])
                numberOfSecondPlayersFlags++;
        }
        if (numberOfFirstPlayersFlags >= numberOfFlags / 2) {
            players[0].setBudget(players[0].getBudget() + PRIZE);
            return FIRST_PLAYER_WIN;
        }
        else if (numberOfSecondPlayersFlags >= numberOfFlags / 2) {
            players[0].setBudget(players[1].getBudget() + PRIZE);
            return SECOND_PLAYER_WIN;
        }
        return UNFINISHED_GAME;
    }

    //Getters and Setters
    public static int getNumberOfFlags() {
        return numberOfFlags;
    }

    public static void gameInfo(){}
    public static void help(){}
}