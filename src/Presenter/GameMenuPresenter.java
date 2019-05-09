package Presenter;

import Datas.DeckDatas;
import Model.*;

public class GameMenuPresenter {

    private final static int STORY_REWARD_L1 = 500;
    private final static int STORY_REWARD_L2 = 1000;
    private final static int STORY_REWARD_L3 = 1500;
    private final static int CUSTOM_REWARD = 1000;
    private static int numberOfFlags;

    public static void setNumberOfFlags(int numberOfFlags) {
        GameMenuPresenter.numberOfFlags = numberOfFlags;
    }

    public Battle prepareForSingleGame(int levelNumber, Hero customHero) {
        Deck AIDeck;
        Battle.Mode battleMode;
        AI AI = new AI("AI", "1234");
        int reward;
        switch (levelNumber) {
            case 1:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel1().getName(), DeckDatas.getEnemyDeckInStoryGameLevel1().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel1().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel1().getCards());
                battleMode = Battle.Mode.NORMAL;
                reward = STORY_REWARD_L1;
                return new KillHeroBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case 2:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel2().getName(), DeckDatas.getEnemyDeckInStoryGameLevel2().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel2().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel2().getCards());
                battleMode = Battle.Mode.FLAG_KEEPING;
                reward = STORY_REWARD_L2;
                return new KeepFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case 3:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel3().getName(), DeckDatas.getEnemyDeckInStoryGameLevel3().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel3().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel3().getCards());
                battleMode = Battle.Mode.FLAG_COLLECTING;
                reward = STORY_REWARD_L3;
                return new CollectFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case -1:
                AIDeck = new Deck(DeckDatas.getDefaultDeck().getName(), customHero, DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
                battleMode = Battle.Mode.FLAG_KEEPING;
                reward = CUSTOM_REWARD;
                return new KeepFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
            case -2:
                AIDeck = new Deck(DeckDatas.getDefaultDeck().getName(), customHero, DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
                battleMode = Battle.Mode.FLAG_COLLECTING;
                reward = CUSTOM_REWARD;
                return new CollectFlagBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward,numberOfFlags);
            default:
                AIDeck = new Deck(DeckDatas.getDefaultDeck().getName(), customHero, DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
                battleMode = Battle.Mode.NORMAL;
                reward = CUSTOM_REWARD;
                return new KillHeroBattle
                        (battleMode,
                                CurrentAccount.getCurrentAccount(),
                                AI,
                                new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                                AIDeck,
                                new BattleGround(), reward);
        }
    }
}
