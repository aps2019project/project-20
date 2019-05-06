package Presenter;

import Exceptions.AssetNotFoundException;
import Exceptions.InvalidInGameAssetIDFormatException;
import Model.Asset;
import Model.Battle;
import Model.Card;
import Model.Warrior;

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

    public void setPlayer() {
    }

    public void setOpponent() {
    }

    public void gameInfoPresenter() {
    }

    public void selectCardPresenter(String cardID) {
        if (!IsValidInGameCardID(cardID)) {
            throw new InvalidInGameAssetIDFormatException("");
        }
        Asset asset;
        try {
            asset = battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
        }catch (AssetNotFoundException e){
            throw e;
        }
        battle.selectCard(battle.getPlayers()[0], asset.getID());

    }

    public void attackPresenter(String opponentCardID) {
    }

    public void attackComboPresenter(String opponentCardID, String[] cardsID) {
        if(!IsValidInGameCardID(opponentCardID)){
            throw new InvalidInGameAssetIDFormatException("");
        }
        ArrayList<Warrior> warriors=new ArrayList<>();
        for (String cardID : cardsID) {
            if(!IsValidInGameCardID(cardID)){
                throw new InvalidInGameAssetIDFormatException("");
            }
            Asset asset;
            try {
                asset = battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
            }catch (AssetNotFoundException i){
              throw i;
            }
            if(asset instanceof Warrior){
                warriors.add((Warrior)asset);
            }
        }
        //battle.
    }

    public void counterAttackPresenter(int opponentCardID, int myCardID) {
    }

    public void useSpecialPowerPresenter(int x, int y) {
    }

    public void insertCardPresenter(String cardName, int x, int y) {
        battle.insertIn(battle.getPlayers()[0],cardName,x,y,battle.getBattleGround());
    }

    public void endTurnPresenter() {
    }

    public void selectItemPresenter(int collectableID) {
        battle.selectItem(battle.getPlayers()[0], collectableID);
    }

    public void cardMoveToPresenter(int x, int y) {
    }

    public void useItemPresenter(int x, int y) {
    }

    public void showInfoOfCard(String cardID) {
        if(!IsValidInGameCardID(cardID)){
            throw new InvalidInGameAssetIDFormatException("");
        }
        try {
            battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
            Asset.searchCard(battle.getPlayersDeck()[0].getCards(), cardID.split("_")[1]);
        }catch (AssetNotFoundException i){
            throw i;
        }
    }

    public void endGamePresenter() {
    }

    public void showMyMinionsPresenter() {
    }

    public void showOpponentMinionsPresenter() {
    }

    public void showCardInfoInGraveYardPresenter(String cardID) {
    }

    public void showHandPresenter() {
    }

    public void showNextCardPresenter() {
    }

    public void showItemInfoPresenter() {
    }

    public void showMenuPresenter() {
    }

    public void showCollectableItemsPresenter() {
    }

    public void showhelpPresenter() {
    }

    public static boolean IsValidInGameCardID(String cardID) {
        return cardID.matches("(\\s+)_(\\s+)_(\\d+)");
    }
}
