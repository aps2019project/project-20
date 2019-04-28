package Model;

public class Battle {
    final int MAX_MANA_IN_LATE_TURNS = 9;
    private int turn;
    private Account firstPlayer;
    private Account secondPlayer;
    private BattleGround battleGround;
    private int firstPlayerMana = 2;
    private int secondPlayerMana = 2;
    private Card firstPlayerSelectedCard;
    private Card secondPlayerSelectedCard;
    private Item firstPlayerSelectedItem;
    private Item secondPlayerSelectedItem;
    private Card firstPlayerNextCardFromDeck;
    private Card secondPlayerNextCardFromDeck;
    private GraveYard firstPlayerGraveYard;
    private GraveYard secondPlayerGraveYard;
    private int battleID;
    //private View view;

    public void setPlayersMana() {
        if (1 < turn && turn <= 14){
            if (turn % 2 == 0)
                firstPlayerMana++;
            else
                secondPlayerMana++;
        }
        else {
            firstPlayerMana = MAX_MANA_IN_LATE_TURNS;
            secondPlayerMana = MAX_MANA_IN_LATE_TURNS;
        }
    }

    public static void handleBattleEvents(){}
    public static void gameInfo(){}
    public static void selectCard(int cardID){}
    public static void moveCardTo(int x,int y, Account account){ }
    public static void attack(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void comboAttack(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void counterAttack(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void useSpecialPower(Account player, int x,int y){}
    public static void insertIn(Account player , String cardName,int x , int y){}
    public void endTurn(Account player){
        turn++;
        setPlayersMana();
    }
    public static void selectItem(Account player , int collectableCardID){}
    public static void useItem(Account player ,Item playerItemSelected){}
    public static void enterGraveYard(Account player){}
    public static void help(){}
    public static void endGame(){}
}
