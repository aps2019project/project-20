package Controller;

import View.Main;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public ImageView battle;
    public ImageView collection;
    public ImageView shop;
    public ImageView matchHistory;
    public ImageView leaderBoard;
    public JFXTextArea description;
    public AnchorPane anchorPane;

    public void setBattleButtonPressed() {
        battle.setImage(new Image("file:images/pressed_battle_button.png"));
    }

    public void setBattleMouseOver() {
        battle.setImage(new Image("file:images/hover_battle_button.png"));
        description.setText("\n     You Can Start A Battle");
    }

    public void setBattleMouseUnOver() {
        battle.setImage(new Image("file:images/unhover_battle_button.png"));
        description.setText("\n                Welcome");
    }

    public void setCollectionButtonPressed() {
        collection.setImage(new Image("file:images/pressed_collection_button.png"));
    }

    public void setCollectionMouseOver() {
        collection.setImage(new Image("file:images/hover_collection_button.png"));
        description.setText("\n All Of Your Cards And Items\n                Are Here");
    }

    public void setCollectionMouseUnOver() {
        collection.setImage(new Image("file:images/unhover_collection_button.png"));
        description.setText("\n                Welcome");
    }

    public void setShopButtonPressed() {
        shop.setImage(new Image("file:images/pressed_shop_button.png"));
    }

    public void setShopMouseOver() {
        shop.setImage(new Image("file:images/hover_shop_button.png"));
        description.setText("\n     Your Can Buy Anything\n                From Here");
    }

    public void setShopMouseUnOver() {
        shop.setImage(new Image("file:images/unhover_shop_button.png"));
        description.setText("\n                Welcome");
    }

    public void setLeaderBoardButtonPressed() {
        leaderBoard.setImage(new Image("file:images/pressed_leaderboard_button.png"));
    }

    public void setLeaderBoardMouseOver() {
        leaderBoard.setImage(new Image("file:images/hover_leaderboard_button.png"));
        description.setText("\n  You Can See Your Ranking");
    }

    public void setLeaderMouseUnOver() {
        leaderBoard.setImage(new Image("file:images/unhover_leaderboard_button.png"));
        description.setText("\n                Welcome");
    }

    public void setMatchHistoryButtonPressed() {
        matchHistory.setImage(new Image("file:images/pressed_matchHistory_button.png"));
    }

    public void setMatchHistoryMouseOver() {
        matchHistory.setImage(new Image("file:images/hover_matchHistory_button.png"));
        description.setText("\n  Matches You Have Played\n                Are Here");
    }

    public void setMatchHistoryMouseUnOver() {
        matchHistory.setImage(new Image("file:images/unhover_matchHistory_button.png"));
        description.setText("\n                Welcome");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageView main = new ImageView(new Image("file:images/ranked_chevron_empty.png"));
        ImageView myProfile = new ImageView(new Image("file:images/profile.png"));
        ImageView logout = new ImageView(new Image("file:images/logoff.png"));
        ImageView save = new ImageView(new Image("file:images/save.png"));
        ImageView exit = new ImageView(new Image("file:images/exit.png"));
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        Pane pane4 = new Pane();
        Pane pane5 = new Pane();
        pane1.getChildren().add(main);
        pane2.getChildren().add(myProfile);
        pane3.getChildren().add(logout);
        pane4.getChildren().add(save);
        pane5.getChildren().add(exit);

        JFXNodesList nodesList = new JFXNodesList();
        nodesList.setSpacing(20);
        nodesList.setRotate(90);
        nodesList.setLayoutX(1750.0);nodesList.setLayoutY(940.0);
        nodesList.addAnimatedNode(pane1);
        nodesList.addAnimatedNode(pane2);
        nodesList.addAnimatedNode(pane3);
        nodesList.addAnimatedNode(pane4);
        nodesList.addAnimatedNode(pane5);

        //todo better graphic

        myProfile.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myProfile.setImage(new Image("file:images/hover_profile_button.png"));
                description.setText("\n         Edit Your Profile");
            }
        });
        myProfile.setOnMouseExited(event -> {
            myProfile.setImage(new Image("file:images/profile.png"));
            description.setText("\n                Welcome");
        });
        logout.setOnMouseEntered(event -> {
            logout.setImage(new Image("file:images/hover_logoff_button.png"));
            description.setText("\n                 Logout");
        });
        logout.setOnMouseExited(event -> {
            logout.setImage(new Image("file:images/logoff.png"));
            description.setText("\n                Welcome");
        });
        save.setOnMouseEntered(event -> {
            save.setImage(new Image("file:images/hover_save_button.png"));
            description.setText("\n                  Save");
        });
        save.setOnMouseExited(event -> {
            save.setImage(new Image("file:images/save.png"));
            description.setText("\n                Welcome");
        });
        exit.setOnMouseEntered(event -> {
            exit.setImage(new Image("file:images/hover_exit_button.png"));
            description.setText("\n                  Exit");
        });
        exit.setOnMouseExited(event -> {
            exit.setImage(new Image("file:images/exit.png"));
            description.setText("\n                Welcome");
        });
        main.setOnMouseEntered(event -> {
            if(Main.computeSnapshotSimilarity(main.getImage(),new Image("file:images/onClicked_toolbar.png"))!=100.0) {
                main.setImage(new Image("file:images/ranked_chevron_full.png"));
            }
        });
        main.setOnMouseExited(event -> {
            if(Main.computeSnapshotSimilarity(main.getImage(),new Image("file:images/onClicked_toolbar.png"))!=100.0) {
                main.setImage(new Image("file:images/ranked_chevron_empty.png"));
            }
        });
        main.setOnMouseClicked(event -> {
            if(Main.computeSnapshotSimilarity(main.getImage(),new Image("file:images/ranked_chevron_full.png"))==100.0){
                main.setImage(new Image("file:images/onClicked_toolbar.png"));
            }else {
                main.setImage(new Image("file:images/ranked_chevron_full.png"));
            }
        });

        anchorPane.getChildren().add(nodesList);

    }
}
