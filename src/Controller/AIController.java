package Controller;

import Exceptions.*;
import Model.*;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<Card> inGroundCards;

    //    player[1] is ai
    public AIController(Battle battle) {
        this.battle = battle;
        inGroundCards = battle.getInGroundCards();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleAIEvent(Battle battle) {
        Account player = battle.getPlayers()[0];
        int i = makeRandomNumber(BattleGround.getRows());
        int j = makeRandomNumber(BattleGround.getColumns());
        int key = makeRandomNumber(6);

        selectAICard(battle , i , j);
        System.out.println(key);

        try {
            switch (key) {
                case 0:
//                    selectAICard(battle, i, j);
                    insertAICard(battle, i, j);

                    break;
                case 1:
                    Hero hero = battle.getPlayers()[1].getMainDeck().getHero();
                    selectAICard(battle, hero.getXInGround(), hero.getYInGround());
                    AIAttackPlayerHero(battle.getPlayers()[1], battle);

                    break;
                case 2:
                    selectAICard(battle, i, j);
                    moveAICard(battle, i-1, j);

                    break;
                case 3:
                    AIAttackWarriors(player, battle);

                    break;
                case 4:
                    AIUseSpecialPower(battle);

                    break;
                case 5:
                    AIComboAttack(player, battle);

                    break;

            }
            battle.endTurn(battle.getPlayers()[1]);

        } catch (Exception e) {
            battle.endTurn(battle.getPlayers()[1]);
        }
    }


    //checked and fast forward
    public void AIUseSpecialPower(Battle battle) {
        //TODO
//        battle.applyHeroSpecialPower(battle.getPlayers()[1], );
        showSpecialPowerAnimation();
    }

    public void showSpecialPowerAnimation() {
        Hero hero = battle.getPlayers()[0].getMainDeck().getHero();

        new Thread(() -> {
            TranslateTransition translateTransition = new TranslateTransition();
            groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(hero.getCastLoopImageAddress()));
            translateTransition.setNode(battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
            translateTransition.setToX(battleGroundPanes[hero.getXInGround()][hero.getYInGround()].getLayoutX());
            translateTransition.setToY(battleGroundPanes[hero.getXInGround()][hero.getYInGround()].getLayoutY());
            translateTransition.setDuration(new Duration(500));
            translateTransition.play();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                handleError();
            }
            groundImageViews[hero.getXInGround()][hero.getYInGround()].setImage(new Image(hero.getBreathingImageAddress()));
        }).start();
    }

    public Asset findAttacker(Battle battle) {
        Asset attacker;

        if (battle.getPlayersSelectedCard()[1] instanceof Warrior)
            return battle.getPlayersSelectedCard()[1];
        else {
            while (true) {
                attacker = inGroundCards.get(makeRandomNumber(inGroundCards.size()));
                if (attacker instanceof Warrior && attacker.getOwner() == battle.getPlayers()[1])
                    break;
            }
            return attacker;
        }
    }

    public Warrior findPlayerMinion(Account player) {
        Asset playerCard;
        while (true) {
            playerCard = inGroundCards.get(makeRandomNumber(inGroundCards.size()));
            if (playerCard instanceof Warrior && playerCard.getOwner() == player)
                break;
        }
        return (Warrior) playerCard;
    }

    public void AIAttackWarriors(Account player, Battle battle) {
        Asset attacker;
        attacker = findAttacker(battle);
        Asset playerCard = findPlayerMinion(player);
        try {
            battle.attack(battle.getPlayers()[1], (Warrior) attacker, (Warrior) playerCard);
            showAttackAnimation((Warrior) attacker);

        } catch (AssetNotFoundException | InvalidAttackException e) {
            handleError();
        }

    }

    public void AIAttackPlayerHero(Account player, Battle battle) {
        Asset attacker = findAttacker(battle);
        battle.attack(battle.getPlayers()[1], (Warrior) attacker, (Warrior) findPlayerHero(player));
    }

    public void showAttackAnimation(Warrior attacker) {
//        Warrior attacker = (Warrior) battle.getPlayersSelectedCard()[0];

        int attackerRow = selectedCardCoordinates[0];
        int attackerColumn = selectedCardCoordinates[1];
        groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getAttackImageAddress()));
    }

    public Asset findPlayerHero(Account player) {
        return player.getMainDeck().getHero();
    }

    public int makeRandomNumber(int value) {
        Random rand = new Random();
        return rand.nextInt(value);
    }

    public void moveAICard(Battle battle, int i, int j) {

        Asset moveWarrior = findPlayerMinion(battle.getPlayers()[1]);
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
            translateTransition.setDuration(new Duration(100));
            translateTransition.play();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
        }).start();
    }

    public void insertAICard(Battle battle, int i, int j) {
        try {
            Card[] AIHand = battle.getPlayersHand()[1];
            Card insertedCard = AIHand[makeRandomNumber(battle.getNUMBER_OF_CARDS_IN_HAND())];
            battle.insertCard(battle.getPlayers()[1], battle.getPlayersHand()[1][selectedCardCoordinates[1] - 1].getName(), j + 1, i + 1);
            showInsertAnimation(i, j);
//            battle.selectWarrior(battle.getPlayers()[1], insertedCard.getID());
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | InsufficientManaException e) {
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
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            groundImageViews[i][j].setImage(null);
        }
        handAndNextCardImageViews[selectedCardCoordinates[1]].setImage(null);
    }

    public void selectAICard(Battle battle, int i, int j) {
        Asset asset;
        while (true) {
            asset = inGroundCards.get(makeRandomNumber(inGroundCards.size()));
            if (asset != null && asset.getOwner() == battle.getPlayers()[1])
                break;
        }
//        battle.selectWarrior(battle.getPlayers()[1], asset.getID());
        if (selectedCardCoordinates[0] == -1) //Card is already in hand.
            handAndNextCard.getChildren().remove(selectedCardBackground);
        else if (selectedCardCoordinates[0] > -1) {
            battleGroundGrid.getChildren().remove(selectedCardBackground);
        }
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
        battleGroundGrid.add(selectedCardBackground, j, i);
    }

    public void handleError() {
        battle.endTurn(battle.getPlayers()[1]);
    }


    // unchecked


    public void AIComboAttack(Account player, Battle battle) {
        Asset playerAsset;
        ArrayList<Minion> minions = new ArrayList<>();
        while (true) {
            playerAsset = inGroundCards.get(makeRandomNumber(inGroundCards.size()));
            if (playerAsset != null && playerAsset.getOwner() == player)
                break;
        }
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Minion minion = (Minion) battle.getBattleGround().getGround().get(i).get(j);
                if (minion.getOwner() == battle.getPlayers()[1] && minion.getActivateTimeOfSpecialPower() == COMBO) {
                    minions.add(minion);
                }
            }
        }

        Asset playerCard = findPlayerMinion(player);

        for (int i = 0; i < minions.size(); i++) {
            try {
                battle.attack(battle.getPlayers()[1], minions.get(i), (Warrior) playerCard);
                showAttackAnimation(minions.get(i));

            } catch (AssetNotFoundException | InvalidAttackException e) {
                handleError();
            }

        }
    }

    public Asset findAIStrongAsset(Battle battle) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                if (battle.getBattleGround().getGround().get(i).get(j).getOwner() == battle.getPlayers()[1])
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


}
