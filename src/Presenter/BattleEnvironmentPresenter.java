package Presenter;

import Exceptions.AssetNotFoundException;
import Exceptions.InvalidInGameAssetIDFormatException;
import Exceptions.InvalidTargetException;
import Model.*;

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
        battle.selectCard(battle.getPlayers()[0], asset.getID(),cardID);

    }

    public void attackPresenter(String opponentCardID) {
        battle.attack(battle.getPlayers()[0],(Warrior)battle.getPlayersSelectedCard()[0], battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0],opponentCardID.split("_")[1]).getID());
    }

    public void attackComboPresenter(String opponentCardID, String[] cardsID) {
        if(!IsValidInGameCardID(opponentCardID)){
            throw new InvalidInGameAssetIDFormatException("");
        }
        Card opponent = (Card) battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[1],opponentCardID);
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
        battle.attackCombo(battle.getPlayers()[0],warriors,opponent.getID());
    }


    public void useSpecialPowerPresenter(int x, int y) {
      battle.useSpecialPower(battle.getPlayers()[0],x,y);
    }

    public void insertCardPresenter(String cardName, int x, int y) {
        battle.insertIn(battle.getPlayers()[0],cardName,x,y,battle.getBattleGround());
    }

    public void endTurnPresenter() {
        battle.endTurn(battle.getPlayers()[0]);
        ((AI)battle.getPlayers()[1]).handleAIEvent(battle.getPlayers()[0],battle,battle.getBattleGround());
    }

    public void selectItemPresenter(int collectableID) {
        battle.selectItem(battle.getPlayers()[0], collectableID);
    }

    public void cardMoveToPresenter(int x, int y) {
        battle.cardMoveTo(battle.getPlayers()[0],(Warrior)battle.getPlayersSelectedCard()[0],x,y);
    }

    public void useItemPresenter(int x, int y) {
        x--;y--;
        if(battle.getBattleGround().getGround().get(y).get(x) instanceof Warrior && battle.getBattleGround().getGround().get(y).get(x).getOwner() == battle.getPlayers()[1]) {
            battle.useItem(battle.getPlayers()[0], battle.getPlayers()[1], (Warrior)battle.getBattleGround().getGround().get(y).get(x),battle.getPlayersSelectedCard()[0],battle.getPlayersSelectedItem()[0]);
        }else {
            throw new InvalidTargetException();
        }

    }

    public Card showInfoOfCard(String cardID) {
        if(!IsValidInGameCardID(cardID)){
            throw new InvalidInGameAssetIDFormatException("");
        }
        Card card;
        try {
            card = (Card)battle.getBattleGround().searchAssetInBattleGround(battle.getPlayers()[0], cardID.split("_")[1]);
            if(card!=null){
                return card;
            }
            card = Asset.searchCard(battle.getPlayersDeck()[0].getCards(), cardID.split("_")[1]);
        }catch (AssetNotFoundException i){
            throw i;
        }
        return card;
    }

    public void endGamePresenter() {
    }

    public Card showCardInfoInGraveYardPresenter(String cardID) {
        return battle.getPlayersGraveYard()[0].searchInGraveYard(cardID.split("_")[1]);
    }


    public static boolean IsValidInGameCardID(String cardID) {
        return cardID.matches("(\\s+)_(\\s+)_(\\d+)");
    }
}
