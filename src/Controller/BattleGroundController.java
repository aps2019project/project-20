package Controller;

import Exceptions.*;
import Model.*;
import Presenter.Animationable;
import Presenter.CurrentAccount;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import static Model.BattleGround.CellEffect;

public class BattleGroundController implements Initializable, ScreenManager , DialogThrowable, Animationable {
    private static final int CELL_HEIGHT = 80;
    private static final int CELL_WIDTH = 80;
    private static final int MAX_NUMBER_OF_COLLECTIBLE_ITEMS = 9;
    public StackPane mainStackPane;
    public ImageView battleGroundImage;
    public ImageView playerProfilePic;
    public ImageView opponentProfilePic;
    public Label playerName;
    public Label opponentName;
    public GridPane groundGrid;
    public GridPane handAndNextCardGrid;
    public GridPane manaGemsRibbon;
    public GridPane collectedItemsGrid;
    public ImageView menuButton;
    public Pane pauseMenu;
    public ImageView saveButton;
    public ImageView exitButton;
    public ImageView pauseMenuCloseButton;
    private Pane[] collectedItemsPanes;
    private ImageView[] collectedItemsImageViews;
    private Pane[][] groundPanes;
    private Pane[] handAndNextCardPanes;
    public ImageView errorBar;
    private Image errorImage;
    private String freeCellImageAddress = "file:images/card_background.png";
    public JFXTextArea errorMessage;
    public ImageView endTurn;
    public ImageView cheatButton;
    public  ProgressBar progressbar;
    private HashMap<Warrior, Label> healthBars = new HashMap<>();
    //    public Button menuButton;
//    public Button friendButton;
    public AnchorPane battleGroundAnchorPane;
    private ImageView[][] groundImageViews;
    private ArrayList<ImageView>[][] cellEffectImageViews = new ArrayList[BattleGround.getRows()][BattleGround.getColumns()];
    private ImageView[] handAndNextCardImageViews;
    private ImageView selectedCardBackground;
    private int[] selectedCardCoordinates = new int[]{-2, -2};
    private Battle battle;
    private int itemsCursor = 1;
    private AIController aiController;
    private ImageView[] manaGemImageViews;
    private String dragAndDropKey = "Card dragged from hand.";

    public BattleGroundController() {
    }

    public BattleGroundController(Battle battle) {
        this.battle = battle;
    }

    public  ProgressBar getProgressbar() {
        return progressbar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Next three lines are Just for test and must be replaced properly.
//        Account temp = new Account("reza", "1234");
//        temp.setMainDeck(DeckDatas.getDefaultDeck());
//        CurrentAccount.setCurrentAccount(temp);
//        battle = Battle.soloCustomKillHeroModeConstructor("rostam");

        aiController = new AIController(battle);
        selectedCardBackground = new ImageView(new Image("file:images/card_background_highlight.png"));
        errorImage = new Image("file:images/notification_quest_small.png");
        initializeSize(selectedCardBackground);
        initializeProfilesInfo();
        initializeManaGemImages();
        initializeGround();
        initializeHandImages();
        initializeMenuButtonEvents();
        setEndTurnEvent();


        TimeLine t0 = new TimeLine(progressbar);
        TimeLine t1 = new TimeLine(progressbar);


        t0.start();
        battle.endTurn(battle.getPlayers()[0]);

        t1.start();
        battle.endTurn(battle.getPlayers()[1]);


    }

    private void initializeSize(ImageView imageView) {
        if (imageView == selectedCardBackground || (imageView.getImage() != null && imageView.getImage().impl_getUrl().equals(freeCellImageAddress)))
            imageView.setOpacity(0.5);
        else
            imageView.setOpacity(1);
        imageView.setFitHeight(CELL_HEIGHT);
        imageView.setFitWidth(CELL_WIDTH);
    }

    private void initializeSize(ImageView imageView, double scale) {
        initializeSize(imageView);
        imageView.setFitHeight(CELL_HEIGHT * scale);
        imageView.setFitWidth(CELL_WIDTH * scale);

    }

    private void initializeProfilesInfo() {
        String firstHeroName = battle.getPlayers()[0].getMainDeck().getHero().getName();
        String secondHeroName = battle.getPlayers()[1].getMainDeck().getHero().getName();
        playerProfilePic.setImage(new Image("file:images/cards/hero/" + firstHeroName + "/" + firstHeroName + "_profile.png"));
        opponentProfilePic.setImage(new Image("file:images/cards/hero/" + secondHeroName + "/" + secondHeroName + "_profile.png"));
        playerName.setText(battle.getPlayers()[0].getName());
        opponentName.setText(battle.getPlayers()[1].getName());
    }

    private void initializeManaGemImages() {
        manaGemImageViews = new ImageView[Battle.MAX_MANA_IN_LATE_TURNS];
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
                handAndNextCardImageViews[i] = new ImageView(new Image(((Warrior) card).getIdleImageAddress()));
                if (i != 0)
                    setSelectedCardFromHandEvent(handAndNextCardImageViews[i], i);
            } else if (card instanceof Spell) {
                handAndNextCardImageViews[i] = new ImageView(new Image(((Spell) card).getActionBarImageAddress()));
                if (i != 0)
                    setSelectedCardFromHandEvent(handAndNextCardImageViews[i], i);
            } else
                handAndNextCardImageViews[i] = new ImageView();
            initializeSize(handAndNextCardImageViews[i]);
            handAndNextCardPanes[i] = new Pane(handAndNextCardImageViews[i]);
            handAndNextCardGrid.add(handAndNextCardPanes[i], i, 0);
        }
    }

    private void setSelectedCardFromHandEvent(ImageView handAndNextCardImageView, int i) {
        handAndNextCardImageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedCardCoordinates[0] == -1) //Card is already in hand.
                    handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                else if (selectedCardCoordinates[0] > -1)
                    groundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                selectedCardCoordinates[0] = -1;
                selectedCardCoordinates[1] = i;
                handAndNextCardPanes[i].getChildren().add(selectedCardBackground);
                Dragboard db = handAndNextCardImageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(dragAndDropKey);
                db.setContent(content);
                event.consume();
            }
        });
        handAndNextCardImageView.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
            }
        });
    }

    private void setNextCardRing() {
        ImageView nextCardRing = new ImageView(new Image("file:images/replace_outer_ring_smoke.png"));
        initializeSize(nextCardRing);
        handAndNextCardPanes[0] = new Pane(nextCardRing);
        handAndNextCardGrid.add(handAndNextCardPanes[0], 0, 0);
    }

    private void initializeGround() {
        groundPanes = new Pane[BattleGround.getRows()][BattleGround.getColumns()];
        groundImageViews = new ImageView[BattleGround.getRows()][BattleGround.getColumns()];
        collectedItemsPanes = new Pane[MAX_NUMBER_OF_COLLECTIBLE_ITEMS];
        collectedItemsImageViews = new ImageView[MAX_NUMBER_OF_COLLECTIBLE_ITEMS];
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                if (asset instanceof Warrior) {
                    groundImageViews[i][j] = new ImageView(new Image(((Warrior) asset).getBreathingImageAddress()));
                    healthBars.put((Warrior) asset, new Label(String.valueOf(((Warrior) asset).getHP())));
                } else if (asset instanceof Item)
                    groundImageViews[i][j] = new ImageView(new Image(((Item) asset).getActionbarImageAddress()));
                else if (asset instanceof Flag)
                    groundImageViews[i][j] = new ImageView(new Image("file:images/flag_icon.png"));
                else
                    groundImageViews[i][j] = new ImageView(new Image("file:images/card_background.png"));
                initializeSize(groundImageViews[i][j]);
                preparePaneAndCoordinates(asset, i, j);
                setGroundCellEvent(groundImageViews[i][j], asset, i, j);
            }
        }
        for (int i = 0; i < MAX_NUMBER_OF_COLLECTIBLE_ITEMS; i++) {
            collectedItemsImageViews[i] = new ImageView();
            initializeSize(collectedItemsImageViews[i]);
            collectedItemsPanes[i] = new Pane(collectedItemsImageViews[i]);
            collectedItemsGrid.add(collectedItemsPanes[i], 0, i);
//            setCollectedItemEvent(collectedItemsImageViews[i], i);
        }
    }

    private void setCollectedItemEvent(int index) {
        ImageView imageView = collectedItemsImageViews[index];
        if (imageView.getImage() != null) {
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            battle.useItem(battle.getPlayers()[0], battle.getPlayers()[1], battle.getPlayersDeck()[0].getItems().get(index));
                            imageView.setImage(new Image(battle.getPlayersDeck()[0].getItems().get(index).getActiveImageAddress()));
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int i = index; i < itemsCursor; i++)
                                collectedItemsImageViews[i].setImage(collectedItemsImageViews[i + 1].getImage());
                            itemsCursor--;
                        }
                    }).start();
                }
            });
        }
    }

    private void preparePaneAndCoordinates(Asset asset, int i, int j) {
        groundPanes[i][j] = new Pane(groundImageViews[i][j]);
//        groundPanes[i][j].getChildren().add(healthBars.get(asset));
        groundPanes[i][j].setLayoutX(groundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutX());
        groundPanes[i][j].setLayoutY(groundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutY());
        groundImageViews[i][j].setLayoutX(groundPanes[i][j].getLayoutX());
        groundImageViews[i][j].setLayoutY(groundPanes[i][j].getLayoutY());
        groundPanes[i][j].setPrefHeight(CELL_HEIGHT);
        groundPanes[i][j].setPrefWidth(CELL_WIDTH);
        groundGrid.add(groundPanes[i][j], j, i);
        if (asset instanceof Warrior) {
            groundPanes[i][j].getChildren().add(healthBars.get(asset));
            healthBars.get(asset).setLayoutX(groundPanes[i][j].getLayoutX());
            healthBars.get(asset).setLayoutY(groundPanes[i][j].getLayoutY() - 2 * groundPanes[i][j].getHeight());
        }
    }

    private void setGroundCellEvent(ImageView imageView, Asset asset, int i, int j) {
        imageView.setOnMouseClicked(event -> {
            if (asset instanceof Warrior) {
                if (asset.getOwner() == battle.getPlayers()[0])
                    selectCardInGround(asset, i, j);
                else if (asset.getOwner() == battle.getPlayers()[1] && selectedCardCoordinates[0] > -1)
                    attack((Warrior) asset, i, j);
            } else if (selectedCardCoordinates[0] > -1)
                moveCard(i, j);
        });
        imageView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != imageView && event.getDragboard().getString().equals(dragAndDropKey))
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        imageView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.getString().equals(dragAndDropKey) && asset == null && selectedCardCoordinates[0] == -1) {
                    insertCard(i, j);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    private void selectCardInGround(Asset asset, int i, int j) {
        battle.selectWarrior(asset.getOwner(), asset.getID());
        if (selectedCardCoordinates[0] == -1) //Card is already in hand.
            handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        else if (selectedCardCoordinates[0] > -1)
            groundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
        groundPanes[i][j].getChildren().add(selectedCardBackground);
    }

    private void insertCard(int i, int j) {
        try {
            Card handCard = battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1]; // Because after the next line, target card becomes null.
            battle.insertCard(battle.getPlayers()[0], battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1].getName(), j + 1, i + 1);
            showInsertAnimation(i, j, handCard);
            setSelectedCardCoordinates(battle.getPlayersHand()[0][selectedCardCoordinates[1] - 1], i, j);
            updateGroundCellEvents();
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | DontHaveEnoughManaException e) {
            handleError(e);
        }
    }

    private void showInsertAnimation(int i, int j, Card handCard) {
        groundImageViews[i][j].setOpacity(1);
        handAndNextCardImageViews[selectedCardCoordinates[1]].setImage(null);
        handAndNextCardPanes[selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        if (handCard instanceof Warrior)
            setRequirements(i, j, (Warrior) handCard);
        else if (handCard instanceof Spell) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    groundImageViews[i][j].setImage(new Image(((Spell) handCard).getActiveImageAddress()));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    groundImageViews[i][j].setImage(new Image(freeCellImageAddress));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            makeCellEffects();
                        }
                    });
                }
            });
            t1.start();
        }
    }

    private void makeCellEffects() {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                for (CellEffect cellEffect : battle.getBattleGround().getEffectsPosition().get(i).get(j)) {
                    ImageView temp = new ImageView(new Image("file:images/cellEffect_" + cellEffect.name() + ".png"));
                    initializeSize(temp, 0.5);
                    temp.setOpacity(0.4);
                    cellEffectImageViews[i][j] = new ArrayList<>();
                    cellEffectImageViews[i][j].add(temp);
                    groundPanes[i][j].getChildren().add(temp);
                }
            }
        }
    }

    private void setRequirements(int i, int j, Warrior warrior) {
        groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
        groundPanes[i][j].getChildren().add(selectedCardBackground);
        healthBars.put(warrior, new Label(String.valueOf(warrior.getHP())));
        healthBars.get(warrior).setLayoutX(groundPanes[i][j].getLayoutX());
        healthBars.get(warrior).setLayoutY(groundPanes[i][j].getLayoutY());
        groundPanes[i][j].getChildren().add(healthBars.get(warrior));
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
    }

    private void setSelectedCardCoordinates(Card card, int i, int j) {
        if (card instanceof Warrior) {
            selectedCardCoordinates[0] = i;
            selectedCardCoordinates[1] = j;
            return;
        }
        selectedCardCoordinates[0] = -2;
        selectedCardCoordinates[1] = -2;
    }

    private void moveCard(int i, int j) {
        try {
            Asset temp = battle.getBattleGround().getGround().get(i).get(j); // Because map will be changed after the next line being executed.
            battle.cardMoveTo(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], j + 1, i + 1);
            showMove(i, j, temp);
            updateGroundCellEvents();
        } catch (InvalidTargetException | ThisCellFilledException | WarriorSecondMoveInTurnException e) {
            handleError(e);
        }
    }

    private void addItemToInventory(Item collected) {
        collectedItemsImageViews[itemsCursor].setImage(new Image(collected.getActionbarImageAddress()));
        setCollectedItemEvent(itemsCursor);
        itemsCursor++;
    }

    private void updateGroundCellEvents() {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                if (asset instanceof Warrior) {
                    healthBars.get(asset).setText(String.valueOf(((Warrior) asset).getHP()));
                    healthBars.get(asset).setLayoutX(groundPanes[i][j].getLayoutX());
                    healthBars.get(asset).setLayoutY(groundPanes[i][j].getLayoutY() - 2 * groundPanes[i][j].getHeight());
                }
                initializeSize(groundImageViews[i][j]);
                setGroundCellEvent(groundImageViews[i][j], asset, i, j);
            }
        }
    }

    private void showMove(int i, int j, Asset temp) {
        Warrior warrior = (Warrior) battle.getPlayersSelectedCard()[0];
        Thread moveAnimation = new Thread(new Runnable() {
            @Override
            public void run() {
                animateMove(warrior, i, j);
                groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(freeCellImageAddress));
//                if (battle.getBattleGround().getGround().get(i).get(j) instanceof Item)
//                    updateCollectedItems((Item) battle.getBattleGround().getGround().get(i).get(j));
                //System.out.println(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getImage().impl_getUrl());
                //System.out.println(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getImage().impl_getUrl());
                //System.out.println(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getLayoutX());
                //System.out.println(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getLayoutY());
                initializeSize(groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        groundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                        groundPanes[i][j].getChildren().add(selectedCardBackground);
                    }
                });
                groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
                groundImageViews[i][j].setOpacity(1);
                selectedCardCoordinates[0] = i;
                selectedCardCoordinates[1] = j;
                if (temp instanceof Item)
                    addItemToInventory((Item) temp);
            }
        });
        moveAnimation.start();
    }

    private void updateCollectedItems(Item collected) {
        for (Item item : battle.getPlayersDeck()[0].getItems()) {
            if (item.getPrice() == 0) {

            }
        }
    }

    private void animateMove(Warrior warrior, int finalRow, int finalColumn) {
        Pane startPane = groundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]];
        Pane endPane = groundPanes[finalRow][finalColumn];
        ImageView startImageView = groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]];
        ImageView endImageView = groundImageViews[finalRow][finalColumn];
//        Path path = new Path(new MoveTo(startPane.getLayoutX(), startPane.getLayoutY() - 2 * startPane.getHeight()), new LineTo(endPane.getLayoutX(), endPane.getLayoutY()));
//        path.setVisible(true);
//        PathTransition pathTransition = new PathTransition(Duration.millis(2000), path, startImageView);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        root.getChildren().add(path);
//        pathTransition.setCycleCount(1);
//        pathTransition.setAutoReverse(false);
////        root.getChildren().addAll(button, rectangle, label, polygon);
//        pathTransition.play();
//        healthBars.get(warrior).setLayoutX(startPane.getLayoutX());
//        healthBars.get(warrior).setLayoutY(endPane.getLayoutY() - 2 * endPane.getHeight());

        TranslateTransition translateTransition = new TranslateTransition();
        startImageView.setImage(new Image(warrior.getRunImageAddress()));
        translateTransition.setNode(startImageView);
        double initX = startImageView.getLayoutX();
        double initY = startImageView.getLayoutY();
        translateTransition.setFromX(initX);
        translateTransition.setFromY(initY);
        translateTransition.setToX(endImageView.getLayoutY());
        translateTransition.setToY(endImageView.getLayoutY());
        translateTransition.setDuration(new Duration(1000));
        translateTransition.play();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        translateTransition.stop();


//        translateTransition.setToX(initX);
//        translateTransition.setToY(initY);
//        translateTransition.play();
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        translateTransition.stop();
//        translateTransition.stop();
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
            updateGroundCellEvents();
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
                groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getAttackImageAddress()));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getBreathingImageAddress()));
            }
        }).start();
        //TODO: Is it needed to show a bubble indicating value of damage applied?
    }

    private void setEndTurnEvent() {
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
                updateHandImages();
//                System.out.println(battle.getPlayersDeck()[0].getHero().getHP());
                aiController.handleAIEvent(battle);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateGroundCellEvents();
                updateCellEffects();
            }
        });
    }

    private void updateManaGemImages() {
        for (int i = 0; i < battle.getPlayersMana()[0]; i++)
            manaGemImageViews[i].setImage(new Image("file:images/icon_mana.png"));
        for (int i = battle.getPlayersMana()[0]; i < Battle.MAX_MANA_IN_LATE_TURNS; i++)
            manaGemImageViews[i].setImage(new Image("file:images/icon_mana_inactive.png"));
    }

    private void updateHandImages() {
        for (int i = 0; i <= Battle.NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card;
            if (i == 0)
                card = battle.getPlayersNextCardFromDeck()[0];
            else
                card = battle.getPlayersHand()[0][i - 1];

            if (card instanceof Warrior) {
                handAndNextCardImageViews[i].setImage(new Image(((Warrior) card).getIdleImageAddress()));
                if (i != 0)
                    setSelectedCardFromHandEvent(handAndNextCardImageViews[i], i);
            } else if (card instanceof Spell) {
                handAndNextCardImageViews[i].setImage(new Image(((Spell) card).getActionBarImageAddress()));
                if (i != 0)
                    setSelectedCardFromHandEvent(handAndNextCardImageViews[i], i);
            }
            initializeSize(handAndNextCardImageViews[i]);
        }
    }

    private void updateCellEffects() {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                for (CellEffect cellEffect : battle.getBattleGround().getEffectsPosition().get(i).get(j)) {
                    if (cellEffect.getEffectLifeTime() == 0) {
                        Iterator<ImageView> iterator = cellEffectImageViews[i][j].iterator();
                        while (iterator.hasNext()) {
                            ImageView imageView = iterator.next();
                            if (imageView.getImage().impl_getUrl().equals("file:images/cellEffect_" + cellEffect.name() + ".png")) {
                                groundPanes[i][j].getChildren().remove(imageView);
                                iterator.remove();
                            }
                        }
                    }
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
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    @FXML
    private void cheatMode() {
        new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DIGIT1:
                        //Mode1
                        battle.getPlayers()[0].setBudget(battle.getPlayers()[0].getBudget() + 10000000);
                        battle.getPlayers()[0].setBudget(battle.getPlayers()[0].getBudget() + 10000000);
                        break;

                    case DIGIT2:
                        //Mode2
                        battle.getPlayers()[0].getMainDeck().getHero().setAmountOfChangedAP(100);
                        battle.getPlayers()[0].getMainDeck().getHero().changeHP(100);
                        break;

                    case DIGIT3:
                        //Mode3
                        battle.getPlayers()[1].getMainDeck().getHero().setAmountOfChangedAP(-10);
                        battle.getPlayers()[1].getMainDeck().getHero().changeHP(-10);
                        break;

                    case DIGIT4:
                        //Mode4
                        for (Card inGroundCard : battle.getInGroundCards()) {
                            if (inGroundCard.getOwner() == battle.getPlayers()[0] && inGroundCard instanceof Warrior) {
                                ((Warrior) inGroundCard).changeHP(20);
                            }
                        }
                        break;

                    case DIGIT5:
                        //Mode5
                        for (Card inGroundCard : battle.getInGroundCards()) {
                            if (inGroundCard.getOwner() == battle.getPlayers()[1] && inGroundCard instanceof Warrior) {
                                ((Warrior) inGroundCard).changeHP(-10);
                            }
                        }
                        break;
                    case DIGIT6:
                        //Mode6
//                        int id = 9000;
//                        Minion minion = new Minion("hahahahah", "hahahahah", 0, id++, 10, 100, 100, 0, AttackType.HYBRID);
//                        battle.getPlayers()[0].getMainDeck().getCards().add(minion);
//                        battle.getPlayersSelectedCard()[0] = minion;
//                        battle.getPlayers()[0].se
//
//                        insertCard(0, 0);
                        break;
                }
            }
        };
    }

    public void setMenuButtonReleased() {
        pauseMenu.setVisible(true);
        FadeTransition fadeTransition = nodeFadeAnimation(pauseMenu,300,0,1);
        fadeTransition.play();
        setMenuButtonMouseOver();
    }

    public void setMenuButtonPressed() {
        menuButton.setImage(new Image("file:images/menu_button_pressed.png"));
    }

    public void setMenuButtonMouseOver() {
        menuButton.setImage(new Image("file:images/menu_button_onmouseover.png"));
    }

    public void setMenuButtonMouseExited() {
        menuButton.setImage(new Image("file:images/menu_button.png"));
    }

    public void setSaveButtonReleased() {
        SavedBattle newSave = new SavedBattle(battle);
        CurrentAccount.getCurrentAccount().getSavedBattles().add(0,newSave);
        newSave.saveBattleInToFile(CurrentAccount.getCurrentAccount().getName(),"Data/AccountsData.json");
        showOneButtonInformationDialog("Save Message","This Game Saved Successfully.",false);
        setSaveButtonMouseOver();
    }

    public void setSaveButtonPressed() {
        saveButton.setImage(new Image("file:images/save_button_pressed.png"));
    }

    public void setSaveButtonMouseOver() {
        saveButton.setImage(new Image("file:images/save_button_hover.png"));
    }

    public void setSaveButtonMouseExited() {
        saveButton.setImage(new Image("file:images/save_button.png"));
    }

    public void setExitButtonReleased(){
        confirmationDialog("Exit Confirmation","Are You Sure To Exit From Battle ?").setOnAction(event -> {
            try {
                loadPageOnStackPane(battleGroundAnchorPane, "FXML/MainMenu.fxml", "rtl");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        setExitButtonMouseOver();
    }

    public void setExitButtonPressed() {
        exitButton.setImage(new Image("file:images/exit_button_pressed.png"));
    }

    public void setExitButtonMouseOver() {
        exitButton.setImage(new Image("file:images/exit_button_hover.png"));
    }

    public void setExitButtonMouseExited() {
        exitButton.setImage(new Image("file:images/exit_button.png"));
    }

    public void setPauseMenuCloseButtonReleased() {
        FadeTransition fadeTransition = nodeFadeAnimation(pauseMenu,300,1,0);
        fadeTransition.setOnFinished((event)-> pauseMenu.setVisible(false));
        fadeTransition.play();
          setPauseMenuCloseButtonMouseOver();
    }

    public void setPauseMenuCloseButtonPressed() {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close_pressed.png"));
    }

    public void setPauseMenuCloseButtonMouseOver() {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close_hover.png"));
    }

    public void setPauseMenuCloseButtonMouseExited() {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close.png"));
    }

    public void initializeMenuButtonEvents(){
        menuButton.setOnMouseReleased(event -> setMenuButtonReleased());
        menuButton.setOnMousePressed(event -> setMenuButtonPressed());
        menuButton.setOnMouseEntered(event -> setMenuButtonMouseOver());
        menuButton.setOnMouseExited(event -> setMenuButtonMouseExited());

        saveButton.setOnMouseReleased(event -> setSaveButtonReleased());
        saveButton.setOnMousePressed(event -> setSaveButtonPressed());
        saveButton.setOnMouseEntered(event -> setSaveButtonMouseOver());
        saveButton.setOnMouseExited(event -> setSaveButtonMouseExited());

        exitButton.setOnMouseReleased(event -> setExitButtonReleased());
        exitButton.setOnMousePressed(event -> setExitButtonPressed());
        exitButton.setOnMouseEntered(event -> setExitButtonMouseOver());
        exitButton.setOnMouseExited(event -> setExitButtonMouseExited());

        pauseMenuCloseButton.setOnMouseReleased(event -> setPauseMenuCloseButtonReleased());
        pauseMenuCloseButton.setOnMousePressed(event -> setPauseMenuCloseButtonPressed());
        pauseMenuCloseButton.setOnMouseEntered(event -> setPauseMenuCloseButtonMouseOver());
        pauseMenuCloseButton.setOnMouseExited(event -> setPauseMenuCloseButtonMouseExited());
    }


}