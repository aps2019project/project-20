package Controller;

import Exceptions.*;
import Model.*;
import Controller.BattleGroundController;
import Presenter.CurrentAccount;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static Model.Minion.ActivateTimeOfSpecialPower.COMBO;

public class AIController implements Initializable {

    private static final int CELL_HEIGHT = 100;
    private static final int CELL_WIDTH = 100;
    public StackPane mainStackPane;
    public ImageView battleGroundImage;
    public ImageView playerProfilePic;
    public ImageView opponentProfilePic;
    public GridPane battleGroundGrid;
    public GridPane handAndNextCard;
    public GridPane manaGemsRibbon;
    public Pane[][] battleGroundPanes;
    public Pane[][] handAndNextCardPanes;
    public ImageView errorBar;
    public Image errorImage;
    public JFXTextArea errorMessage;
    public AnchorPane battleGroundAnchorPane;
    private ImageView[][] groundImageViews;
    private ImageView[] handAndNextCardImageViews;
    private ImageView selectedCardBackground;
    private int[] selectedCardCoordinates = new int[]{-2, -2};
    private Battle battle;
    private ArrayList<Card> inGroundCards = new ArrayList<>();
    private AI ai = new AI("AI", "1234");

    public AIController(Battle battle) {
        this.battle = battle;
        inGroundCards.add(battle.getPlayersDeck()[1].getHero());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleAIEvent(Battle battle) {
        Account player = battle.getPlayers()[0];
        int i = makeRandomNumber(BattleGround.getRows());
        int j = makeRandomNumber(BattleGround.getColumns());
        try {
            int key = makeRandomNumber(3);
            switch (key) {
                case 0:

                    int event1 = makeRandomNumber(3);
                    switch (event1) {
                        case 0:
                            insertAICard(battle, i, j);
                            break;
                        case 1:

                            selectAICard(battle, i, j);
                            break;
                        case 2:

                            moveAICard(battle, i, j);
                            break;
                    }
                    battle.endTurn(ai);

                    event1 = makeRandomNumber(3);
                    switch (event1) {
                        case 0:

                            AIAttackWarriors(player, battle);
                            break;
                        case 1:

                            AIComboAttack(player, battle);
                            break;
                        case 2:

                            AIUseSpecialPower(battle);
                            break;
                    }
                    battle.endTurn(ai);
                    break;

                case 1:

                    int event2 = makeRandomNumber(3);

                    switch (event2) {
                        case 0:

                            AIAttackWarriors(player, battle);
                            break;

                        case 1:

                            insertAICard(battle, i, j);
                            break;
                        case 2:

                            selectAICard(battle, i, j);
                            break;
                    }
                    battle.endTurn(ai);
                    event2 = makeRandomNumber(3);
                    switch (event2) {
                        case 0:

                            AIUseSpecialPower(battle);
                            break;

                        case 1:

                            moveAICard(battle, i, j);
                            break;

                        case 2:

                            AIComboAttack(player, battle);
                            break;

                    }
                    battle.endTurn(ai);
                    break;

                case 2:
                    int event3 = makeRandomNumber(3);

                    switch (event3) {
                        case 0:

                            insertAICard(battle, i, j);
                            break;
                        case 1:

                            AIAttackPlayerHero(player, battle);
                            break;
                        case 2:

                            AIUseSpecialPower(battle);
                            break;
                    }

                    event3 = makeRandomNumber(3);
                    switch (event3) {
                        case 0:

                            selectAICard(battle, i, j);
                            break;
                        case 1:

                            AIComboAttack(player, battle);
                            break;
                        case 2:

                            moveAICard(battle, i, j);
                            break;
                    }
                    battle.endTurn(ai);
                    break;

            }
        } catch (Exception e) {
            battle.endTurn(ai);
        }
    }

    public void AIUseSpecialPower(Battle battle) {
        battle.applySpecialPower(ai.getMainDeck().getHero(),(Warrior) ai.findPlayerMinion(battle.getPlayers()[1],battle), Minion.ActivateTimeOfSpecialPower.ON_ATTACK);
        showSpecialPowerAnimation();
    }

    public void showSpecialPowerAnimation(){
        Hero hero = battle.getPlayers()[0].getMainDeck().getHero();

        new Thread(()->{
            TranslateTransition translateTransition = new TranslateTransition();
            groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(hero.getCastLoopImageAddress()));
            translateTransition.setNode(battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
            translateTransition.setToX(battleGroundPanes[hero.getXInGround()][hero.getYInGround()].getLayoutX());
            translateTransition.setToY(battleGroundPanes[hero.getXInGround()][hero.getYInGround()].getLayoutY());
            translateTransition.setDuration(new Duration(500));
            translateTransition.play();

            try {
                Thread.sleep(500);
            } catch (Exception e) {
                handleError();
            }
            groundImageViews[hero.getXInGround()][hero.getYInGround()].setImage(new Image(hero.getBreathingImageAddress()));
        }).start();
    }

    public void AIComboAttack(Account player, Battle battle) {
        Asset playerAsset;
        ArrayList<Minion> minions = new ArrayList<>();
        while (true) {
            playerAsset = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getRows())).get(makeRandomNumber(BattleGround.getColumns()));
            if (playerAsset.getOwner() == player)
                break;
        }
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Minion minion = (Minion) battle.getBattleGround().getGround().get(i).get(j);
                if (minion.getOwner() == ai && minion.getActivateTimeOfSpecialPower() == COMBO) {
                    minions.add(minion);
                }
            }
        }

        Asset playerCard = findPlayerMinion(player, battle);

        for (int i = 0  ; i< minions.size() ; i++){
            try {
                battle.attack(ai, minions.get(i), (Warrior) playerCard);
                showAttackAnimation(minions.get(i), minions.get(i).getXInGround(), minions.get(i).getYInGround());

            } catch (AssetNotFoundException | InvalidAttackException e) {
                handleError();
            }

        }
    }

    public void AIAttackWarriors(Account player, Battle battle) {
        Asset attacker;
        attacker = findAttacker(battle);
        Asset playerCard = findPlayerMinion(player, battle);
        try {
            battle.attack(ai,(Warrior) attacker, (Warrior) playerCard);
            showAttackAnimation((Warrior) attacker, attacker.getXInGround(), attacker.getYInGround());

        } catch (AssetNotFoundException | InvalidAttackException e) {
            handleError();
        }

    }

    public Asset findPlayerMinion(Account player, Battle battle) {
        Asset playerCard;
        while (true) {
            playerCard = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getRows())).get(makeRandomNumber(BattleGround.getColumns()));
            if (playerCard instanceof Warrior && playerCard.getOwner() == player)
                break;
        }
        return playerCard;
    }

    public void AIAttackPlayerHero(Account player, Battle battle) {
        Asset attacker = findAttacker(battle);
        battle.attack(ai, (Warrior) attacker, (Warrior) findPlayerHero(player, battle));
    }

    public void moveAICard(Battle battle, int i, int j) {

        Asset moveWarrior = findPlayerMinion(ai, battle);
        try {
            battle.cardMoveTo(battle.getPlayers()[0], (Warrior) moveWarrior, j + 1, i + 1);
            showMoveAnimation(i, j);
        } catch (InvalidTargetException | ThisCellFilledException | WarriorSecondMoveInTurnException e) {
            handleError();
        }

    }

    public void showMoveAnimation(int i, int j) {
        Warrior warrior = (Warrior) battle.getPlayersSelectedCard()[0];
        new Thread(() -> {
            TranslateTransition translateTransition = new TranslateTransition();
            groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(warrior.getRunImageAddress()));
            translateTransition.setNode(battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
            translateTransition.setToX(battleGroundPanes[i][j].getLayoutX());
            translateTransition.setToY(battleGroundPanes[i][j].getLayoutY());
            translateTransition.setDuration(new Duration(500));
            translateTransition.play();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
        }).start();
    }

    public void selectAICard(Battle battle, int i, int j) {
        Asset asset;
        while (true) {
            asset = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getRows())).get(BattleGround.getColumns());
            if (asset.getOwner() == ai)
                break;
        }
        battle.selectWarrior(ai, asset.getID());
        if (selectedCardCoordinates[0] == -1) //Card is already in hand.
            handAndNextCard.getChildren().remove(selectedCardBackground);
        else if (selectedCardCoordinates[0] > -1) {
            battleGroundGrid.getChildren().remove(selectedCardBackground);
        }
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
        battleGroundGrid.add(selectedCardBackground, j, i);
    }

    public void insertAICard(Battle battle, int i, int j) {
        try {

            Card[] AIHand = battle.getPlayersHand()[1];

            Card insertedCard = AIHand[makeRandomNumber(battle.getNUMBER_OF_CARDS_IN_HAND())];
            battle.insertCard(ai, battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1].getName(), j + 1, i + 1);
            showInsertAnimation(i, j);
            battle.selectWarrior(ai, insertedCard.getID());
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | DontHaveEnoughManaException e) {
            handleError();
        }
    }

    public void showInsertAnimation(int i, int j) {
        Card handCard = battle.getPlayersHand()[0][selectedCardCoordinates[1]];
        if (handCard instanceof Warrior)
            groundImageViews[i][j].setImage(new Image(((Warrior) handCard).getBreathingImageAddress()));
        else if (handCard instanceof Spell) {
            groundImageViews[i][j].setImage(new Image(((Spell) handCard).getActiveImageAddress()));

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            groundImageViews[i][j].setImage(null);
        }
        handAndNextCardImageViews[selectedCardCoordinates[1]].setImage(null);
    }

    public void showAttackAnimation(Warrior opponentWarrior, int i, int j) {
        Warrior attacker = (Warrior) battle.getPlayersSelectedCard()[0];
        int attackerRow = selectedCardCoordinates[0];
        int attackerColumn = selectedCardCoordinates[1];
        groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getAttackImageAddress()));
    }

    public int makeRandomNumber(int value) {
        Random rand = new Random();
        return rand.nextInt(value);
    }

    public Asset findPlayerHero(Account player, Battle battle) {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == player
                        && battle.getBattleGround().getGround().get(i).get(j) instanceof Hero)
                    return battle.getBattleGround().getGround().get(i).get(j);
            }
        }
        return null;
    }

    public Asset findAttacker(Battle battle) {
        Asset attacker;
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {

            }
        }
//        while (true) {
            attacker = battle.getBattleGround().getGround().get(makeRandomNumber(BattleGround.getRows())).get(makeRandomNumber(BattleGround.getColumns()));
//            if (attacker instanceof Warrior && attacker.getOwner() == ai)
//                break;
//        }
        return attacker;
    }

    public Asset findAIStrongAsset(Battle battle) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == ai)
                    assets.add(battle.getBattleGround().getGround().get(i).get(j));
            }
        }
        if (assets.size() == 0)
            return null;
        else {
            Asset strongAsset = assets.get(0);
            for (Asset asset : assets) {
                if (((Warrior) asset).getAP() >= ((Warrior) strongAsset).getAP())
                    strongAsset = asset;
            }
            return strongAsset;
        }
    }

    public Asset findPlayerWeakAsset(Account player, Battle battle) {
        ArrayList<Asset> playerAssets = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == player)
                    playerAssets.add(battle.getBattleGround().getGround().get(i).get(j));
            }
        }
        if (playerAssets.size() == 0)
            return null;
        else {
            Asset weakAsset = playerAssets.get(0);
            for (Asset asset : playerAssets) {
                if (((Warrior) asset).getHP() <= ((Warrior) weakAsset).getHP())
                    weakAsset = asset;
            }
            return weakAsset;
        }
    }

    private void handleError() {
        battle.endTurn(ai);
    }
}
