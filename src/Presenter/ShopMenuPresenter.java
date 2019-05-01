package Presenter;

import Exceptions.AssetNotFoundException;
import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;
import Model.*;
import View.CollectionMenu;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class ShopMenuPresenter {


    public void sellPresenter(int ID) {
        try{
            Shop.sell(CurrentAccount.getCurrentAccount(),ID);
        }catch (AssetNotFoundException e){
            throw e;
        }
    }

    public void buyPresenter(String name) {
        try{
            Shop.buy(CurrentAccount.getCurrentAccount(),name);
        }catch (AssetNotFoundException | InsufficientMoneyInBuyFromShopException | MaximumNumberOfItemsInBuyException e){
            throw e;
        }
    }

    public int searchInMyCollectionPresenter(String assetName) {
        int ID;
        try {
            ID = CurrentAccount.getCurrentAccount().getCollection().searchAssetInMyCollection(assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return ID;
    }

    public int searchInShopPresenter(String assetName) {
        int ID;
        try {
            ID = Shop.searchAssetInShopCollection(assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        return ID;
    }

    public void showShopCollectionPresenter() {
        ArrayList<Item> items = Asset.getItemsOfAssetCollection(Shop.getAssets());
        ArrayList<Hero> heroes = Asset.getHeroesOfAssetCollection(Shop.getAssets());
        ArrayList<Spell> spells = Asset.getSpellsOfAssetCollection(Shop.getAssets());
        ArrayList<Minion> minions = Asset.getMinionsOfAssetCollection(Shop.getAssets());
        CollectionMenu.showCollectionInBuyMenu(items, heroes, spells, minions);
    }

    public void showMyCollectionPresenter() {
        CollectionMenuPresenter collectionMenuPresenter = new CollectionMenuPresenter();
        collectionMenuPresenter.showCollection();
    }
}
