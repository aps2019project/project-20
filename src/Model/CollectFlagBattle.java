package Model;

public class CollectFlagBattle extends Battle{
    public CollectFlagBattle(Mode mode, Account firstPlayer, Account secondPlayer, Deck firstplayerDeck, Deck secondPlayerDeck, BattleGround battleGround, int reward) {
        super(mode, firstPlayer, secondPlayer, firstplayerDeck, secondPlayerDeck, battleGround, reward);
    }

    public static void gameInfo(){}
    public static void help(){}
//    public int endGame(){
//        if (players[0].getMainDeck().getHero().getHP() <= 0 ) {
//            players[1].setBudget(players[1].getBudget()+PRIZE);
//            return EXIT;
//        }else if( players[1].getMainDeck().getHero().getHP() <= 0){
//            players[0].setBudget(players[0].getBudget()+PRIZE);
//            return EXIT;
//        }
//        return CONTINUE;
//    }

}
