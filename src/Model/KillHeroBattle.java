package Model;

public class KillHeroBattle extends Battle{
    public KillHeroBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstPlayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstPlayerDeck, secondPlayerDeck, battleGround, reward);
    }

    @Override
    public int endGame() {
        if (playersDeck[0].getHero().getHP() <= 0 && playersDeck[1].getHero().getHP() <= 0)
            return DRAW;
        else if (playersDeck[0].getHero().getHP() <= 0) {
            players[1].setBudget(players[1].getBudget() + reward);
            return SECOND_PLAYER_WIN;
        }
        else if (playersDeck[1].getHero().getHP() <= 0) {
            players[0].setBudget(players[0].getBudget() + reward);
            return FIRST_PLAYER_WIN;
        }
        return UNFINISHED_GAME;
    }

    public static void gameInfo(){}
    public static void help(){}
    //public int endGame(){}

}
