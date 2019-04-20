package Model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Card hero;
    private Item item;

    public String getName(){
        return name;
    }
    //Maybe unnecessary getters
    public Card getHero() {
        return hero;
    }

    public Item getItem() {
        return item;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addToDeck(Asset asset) {
        if (asset instanceof Hero) {
             if (hero == null)
                 hero = (Hero) asset;
             else
                 throw new IllegalAddToDeckException("The deck's hero is already selected.");
        }
        else if (asset instanceof Item){
            if (item == null)
                item = (Item) asset;
            else
                throw new IllegalAddToDeckException("The deck's item is already selected.");
        }
        else {
            if (cards.size() < 20)
                cards.add((Card) asset);
            else
                throw new IllegalAddToDeckException("Maximum number of cards in the deck has reached");
        }
    }

    public void removeFromDeck(int assetID) {
        if (hero != null && assetID == hero.getID())
            hero = null;
        else if (item != null && assetID == item.getID())
            item = null;
        else {
            for (Card card: cards)
                if (assetID == card.getID()) {
                    cards.remove(card);
                    return;
                }
            throw new IllegalRemoveFromDeckException("The asset doesn't exist in the deck.");
        }
    }

//    public static Deck findDeck(String deckName){
//        for (Deck deck: decks)
//            if (deckName.equals(deck.getName()))
//                return deck;
//        throw new DeckNotFoundException("The deck not found.");
//    }
}
