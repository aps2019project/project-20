package Presenter;

import Datas.DeckDatas;
import Model.*;

public class GameMenuPresenter {
    public Battle prepareForSingleGame(int levelNumber, Hero customHero) {
        Deck AIDeck;
        Battle.Mode battleMode;
        Account AI = new Account("AI", "1234");
        int reward;
        switch (levelNumber) {
            case 1:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel1().getName(), DeckDatas.getEnemyDeckInStoryGameLevel1().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel1().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel1().getCards());
                battleMode = Battle.Mode.NORMAL;
                reward = 500;
                break;
            case 2:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel2().getName(), DeckDatas.getEnemyDeckInStoryGameLevel2().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel2().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel2().getCards());
                battleMode = Battle.Mode.FLAG_KEEPING;
                reward = 1000;
                break;
            case 3:
                AIDeck = new Deck(DeckDatas.getEnemyDeckInStoryGameLevel3().getName(), DeckDatas.getEnemyDeckInStoryGameLevel3().getHero(), DeckDatas.getEnemyDeckInStoryGameLevel3().getItems(), DeckDatas.getEnemyDeckInStoryGameLevel3().getCards());
                battleMode = Battle.Mode.FLAG_COLLECTING;
                reward = 1500;
                break;
            default:
                AIDeck = new Deck(DeckDatas.getDefaultDeck().getName(), customHero, DeckDatas.getDefaultDeck().getItems(), DeckDatas.getDefaultDeck().getCards());
                battleMode = Battle.Mode.NORMAL;
                reward = 1000;
        }
        return new Battle
                (battleMode,
                        CurrentAccount.getCurrentAccount(),
                        AI,
                        new Deck(CurrentAccount.getCurrentAccount().getMainDeck().getName(), CurrentAccount.getCurrentAccount().getMainDeck().getHero(), CurrentAccount.getCurrentAccount().getMainDeck().getItems(), CurrentAccount.getCurrentAccount().getMainDeck().getCards()),
                        AIDeck,
                        new BattleGround(), reward);


    }
}
