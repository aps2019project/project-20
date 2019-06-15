package Controller;

import Model.Battle;
import Presenter.Animationable;
import Presenter.ScreenManager;
import com.jfoenix.controls.JFXRippler;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BeforeBattleMenu3Controller implements Initializable, ScreenManager, Animationable {
    public ImageView back;
    public ImageView level1Button;
    public Pane level1PaneDetail;
    public ImageView level2Button;
    public Pane level2PaneDetail;
    public AnchorPane anchorPane;
    public Pane level3PaneDetail;


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
        loadPageOnStackPane(back.getParent(),"FXML/BeforeBattleMenu2.fxml","ltr");
    }


    public void setLevel1ButtonOnMouseEntered(){
        nodeFadeAnimation(level1PaneDetail,200,0,1).play();
    }

    public void setLevel1ButtonOnMouseExited(){
        nodeFadeAnimation(level1PaneDetail,200,1,0).play();
    }

    public void setLevel1ButtonOnMouseReleased() throws IOException {
        //todo
        Battle.soloStoryModeConstructor(1);
        loadPageOnStackPane(anchorPane,"","rtl");
    }

    public void setLevel2ButtonOnMouseEntered(){
        nodeFadeAnimation(level2PaneDetail,200,0,1).play();
    }

    public void setLevel2ButtonOnMouseExited(){
        nodeFadeAnimation(level2PaneDetail,200,1,0).play();
    }

    public void setLevel2ButtonOnMouseReleased() throws IOException {
        //todo
        Battle.soloStoryModeConstructor(2);
        loadPageOnStackPane(anchorPane,"","rtl");
    }

    public void setLevel3ButtonOnMouseEntered(){
        nodeFadeAnimation(level3PaneDetail,200,0,1).play();
    }

    public void setLevel3ButtonOnMouseExited(){
        nodeFadeAnimation(level3PaneDetail,200,1,0).play();
    }

    public void setLevel3ButtonOnMouseReleased() throws IOException {
        //todo
        Battle.soloStoryModeConstructor(3);
        loadPageOnStackPane(anchorPane,"","rtl");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        level1PaneDetail.setOpacity(0);
        level2PaneDetail.setOpacity(0);
        level3PaneDetail.setOpacity(0);

        JFXRippler ripple1 = new JFXRippler(level1PaneDetail);
        JFXRippler ripple2 = new JFXRippler(level2PaneDetail);
        JFXRippler ripple3 = new JFXRippler(level3PaneDetail);
        ripple1.resizeRelocate(level1PaneDetail.getLayoutX(), level1PaneDetail.getLayoutY(), level1PaneDetail.getPrefWidth(), level1PaneDetail.getPrefHeight());
        ripple2.resizeRelocate(level2PaneDetail.getLayoutX(), level2PaneDetail.getLayoutY(), level2PaneDetail.getPrefWidth(), level2PaneDetail.getPrefHeight());
        ripple3.resizeRelocate(level3PaneDetail.getLayoutX(), level3PaneDetail.getLayoutY(), level3PaneDetail.getPrefWidth(), level3PaneDetail.getPrefHeight());
        ripple1.setRipplerFill(Paint.valueOf("#000000"));
        ripple2.setRipplerFill(Paint.valueOf("#000000"));
        ripple3.setRipplerFill(Paint.valueOf("#000000"));
        anchorPane.getChildren().addAll(ripple1,ripple2,ripple3);

    }
}
