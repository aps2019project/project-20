package Presenter;
import Exceptions.*;
import Model.Account;
import Model.Asset;
import Model.Deck;
import Model.Shop;

import java.io.IOException;
import java.util.ArrayList;

public interface AccountManageable {

    default void login(String userName, String password){
        Account account;
        try {
            account = Account.login(userName,password);
        }catch (UserNotFoundException | WrongPasswordException e) {
            throw e;
        }
        CurrentAccount.setCurrentAccount(account);
    }

    default void createAccount(String userName, String password){
        Account account = null;
        try {
            account = Account.createAccount(userName, password);
        }catch (RepeatedUserNameException e){
         throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        CurrentAccount.setCurrentAccount(account);
    }

    default void changePassWord(String passWord){
        CurrentAccount.getCurrentAccount().setPassword(passWord);
    }

    default void changeUserName(String userName){
        CurrentAccount.getCurrentAccount().setName(userName);
    }

    default void deleteAccount(){
        Account.deleteAccount(CurrentAccount.getCurrentAccount());
    }

    default void saveAccount(){
        try {
            CurrentAccount.getCurrentAccount().saveInToFile("Data/AccountsData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void logout(){
        CurrentAccount.setCurrentAccount(null);
    }

    default void sell(Asset sellingAsset) {
            Shop.sell(CurrentAccount.getCurrentAccount(),sellingAsset);
    }

    default void buy(Asset buyingAsset) throws InsufficientMoneyInBuyFromShopException, MaximumNumberOfItemsInBuyException {
        Shop.buy(getCurrentAccount(),buyingAsset);
    }

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
