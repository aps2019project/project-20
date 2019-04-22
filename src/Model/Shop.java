package Model;

import Exceptions.AssetNotFoundException;
import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;

import java.util.ArrayList;

public class Shop {

    final static int MAX_NUMBER_OF_ITEMS_IN_COLLECTION = 3;

    private static ArrayList<Asset> assets = new ArrayList<>();

    public static ArrayList<Asset> getAssets() {
        return assets;
    }

    public static void sell(Account account, int assetID) {
        Asset asset;
        try {
            asset = Asset.searchAsset(account.getCollection().getAssets(), assetID);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        account.getCollection().getAssets().remove(asset);
        account.setBudget(account.getBudget() + asset.getPrice());
    }

    public static void buy(Account account, String assetName) {
        Asset buyingAsset;
        try {
            buyingAsset = Asset.searchAsset(Shop.getAssets(),assetName);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        int numberOfAccountItems = 0;
        for (Asset asset : account.getCollection().getAssets())
            if (asset instanceof Item)
                numberOfAccountItems++;

        if (buyingAsset.getPrice() > account.getBudget())
            throw new InsufficientMoneyInBuyFromShopException("Your budget is insufficient to buy the asset.");
        else if (numberOfAccountItems == MAX_NUMBER_OF_ITEMS_IN_COLLECTION)
            throw new MaximumNumberOfItemsInBuyException();
        account.getCollection().getAssets().add(buyingAsset);
        account.setBudget(account.getBudget() - buyingAsset.getPrice());
    }

    public static int searchAssetInShopCollection(String assetName) {
        for (Asset asset: assets)
            if (assetName.equals(asset.getName()))
                return asset.getID();
        throw new AssetNotFoundException("Asset not found in the shop.");
    }
}
