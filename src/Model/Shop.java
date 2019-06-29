package Model;

import Datas.AssetDatas;
import Exceptions.AssetNotFoundException;
import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Shop {

    private final static int MAX_NUMBER_OF_ITEMS_IN_COLLECTION = 3;

    private static ArrayList<Asset> assets = new ArrayList<>();

    public static ArrayList<Asset> getAssets() {
        return assets;
    }

    public static void sell(Account account, Asset sellingAsset) {
        for (Deck deck : account.getDecks()) {
            deck.removeFromDeck(sellingAsset.getID());
        }
        account.getCollection().getAssets().remove(sellingAsset);
        account.setBudget(account.getBudget() + sellingAsset.getPrice());
        sellingAsset.setOwner(null);
    }

    public static void buy(Account account, Asset buyingAsset) {
        int numberOfAccountItems = 0;
        for (Asset asset : account.getCollection().getAssets())
            if (asset instanceof Item)
                numberOfAccountItems++;
        if (buyingAsset.getPrice() > account.getBudget())
            throw new InsufficientMoneyInBuyFromShopException("Your budget is insufficient to buy the asset.");
        else if (numberOfAccountItems == MAX_NUMBER_OF_ITEMS_IN_COLLECTION)
            throw new MaximumNumberOfItemsInBuyException();
        buyingAsset.setOwner(account);
        account.getCollection().getAssets().add(buyingAsset.clone());
        account.setBudget(account.getBudget() - buyingAsset.getPrice());
    }



}
