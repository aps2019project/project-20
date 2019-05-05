package Model;

import Exceptions.*;
import Presenter.CurrentAccount;

import java.util.ArrayList;

public class Deck {
    public static final int STANDARD_NUMBER_OF_HEROES = 1;
    public static final int STANDARD_NUMBER_OF_MINIONS_AND_SPELLS = 20;
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private Hero hero;
    private ArrayList<Item> items = new ArrayList<>();
    private int nextCardFromDeckIndex = 0;

    public int getNextCardFromDeckIndex() {
        return nextCardFromDeckIndex;
    }

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero getHero() {
        return hero;
    }

    public static int getStandardNumberOfHeroes() {
        return STANDARD_NUMBER_OF_HEROES;
    }

    public static int getStandardNumberOfMinionsAndSpells() {
        return STANDARD_NUMBER_OF_MINIONS_AND_SPELLS;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setNextCardFromDeckIndex(int nextCardFromDeckIndex) {
        this.nextCardFromDeckIndex = nextCardFromDeckIndex;
    }

    public void addToDeck(Account account, int ID) {
        Asset asset;
        try {
            asset = Asset.searchAsset(account.getCollection().getAssets(),ID);
        }catch (AssetNotFoundException e){
            throw e;
        }
        if (asset instanceof Hero) {
             if (hero == null)
                 hero = (Hero) asset;
             else
                 throw new IllegalHeroAddToDeckException("The deck's hero is already selected.");
        }
        else if (!(asset instanceof Card)){
            if (cards.size() < 20)
                cards.add((Card) asset);
            else
                throw new IllegalCardAddToDeckException();
        }
    }

    public void removeFromDeck(int assetID) {
        if (hero != null && assetID == hero.getID())
            hero = null;
        else {
            for (Item item: items)
                if (assetID == item.getID()) {
                    items.remove(item);
                    return;
                }
            for (Card card: cards)
                if (assetID == card.getID()) {
                    cards.remove(card);
                    return;
                }
            throw new AssetNotFoundException("Asset not found in the deck.");
        }
    }

    public Asset searchAssetInDeck(int assetID) {
        if (assetID == hero.getID())
            return hero;
        else {
            for (Item item: items) {
                if (assetID == item.getID())
                    return item;
            }
            for (Card card: cards) {
                if (assetID == card.getID())
                    return card;
            }
        }
        throw new AssetNotFoundException("Asset not found in the deck");
    }

    public static Deck findDeck (ArrayList<Deck> decks,String deckName){
        for (Deck deck: decks)
            if (deckName.equals(deck.getName()))
                return deck;
        throw new DeckNotFoundException("The deck not found.");
    }

    public static void selectMainDeck(Account account, String deckName) {
        Deck deck ;
        try {
            deck = Deck.findDeck(account.getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        if(account.getMainDeck()!=null && account.getMainDeck().getName().equals(deckName)){
            throw new RepeatedDeckException("");
        }
        if (deck.isValidOfMainDeck())
            account.setMainDeck(deck);
        else
            throw new InvalidSelectMainDeckException("The selected deck is invalid to be the main deck.");
    }

//    public static void removeFromDeck(Account account, String deckName, int assetID) {
//        try {
//            searchDeck(account, deckName);
//        } catch (DeckNotFoundException e) {
//            throw e;
//        }
//        Deck deck = searchDeck(account, deckName);
//        try {
//            deck.removeFromDeck(assetID);
//        } catch (AssetNotFoundException e) {
//            throw e;
//        }
//        deck.removeFromDeck(assetID);
//    }

    public static void createDeck(String deckName) {
        try {
            Deck.findDeck(CurrentAccount.getCurrentAccount().getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            CurrentAccount.getCurrentAccount().getDecks().add(new Deck(deckName));
            return;
        }
        throw new RepeatedDeckException("");
    }

    public static void deleteDeck(Account account,String deckName) {
        Deck deck ;
        try {
            deck = Deck.findDeck(account.getDecks(),deckName);
        }catch (DeckNotFoundException e){
            throw e;
        }
        account.getDecks().remove(deck);
    }

    public boolean isValidOfMainDeck() {
        return  (this.getHero() != null && this.getCards().size() == STANDARD_NUMBER_OF_MINIONS_AND_SPELLS);
    }
}
