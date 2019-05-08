package Model;

public class KeepFlagBattle extends Battle{
    private Flag singleFlag;
    private static final int DURATION_TO_WIN = 7;

    public KeepFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
        Flag singleFlag = new Flag();
    }

    @Override
    public int endGame() {
        if (singleFlag.getKeptDuration() >= DURATION_TO_WIN) {
            if (singleFlag.getOwner() == players[0]) {
                players[0].setBudget(players[0].getBudget() + PRIZE);
                return FIRST_PLAYER_WIN;
            }
            else if (singleFlag.getOwner() == players[1]) {
                players[1].setBudget(players[0].getBudget() + PRIZE);
                return SECOND_PLAYER_WIN;
            }
        }
        return UNFINISHED_GAME;
    }

    public static void gameInfo(){}
    public static void help(){}
    //public int endGame(){}

}
