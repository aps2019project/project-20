package Model;

import java.util.ArrayList;

public class CollectFlagBattle extends Battle{
    private static final int DEFAULT_NUMBER_OF_FLAGS = 7;
    private static int numberOfFlags;
    private Flag[] flags;
    private int[] numberOfPlayersCollectedFlags = new int[]{0, 0};


    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        numberOfFlags = DEFAULT_NUMBER_OF_FLAGS;
        flags = Flag.insertFlagsInBattleGround(this,numberOfFlags);
    }

    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward, int numberOfFlags) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        CollectFlagBattle.numberOfFlags = numberOfFlags;
        flags = Flag.insertFlagsInBattleGround(this,numberOfFlags);
    }

    @Override
    protected void collectFlag(int playerIndex, Warrior warrior, int flagX, int flagY) {
        warrior.setCollectedFlag(((Flag) battleGround.getGround().get(flagY).get(flagX)));
        battleGround.getGround().get(flagY).set(flagX, null);
        numberOfPlayersCollectedFlags[playerIndex] ++;
    }

    @Override
    public int endGame() {
        if (numberOfPlayersCollectedFlags[0] >= numberOfFlags / 2) {
            players[0].setBudget(players[0].getBudget() + reward);
            return FIRST_PLAYER_WIN;
        }
        else if (numberOfPlayersCollectedFlags[1] >= numberOfFlags / 2) {
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

    public int[] getNumberOfPlayersCollectedFlags() {
        return numberOfPlayersCollectedFlags;
    }

    //    public static int getNumberOfPlayerFlags(Account player, Flag[] flags){
//        int num=0;
//        for (Flag flag : flags) {
//            if(flag.owner==player){
//                num++;
//            }
//        }
//        return num;
//    }

    public void changeNumberOfPlayerCollectedFlags(int playerIndex, int value) {
        numberOfPlayersCollectedFlags[playerIndex] += value;
    }
}