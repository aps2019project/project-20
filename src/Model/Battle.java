package Model;

import Exceptions.AttackOwnCardException;
import Exceptions.CardNotFoundInDeckException;
import Exceptions.MoreThanNormalDistanceException;
import Exceptions.ThisCellFilledException;

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

    public static void handleBattleEvent() {
    }

    public static void gameInfo() {
    }

    public void selectCard(Account firstPlayer,int cardID) {
        for (Card card : firstPlayer.getMainDeck().getCards()) {
            if (card.getID()==cardID ) {
                firstPlayerSelectedCard=card;
                return;
            }
        }
        throw new CardNotFoundInDeckException();
    }

    public  void cardMoveTo(int x, int y, Account account ,BattleGround battleGround) {
        if (Math.abs(x-firstPlayerSelectedCard.getXInGround())+Math.abs(x-firstPlayerSelectedCard.getXInGround())>2){
            throw new MoreThanNormalDistanceException();
        }

        if(battleGround.getGround().get(x).get(y) instanceof Item){
            selectItem(account,battleGround.getGround().get(x).get(y).getID());
        }
        else if(battleGround.getGround().get(x).get(y) instanceof Card){
            throw new ThisCellFilledException();
        }else {
            firstPlayerSelectedCard.setXInGround(x);
            firstPlayerSelectedCard.setYInGround(y);
        }
    }

    public  void attack(Account player, Account opponent, int opponentCardId,BattleGround battleGround) {
        for (Card card : opponent.getMainDeck().getCards()) {
            if (card.getID()==opponentCardId){
                if(card.getOwner()==opponent){
                    if (card instanceof Minion){
                        ((Minion) card).changeHP(-((Minion)firstPlayerSelectedCard).getAP());

                    }
                    else if(card instanceof Hero){
                        ((Hero) card).changeHP(-((Minion)firstPlayerSelectedCard).getAP());
                    }
                }
                else
                    throw new AttackOwnCardException();
            }
        }
        throw new CardNotFoundInDeckException();
    }

    public static void attackCombo(Account player, Account opponent, int opponentCardId, int MyCardID) {
    }

    public static void CounterAttack(Account player, Account opponent, int opponentCardId, int MyCardID) {
    }

    public static void useSpecialPower(Account player, int x, int y) {
    }

    public  void insertIn(Account player, String cardName, int x, int y,BattleGround battleGround) {
        for (Card card : player.getMainDeck().getCards()) {
            if (card.getName().equals(cardName)){
                if(! (battleGround.getGround().get(x).get(y) instanceof Card)){
                     throw new ThisCellFilledException();
                }
                //else
                   // battleGround.getGround().get(x).get(y).;

            }
        }
        throw new CardNotFoundInDeckException();
    }

    public void endTurn(Account player) {
        turn++;
        setPlayersMana();
    }

    public  void selectItem(Account player, int collectableItemID) {
    }

    public static void useItem(Account player, Item playerItemSelected) {
    }

    public static void enterGraveYard(Account player) {
    }

    public static void help() {
    }

    public static void endGame() {
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Account getPlayer1() {
        return firstPlayer;
    }

    public void setPlayer1(Account firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Account getPlayer2() {
        return secondPlayer;
    }

    public void setPlayer2(Account secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public Card getPlayer1CardSelected() {
        return firstPlayerSelectedCard;
    }

    public void setPlayer1CardSelected(Card firstPlayerSelectedCard) {
        this.firstPlayerSelectedCard = firstPlayerSelectedCard;
    }

    public Card getPlayer2CardSelected() {
        return secondPlayerSelectedCard;
    }

    public void setPlayer2CardSelected(Card secondPlayerSelectedCard) {
        this.secondPlayerSelectedCard = secondPlayerSelectedCard;
    }

    public Item getPlayer1ItemSelected() {
        return firstPlayerSelectedItem;
    }

    public void setPlayer1ItemSelected(Item firstPlayerSelectedItem) {
        this.firstPlayerSelectedItem = firstPlayerSelectedItem;
    }

    public Item getPlayer2ItemSelected() {
        return secondPlayerSelectedItem;
    }

    public void setPlayer2ItemSelected(Item secondPlayerSelectedItem) {
        this.secondPlayerSelectedItem = secondPlayerSelectedItem;
    }

    public Card getPlayer1NextCardFromDeck() {
        return firstPlayerNextCardFromDeck;
    }

    public void setPlayer1NextCardFromDeck(Card firstPlayerNextCardFromDeck) {
        this.firstPlayerNextCardFromDeck = firstPlayerNextCardFromDeck;
    }

    public Card getPlayer2NextCardFromDeck() {
        return secondPlayerNextCardFromDeck;
    }

    public void setPlayer2NextCardFromDeck(Card secondPlayerNextCardFromDeck) {
        this.secondPlayerNextCardFromDeck = secondPlayerNextCardFromDeck;
    }

    public GraveYard getPlayer1GraveYard() {
        return firstPlayerGraveYard;
    }

    public void setPlayer1GraveYard(GraveYard firstPlayerGraveYard) {
        this.firstPlayerGraveYard = firstPlayerGraveYard;
    }

    public GraveYard getPlayer2GraveYard() {
        return secondPlayerGraveYard;
    }

    public void setPlayer2GraveYard(GraveYard secondPlayerGraveYard) {
        this.secondPlayerGraveYard = secondPlayerGraveYard;
    }

    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }

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
}
