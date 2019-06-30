package Controller;

import Model.Account;
import Presenter.ScreenManager;
import View.Main;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

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


    public void setBackButtonOnMouseEntered(){
        back.setImage(new Image("file:images/hover_back_button_corner.png"));
    }

    public void setBackButtonOnMousePressed(){
        back.setImage(new Image("file:images/pressed_back_button_corner.png"));
    }

    public void setBackButtonOnMouseExited(){
        back.setImage(new Image("file:images/button_back_corner.png"));
    }

    public void setBackButtonOnMouseReleased() throws IOException {
        loadPageOnStackPane(back.getParent(),"FXML/MainMenu.fxml","ltr");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Account> accountsReceivedFromServer = new ArrayList<>();
        try {
            accountsReceivedFromServer = Account.getAccountsFromFile("Data/AccountsData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Account.sortAccounts(accountsReceivedFromServer);

        ObservableList<Account> myList = FXCollections.observableArrayList();
        myList.addAll(accountsReceivedFromServer);

        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        winCol.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        loosesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfLooses"));

        leaderTable.setItems(myList);
        leaderTable.getColumns().addAll(rankCol,nameCol,winCol,loosesCol);

    }

}
