package Presenter;

import Controller.ScreenRecordController;
import Exceptions.AssetNotFoundException;
import Exceptions.InvalidInGameAssetIDFormatException;
import Exceptions.SpecialPowerMisMatchException;
import Model.*;
import View.BattleEnvironment;

import java.util.ArrayList;

public class BattleEnvironmentPresenter {

    private Battle battle;

    public BattleEnvironmentPresenter(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void selectCardPresenter(String cardID) {
        if (!IsValidInGameCardID(cardID)) {
            throw new InvalidInGameAssetIDFormatException("");
        }
        Asset asset;
        try {
            asset = battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
        } catch (AssetNotFoundException e) {
            throw e;
        }
        battle.selectWarrior(battle.getPlayers()[0], asset.getID());
    }

    public void attackPresenter(String opponentCardID) {
        battle.attack(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], (Warrior) battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[1], opponentCardID.split("_")[1]));
    }

    public void attackComboPresenter(String opponentCardID, String[] cardsID) {
        if (!IsValidInGameCardID(opponentCardID)) {
            throw new InvalidInGameAssetIDFormatException("");
        }
        Warrior opponent = (Warrior) battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[1], opponentCardID);
        ArrayList<Minion> minions = new ArrayList<>();
        for (String cardID : cardsID) {
            if (!IsValidInGameCardID(cardID)) {
                throw new InvalidInGameAssetIDFormatException("");
            }
            Asset asset;
            try {
                asset = battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
            } catch (AssetNotFoundException i) {
                throw i;
            }
            if (asset instanceof Minion) {
                minions.add((Minion) asset);
            }
        }
        battle.attackCombo(battle.getPlayers()[0], minions, opponent);
    }

    public void useSpecialPowerPresenter(int x, int y) {
        x--;
        y--;
        if(battle.getBattleGround().getGround().get(y).get(x)!=null && battle.getBattleGround().getGround().get(y).get(x).getOwner()!=battle.getPlayers()[1]) {
            battle.applySpecialPower(battle.getPlayersDeck()[0].getHero(),(Warrior)battle.getBattleGround().getGround().get(y).get(x),((Minion)battle.getBattleGround().getGround().get(y).get(x)).getActivateTimeOfSpecialPower());
        }
        throw new SpecialPowerMisMatchException();
    }

    public void insertCardPresenter(String cardName, int x, int y) {
        battle.insertCard(battle.getPlayers()[0], cardName, x, y);
    }

    public void endTurnPresenter() {
        battle.endTurn(battle.getPlayers()[0]);
        ((AI) battle.getPlayers()[1]).handleAIEvent(battle.getPlayers()[0], battle);
    }

    public void selectItemPresenter(int collectableID) {
        battle.selectItem(battle.getPlayers()[0], collectableID);
    }

    public void cardMoveToPresenter(int x, int y) {
        battle.cardMoveTo(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], x, y);
    }

    public void useItemPresenter(int x, int y) {
        x--;
        y--;
//        if (!(battle.getBattleGround().getGround().get(y).get(x) instanceof Warrior)) {
//            throw new InvalidTargetException();
//        }
        battle.useItem(battle.getPlayers()[0], battle.getPlayers()[1], battle.getPlayersSelectedItem()[0]);
    }

    public Card showInfoOfCard(String cardID) {
        if (!IsValidInGameCardID(cardID)) {
            throw new InvalidInGameAssetIDFormatException("");
        }
        Card card;
        try {
            card = (Card) battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
            if (card != null) {
                return card;
            }
            card = Asset.searchCard(battle.getPlayersDeck()[0].getCards(), cardID.split("_")[1]);
        } catch (AssetNotFoundException i) {
            throw i;
        }
        return card;
    }

    public int endGamePresenter() {
//        int status = battle.endGame();
//        if(status!=-1) {
//            switch (status) {
//                case 0:
//                    BattleEnvironment.ShowEndGame(battle.getPlayers()[0], battle.getPlayers()[1], battle, battle.getMode(), 0);
//                    battle.getPlayers()[0].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.DRAW, battle.getPlayers()[1].getName()));
//                    battle.getPlayers()[1].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.DRAW, battle.getPlayers()[0].getName()));
//                    return 0;
//                case 1:
//                    BattleEnvironment.ShowEndGame(battle.getPlayers()[0], battle.getPlayers()[1], battle, battle.getMode(), 1);
//                    battle.getPlayers()[0].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.WIN, battle.getPlayers()[0].getName()));
//                    battle.getPlayers()[1].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.LOOSE, battle.getPlayers()[1].getName()));
//                    return 1;
//                case 2:
//                    BattleEnvironment.ShowEndGame(battle.getPlayers()[0], battle.getPlayers()[1], battle, battle.getMode(), 2);
//                    battle.getPlayers()[0].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.LOOSE, battle.getPlayers()[0].getName()));
//                    battle.getPlayers()[1].getMatchHistories().add(new MatchHistory(battleStartDate, MatchHistory.Result.WIN, battle.getPlayers()[1].getName()));
//                    return 2;
//            }
//        }
        return -1;
    }

    public Card showCardInfoInGraveYardPresenter(String cardID) {
        if(!IsValidInGameCardID(cardID)){
            throw new InvalidInGameAssetIDFormatException("");
        }
        return battle.getPlayersGraveYard()[0].searchInGraveYard(cardID.split("_")[1]);
    }

    public static boolean IsValidInGameCardID(String cardID) {
        return cardID.matches("(\\w+)_(\\w+)_(\\d+)");
    }
}
