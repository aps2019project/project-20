package Model;

import Exceptions.AttackOwnCardException;
import Exceptions.CardNotFoundInDeckException;
import Exceptions.MoreThanNormalDistanceException;
import Exceptions.ThiCellFillException;

import java.util.ArrayList;

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

    public static void handleBattleEvent() {
    }

    public static void gameInfo() {
    }

    public void selectCard(Account player1,int cardID) {
        for (Card card : player1.getMainDeck().getCards()) {
            if (card.getID()==cardID ) {
                player1CardSelected=card;
                return;
            }
        }
        throw new CardNotFoundInDeckException();
    }

    public  void cardMoveTo(int x, int y, Account account ,BattleGround battleGround) {
        if (Math.abs(x-player1CardSelected.getXInGround())+Math.abs(x-player1CardSelected.getXInGround())>2){
            throw new MoreThanNormalDistanceException();
        }

        if(battleGround.getGround().get(x).get(y) instanceof Item){
            selectItem(account,battleGround.getGround().get(x).get(y).getID());
        }
        else if(battleGround.getGround().get(x).get(y) instanceof Card){
            throw new ThiCellFillException();
        }else {
            player1CardSelected.setXInGround(x);
            player1CardSelected.setYInGround(y);
        }
    }

    public  void attack(Account player, Account opponent, int opponentCardId,BattleGround battleGround) {
        for (Card card : opponent.getMainDeck().getCards()) {
            if (card.getID()==opponentCardId){
                if(card.getOwner()==opponent){
                    if (card instanceof Minion){
                        ((Minion) card).setHP(((Minion) card).getHP()-((Minion)player1CardSelected).getAP());

                    }
                    else if(card instanceof Hero){
                        ((Hero) card).setHP(((Hero) card).getHP()-((Minion)player1CardSelected).getAP());
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

    public static void insertIn(Account player, String cardName, int x, int y) {
    }

    public static void endTurn(Account player) {
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
        return player1;
    }

    public void setPlayer1(Account player1) {
        this.player1 = player1;
    }

    public Account getPlayer2() {
        return player2;
    }

    public void setPlayer2(Account player2) {
        this.player2 = player2;
    }

    public BattleGround getBattleGround() {
        return battleGround;
    }

    public void setBattleGround(BattleGround battleGround) {
        this.battleGround = battleGround;
    }

    public Card getPlayer1CardSelected() {
        return player1CardSelected;
    }

    public void setPlayer1CardSelected(Card player1CardSelected) {
        this.player1CardSelected = player1CardSelected;
    }

    public Card getPlayer2CardSelected() {
        return player2CardSelected;
    }

    public void setPlayer2CardSelected(Card player2CardSelected) {
        this.player2CardSelected = player2CardSelected;
    }

    public Item getPlayer1ItemSelected() {
        return player1ItemSelected;
    }

    public void setPlayer1ItemSelected(Item player1ItemSelected) {
        this.player1ItemSelected = player1ItemSelected;
    }

    public Item getPlayer2ItemSelected() {
        return player2ItemSelected;
    }

    public void setPlayer2ItemSelected(Item player2ItemSelected) {
        this.player2ItemSelected = player2ItemSelected;
    }

    public Card getPlayer1NextCardFromDeck() {
        return player1NextCardFromDeck;
    }

    public void setPlayer1NextCardFromDeck(Card player1NextCardFromDeck) {
        this.player1NextCardFromDeck = player1NextCardFromDeck;
    }

    public Card getPlayer2NextCardFromDeck() {
        return player2NextCardFromDeck;
    }

    public void setPlayer2NextCardFromDeck(Card player2NextCardFromDeck) {
        this.player2NextCardFromDeck = player2NextCardFromDeck;
    }

    public GraveYard getPlayer1GraveYard() {
        return player1GraveYard;
    }

    public void setPlayer1GraveYard(GraveYard player1GraveYard) {
        this.player1GraveYard = player1GraveYard;
    }

    public GraveYard getPlayer2GraveYard() {
        return player2GraveYard;
    }

    public void setPlayer2GraveYard(GraveYard player2GraveYard) {
        this.player2GraveYard = player2GraveYard;
    }

    public int getBattleID() {
        return battleID;
    }

    public void setBattleID(int battleID) {
        this.battleID = battleID;
    }
}
