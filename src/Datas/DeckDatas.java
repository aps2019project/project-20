package Datas;

import Model.Deck;

public class DeckDatas {
    private static Deck enemyDeckInStoryGameLevel1 = new Deck("enemyDeckInStoryGameLevel1", AssetDatas.getWhiteDamn(), AssetDatas.getTotalDisarm(), AssetDatas.getLightingBolt(), AssetDatas.getAllDisarm(), AssetDatas.getAllPoison(), AssetDatas.getDispel(), AssetDatas.getSacrifice(), AssetDatas.getShock(), AssetDatas.getFarsArcher(), AssetDatas.getTooraniSpear(), AssetDatas.getTooraniSwampy(), AssetDatas.getTooraniSwampy(), AssetDatas.getBlackGoblin(), AssetDatas.getBigGiant(), AssetDatas.getPoisonSnake(), AssetDatas.getGiantSnake(), AssetDatas.getWhiteWolf(), AssetDatas.getGreatWizard(), AssetDatas.getColdMother(), AssetDatas.getSiavash(), AssetDatas.getArjangGoblin(), AssetDatas.getKnowledgeCrown());
    private static Deck enemyDeckInStoryGameLevel2 = new Deck("enemyDeckInStoryGameLevel2", AssetDatas.getZahhak(), AssetDatas.getAreaDispel(), AssetDatas.getEmpower(), AssetDatas.getGodStrength(), AssetDatas.getPoisonLake(), AssetDatas.getMadness(), AssetDatas.getHealthWithProfit(), AssetDatas.getKingsGuard(), AssetDatas.getFarsSwordsman(), AssetDatas.getFarsSpear(), AssetDatas.getFarsChampion(), AssetDatas.getTooraniCatapult(), AssetDatas.getTooraniPrince(), AssetDatas.getEagle(), AssetDatas.getEagle(), AssetDatas.getThrowFireDragon(), AssetDatas.getTiger(), AssetDatas.getElf(), AssetDatas.getGiv(), AssetDatas.getIraj(), AssetDatas.getKingGiant(), AssetDatas.getSoulEater());
    private static Deck enemyDeckInStoryGameLevel3 = new Deck("enemyDeckInStoryGameLevel3", AssetDatas.getArash(), AssetDatas.getHellFire(), AssetDatas.getAllDisarm(), AssetDatas.getDispel(), AssetDatas.getGhazaBokhorJoonBegiri(), AssetDatas.getAllPower(), AssetDatas.getAllAttack(), AssetDatas.getWeakening(), AssetDatas.getFarsChief(), AssetDatas.getTooraniArcher(), AssetDatas.getTooraniSpy(), AssetDatas.getThrowStoneGiant(), AssetDatas.getHogRiderGoblin(), AssetDatas.getHogRiderGoblin(), AssetDatas.getDrainLion(), AssetDatas.getWolf(), AssetDatas.getWizard(), AssetDatas.getWildBoar(), AssetDatas.getPiran(), AssetDatas.getBahman(), AssetDatas.getBigGiant(), AssetDatas.getTerrorHood());
    private static Deck defaultDeck = new Deck("defaultDeck",AssetDatas.getRostam(), AssetDatas.getHellFire(), AssetDatas.getAllDisarm(), AssetDatas.getDispel(), AssetDatas.getGhazaBokhorJoonBegiri(), AssetDatas.getAllDisarm(), AssetDatas.getAllPoison(), AssetDatas.getDispel(), AssetDatas.getFarsChief(), AssetDatas.getTooraniArcher(), AssetDatas.getTooraniSpy(), AssetDatas.getSacrifice(), AssetDatas.getShock(), AssetDatas.getFarsArcher(), AssetDatas.getTooraniSpear(), AssetDatas.getTooraniSwampy(), AssetDatas.getEagle(), AssetDatas.getEagle(), AssetDatas.getThrowFireDragon(), AssetDatas.getTiger(), AssetDatas.getElf(),AssetDatas.getBaptism());

    public static Deck getEnemyDeckInStoryGameLevel1() {
        return enemyDeckInStoryGameLevel1;
    }

    public static Deck getEnemyDeckInStoryGameLevel2() {
        return enemyDeckInStoryGameLevel2;
    }

    public static Deck getEnemyDeckInStoryGameLevel3() {
        return enemyDeckInStoryGameLevel3;
    }

    public static Deck getDefaultDeck() {
        return defaultDeck;
    }
}
