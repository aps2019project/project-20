package Presenter;
import Exceptions.*;
import Model.Account;
import Model.Asset;
import Model.Deck;
import Model.Shop;

import java.io.IOException;
import java.util.ArrayList;

public interface AccountManageable {

    default void createDeck(String deckName) {
        try {
            Deck.createDeck(deckName);
        } catch (RepeatedDeckException e) {
            throw e;
        }
    }

    default void deleteDeck(Deck deck) {
        Deck.deleteDeck(CurrentAccount.getCurrentAccount(),deck.getName());
    }

    default void addToDeck(Asset asset, Deck deck) {
        try {
            deck.addToDeck(asset);
        }catch (AssetNotFoundException | IllegalHeroAddToDeckException | IllegalCardAddToDeckException e){
            throw e;
        }
    }

    default void removeAssetFromDeck(Asset asset, Deck deck) {
        try {
            deck.removeFromDeck(asset.getID());
        }catch (AssetNotFoundException e){
            throw e;
        }
    }

    default void renameDeck(Deck deck , String name){
        try {
            deck.renameDeck(name);
        } catch (RepeatedDeckException e) {
            throw e;
        }
    }

    default void chooseMainDeck(Deck deck) {
        try {
            Deck.selectMainDeck(CurrentAccount.getCurrentAccount(), deck);
        } catch (InvalidSelectMainDeckException e) {
            throw e;
        }
    }

    default void removeMainDeck() {
       Deck.unSelectMainDeck(CurrentAccount.getCurrentAccount());
    }

    default void isValidDeck(Deck deck) {
        if (!deck.isValidOfMainDeck()) {
            throw new InvalidSelectMainDeckException("");
        }
    }

    default Account getCurrentAccount(){
        return CurrentAccount.getCurrentAccount();
    }

    default ArrayList<Asset> getAllAssets(){
        try {
            return Asset.getAssetsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
