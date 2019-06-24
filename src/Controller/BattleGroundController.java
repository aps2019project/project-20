package Controller;

import Exceptions.*;
import Model.*;
import Presenter.CurrentAccount;
import Presenter.GameMenuPresenter;
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
import java.util.ResourceBundle;

public class BattleGroundController implements Initializable {
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
    public Button endTurnButton;
    public Button menuButton;
    public Button friendButton;
//    public ImageView card1;
//    public ImageView card2;
//    public ImageView card3;
//    public ImageView card4;
//    public ImageView card5;
//    public ImageView nextCard;
    public AnchorPane battleGroundAnchorPane;
    private ImageView[][] groundImageViews;
    private ImageView[] handAndNextCardImageViews;
    private ImageView selectedCardBackground;
//    private ImageView selectedCardFromHandBackground;
//    private BattleEnvironmentController battleEnvironmentController;
    private int[] selectedCardCoordinates = new int[]{-2, -2};
//    private int selectedCardFromHandCoordinate = -1;
    private Battle battle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Next three lines are Just for test and must be replaced properly.
        CurrentAccount.setCurrentAccount(Account.createAccount("a", "1234"));
        GameMenuPresenter gameMenuPresenter = new GameMenuPresenter();
        battle = gameMenuPresenter.prepareForSingleGame(1, null);

//        System.out.println(battle.getPlayers()[1]);
//        System.out.println(battle.getBattleGround().getGround().get(2).get(8).getOwner());
        selectedCardBackground = new ImageView(new Image("file:images/card_background_highlight.png"));
//        selectedCardFromHandBackground = new ImageView(new Image("file:images/card_background_replaced.png"));
        errorImage = new Image("file:images/notification_quest_small.png");

        initializeCardBackground(selectedCardBackground);
//        initializeCardBackground(selectedCardFromHandBackground);
        initializeProfilePics();
        initializeManaGemImages();
        initializeGround();
        initializeHandImages();
//        Button button = new Button();
//        mainStackPane.getChildren().add(button);
//        TranslateTransition translateTransition = new TranslateTransition();
//        translateTransition.setNode(button);
//        translateTransition.setFromX(100);
//        translateTransition.setFromY(100);
//        translateTransition.setToX(300);
//        translateTransition.setToY(300);
//        translateTransition.setDuration(new Duration(1000));
//        translateTransition.setCycleCount(5);
//        translateTransition.play();
    }

    private void initializeCardBackground(ImageView imageView) {
        imageView.setOpacity(0.5);
        imageView.setFitHeight(CELL_HEIGHT);
        imageView.setFitWidth(CELL_WIDTH);
    }

    private void initializeProfilePics() {
        String firstHeroName = battle.getPlayers()[0].getMainDeck().getHero().getName();
        String secondHeroName = battle.getPlayers()[1].getMainDeck().getHero().getName();
        playerProfilePic.setImage(new Image("file:images/cards/hero/" + firstHeroName + "/" + firstHeroName + "_profile.png"));
        opponentProfilePic.setImage(new Image("file:images/cards/hero/" + secondHeroName + "/" + secondHeroName + "_profile.png"));
    }

    private void initializeManaGemImages() {
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
                    showSelectedCardFromHand(handAndNextCardImageViews[i], i);
            }
            else if (card instanceof Spell) {
                handAndNextCardImageViews[i] = new ImageView(new Image(((Spell) card).getImageAddresses().get(Spell.State.actionbar)));
                if (i != 0)
                    showSelectedCardFromHand(handAndNextCardImageViews[i], i);
            }
            else
                handAndNextCardImageViews[i] = new ImageView();
            handAndNextCard.add(handAndNextCardImageViews[i], i, 0);
        }
    }

    private void showSelectedCardFromHand(ImageView handAndNextCardImageView, int i) {
        handAndNextCardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedCardCoordinates[0] == -1) //Card is already in hand.
                    handAndNextCard.getChildren().remove(selectedCardBackground);
                else if (selectedCardCoordinates[0] > -1)
                    battleGroundGrid.getChildren().remove(selectedCardBackground);
                selectedCardCoordinates[0] = -1;
                selectedCardCoordinates[1] = i;
                handAndNextCard.add(selectedCardBackground, i, 0);
//                if (selectedCardFromHandCoordinate != -1){
//                    handAndNextCard.getChildren().remove(selectedCardFromHandCoordinate);
//                    selectedCardFromHandCoordinate = i;
//                }
//                handAndNextCard.add(selectedCardFromHandBackground, i, 0);
            }
        });
    }

    private void setNextCardRing() {
        ImageView nextCardRing = new ImageView(new Image("file:images/replace_outer_ring_smoke.png"));
        nextCardRing.setFitHeight(CELL_HEIGHT);
        nextCardRing.setFitWidth(CELL_WIDTH);
        nextCardRing.setOpacity(1);
        handAndNextCard.add(nextCardRing, 0, 0);
    }

    private void initializeGround() {
        battleGroundPanes = new Pane[BattleGround.getRows()][BattleGround.getColumns()];
        groundImageViews = new ImageView[BattleGround.getRows()][BattleGround.getColumns()];
        for (int i = 0; i < BattleGround.getRows(); i++) {
            for (int j = 0; j < BattleGround.getColumns(); j++) {
                Asset asset = battle.getBattleGround().getGround().get(i).get(j);
                if (asset instanceof Warrior)
                    groundImageViews[i][j] = new ImageView(new Image(((Warrior) asset).getImageAddresses().get(Warrior.State.breathing)));
                else if (asset instanceof Item)
                    groundImageViews[i][j] = new ImageView(new Image(((Item) asset).getImageAddresses().get(Item.State.actionbar)));
                else if (asset instanceof Flag)
                    groundImageViews[i][j] = new ImageView(new Image("file:images/flag_icon.png"));
                else
                    groundImageViews[i][j] = new ImageView(new Image("file:images/card_background.png"));
                setGroundCellOnClickEvent(groundImageViews[i][j], asset, i, j);

                //TODO is next line assignment needed?
//                groundImageViews[i][j].setId(i + "," + j);
//                groundImageViews[i][j].setFitHeight(battleGroundGrid.getHeight());
//                groundImageViews[i][j].setFitWidth(battleGroundGrid.getWidth());
                battleGroundPanes[i][j] = new Pane();
                battleGroundPanes[i][j].getChildren().add(groundImageViews[i][j]);
                battleGroundGrid.add(battleGroundPanes[i][j], j, i);
            }
        }
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
            handAndNextCard.getChildren().remove(selectedCardBackground);
        else if (selectedCardCoordinates[0] > -1)
            battleGroundGrid.getChildren().remove(selectedCardBackground);
        selectedCardCoordinates[0] = i;
        selectedCardCoordinates[1] = j;
        battleGroundGrid.add(selectedCardBackground, j, i);
    }

    private void insertCard(int i, int j) {
        try {
            battle.insertCard(battle.getPlayers()[0], battle.getPlayersHand()[0][selectedCardCoordinates[1]].getName(), j + 1, i + 1);
            showInsertAnimation(i, j);
        } catch (AssetNotFoundException | InvalidInsertInBattleGroundException | ThisCellFilledException | DontHaveEnoughManaException e) {
            handleError(e);
        }
    }

    private void showInsertAnimation(int i, int j) {
        Card handCard = battle.getPlayersHand()[0][selectedCardCoordinates[1]];
        if (handCard instanceof Warrior)
            groundImageViews[i][j].setImage(new Image( ((Warrior) handCard).getImageAddresses().get(Warrior.State.breathing) ));
        else if (handCard instanceof Spell) {
            groundImageViews[i][j].setImage(new Image(((Spell) handCard).getImageAddresses().get(Spell.State.active)));
            //TODO next lines must be edited.
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            groundImageViews[i][j].setImage(null);
        }
        handAndNextCardImageViews[selectedCardCoordinates[1]].setImage(null);
    }

    private void moveCard(int i, int j) {
        try {
            battle.cardMoveTo(battle.getPlayers()[0], (Warrior) battle.getPlayersSelectedCard()[0], j + 1, i + 1);
            showMoveAnimation(i, j);
        } catch (InvalidTargetException | ThisCellFilledException | WarriorSecondMoveInTurnException e) {
            handleError(e);
        }
    }

    private void showMoveAnimation(int i, int j) {
        Warrior warrior = (Warrior) battle.getPlayersSelectedCard()[0];
        new Thread(new Runnable() {
            @Override
            public void run() {
                TranslateTransition translateTransition = new TranslateTransition();
                groundImageViews[selectedCardCoordinates[0]][selectedCardCoordinates[1]].setImage(new Image(warrior.getImageAddresses().get(Warrior.State.run)));
                translateTransition.setNode(battleGroundPanes[selectedCardCoordinates[0]][selectedCardCoordinates[1]]);
                translateTransition.setToX(battleGroundPanes[i][j].getLayoutX());
                translateTransition.setToY(battleGroundPanes[i][j].getLayoutY());
                translateTransition.setDuration(new Duration(1000));
                translateTransition.play();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                groundImageViews[i][j].setImage(new Image(warrior.getImageAddresses().get(Warrior.State.breathing)));
            }
        }).start();
//        translateTransition.pause();
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
        Warrior attacker = (Warrior) battle.getPlayersSelectedCard()[0];
        int attackerRow = selectedCardCoordinates[0];
        int attackerColumn = selectedCardCoordinates[1];
        groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getImageAddresses().get(Warrior.State.attack)));
        //TODO: Is it needed to show a bubble indicating value of damage applied?
        //TODO next lines must be edited.
//        try {
//            TimeUnit.SECONDS.sleep(2);
//            groundImageViews[attackerRow][attackerColumn].setImage(new Image(attacker.getImageAddresses().get(Warrior.State.breathing)));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void handleError(RuntimeException e) {
        new Thread(() -> {
            errorBar.setImage(errorImage);
            errorMessage.setText(getErrorMessage(e));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            errorBar.setImage(null);
            errorMessage.setText(null);
        }).start();
    }

    private String getErrorMessage(RuntimeException e) {
        if (e instanceof AssetNotFoundException)
            return "Asset not found.";
        else if (e instanceof InvalidAttackException)
            return "Invalid target to attack.";
        else if (e instanceof InvalidInsertInBattleGroundException)
            return "Invalid target to insert card.";
        else if (e instanceof InvalidTargetException)
            return "Invalid target to move.";
        else if (e instanceof ThisCellFilledException)
            return "Target cell is filled.";
        else if (e instanceof DontHaveEnoughManaException)
            return "You don't have enough mana.";
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