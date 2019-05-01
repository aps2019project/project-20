package Model;

public class CollectFlagBattle extends Battle{
    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstplayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstplayerDeck, secondPlayerDeck, battleGround, reward);
    }

    public static void gameInfo(){}
    public static void help(){}
    public static void endGame(){}

}
