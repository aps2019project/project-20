package Model;

import java.util.ArrayList;

import static Model.CardAndItem.searchCardAndItem;
import static Model.Deck.searchDeckInDecks;

public class Collection {
    private ArrayList<CardAndItem>cardAndItems;

    public ArrayList<CardAndItem> getCardAndItems() {
        return cardAndItems;
    }

    public void setCardAndItems(ArrayList<CardAndItem> cardAndItems) {
        this.cardAndItems = cardAndItems;
    }

    public static Deck selectMainDeck(Account account,String deckName){
        Deck result=searchDeckInDecks(account.getDecks(), deckName);
        if(result==null) {
            throw new NullPointerException();
        }
        else
            return result;
    }
    public static boolean isValidDeck(Deck deck,String deckName){
    int heroNum=0;
    int cardNum=0;
        for (CardAndItem cardAndItem : deck.getCardAndItems()) {
            if(cardAndItem instanceof Hero)
                heroNum++;
            if(cardAndItem instanceof Card)
                cardNum++;
        }
      if(heroNum==1 && cardNum==21)
          return true;
      return false;
    }
    public static void removeFromDeck(int cardAndItemAndHeroID,Deck deck){
        CardAndItem card = searchCardAndItem(cardAndItemAndHeroID, deck);
        if(card==null){
            throw new NullPointerException();
        }
        else{
            deck.getCardAndItems().remove(card);

        }
    }
    public static void addToDeck(Deck deck,int cardAndItemAndHeroID){
        CardAndItem card = searchCardAndItem(cardAndItemAndHeroID, deck);
        if(card==null){
            throw new NullPointerException();
        }
        else{
            deck.getCardAndItems().add(card);

        }
    }
    public static void deleteDeck(Account account,Deck deck ){
        for (Deck accountDeck : account.getDecks()) {
            if(accountDeck==deck){
                account.getDecks().remove(accountDeck);
                return;
            }
        }
        throw new NullPointerException();
    }
    public static void createDeck(Account account,Deck deck ){
        account.getDecks().add(deck);
    }
    public static int searchDeck(Deck deck,String cardAndItemName){
        for (CardAndItem cardAndItem : deck.getCardAndItems()) {
            if(cardAndItem.getName().compareTo(cardAndItemName)== 0)
                return cardAndItem.getID();
        }
        throw new NullPointerException();
    }
    //public static void save(){}
}