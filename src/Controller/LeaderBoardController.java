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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable, ScreenManager {
    public ImageView back;
    public AnchorPane anchorPane;
    public JFXTreeTableView leaderTable;

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
        ArrayList<PersonAccount> accountsReceivedFromServer = new ArrayList<>();
        try {
            accountsReceivedFromServer = new PersonAccount().setAccountDatasInPersonAccountFormatCollection(Account.getAccountsFromFile("Data/AccountsData.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeTableColumn rankCol = new TreeTableColumn("RANK");
        TreeTableColumn nameCol = new TreeTableColumn("NAME");
        TreeTableColumn winsCol = new TreeTableColumn("WINS");
        TreeTableColumn loosesCol = new TreeTableColumn("LOOSES");
        leaderTable.getColumns().addAll(rankCol,nameCol,winsCol,loosesCol);


        ObservableList<PersonAccount> myList = FXCollections.observableArrayList(accountsReceivedFromServer);

        rankCol.setCellValueFactory(new PropertyValueFactory<PersonAccount,Integer>("rank"));
        nameCol.setCellValueFactory(new PropertyValueFactory<PersonAccount,String>("name"));
        winsCol.setCellValueFactory(new PropertyValueFactory<PersonAccount,Integer>("wins"));
        loosesCol.setCellValueFactory(new PropertyValueFactory<PersonAccount,Integer>("looses"));

        leaderTable.setRoot(new RecursiveTreeItem<>(myList, RecursiveTreeObject::getChildren));
        //leaderTable.setShowRoot(true);


    }

    public class PersonAccount extends RecursiveTreeObject<PersonAccount>{
        private SimpleStringProperty name;
        private SimpleIntegerProperty wins;
        private SimpleIntegerProperty looses;
        private SimpleIntegerProperty rank;

        public PersonAccount(){}

        public PersonAccount(SimpleStringProperty name, SimpleIntegerProperty wins, SimpleIntegerProperty looses) {
            this.name = name;
            this.wins = wins;
            this.looses = looses;
            this.rank = new SimpleIntegerProperty(0);
        }

        public ArrayList<PersonAccount> setAccountDatasInPersonAccountFormatCollection(ArrayList<Account> accounts){
            Account.sortAccounts(accounts);
            ArrayList<PersonAccount> personAccounts =new ArrayList<>();
            for (int i = 0; i < accounts.size(); i++) {
                PersonAccount personAccount = new PersonAccount(accounts.get(i).getName(),accounts.get(i).getNumberOfWins(),accounts.get(i).getNumberOfLoses());
                personAccount.setRank(i+1);
                personAccounts.add(personAccount);

            }
            return personAccounts;
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getWins() {
            return wins.get();
        }

        public SimpleIntegerProperty winsProperty() {
            return wins;
        }

        public void setWins(int wins) {
            this.wins.set(wins);
        }

        public int getLooses() {
            return looses.get();
        }

        public SimpleIntegerProperty loosesProperty() {
            return looses;
        }

        public void setLooses(int looses) {
            this.looses.set(looses);
        }

        public int getRank() {
            return rank.get();
        }

        public SimpleIntegerProperty rankProperty() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank.set(rank);
        }
    }
}
