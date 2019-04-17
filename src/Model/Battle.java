package Model;

public class Battle {
    private int turn;
    private Account player1;
    private Account player2;
    private BattleGround battleGround;
    private Card player1CardSelected;
    private Card player2CardSelected;
    private Item player1ItemSelected;
    private Item player2ItemSelected;
    private Card player1NextCardFromDeck;
    private Card player2NextCardFromDeck;
    private GraveYard player1GraveYard;
    private GraveYard player2GraveYard;
    private int battleID;
    //private View view;

    public static void handleBattleEvent(){}
    public static void gameInfo(){}
    public static void selectCard(int cardID){}
    public static void cardMoveTo(int x,int y, Account account){ }
    public static void attack(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void attackCombo(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void CounterAttack(Account player, Account opponent,int opponentCardId,int MyCardID){}
    public static void useSpecialPower(Account player, int x,int y){}
    public static void insertIn(Account player , String cardName,int x , int y){}
    public static void endTurn(Account player){}
    public static void selectItem(Account player , int collectableCardID){}
    public static void useItem(Account player ,Item playerItemSelected){}
    public static void enterGraveYard(Account player){}
    public static void help(){}
    public static void endGame(){}
}
