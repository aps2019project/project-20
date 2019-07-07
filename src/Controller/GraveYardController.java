package Controller;

import Model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import java.io.IOException;
import java.net.URL;
import Presenter.ScreenManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;

public class GraveYardController implements Initializable, ScreenManager {
    public AnchorPane graveYardAnchorPane;
    public GridPane playerDeadCardsGrid;
    public GridPane opponentDeadCardsGrid;
    private Pane[][] playerDeadCardsPanes = new Pane[10][2];
    private Pane[][] opponentDeadCardsPanes = new Pane[10][2];
    public ImageView exitButton;
    private Battle battle;

    public GraveYardController(){}

    public GraveYardController(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setExitButtonEvent();
        initializeDeadCardsImages(true);
        initializeDeadCardsImages(false);
    }

    public void initializeDeadCardsImages(boolean isPlayer) {
        Pane[][] panes = playerDeadCardsPanes;
        GridPane gridPane = playerDeadCardsGrid;
        int playerIndex = 0;
        if (!isPlayer) {
            panes = opponentDeadCardsPanes;
            gridPane = opponentDeadCardsGrid;
            playerIndex = 1;
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

    private void setExitButtonEvent() {
        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setImage(new Image("file:images/exit_button_hover.png"));
            }
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setImage(new Image("file:images/exit_button.png"));
            }
        });
        exitButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setImage(new Image("file:images/exit_button_pressed.png"));
                try {
                    loadPageOnStackPane(graveYardAnchorPane, "../View/FXML/BattleGround.fxml", "rtl");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        exitButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitButton.setImage(new Image("file:images/exit_button_hover.png"));
            }
        });
    }
}