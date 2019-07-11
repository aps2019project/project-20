package Controller;

import Datas.SoundDatas;
import Model.MatchHistory;
import Model.SavedBattle;
import Presenter.AccountManageable;
import Presenter.CurrentAccount;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import com.jfoenix.controls.JFXTabPane;
import com.sun.deploy.util.NativeLibraryBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MatchHistoryController implements Initializable, ScreenManager, AccountManageable, DialogThrowable {
    public ImageView back;
    public AnchorPane anchorPane;
    public TableView<MatchHistory> matchHistoryTable;
    public TableColumn<MatchHistory,Integer> numberCol;
    public TableColumn<MatchHistory,String>  opponentNameCol;
    public TableColumn<MatchHistory,String>  timeCol;
    public TableColumn<MatchHistory, MatchHistory.Result>  resultCol;
    public JFXTabPane tabPane;
    public Tab matchHistoryTab;
    public Tab gameSavesTab;
    public TableView<SavedBattle> saveTable;
    public TableColumn<SavedBattle,String > dateCol;
    public TableColumn<SavedBattle,String> opponentHeroNameCol;
    public TableColumn<SavedBattle,Integer> numbersCol;
    public ImageView deleteButton;
    public ImageView startButton;
    public ImageView replayButton;

    public void setBackButtonOnMouseEntered(){
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed(){
        SoundDatas.playSFX(SoundDatas.PAGE_CHANGING);
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited(){
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        loadPageOnStackPane(back.getParent(),"../View/FXML/MainMenu.fxml","ltr");
    }

    public void setDeleteButtonReleased() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            for (int i = 0; i < getCurrentAccount().getSavedBattles().size(); i++) {
                if(getCurrentAccount().getSavedBattles().get(i).getDate().equals(saveTable.getSelectionModel().getSelectedItem().getDate())){
                    getCurrentAccount().getSavedBattles().remove(i);
                    break;
                }
            }
            saveTable.getSelectionModel().getSelectedItem().removeBattleFromFile(getCurrentAccount().getName(),"Data/AccountsData.json");
            saveTable.getItems().remove(saveTable.getSelectionModel().getSelectedItem());
            deleteButton.setImage(new Image("file:images/hover_delete_button.png"));
        }
    }

    public void setDeleteButtonPressed() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            deleteButton.setImage(new Image("file:images/pressed_delete_button.png"));
        }else {
            deleteButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }

    public void setDeleteButtonMouseOver() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            deleteButton.setImage(new Image("file:images/hover_delete_button.png"));
        }else {
            deleteButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }

    public void setDeleteButtonMouseExited() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            deleteButton.setImage(new Image("file:images/available_delete_button.png"));
        }else {
            deleteButton.setImage(new Image("file:images/unavailable_delete_button.png"));
        }
    }

    public void setStartButtonReleased() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            //todo some of battleGround data like accumulated items is not saved
            startNewGame(saveTable.getSelectionModel().getSelectedItem().getBattle(), -1);
            startButton.setImage(new Image("file:images/start_button_hover.png"));
        }
    }

    public void setStartButtonPressed() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            startButton.setImage(new Image("file:images/start_button_pressed.png"));
        }else {
            startButton.setImage(new Image("file:images/start_button_unavailable.png"));
        }
    }

    public void setStartButtonMouseOver() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            startButton.setImage(new Image("file:images/start_button_hover.png"));
        }else {
            startButton.setImage(new Image("file:images/start_button_unavailable.png"));
        }
    }

    public void setStartButtonMouseExited() {
        if(saveTable.getSelectionModel().getSelectedItem()!=null) {
            startButton.setImage(new Image("file:images/start_button.png"));
        }else {
            startButton.setImage(new Image("file:images/start_button_unavailable.png"));
        }
    }

    public void setReplayButtonReleased() {
        if(matchHistoryTable.getSelectionModel().getSelectedItem()!=null) {
            initializeMediaPane();
            replayButton.setImage(new Image("file:images/button_play_hover.png"));
        }
    }

    public void setReplayButtonPressed() {
        if(matchHistoryTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            replayButton.setImage(new Image("file:images/button_play_pressed.png"));
        }else {
            replayButton.setImage(new Image("file:images/button_play_unavailable.png"));
        }
    }

    public void setReplayButtonMouseOver() {
        if(matchHistoryTable.getSelectionModel().getSelectedItem()!=null) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            replayButton.setImage(new Image("file:images/button_play_hover.png"));
        }else {
            replayButton.setImage(new Image("file:images/button_play_unavailable.png"));
        }
    }

    public void setReplayButtonMouseExited() {
        if(matchHistoryTable.getSelectionModel().getSelectedItem()!=null) {
            replayButton.setImage(new Image("file:images/button_play_available.png"));
        }else {
            replayButton.setImage(new Image("file:images/button_play_unavailable.png"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory.png")));
        gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles.png")));
        tabPane.setOnMouseClicked(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory_pressed.png")));
                gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles.png")));
            } else if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                SoundDatas.playSFX(SoundDatas.TAB_PRESS);
                gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles_pressed.png")));
                matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory.png")));
            }
        });
        tabPane.setOnMouseMoved(event -> {
            if (isInRightTabCoordination(event.getX(), event.getY(), 10, 6, 160, 46)) {
                matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory_hover.png")));
            } else if (!matchHistoryTab.isSelected()) {
                matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory.png")));
            } else {
                matchHistoryTab.setGraphic(new ImageView(new Image("file:images/tab_matchHistory_pressed.png")));
            }
            if (isInRightTabCoordination(event.getX(), event.getY(), 10 + 160 + 15, 6, 160, 40)) {
                gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles_hover.png")));
            } else if (!gameSavesTab.isSelected()) {
                gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles.png")));
            } else {
                gameSavesTab.setGraphic(new ImageView(new Image("file:images/tab_savedBattles_pressed.png")));
            }
        });
        initializeMatchHistoryTabTable();
        initializeSavedBattlesTabTable();
        saveTable.setOnMouseClicked(event -> {
            SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
            startButton.setImage(new Image("file:images/start_button.png"));
            deleteButton.setImage(new Image("file:images/available_delete_button.png"));
        });
        matchHistoryTable.setOnMouseClicked(event -> {
            SoundDatas.playSFX(SoundDatas.SELECT_ITEM);
            replayButton.setImage(new Image("file:images/button_play_available.png"));
        });
    }

    public boolean isInRightTabCoordination(double x, double y, double tabX, double tabY, int tabWidth, int tabHeight) {
        return (x > tabX && x < tabX + tabWidth && y > tabY && y < tabY + tabHeight);
    }

    public void initializeMatchHistoryTabTable(){
        ArrayList<MatchHistory> matchHistories = CurrentAccount.getCurrentAccount().getMatchHistories();
        for (int i = 0; i < matchHistories.size(); i++) {
            matchHistories.get(i).setNumber(i+1);
        }

        ObservableList<MatchHistory> myList = FXCollections.observableArrayList();
        myList.addAll(matchHistories);

        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        opponentNameCol.setCellValueFactory(new PropertyValueFactory<>("playerName1"));

        matchHistoryTable.setItems(myList);
        matchHistoryTable.getColumns().addAll(timeCol,numberCol,opponentNameCol,resultCol);
    }

    public void initializeSavedBattlesTabTable(){
        ArrayList<SavedBattle> savedBattles = CurrentAccount.getCurrentAccount().getSavedBattles();
        for (int i = 0; i < savedBattles.size(); i++) {
            savedBattles.get(i).setNumber(i+1);
        }

        ObservableList<SavedBattle> myList = FXCollections.observableArrayList();
        myList.addAll(savedBattles);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        numbersCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        opponentHeroNameCol.setCellValueFactory(new PropertyValueFactory<>("AIHeroName"));

        saveTable.setItems(myList);
        saveTable.getColumns().addAll(numbersCol,dateCol,opponentHeroNameCol);
    }

    public void initializeMediaPane(){
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+new File(matchHistoryTable.getSelectionModel().getSelectedItem().getVideoPath()).toURI().toURL().toString()).waitFor();
        } catch (InterruptedException | IOException e) {
            showOneButtonErrorDialog("Playing Video Error","you don't have any video player!!!");
        }
    }

}
