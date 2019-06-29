package Controller;

import Model.Account;
import Model.MatchHistory;
import Presenter.CurrentAccount;
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

public class MatchHistoryController implements Initializable, ScreenManager {
    public ImageView back;
    public AnchorPane anchorPane;
    public TableView<MatchHistory> leaderTable;
    public TableColumn<MatchHistory,Integer> numberCol;
    public TableColumn<MatchHistory,String>  opponentNameCol;
    public TableColumn<MatchHistory,String>  timeCol;
    public TableColumn<MatchHistory, MatchHistory.Result>  resultCol;

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
        ArrayList<MatchHistory> matchesReceivedFromServer = CurrentAccount.getCurrentAccount().getMatchHistories();
        for (int i = 0; i < matchesReceivedFromServer.size(); i++) {
            matchesReceivedFromServer.get(i).setNumber(i+1);;
        }

        ObservableList<MatchHistory> myList = FXCollections.observableArrayList();
        myList.addAll(matchesReceivedFromServer);

        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        opponentNameCol.setCellValueFactory(new PropertyValueFactory<>("opponentName"));

        leaderTable.setItems(myList);
        leaderTable.getColumns().addAll(timeCol,numberCol,opponentNameCol,resultCol);

    }

}
