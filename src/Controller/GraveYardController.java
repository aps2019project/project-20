//package Controller;
//
//import Model.*;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.*;
//import java.io.IOException;
//import java.net.URL;
//import Presenter.ScreenManager;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import java.util.ResourceBundle;
//
//public class GraveYardController implements Initializable, ScreenManager {
//    public AnchorPane graveYardAnchorPane;
//    public GridPane deadCardsGrid0;
//    public GridPane deadCardsGrid1;
//    private Pane[][] deadCards0Panes = new Pane[10][2];
//    private Pane[][] deadCards1Panes = new Pane[10][2];
//    public ImageView exitButton;
//    private Battle battle;
//
//    public GraveYardController(){}
//
//    public GraveYardController(Battle battle) {
//        this.battle = battle;
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        setExitButtonEvent();
//        initializeDeadCardsImages(true);
//        initializeDeadCardsImages(false);
//    }
//
//
//}