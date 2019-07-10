package Controller;

import Client.Client;
import Datas.SoundDatas;
import Model.PlayerViewer;
import Presenter.DialogThrowable;
import Presenter.ImageComparable;
import Presenter.ScreenManager;
import com.gilecode.yagson.YaGson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OnlinePlayersTableController implements Initializable, ScreenManager, ImageComparable, DialogThrowable {
    public ImageView back;
    public AnchorPane anchorPane;
    public TableView<PlayerViewer> playerTable;
    public TableColumn<PlayerViewer, String> nameCol;
    public TableColumn<PlayerViewer, String> statusCol;
    public ImageView actionButton;
    private ObservableList<PlayerViewer> myList = FXCollections.observableArrayList();

    public void setBackButtonOnMouseEntered() {
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed() {
        SoundDatas.playSFX(SoundDatas.PAGE_CHANGING);
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited() {
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        Client.getWriter().println("exitFromPage");
        loadPageOnStackPane(back.getParent(), "../View/FXML/MainMenu.fxml", "ltr");
    }

    public void setActionButtonReleased() {
        if(playerTable.getSelectionModel().getSelectedItem()!=null ) {
            switch (playerTable.getSelectionModel().getSelectedItem().getStatus()){
                case FREE:
                sendBattleRequest();
                break;
                case PLAYING:
                    //todo show battle;
                    break;
            }
            setActionButtonPressed();
        }
    }

    public void setActionButtonPressed() {
        if(playerTable.getSelectionModel().getSelectedItem()!=null && playerTable.getSelectionModel().getSelectedItem().getStatus()!= PlayerViewer.Status.OFFLINE) {
            SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
            if(playerTable.getSelectionModel().getSelectedItem().getStatus()== PlayerViewer.Status.FREE){
                actionButton.setImage(new Image("file:images/button_battle_pressed.png"));
            }else {
                actionButton.setImage(new Image("file:images/button_watch_pressed.png"));
            }
        }else {
            actionButton.setImage(new Image("file:images/unhover_button.png"));
        }
    }

    public void setActionButtonMouseOver() {
        if(playerTable.getSelectionModel().getSelectedItem()!=null && playerTable.getSelectionModel().getSelectedItem().getStatus()!= PlayerViewer.Status.OFFLINE) {
            SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
            if(playerTable.getSelectionModel().getSelectedItem().getStatus()== PlayerViewer.Status.FREE){
                actionButton.setImage(new Image("file:images/button_battle_hover.png"));
            }else {
                actionButton.setImage(new Image("file:images/button_watch_hover.png"));
            }
        }else {
            actionButton.setImage(new Image("file:images/unhover_button.png"));
        }
    }

    public void setActionButtonMouseExited() {
        if(playerTable.getSelectionModel().getSelectedItem()!=null && playerTable.getSelectionModel().getSelectedItem().getStatus()!= PlayerViewer.Status.OFFLINE) {
            if(playerTable.getSelectionModel().getSelectedItem().getStatus()== PlayerViewer.Status.FREE){
                actionButton.setImage(new Image("file:images/button_battle_available.png"));
            }else {
                actionButton.setImage(new Image("file:images/button_watch_available.png"));
            }
        }else {
            actionButton.setImage(new Image("file:images/unhover_button.png"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.setOnlinePlayersTableController(this);
        Client.getWriter().println("getOnlinePlayersTable");
        Client.waitForListener();
        PlayerViewer[] playersReceivedFromServer = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), PlayerViewer[].class);
        updateOnlinePlayersTableButtons(playersReceivedFromServer);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusView"));
        playerTable.setOnMouseClicked(event -> setActionButtonMouseExited());
        playerTable.setItems(myList);
        playerTable.getColumns().addAll(nameCol, statusCol);
    }

    public void sendBattleRequest(){
        Client.getWriter().println("battleQuest " + playerTable.getSelectionModel().getSelectedItem().getName());
        Client.waitForListener();
    }

    public void updateOnlinePlayersTableButtons(PlayerViewer[] playersReceivedFromServer){
        myList.setAll(playersReceivedFromServer);
    }

    public ObservableList<PlayerViewer> getMyList() {
        return myList;
    }

    public void setMyList(ObservableList<PlayerViewer> myList) {
        this.myList = myList;
    }
}
