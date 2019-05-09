package Model;

import java.util.ArrayList;

public class CollectFlagBattle extends Battle{
    private static final int DEFAULT_NUMBER_OF_FLAGS = 7;
    private static int numberOfFlags;
    private Flag[] flags;

    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        numberOfFlags = DEFAULT_NUMBER_OF_FLAGS;
        flags = new Flag[numberOfFlags];
        for (int i = 0; i < numberOfFlags; i++) {
            flags[i] = new Flag(flags, i);
            battleGround.getGround().get(flags[i].getYInGround()).set(flags[i].getXInGround(), flags[i]);
        }
    }

    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward, int numberOfFlags) {
        this(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        flags = new Flag[numberOfFlags];
        for (int i = 0; i < numberOfFlags; i++) {
            flags[i] = new Flag(flags, i);
            battleGround.getGround().get(flags[i].getYInGround()).set(flags[i].getXInGround(), flags[i]);
        }
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
            players[0].setBudget(players[0].getBudget() + reward);
            return FIRST_PLAYER_WIN;
        }
        else if (numberOfSecondPlayersFlags >= numberOfFlags / 2) {
            players[0].setBudget(players[1].getBudget() + reward);
            return SECOND_PLAYER_WIN;
        }
        return UNFINISHED_GAME;
    }

    public Flag[] getFlags() {
        return flags;
    }

    //Getters and Setters
    public static int getNumberOfFlags() {
        return numberOfFlags;
    }

    public static int getNumberOfPlayerFlags(Account player,Flag[] flags){
        int num=0;
        for (Flag flag : flags) {
            if(flag.owner==player){
                num++;
            }
        }
        return num;
    }
}