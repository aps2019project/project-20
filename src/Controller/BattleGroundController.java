package Controller;

import Client.Client;
import Datas.AssetDatas;
import Exceptions.*;
import Model.*;
import Presenter.*;
import com.gilecode.yagson.YaGson;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static Model.BattleGround.CellEffect;

public class BattleGroundController implements Initializable, ScreenManager , DialogThrowable, Animationable, DateGetter {
    private static final int CELL_HEIGHT = 80;
    private static final int CELL_WIDTH = 80;
    private static final int MAX_NUMBER_OF_COLLECTIBLE_ITEMS = 9;
    private static final double MARGIN_WIDTH = 30;
    protected int clientIndex = 0;
    public StackPane mainStackPane;
    public ImageView battleGroundImage;
    public ImageView profilePic0;
    public ImageView profilePic1;
    public Button specialPowerButton;
    public Label successfulSpecialPowerBanner;
    public ImageView deckItem;
    public ImageView opponentProfilePic;
    public Label playerName0;
    public Label playerName1;
    public GridPane groundGrid;
    public GridPane handAndNextCardGrid;
    public GridPane manaGemsRibbon;
    public GridPane collectedItemsGrid;
    public ImageView menuButton;
    public Pane pauseMenu;
    public ImageView saveButton;
    public ImageView exitButton;
    public ImageView pauseMenuCloseButton;
    protected Pane[] collectedItemsPanes;
    protected ImageView[] collectedItemsImageViews;
    protected Pane[][] groundPanes;
    protected Pane[] handAndNextCardPanes;
    public ImageView errorBar;
    protected Image errorImage;
    protected String freeCellImageAddress = "file:images/card_background.png";
    public JFXTextArea errorMessage;
    public ImageView endTurn;
    public ImageView enterGraveYardButton;
    public ImageView cheatButton;
    public ProgressBar progressbar;
    protected HashMap<Warrior, Label> hpBars = new HashMap<>();
    protected HashMap<Warrior, Label> apBars = new HashMap<>();
//    public Button menuButton;
//    public Button friendButton;
    public AnchorPane battleGroundAnchorPane;
    protected ImageView[][] groundImageViews;
    protected ArrayList<ImageView>[][] cellEffectImageViews = new ArrayList[BattleGround.getRows()][BattleGround.getColumns()];
    protected ImageView[] handAndNextCardImageViews;
    protected ImageView selectedCardBackground;
    protected int[] playerSelectedCardCoordinates = new int[]{-2, -2};
    protected int[] opponentSelectedCardCoordinates = new int[]{-2, -2};
    protected Battle battle;
    protected int itemsCursor;
    protected AIController aiController;
    protected ImageView[] manaGemImageViews;
    public Pane graveYardPane;
    public GridPane deadCardsGrid0;
    public GridPane deadCardsGrid1;
    protected Pane[][] deadCards0Panes = new Pane[10][2];
    protected Pane[][] deadCards1Panes = new Pane[10][2];
    public ImageView exitGraveYardButton;
    protected String dragAndDropKey = "Card dragged from hand.";
    protected ScreenRecordController screenRecordController;
    protected String startBattleDate;
    protected ArrayList<Integer> IDsOfSpecialPowersNeedingCell = new ArrayList<>();
    protected boolean isCursorChanged = false;
    protected int[] cellCoordinatesForSpecialPower = new int[]{-1, -1};

    public BattleGroundController(){}

    public BattleGroundController(Battle battle) {
        this.battle = battle;
        itemsCursor = battle.getPlayersDeck()[clientIndex].getItems().size();
    }

    public ProgressBar getProgressbar() {
        return progressbar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Next three lines are Just for test and must be replaced properly.
//        Account temp = new Account("reza", "1234");
//        temp.setMainDeck(DeckDatas.getDefaultDeck());
//        CurrentAccount.setCurrentAccount(temp);
//        battle = Battle.soloCustomKillHeroModeConstructor("rostam");
        startBattleDate = getDateFormat2();
        screenRecordController = new ScreenRecordController(startBattleDate);
        //for start capturing video
        // screenRecordController.start();
        //for finishing the game
//TODO        MatchHistory.BuildMatchHistory(startBattleDate,MatchHistory.Result.WIN,"AI",screenRecordController,CurrentAccount.getCurrentAccount());

        aiController = new AIController(battle);
        selectedCardBackground = new ImageView(new Image("file:images/card_background_highlight.png"));
        errorImage = new Image("file:images/notification_quest_small.png");
        successfulSpecialPowerBanner.setText("");
        initializeSize(selectedCardBackground);
        initializeIDsOfspecialPowersNeedingCell();
        initializeProfilesInfo();
        initializeManaGemImages();
        initializeDeckItem();
        setSpecialPowerEvent();
        initializeGround();
        initializeHandImages();
        initializeMenuButtonEvents();
        setEndTurnEvent();
        setEnterGraveYardEvent();
        setExitGraveYardButtonEvent();

        cheatButton.setOnMouseClicked(event -> cheatMode());

//        TimeLine t0 = new TimeLine(progressbar);
//        TimeLine t1 = new TimeLine(progressbar);
//
//        t0.start();
//        battle.endTurn(battle.getPlayers()[clientIndex]);
//
//        t1.start();
//        battle.endTurn(battle.getPlayers()[1]);
    }

    protected void initializeIDsOfspecialPowersNeedingCell() {
        IDsOfSpecialPowersNeedingCell.add(AssetDatas.getKaveh().getID());
        IDsOfSpecialPowersNeedingCell.add(AssetDatas.getSevenHeadDragon().getID());
        IDsOfSpecialPowersNeedingCell.add(AssetDatas.getRakhsh().getID());
        IDsOfSpecialPowersNeedingCell.add(AssetDatas.getLegend().getID());
        ArrayList<Asset> allAssets = null;
        try {
            allAssets = Asset.getAssetsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Asset asset: allAssets) {
            if (asset.getID() >= 2010 && asset.getID() < 3000)
                IDsOfSpecialPowersNeedingCell.add(asset.getID());
        }
    }

    protected void setSpecialPowerEvent() {
        specialPowerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        int j;
                        Asset asset;
                        if (!isHeroSelected()) {
                            handleError(new NoAvailableBufferForCardException("No selected hero detected."));
                            return;
                        }
                        if (IDsOfSpecialPowersNeedingCell.contains(battle.getPlayersDeck()[clientIndex].getHero().getID())) {
                            synchronized (cellCoordinatesForSpecialPower) {
                                specialPowerButton.getScene().setCursor(new ImageCursor(new Image("file:images/mouse_assist@2x.png")));
                                isCursorChanged = true;
                                setSelectedCardCoordinates(null, -2, -2);
                                try {
                                    cellCoordinatesForSpecialPower.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                i = cellCoordinatesForSpecialPower[0];
                                j = cellCoordinatesForSpecialPower[1];
                                asset = battle.getBattleGround().getGround().get(i).get(j);
                            }
                        }
                        else {
                            i = -1;
                            j = -1;
                            asset = null;
                        }
                        try {
                            if (BattleGroundController.this instanceof ClientBattleGroundController) {
                                Client.getWriter().println("applyHeroSpecialPower;" + new YaGson().toJson(battle.getPlayers()[clientIndex], Account.class) + ";" + new YaGson().toJson(asset, Asset.class) + ";" + j + ";" + i);
                                synchronized (Client.getrLock()) {
                                    Client.getrLock().wait();
                                }
                                battle = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Battle.class);
                            }
                            else
                                battle.applyHeroSpecialPower(battle.getPlayers()[clientIndex], asset, j, i);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    successfulSpecialPowerBanner.setText("Your special power was applied.");
//                              linked      if (!battle.getPlayersDeck()[clientIndex].getHero().getCastLoopImageAddress().isEmpty())
//                                        groundImageViews[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]].setImage(new Image(battle.getPlayersDeck()[clientIndex].getHero().getCastLoopImageAddress()));
                                    makeCellEffects();
                                }
                            });
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    successfulSpecialPowerBanner.setText("");
//                              linked     groundImageViews[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]].setImage(new Image(battle.getPlayersDeck()[clientIndex].getHero().getBreathingImageAddress()));
                                    //TODO next line is critical code.
                                    specialPowerButton.getScene().setCursor(null);
                                }
                            });
                        } catch (InvalidTargetException | NoAvailableBufferForCardException e) {
                            handleError(e);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    protected boolean isHeroSelected() {
                        return battle.getPlayersDeck()[clientIndex].getHero().getHP() > 0 && playerSelectedCardCoordinates[0] == battle.getPlayersDeck()[clientIndex].getHero().getYInGround() && playerSelectedCardCoordinates[1] == battle.getPlayersDeck()[clientIndex].getHero().getXInGround();
                    }
                }).start();
            }
        });
    }

    public void showOpponentSpecialPowerUsing() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                makeCellEffects();
            }
        }).start();
    }

    protected void initializeDeckItem() {
        if (battle.getPlayersDeck()[clientIndex].getItems().size() == 1)
            deckItem.setImage(new Image(battle.getPlayersDeck()[clientIndex].getItems().get(0).getActionbarImageAddress()));

    }

    protected void initializeSize(ImageView imageView) {
        if (imageView == selectedCardBackground || (imageView.getImage() != null && imageView.getImage().impl_getUrl().equals(freeCellImageAddress)))
            imageView.setOpacity(0.5);
        else
            imageView.setOpacity(1);
        imageView.setFitHeight(CELL_HEIGHT);
        imageView.setFitWidth(CELL_WIDTH);
    }

    protected void initializeSize(ImageView imageView, double scale) {
        initializeSize(imageView);
        imageView.setFitHeight(CELL_HEIGHT * scale);
        imageView.setFitWidth(CELL_WIDTH * scale);

    }

    protected void initializeProfilesInfo() {
        String firstHeroName = battle.getPlayers()[clientIndex].getMainDeck().getHero().getName();
        String secondHeroName = battle.getPlayers()[1].getMainDeck().getHero().getName();
        profilePic0.setImage(new Image("file:images/cards/hero/" + firstHeroName + "/" + firstHeroName + "_profile.png"));
        opponentProfilePic.setImage(new Image("file:images/cards/hero/" + secondHeroName + "/" + secondHeroName + "_profile.png"));
        playerName0.setText(battle.getPlayers()[0].getName());
        playerName1.setText(battle.getPlayers()[1].getName());
    }

    protected void initializeManaGemImages() {
        manaGemImageViews = new ImageView[Battle.MAX_MANA_IN_LATE_TURNS];
        for (int i = 0; i < battle.getPlayersMana()[clientIndex]; i++) {
            manaGemImageViews[i] = new ImageView(new Image("file:images/icon_mana.png"));
            manaGemsRibbon.add(manaGemImageViews[i], i, 0);
        }
        for (int i = battle.getPlayersMana()[clientIndex]; i < Battle.MAX_MANA_IN_LATE_TURNS; i++) {
            manaGemImageViews[i] = new ImageView(new Image("file:images/icon_mana_inactive.png"));
            manaGemsRibbon.add(manaGemImageViews[i], i, 0);
        }
    }

    protected void initializeHandImages() {
        handAndNextCardImageViews = new ImageView[Battle.NUMBER_OF_CARDS_IN_HAND + 1];
        handAndNextCardPanes = new Pane[Battle.NUMBER_OF_CARDS_IN_HAND + 1];
        setNextCardRing();
        for (int i = 0; i <= Battle.NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card;
            if (i == 0)
                card = battle.getPlayersNextCardFromDeck()[clientIndex];
            else
                card = battle.getPlayersHand()[clientIndex][i - 1];

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

    protected void setSelectedCardFromHandEvent (ImageView handAndNextCardImageView,int i){
        handAndNextCardImageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (playerSelectedCardCoordinates[0] == -1) //Card is already in hand.
                    handAndNextCardPanes[playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                else if (playerSelectedCardCoordinates[0] > -1)
                    groundPanes[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                playerSelectedCardCoordinates[0] = -1;
                playerSelectedCardCoordinates[1] = i;
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

    protected void setNextCardRing() {
        ImageView nextCardRing = new ImageView(new Image("file:images/replace_outer_ring_smoke.png"));
        initializeSize(nextCardRing);
        handAndNextCardPanes[0] = new Pane(nextCardRing);
        handAndNextCardGrid.add(handAndNextCardPanes[0], 0, 0);
    }

    protected void initializeGround () {
        graveYardPane.setVisible(false);
        groundPanes = new Pane[BattleGround.getRows()][BattleGround.getColumns()];
        groundImageViews = new ImageView[BattleGround.getRows()][BattleGround.getColumns()];
        collectedItemsPanes = new Pane[MAX_NUMBER_OF_COLLECTIBLE_ITEMS];
        collectedItemsImageViews = new ImageView[MAX_NUMBER_OF_COLLECTIBLE_ITEMS];
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                if (asset instanceof Warrior) {
                    groundImageViews[i][j] = new ImageView(new Image(((Warrior) asset).getBreathingImageAddress()));
                    hpBars.put((Warrior) asset, new Label(String.valueOf(((Warrior) asset).getHP())));
                    apBars.put((Warrior) asset, new Label(String.valueOf(((Warrior) asset).getAP())));

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

    protected void setCollectedItemEvent (int index){
        ImageView imageView = collectedItemsImageViews[index];
        if (imageView.getImage() != null) {
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (BattleGroundController.this instanceof ClientBattleGroundController) {
                                Client.getWriter().println("useItem;" + new YaGson().toJson(battle.getPlayers()[clientIndex], Account.class) + ";" + new YaGson().toJson(battle.getPlayersDeck()[clientIndex].getItems().get(index), Item.class));
                                synchronized (Client.getrLock()) {
                                    try {
                                        Client.getrLock().wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                battle = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Battle.class);
                            }
                            else
                                battle.useItem(battle.getPlayers()[clientIndex], null, null, battle.getPlayersDeck()[clientIndex].getItems().get(index));
                            imageView.setImage(new Image(battle.getPlayersDeck()[clientIndex].getItems().get(index).getActiveImageAddress()));
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

    protected void preparePaneAndCoordinates(Asset asset, int i, int j) {
        groundPanes[i][j] = new Pane(groundImageViews[i][j]);
//        groundPanes[i][j].getChildren().add(hpBars.get(asset));
        groundPanes[i][j].setLayoutX(groundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutX());
        groundPanes[i][j].setLayoutY(groundGrid.getChildren().get(i * BattleGround.getRows() + j).getLayoutY());
        groundImageViews[i][j].setLayoutX(groundPanes[i][j].getLayoutX());
        groundImageViews[i][j].setLayoutY(groundPanes[i][j].getLayoutY());
        groundPanes[i][j].setPrefHeight(CELL_HEIGHT);
        groundPanes[i][j].setPrefWidth(CELL_WIDTH);
        groundGrid.add(groundPanes[i][j], j, i);
        if (asset instanceof Warrior) {
            groundPanes[i][j].getChildren().add(hpBars.get(asset));
            groundPanes[i][j].getChildren().add(apBars.get(asset));
            locateHpAndApLabels(groundPanes[i][j], asset);
        }
    }

    protected void setGroundCellEvent (ImageView imageView, Asset asset,int i, int j){
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                synchronized (cellCoordinatesForSpecialPower) {
                    if (isCursorChanged) {
                        isCursorChanged = false;
                        cellCoordinatesForSpecialPower[0] = i;
                        cellCoordinatesForSpecialPower[1] = j;
                        cellCoordinatesForSpecialPower.notify();
                    }
                }

                if (asset instanceof Warrior) {
                    if (asset.getOwner() == battle.getPlayers()[clientIndex])
                        selectCardInGround(asset, i, j);
                    else if (asset.getOwner() == battle.getPlayers()[1 - clientIndex] && playerSelectedCardCoordinates[0] > -1)
                        attack((Warrior) asset, i, j);
                } else if (playerSelectedCardCoordinates[0] > -1)
                    moveCard(i, j);
            }
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
                if (db.getString().equals(dragAndDropKey) && asset == null && playerSelectedCardCoordinates[0] == -1) {
                    insertCard(i, j);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    protected void selectCardInGround (Asset asset,int i, int j){
        battle.selectWarrior(asset.getOwner(), asset.getID());
        if (playerSelectedCardCoordinates[0] == -1) //Card is already in hand.
            handAndNextCardPanes[playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        else if (playerSelectedCardCoordinates[0] > -1)
            groundPanes[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        playerSelectedCardCoordinates[0] = i;
        playerSelectedCardCoordinates[1] = j;
        groundPanes[i][j].getChildren().add(selectedCardBackground);
    }

    protected void insertCard (int i, int j){
        try {
            Card handCard = battle.getPlayersHand()[clientIndex][playerSelectedCardCoordinates[1] - 1]; // Because after the next line, target card becomes null.
            if (BattleGroundController.this instanceof ClientBattleGroundController) {
                Client.getWriter().println("insertCard;" + new YaGson().toJson(battle.getPlayers()[clientIndex], Account.class) + ";" + new YaGson().toJson(handCard, Card.class) + ";" + (j + 1) + ";" + (i + 1));
                synchronized (Client.getrLock()) {
                    Client.getrLock().wait();
                }
                battle = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Battle.class);
            }
            else
                battle.insertCard(battle.getPlayers()[clientIndex], battle.getPlayersHand()[clientIndex][playerSelectedCardCoordinates[1] - 1].getName(), j + 1, i + 1);
            showInsertAnimation(i, j, handCard, true);
            setSelectedCardCoordinates(battle.getPlayersHand()[clientIndex][playerSelectedCardCoordinates[1] - 1], i, j);
            updateGroundCells();
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | InsufficientManaException e) {
            handleError(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showInsertAnimation (int i, int j, Card handCard, boolean isOwn){
        groundImageViews[i][j].setOpacity(1);
        if (isOwn) {
            handAndNextCardImageViews[playerSelectedCardCoordinates[1]].setImage(null);
            handAndNextCardPanes[playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
        }
        if (handCard instanceof Warrior)
            setInsertionRequirements(i, j, (Warrior) handCard, isOwn);
        else if (handCard instanceof Spell) {
            new Thread(new Runnable() {
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
            }).start();
        }
    }

    protected void makeCellEffects () {
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

    protected void setInsertionRequirements(int i, int j, Warrior warrior, boolean isOwn){
        groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
        if (isOwn)
            groundPanes[i][j].getChildren().add(selectedCardBackground);
        hpBars.put(warrior, new Label(String.valueOf(warrior.getHP())));
        apBars.put(warrior, new Label(String.valueOf(warrior.getAP())));
        locateHpAndApLabels(groundPanes[i][j], warrior);
        groundPanes[i][j].getChildren().add(hpBars.get(warrior));
        groundPanes[i][j].getChildren().add(apBars.get(warrior));
    }

    protected void setSelectedCardCoordinates (Card card,int i, int j){
        if (card instanceof Warrior) {
            playerSelectedCardCoordinates[0] = i;
            playerSelectedCardCoordinates[1] = j;
            return;
        }
        playerSelectedCardCoordinates[0] = -2;
        playerSelectedCardCoordinates[1] = -2;
    }

    protected void moveCard (int i, int j) {
        try {
            Asset targetCell = battle.getBattleGround().getGround().get(i).get(j); // Because map will be changed after the next line execution.
            if (BattleGroundController.this instanceof ClientBattleGroundController) {
                Client.getWriter().println("cardMoveTo;" + new YaGson().toJson(battle.getPlayers()[clientIndex], Account.class) + ";" + new YaGson().toJson(battle.getPlayersSelectedCard()[clientIndex], Card.class) + ";" + (j + 1) + ";" + (i + 1));
                synchronized (Client.getrLock()) {
                    Client.getrLock().wait();
                }
                battle = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Battle.class);
            }
            else
                battle.cardMoveTo(battle.getPlayers()[clientIndex], (Warrior) battle.getPlayersSelectedCard()[clientIndex], j + 1, i + 1);
            showMove(i, j, true);
            if (targetCell instanceof Item)
                addItemToInventory((Item) targetCell);
//                updateGroundCells();
        } catch (InvalidTargetException | ThisCellFilledException | WarriorSecondMoveInTurnException e) {
            handleError(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void addItemToInventory (Item collected){
        collectedItemsImageViews[itemsCursor].setImage(new Image(collected.getActionbarImageAddress()));
        setCollectedItemEvent(itemsCursor);
        itemsCursor++;
    }

    public void updateGroundCells () {
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                //To update dead cards' images
                if (asset == null)
                    groundImageViews[i][j].setImage(new Image(freeCellImageAddress));

                if (asset instanceof Warrior)
                    locateHpAndApLabels(groundPanes[i][j], asset);
                initializeSize(groundImageViews[i][j]);
                setGroundCellEvent(groundImageViews[i][j], asset, i, j);
            }
        }
    }

    protected void locateHpAndApLabels(Pane pane, Asset asset) {
        hpBars.get(asset).setText(String.valueOf(((Warrior) asset).getHP()));
        hpBars.get(asset).setLayoutX(pane.getLayoutX());
        hpBars.get(asset).setLayoutY(pane.getLayoutY() - 2 * pane.getHeight());
        apBars.get(asset).setText(String.valueOf(((Warrior) asset).getAP()));
        apBars.get(asset).setLayoutX(pane.getLayoutX() + MARGIN_WIDTH);
        apBars.get(asset).setLayoutY(pane.getLayoutY() - 2 * pane.getHeight());
    }

    public void showMove (int i, int j, boolean isOwn){
        Warrior warrior = (Warrior) battle.getPlayersSelectedCard()[clientIndex];
        new Thread(new Runnable() {
            @Override
            public void run() {
                animateMove(warrior, i, j, true);
                ImageView targetImageView = isOwn ? groundImageViews[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]] : groundImageViews[opponentSelectedCardCoordinates[0]][opponentSelectedCardCoordinates[1]];
                targetImageView.setImage(new Image(freeCellImageAddress));
                initializeSize(targetImageView);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (isOwn) {
                            groundPanes[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]].getChildren().remove(selectedCardBackground);
                            groundPanes[i][j].getChildren().add(selectedCardBackground);
                        }
                        locateHpAndApLabels(groundPanes[i][j], warrior);
                    }
                });
                groundImageViews[i][j].setImage(new Image(warrior.getBreathingImageAddress()));
                groundImageViews[i][j].setOpacity(1);
                if (isOwn) {
                    playerSelectedCardCoordinates[0] = i;
                    playerSelectedCardCoordinates[1] = j;
                }
                else {
                    opponentSelectedCardCoordinates[0] = i;
                    opponentSelectedCardCoordinates[1] = j;
                }
            }
        }).start();
    }

    protected void animateMove (Warrior warrior,int finalRow, int finalColumn, boolean isOwn){
        Pane startPane = groundPanes[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]];
        Pane endPane = groundPanes[finalRow][finalColumn];
        ImageView startImageView = isOwn ? groundImageViews[playerSelectedCardCoordinates[0]][playerSelectedCardCoordinates[1]] : groundImageViews[opponentSelectedCardCoordinates[0]][opponentSelectedCardCoordinates[1]];
        ImageView endImageView = groundImageViews[finalRow][finalColumn];

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

    protected void attack (Warrior attackedWarrior, int i, int j) {
        try {
            if (BattleGroundController.this instanceof ClientBattleGroundController) {
                Client.getWriter().println("attack;" + new YaGson().toJson(battle.getPlayers()[clientIndex], Account.class) + ";" + new YaGson().toJson(battle.getPlayersSelectedCard()[0], Card.class) + ";" + new YaGson().toJson(attackedWarrior, Warrior.class) + ";" + i + ";" + j);
                synchronized (Client.getrLock()) {
                    Client.getrLock().wait();
                }
                battle = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Battle.class);
            }
            else
                battle.attack(battle.getPlayers()[clientIndex], (Warrior) battle.getPlayersSelectedCard()[clientIndex], attackedWarrior);
            showAttackAnimation(attackedWarrior, i, j, true);
            updateGroundCells();
        } catch (AssetNotFoundException | InvalidAttackException e) {
            handleError(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showAttackAnimation (Warrior attackedWarrior,int i, int j, boolean isOwn) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Warrior attacker = isOwn ? (Warrior) battle.getPlayersSelectedCard()[clientIndex] : (Warrior) battle.getPlayersSelectedCard()[1 - clientIndex];
                int attackerRow = isOwn ? playerSelectedCardCoordinates[0] : opponentSelectedCardCoordinates[0];
                int attackerColumn = isOwn ? playerSelectedCardCoordinates[1] : opponentSelectedCardCoordinates[1];
                groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getAttackImageAddress()));
                if (attackedWarrior.getHP() <= 0)
                    groundImageViews[i][j].setImage(new Image(attackedWarrior.getDeathImageAddress()));
                if (attacker.getHP() <= 0)
                    groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getDeathImageAddress()));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (attacker.getHP() > 0)
                    groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getBreathingImageAddress()));
            }
        }).start();
    }

    protected void setEndTurnEvent () {
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
                battle.endTurn(battle.getPlayers()[clientIndex]);
                updateManaGemImages();
                updateHandImages();
                if (!(BattleGroundController.this instanceof ClientBattleGroundController))
                    aiController.handleAIEvent(battle);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                    graveYardController.initializeDeadCardsImages(true);
//                    graveYardController.initializeDeadCardsImages(false);
                updateGroundCells();
                updateCellEffects();
            }
        });
    }

    protected void updateManaGemImages () {
        for (int i = 0; i < battle.getPlayersMana()[clientIndex]; i++)
            manaGemImageViews[i].setImage(new Image("file:images/icon_mana.png"));
        for (int i = battle.getPlayersMana()[clientIndex]; i < Battle.MAX_MANA_IN_LATE_TURNS; i++)
            manaGemImageViews[i].setImage(new Image("file:images/icon_mana_inactive.png"));
    }

    protected void updateHandImages () {
        for (int i = 0; i <= Battle.NUMBER_OF_CARDS_IN_HAND; i++) {
            Card card;
            if (i == 0)
                card = battle.getPlayersNextCardFromDeck()[clientIndex];
            else
                card = battle.getPlayersHand()[clientIndex][i - 1];

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

    protected void updateCellEffects () {
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

    protected void handleError (RuntimeException e){
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

    protected String getErrorMessage (RuntimeException e){
        if (e.getMessage() != null)
            return e.getMessage();
        else if (e instanceof AssetNotFoundException)
            return "Asset not found.";
        else if (e instanceof InvalidInsertInBattleGroundException)
            return "Invalid target to insert card.";
        else if (e instanceof InvalidTargetException)
            return "Invalid target to move.";
        else if (e instanceof ThisCellFilledException)
            return "Target cell is filled.";
        else if (e instanceof InsufficientManaException)
            return "You don't have enough mana.";
        else if (e instanceof WarriorSecondMoveInTurnException)
            return "You can't move this unit twice.";
        return null;
    }

    protected void setEnterGraveYardEvent() {
        enterGraveYardButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enterGraveYardButton.setImage(new Image("file:images/button_graveYard_hover.png"));
            }
        });
        enterGraveYardButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enterGraveYardButton.setImage(new Image("file:images/button_graveYard.png"));
            }
        });
        enterGraveYardButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enterGraveYardButton.setImage(new Image("file:images/button_graveYard_pressed.png"));
            }
        });
        enterGraveYardButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enterGraveYardButton.setImage(new Image("file:images/button_graveYard_hover.png"));
                initializeDeadCardsImages(0);
                initializeDeadCardsImages(1);
                battleGroundAnchorPane.setVisible(false);
                graveYardPane.setVisible(true);
            }
        });
    }

    protected void initializeDeadCardsImages(int playerIndex) {
        //TODO method mustn't take parameter and grave of both players must be updated simultaneously
        Pane[][] panes = deadCards0Panes;
        GridPane gridPane = deadCardsGrid0;
        if (playerIndex == 1) {
            panes = deadCards1Panes;
            gridPane = deadCardsGrid1;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                ImageView deadCardImageView = null;
                Asset deadAsset = null;
                panes[i][j] = new Pane();
                if (battle.getPlayersGraveYard()[playerIndex].getDeadCards().size() > i * 2 + j) {
                    deadAsset = battle.getPlayersGraveYard()[playerIndex].getDeadCards().get(i * 2 + j);
                    if (deadAsset instanceof Warrior)
                        deadCardImageView = new ImageView(new Image(((Warrior) deadAsset).getDeathImageAddress()));
                    else if (deadAsset instanceof Spell)
                        deadCardImageView = new ImageView(new Image(((Spell) deadAsset).getActionBarImageAddress()));
                    panes[i][j].getChildren().add(deadCardImageView);
                }
                gridPane.add(panes[i][j], j, i);
            }
        }
    }

    protected void setExitGraveYardButtonEvent() {
        exitGraveYardButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGraveYardButton.setImage(new Image("file:images/exit_button_hover.png"));
            }
        });
        exitGraveYardButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGraveYardButton.setImage(new Image("file:images/exit_button.png"));
            }
        });
        exitGraveYardButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGraveYardButton.setImage(new Image("file:images/exit_button_pressed.png"));
            }
        });
        exitGraveYardButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGraveYardButton.setImage(new Image("file:images/exit_button_hover.png"));
                graveYardPane.setVisible(false);
                battleGroundAnchorPane.setVisible(true);
            }
        });
    }

    protected Node getNodeFromGridPane (GridPane gridPane,int col, int row){
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    @FXML
    protected void cheatMode () {
        new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DIGIT1:
                        //Mode1
                        battle.getPlayers()[clientIndex].setBudget(battle.getPlayers()[clientIndex].getBudget() + 10000000);
                        battle.getPlayers()[clientIndex].setBudget(battle.getPlayers()[clientIndex].getBudget() + 10000000);
                        break;

                    case DIGIT2:
                        //Mode2
                        battle.getPlayers()[clientIndex].getMainDeck().getHero().setAmountOfChangedAP(100);
                        battle.getPlayers()[clientIndex].getMainDeck().getHero().changeHP(100);
                        break;

                    case DIGIT3:
                        //Mode3
                        battle.getPlayers()[1].getMainDeck().getHero().setAmountOfChangedAP(-10);
                        battle.getPlayers()[1].getMainDeck().getHero().changeHP(-10);
                        break;

                    case DIGIT4:
                        //Mode4
                        for (Card inGroundCard : battle.getInGroundCards()) {
                            if (inGroundCard.getOwner() == battle.getPlayers()[clientIndex] && inGroundCard instanceof Warrior) {
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
//                        battle.getPlayers()[clientIndex].getMainDeck().getCards().add(minion);
//                        battle.getPlayersSelectedCard()[clientIndex] = minion;
//                        battle.getPlayers()[clientIndex].se
//
//                        insertCard(0, 0);
                        break;
                }
            }
        };
    }

    public void setMenuButtonReleased () {
        pauseMenu.setVisible(true);
        FadeTransition fadeTransition = nodeFadeAnimation(pauseMenu, 300, 0, 1);
        fadeTransition.play();
        setMenuButtonMouseOver();
    }

    public void setMenuButtonPressed () {
        menuButton.setImage(new Image("file:images/menu_button_pressed.png"));
    }

    public void setMenuButtonMouseOver () {
        menuButton.setImage(new Image("file:images/menu_button_onmouseover.png"));
    }

    public void setMenuButtonMouseExited () {
        menuButton.setImage(new Image("file:images/menu_button.png"));
    }

    public void setSaveButtonReleased () {
        SavedBattle newSave = new SavedBattle(battle);
        CurrentAccount.getCurrentAccount().getSavedBattles().add(0, newSave);
        newSave.saveBattleInToFile(CurrentAccount.getCurrentAccount().getName(), "Data/AccountsData.json");
        showOneButtonInformationDialog("Save Message", "This Game Saved Successfully.", false);
        setSaveButtonMouseOver();
    }

    public void setSaveButtonPressed () {
        saveButton.setImage(new Image("file:images/save_button_pressed.png"));
    }

    public void setSaveButtonMouseOver () {
        saveButton.setImage(new Image("file:images/save_button_hover.png"));
    }

    public void setSaveButtonMouseExited () {
        saveButton.setImage(new Image("file:images/save_button.png"));
    }

    public void setExitButtonReleased () {
        confirmationDialog("Exit Confirmation", "Are You Sure To Exit From Battle ?").setOnAction(event -> {
            try {
                loadPageOnStackPane(battleGroundAnchorPane, "../View/FXML/MainMenu.fxml", "rtl");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        setExitButtonMouseOver();
    }

    public void setExitButtonPressed () {
        exitButton.setImage(new Image("file:images/exit_button_pressed.png"));
    }

    public void setExitButtonMouseOver () {
        exitButton.setImage(new Image("file:images/exit_button_hover.png"));
    }

    public void setExitButtonMouseExited () {
        exitButton.setImage(new Image("file:images/exit_button.png"));
    }

    public void setPauseMenuCloseButtonReleased () {
        FadeTransition fadeTransition = nodeFadeAnimation(pauseMenu, 300, 1, 0);
        fadeTransition.setOnFinished((event) -> pauseMenu.setVisible(false));
        fadeTransition.play();
        setPauseMenuCloseButtonMouseOver();
    }

    public void setPauseMenuCloseButtonPressed () {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close_pressed.png"));
    }

    public void setPauseMenuCloseButtonMouseOver () {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close_hover.png"));
    }

    public void setPauseMenuCloseButtonMouseExited () {
        pauseMenuCloseButton.setImage(new Image("file:images/button_close.png"));
    }

    public void initializeMenuButtonEvents () {
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