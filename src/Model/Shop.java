package Model;

import Datas.AssetDatas;
import Exceptions.AssetNotFoundException;
import Exceptions.InsufficientMoneyInBuyFromShopException;
import Exceptions.MaximumNumberOfItemsInBuyException;

import java.util.ArrayList;

public class Shop {

    private final static int MAX_NUMBER_OF_ITEMS_IN_COLLECTION = 3;

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
            buyingAsset = Asset.searchAsset(Shop.getAssets(), assetName);
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
        account.getCollection().getAssets().add((Asset)buyingAsset.clone());
        account.setBudget(account.getBudget() - buyingAsset.getPrice());
    }

    public static int searchAssetInShopCollection(String assetName) {
        for (Asset asset : assets)
            if (assetName.equals(asset.getName()))
                return asset.getID();
        throw new AssetNotFoundException("Asset not found in the shop.");
    }

    public void fillShopData() {

        assets.add(AssetDatas.getTotalDisarm());
        assets.add(AssetDatas.getAreaDispel());
        assets.add(AssetDatas.getEmpower());
        assets.add(AssetDatas.getFireball());
        assets.add(AssetDatas.getGodStrength());
        assets.add(AssetDatas.getHellFire());
        assets.add(AssetDatas.getLightingBolt());
        assets.add(AssetDatas.getPoisonLake());
        assets.add(AssetDatas.getMadness());
        assets.add(AssetDatas.getAllDisarm());
        assets.add(AssetDatas.getAllPoison());
        assets.add(AssetDatas.getDispel());
        assets.add(AssetDatas.getHealthWithProfit());
        assets.add(AssetDatas.getGhazaBokhorJoonBegiri());
        assets.add(AssetDatas.getAllPower());
        assets.add(AssetDatas.getAllAttack());
        assets.add(AssetDatas.getWeakening());
        assets.add(AssetDatas.getSacrifice());
        assets.add(AssetDatas.getKingsGuard());
        assets.add(AssetDatas.getShock());
        assets.add(AssetDatas.getFarsArcher());
        assets.add(AssetDatas.getFarsSwordsman());
        assets.add(AssetDatas.getFarsSpear());
        assets.add(AssetDatas.getFarsHorseman());
        assets.add(AssetDatas.getFarsChampion());
        assets.add(AssetDatas.getFarsChief());
        assets.add(AssetDatas.getTooraniArcher());
        assets.add(AssetDatas.getTooraniCatapult());
        assets.add(AssetDatas.getTooraniSpear());
        assets.add(AssetDatas.getTooraniSpy());
        assets.add(AssetDatas.getTooraniSwampy());
        assets.add(AssetDatas.getTooraniPrince());
        assets.add(AssetDatas.getBlackGoblin());
        assets.add(AssetDatas.getThrowStoneGiant());
        assets.add(AssetDatas.getHogRiderGoblin());
        assets.add(AssetDatas.getEagle());
        assets.add(AssetDatas.getOneEyeGiant());
        assets.add(AssetDatas.getPoisonSnake());
        assets.add(AssetDatas.getThrowFireDragon());
        assets.add(AssetDatas.getDrainLion());
        assets.add(AssetDatas.getGiantSnake());
        assets.add(AssetDatas.getWhiteWolf());
        assets.add(AssetDatas.getTiger());
        assets.add(AssetDatas.getWolf());
        assets.add(AssetDatas.getWizard());
        assets.add(AssetDatas.getGreatWizard());
        assets.add(AssetDatas.getElf());
        assets.add(AssetDatas.getWildBoar());
        assets.add(AssetDatas.getPiran());
        assets.add(AssetDatas.getGiv());
        assets.add(AssetDatas.getBahman());
        assets.add(AssetDatas.getAshkbos());
        assets.add(AssetDatas.getIraj());
        assets.add(AssetDatas.getBigGiant());
        assets.add(AssetDatas.getTwoHeadGiant());
        assets.add(AssetDatas.getColdMother());
        assets.add(AssetDatas.getSteelArmor());
        assets.add(AssetDatas.getSiavash());
        assets.add(AssetDatas.getKingGiant());
        assets.add(AssetDatas.getArjangGoblin());
        assets.add(AssetDatas.getWhiteDamn());
        assets.add(AssetDatas.getSimorgh());
        assets.add(AssetDatas.getSevenHeadDragon());
        assets.add(AssetDatas.getRakhsh());
        assets.add(AssetDatas.getZahhak());
        assets.add(AssetDatas.getKaveh());
        assets.add(AssetDatas.getArash());
        assets.add(AssetDatas.getLegend());
        assets.add(AssetDatas.getEsfandiar());
        assets.add(AssetDatas.getRostam());
        assets.add(AssetDatas.getKnowledgeCrown());
        assets.add(AssetDatas.getNamoos_e_separ());
        assets.add(AssetDatas.getDamoolArch());
        assets.add(AssetDatas.getNooshdaroo());
        assets.add(AssetDatas.getTwoHornArrow());
        assets.add(AssetDatas.getSimorghWing());
        assets.add(AssetDatas.getElixir());
        assets.add(AssetDatas.getManaMixture());
        assets.add(AssetDatas.getInvulnerableMixture());
        assets.add(AssetDatas.getDeathCurse());
        assets.add(AssetDatas.getRandomDamage());
        assets.add(AssetDatas.getTerrorHood());
        assets.add(AssetDatas.getBladesOfAgility());
        assets.add(AssetDatas.getKingWisdom());
        assets.add(AssetDatas.getAssassinationDagger());
        assets.add(AssetDatas.getPoisonousDagger());
        assets.add(AssetDatas.getShockHammer());
        assets.add(AssetDatas.getSoulEater());
        assets.add(AssetDatas.getBaptism());
        assets.add(AssetDatas.getChineseSword());

    }

}
