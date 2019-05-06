package Model;

public class NoFlagBattle extends Battle {
    public NoFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstplayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstplayerDeck, secondPlayerDeck, battleGround, reward);
    }

    public static void gameInfo(){}
    public static void help(){}
//    public int endGame(){}
}
