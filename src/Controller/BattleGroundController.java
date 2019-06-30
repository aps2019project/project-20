package Controller;

import Datas.AssetDatas;
import Exceptions.*;
import Model.*;
import Presenter.CurrentAccount;
import Presenter.GameMenuPresenter;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import static Model.BattleGround.CellEffect;

public class BattleGroundController implements Initializable {
    private static final int CELL_HEIGHT = 80;
    private static final int CELL_WIDTH = 80;
    public StackPane mainStackPane;
    public ImageView battleGroundImage;
    public ImageView playerProfilePic;
    public ImageView opponentProfilePic;
    public GridPane battleGroundGrid;
    public GridPane handAndNextCardGrid;
    public GridPane manaGemsRibbon;
    public Pane[][] battleGroundPanes;
    public Pane[] handAndNextCardPanes;
    public ImageView errorBar;
    public Image errorImage;
    public Image freeCellImage;
    public JFXTextArea errorMessage;
    public ImageView endTurn;
    private HashMap<Warrior, TextArea> healthBars = new HashMap<>();
//    public Button menuButton;
//    public Button friendButton;
    public AnchorPane battleGroundAnchorPane;
    private ImageView[][] groundImageViews;
    private ArrayList<ImageView>[][] cellEffectImageViews = new ArrayList[BattleGround.getRows()][BattleGround.getColumns()];
    private ImageView[] handAndNextCardImageViews;
    private ImageView selectedCardBackground;
    private int[] selectedCardCoordinates = new int[]{-2, -2};
    private Battle battle;
    private AIController aiController;

    public BattleGroundController(Battle battle){
        this.battle = battle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Next three lines are Just for test and must be replaced properly.
//        CurrentAccount.setCurrentAccount(Account.createAccount("a", "1234"));
//        GameMenuPresenter gameMenuPresenter = new GameMenuPresenter();
//        battle = gameMenuPresenter.prepareForSingleGame(1, null);

        aiController = new AIController(battle);
        selectedCardBackground = new ImageView(new Image("file:images/card_background_highlight.png"));
        errorImage = new Image("file:images/notification_quest_small.png");
        freeCellImage = new Image("file:images/card_background.png");

        initializeSize(selectedCardBackground);
        initializeProfilePics();
        updateManaGemImages();
        initializeGround();
        initializeHandImages();
        setEndTurnMouse();
    }

    private void initializeSize(ImageView imageView) {
        if (imageView == selectedCardBackground || imageView.getImage() == freeCellImage)
            imageView.setOpacity(0.5);
        else
            imageView.setOpacity(1);
        imageView.setFitHeight(CELL_HEIGHT);
        imageView.setFitWidth(CELL_WIDTH);
    }

    private void initializeProfilePics() {
        String firstHeroName = battle.getPlayers()[0].getMainDeck().getHero().getName();
        String secondHeroName = battle.getPlayers()[1].getMainDeck().getHero().getName();
        playerProfilePic.setImage(new Image("file:images/cards/hero/" + firstHeroName + "/" + firstHeroName + "_profile.png"));
        opponentProfilePic.setImage(new Image("file:images/cards/hero/" + secondHeroName + "/" + secondHeroName + "_profile.png"));
    }

    private void updateManaGemImages() {
        ImageView[] manaGemImageViews = new ImageView[Battle.MAX_MANA_IN_LATE_TURNS];
        for (int i = 0; i < battle.getPlayersMana()[0]; i++) {
            manaGemImageViews[i] = new ImageView(new Image("file:images/icon_mana.png"));
            manaGemsRibbon.add(manaGemImageViews[i], i, 0);
        }
        for (int i = battle.getPlayersMana()[0]; i < Battle.MAX_MANA_IN_LATE_TURNS; i++) {
            manaGemImageViews[i] = new ImageView(new Image("file:images/icon_mana_inactive.png"));
            manaGemsRibbon.add(manaGemImageViews[i], i, 0);
        }
    }

    private void initializeHandImages() {
        handAndNextCardImageViews = new ImageView[Battle.NUMBER_OF_CARDS_IN_HAND + 1];
        handAndNextCardPanes = new Pane[Battle.NUMBER_OF_CARDS_IN_HAND + 1];
        setNextCardRing();
        for (int i = 0; i <= Battle.NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card;
            if (i == 0)
                card = battle.getPlayersNextCardFromDeck()[0];
            else
                card = battle.getPlayersHand()[0][i - 1];

            if (card instanceof Warrior) {
                handAndNextCardImageViews[i] = new ImageView(new Image(((Warrior) card).getImageAddresses().get(Warrior.State.idle)));
                if (i != 0)
                    setSelectedCardFromHandClick(handAndNextCardImageViews[i], i);
            }
            else if (card instanceof Spell) {
                handAndNextCardImageViews[i] = new ImageView(new Image(((Spell) card).getImageAddresses().get(Spell.State.actionbar)));
                if (i != 0)
                    setSelectedCardFromHandClick(handAndNextCardImageViews[i], i);
            }
            else
                handAndNextCardImageViews[i] = new ImageView();
            initializeSize(handAndNextCardImageViews[i]);
            handAndNextCardPanes[i] = new Pane(handAndNextCardImageViews[i]);
            handAndNextCardGrid.add(handAndNextCardPanes[i], i, 0);
        }
    }

    private void setSelectedCardFromHandClick(ImageView handAndNextCardImageView, int i) {
        handAndNextCardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedCardCoordinates[0] == -1) //Card is already in hand.
                    handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                else if (selectedCardCoordinates[0] > -1)
                    battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                selectedCardCoordinates[0] = -1;
                selectedCardCoordinates[1] = i;
                handAndNextCardPanes[i].getChildren().add(selectedCardBackground);
            }
        });
    }

    private void setNextCardRing() {
        ImageView nextCardRing = new ImageView(new Image("file:images/replace_outer_ring_smoke.png"));
        initializeSize(nextCardRing);
        handAndNextCardGrid.add(nextCardRing, 0, 0);
    }

    private void initializeGround() {
        battleGroundPanes = new Pane[BattleGround.getRows()][BattleGround.getColumns()];
        groundImageViews = new ImageView[BattleGround.getRows()][BattleGround.getColumns()];
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                if (asset instanceof Warrior) {
                    groundImageViews[i][j] = new ImageView(new Image(((Warrior) asset).getImageAddresses().get(Warrior.State.breathing)));
                    healthBars.put((Warrior) asset, new TextArea(String.valueOf(((Warrior) asset).getHP())));
                    healthBars.get(asset).setLayoutX(groundImageViews[i][j].getLayoutX());
                    healthBars.get(asset).setLayoutY(groundImageViews[i][j].getLayoutY());
                }
                else if (asset instanceof Item)
                    groundImageViews[i][j] = new ImageView(new Image(((Item) asset).getImageAddresses().get(Item.State.actionbar)));
                else if (asset instanceof Flag)
                    groundImageViews[i][j] = new ImageView(new Image("file:images/flag_icon.png"));
                else
                    groundImageViews[i][j] = new ImageView(new Image("file:images/card_background.png"));
                initializeSize(groundImageViews[i][j]);
                prepareCoordinates(asset, i, j);
                setGroundCellOnClickEvent(groundImageViews[i][j], asset, i, j);
            }
        }
    }

    private void prepareCoordinates(Asset asset, int i, int j) {
        battleGroundPanes[i][j] = new Pane(groundImageViews[i][j]);
//        battleGroundPanes[i][j].getChildren().add(healthBars.get(asset));
        battleGroundPanes[i][j].setLayoutX(battleGroundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutX());
        battleGroundPanes[i][j].setLayoutY(battleGroundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutY());
        groundImageViews[i][j].setLayoutX(battleGroundPanes[i][j].getLayoutX());
        groundImageViews[i][j].setLayoutY(battleGroundPanes[i][j].getLayoutY());
        battleGroundPanes[i][j].setPrefHeight(CELL_HEIGHT);
        battleGroundPanes[i][j].setPrefWidth(CELL_WIDTH);
        battleGroundGrid.add(battleGroundPanes[i][j], j, i);
    }

    private void setGroundCellOnClickEvent(ImageView imageView, Asset asset, int i, int j) {
        imageView.setOnMouseClicked(event -> {
            if (asset instanceof Warrior) {
                if (asset.getOwner() == battle.getPlayers()[0])
                    selectCardInGround(asset, i, j);
                else if (asset.getOwner() == battle.getPlayers()[1] && selectedCardCoordinates[0] > -1)
                    attack((Warrior) asset, i, j);
            }
            else {
                if (selectedCardCoordinates[0] == -1) {
                    if (battle.getPlayersHand()[0][selectedCardCoordinates[1]] instanceof Warrior) {
                        Warrior picker = (Warrior) battle.getPlayersHand()[0][selectedCardCoordinates[1]];
                        if (asset instanceof Flag)
                            battle.collectFlag(picker, j, i);
                        else if (asset instanceof Item)
                            battle.collectItem(battle.getPlayersDeck()[0], j, i);
                    }
                    insertCard(i, j);
                }
                else if (selectedCardCoordinates[0] > -1)
                    moveCard(i, j);
            }
        });
    }

    private void selectCardInGround(Asset asset, int i, int j) {
        battle.selectWarrior(asset.getOwner(), asset.getID());
        if (selectedCardCoordinates[0] == -1) //Card is already in hand.
            handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        else if (selectedCardCoordinates[0] > -1)
            battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
        battleGroundPanes[i][j].getChildren().add(selectedCardBackground);
    }

    private void insertCard(int i, int j) {
        try {
            Card handCard = battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1]; // Because after the next line, target card becomes null.
            battle.insertCard(battle.getPlayers()[0], battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1].getName(), j + 1, i + 1);
            showInsertAnimation(i, j, handCard);
            resetSelectedCardCoordinates();
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | DontHaveEnoughManaException e) {
            handleError(e);
        }
    }

    private void showInsertAnimation(int i, int j, Card handCard) {
        groundImageViews[i][j].setOpacity(1);
        handAndNextCardImageViews[selectedCardCoordinates[1]].setImage(null);
        handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        if (handCard instanceof Warrior) {
            groundImageViews[i][j].setImage(new Image(((Warrior) handCard).getImageAddresses().get(Warrior.State.breathing)));
            battleGroundPanes[i][j].getChildren().add(selectedCardBackground);
            selectedCardCoordinates[0] = i;
            selectedCardCoordinates[1] = j;
        }
        else if (handCard instanceof Spell) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    groundImageViews[i][j].setImage(new Image(((Spell) handCard).getImageAddresses().get(Spell.State.active)));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    groundImageViews[i][j].setImage(null);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateCellEffects();
                        }
                    });
                }
            });
            t1.start();
        }
    }

    private void resetSelectedCardCoordinates() {
        selectedCardCoordinates[0] = -2;
        selectedCardCoordinates[1] = -2;
    }

    private void moveCard(int i, int j) {
        try {
            battle.cardMoveTo(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], j + 1, i + 1);
            showMove(i, j);
        } catch (InvalidTargetException | ThisCellFilledException | WarriorSecondMoveInTurnException e) {
            handleError(e);
        }
    }

    private void showMove(int i, int j) {
        Warrior warrior = (Warrior) battle.getPlayersSelectedCard()[0];
        Thread moveAnimation = new Thread(new Runnable() {
            @Override
            public void run() {
                animateMove(warrior, i, j);
                groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(freeCellImage);
                initializeSize(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                        battleGroundPanes[i][j].getChildren().add(selectedCardBackground);
                    }
                });
                groundImageViews[i][j].setImage(new Image(warrior.getImageAddresses().get(Warrior.State.breathing)));
                groundImageViews[i][j].setOpacity(1);
                selectedCardCoordinates[0] = i;
                selectedCardCoordinates[1] = j;
            }
        });
        moveAnimation.start();
    }

    private void animateMove (Warrior warrior, int finalRow, int finalColumn) {
        TranslateTransition translateTransition = new TranslateTransition();
        groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(warrior.getImageAddresses().get(Warrior.State.run)));
        translateTransition.setNode(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
        double initX = battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getLayoutX();
        double initY = battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getLayoutY() - 2 * battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getHeight();
//        double initX = groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getX();
//        double initY = groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getY();
        translateTransition.setFromX(initX);
        translateTransition.setFromY(initY);
        translateTransition.setToX(battleGroundPanes[finalRow][finalColumn].getLayoutY());
        translateTransition.setToY(battleGroundPanes[finalRow][finalColumn].getLayoutY() - 2 * battleGroundPanes[finalRow][finalColumn].getHeight());
        translateTransition.setDuration(new Duration(1000));
        translateTransition.play();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        translateTransition.stop();
//        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                groundImageViews[finalRow][finalColumn].setLayoutX(initX);
//                groundImageViews[finalRow][finalColumn].setLayoutY(initY);
//            }
//        });
//        groundImageViews[finalRow][finalColumn].setLayoutX(initX);
//        groundImageViews[finalRow][finalColumn].setLayoutY(initY);
//        translateTransition.setDuration(new Duration(1));
//        translateTransition.play();
    }

    private void attack(Warrior opponentWarrior, int i, int j) {
        try {
            battle.attack(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], opponentWarrior);
            showAttackAnimation(opponentWarrior, i, j);
        } catch (AssetNotFoundException | InvalidAttackException e) {
            handleError(e);
        }
    }

    private void showAttackAnimation(Warrior opponentWarrior, int i, int j) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Warrior attacker = (Warrior) battle.getPlayersSelectedCard()[0];
                int attackerRow = selectedCardCoordinates[0];
                int attackerColumn = selectedCardCoordinates[1];
                groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getImageAddresses().get(Warrior.State.attack)));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getImageAddresses().get(Warrior.State.breathing)));
            }
        }).start();
        //TODO: Is it needed to show a bubble indicating value of damage applied?
    }

    private void setEndTurnMouse() {
        endTurn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endTurn.setOpacity(0.6);
            }
        });
        endTurn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endTurn.setOpacity(1);
            }
        });
        endTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endTurn.setOpacity(1);
                battle.endTurn(battle.getPlayers()[0]);
                updateManaGemImages();
                updateCellEffects();
                initializeHandImages();
//                aiController.handleAIEvent(battle);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateCellEffects() {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                for (CellEffect cellEffect: battle.getBattleGround().getEffectsPosition().get(i).get(j)) {
                    ImageView temp = new ImageView(new Image("file:images/cellEffect_" + cellEffect.name() + ".png"));
                    initializeSize(temp);
                    temp.setOpacity(0.4);
                    cellEffectImageViews[i][j] = new ArrayList<>();
                    cellEffectImageViews[i][j].add(temp);
                    battleGroundPanes[i][j].getChildren().add(temp);
                }
            }
        }
    }

    private void handleError(RuntimeException e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                errorBar.setImage(errorImage);
                errorBar.setFitHeight(100);
                errorBar.setFitWidth(300);
                errorMessage.setVisible(true);
                errorMessage.setText(getErrorMessage(e));
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                errorBar.setImage(null);
                errorMessage.setText(null);
                errorMessage.setVisible(false);
            }
        }).start();
    }

    private String getErrorMessage(RuntimeException e) {
        if (e instanceof AssetNotFoundException)
            return "Asset not found.";
        else if (e instanceof InvalidAttackException)
            return e.getMessage();
        else if (e instanceof InvalidInsertInBattleGroundException)
            return "Invalid target to insert card.";
        else if (e instanceof InvalidTargetException)
            return "Invalid target to move.";
        else if (e instanceof ThisCellFilledException)
            return "Target cell is filled.";
        else if (e instanceof DontHaveEnoughManaException)
            return "You don't have enough mana.";
        else if (e instanceof WarriorSecondMoveInTurnException)
            return "You can't move this unit twice.";
        return null;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node: gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}