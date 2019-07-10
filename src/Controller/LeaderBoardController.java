package Controller;

import Client.Client;
import Datas.SoundDatas;
import Model.Account;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable, ScreenManager {
    public ImageView back;
    public AnchorPane anchorPane;
    public TableView<Account> leaderTable;
    public TableColumn<Account,Integer> rankCol;
    public TableColumn<Account,String> nameCol;
    public TableColumn<Account,Integer> winCol;
    public TableColumn<Account,Integer> loosesCol;
    private static ObservableList<Account> myList = FXCollections.observableArrayList();

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
        Client.getWriter().println("exitFromPage");
        loadPageOnStackPane(back.getParent(),"../View/FXML/MainMenu.fxml","ltr");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.getWriter().println("getLeaderBoard");
        Client.waitForListener();

        Account[] accountsReceivedFromServer = new YaGson().fromJson(Client.getMessageListener().getDataFromServer(), Account[].class);

        myList.setAll(accountsReceivedFromServer);

        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        winCol.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        loosesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfLooses"));

        leaderTable.setItems(myList);
        leaderTable.getColumns().addAll(rankCol,nameCol,winCol,loosesCol);
    }


    public static ObservableList<Account> getMyList() {
        return myList;
    }

    public static void setMyList(ObservableList<Account> myList) {
        LeaderBoardController.myList = myList;
    }
}
