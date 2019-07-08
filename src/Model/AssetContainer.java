package Model;

import Exceptions.AssetNotFoundException;
import Exceptions.NotEnoughQuantityException;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import com.jniwrapper.Str;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static Model.Shop.shop;

public class AssetContainer {
    private static final int DEFAULT_VOLUME = 1;
    private int quantity;
    private Asset asset;

    public static ArrayList<Asset> getAssetArrayList(ArrayList<AssetContainer> containers){
        ArrayList<Asset> assets = new ArrayList<>();
        for (AssetContainer assetContainer : containers) {
            assets.add(assetContainer.getAsset());
        }
        return assets;
    }

    public static AssetContainer assetContainerBuilderFromShop(Asset asset) {
        AssetContainer assetContainer = new AssetContainer(asset);
        try {
            assetContainer.setQuantity(shop.searchInShop(asset.getName()).getQuantity());
        }catch (AssetNotFoundException e){
            e.printStackTrace();
            throw e;
        }
         return assetContainer;
    }

       public static ArrayList<AssetContainer> searchAndGetContainerCollectionFromCollection(ArrayList<AssetContainer> assets ,String assetName){
        if(assetName.equals("") || assets == null){
            return assets;
        }
        ArrayList<AssetContainer> result = new ArrayList<>();
        for (AssetContainer assetContainer : assets) {
            if(assetContainer != null && assetContainer.getAsset().getName().matches(".*"+assetName+".*")){
                result.add(assetContainer);
            }
        }
        return result;
    }

    public static ArrayList<AssetContainer> getAssetContainersArrayFromFile(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        ArrayList<AssetContainer> assetContainers = new YaGson().fromJson(fileReader, new TypeToken<Collection<AssetContainer>>() {
        }.getType());
        fileReader.close();
        return assetContainers;
    }

    public static void writeAssetContainersArrayInFile(ArrayList<AssetContainer> assetContainers, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(new YaGson().toJson(assetContainers, new TypeToken<Collection<AssetContainer>>() {
        }.getType()));
        fileWriter.flush();
        fileWriter.close();
    }

    public void buy() {
        if (quantity == 0) {
            throw new NotEnoughQuantityException();
        }
        quantity--;
    }

    public void sell() {
        quantity++;
    }

    public AssetContainer(Asset asset) {
        this.asset = asset;
        quantity = DEFAULT_VOLUME;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
