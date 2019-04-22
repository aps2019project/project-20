package Presenter;

import Exceptions.*;
import Model.*;
import View.CollectionMenu;

import java.util.ArrayList;

public class CollectionMenuPresenter {

    public Deck chooseDeck(String deckName) {
        Deck deck;
        try {
            deck = Deck.findDeck(CurrentAccount.getCurrentAccount().getDecks(), deckName);
        } catch (DeckNotFoundException e) {
            throw e;
        }
        return deck;
    }

    public void chooseMainDeck(String deckName) {
        try {
            Deck.selectMainDeck(CurrentAccount.getCurrentAccount(), deckName);
        } catch (DeckNotFoundException | InvalidSelectMainDeckException | RepeatedDeckException e) {
            throw e;
        }
    }

    public void isValidDeck(Deck deck) {
        if (!deck.isValidOfMainDeck()) {
            throw new InvalidSelectMainDeckException("");
        }
    }

    public void deleteDeck(Deck deck) {
        Deck.deleteDeck(CurrentAccount.getCurrentAccount(),deck.getName());
    }

    public void addToDeck(int ID, Deck deck) {
        try {
            deck.addToDeck(CurrentAccount.getCurrentAccount(),ID);
        }catch (AssetNotFoundException | IllegalHeroAddToDeckException | IllegalCardAddToDeckException e){
            throw e;
        }
    }

    public void deleteFromDeck(int ID, Deck deck) {
        try {
            deck.removeFromDeck(ID);
        }catch (AssetNotFoundException e){
            throw e;
        }
    }

    public void createDeck(String deckName) {
        try {
            Deck.createDeck(deckName);
        } catch (RepeatedDeckException e) {
            throw e;
        }
    }

    public void save() {
    }

    public int searchAsset(String name) {
        Asset cardAndItem;
        try {
            cardAndItem = Asset.searchAsset(CurrentAccount.getCurrentAccount().getCollection().getAssets(), name);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return cardAndItem.getID();
    }

    public void showCollection() {
        ArrayList<Item> items = Asset.getItemsOfAssetCollection(CurrentAccount.getCurrentAccount().getCollection().getAssets());
        ArrayList<Hero> heroes = Asset.getHeroesOfAssetCollection(CurrentAccount.getCurrentAccount().getCollection().getAssets());
        ArrayList<Spell> spells = Asset.getSpellsOfAssetCollection(CurrentAccount.getCurrentAccount().getCollection().getAssets());
        ArrayList<Minion> minions = Asset.getMinionsOfAssetCollection(CurrentAccount.getCurrentAccount().getCollection().getAssets());
        CollectionMenu.showCollectionInCollectionMenu(items, heroes, spells, minions);
    }

    public void showAllDecks() {
        CollectionMenu.showCollectionInAllDecks(CurrentAccount.getCurrentAccount().getDecks());
    }

}
