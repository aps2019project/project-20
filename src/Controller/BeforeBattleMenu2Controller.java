package Controller;

import Datas.SoundDatas;
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

public class BeforeBattleMenu2Controller implements Initializable, ScreenManager, Animationable {
    public ImageView back;
    public ImageView customButton;
    public Pane customPaneDetail;
    public ImageView storyButton;
    public Pane storyPaneDetail;
    public AnchorPane anchorPane;


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
        loadPageOnStackPane(back.getParent(),"../View/FXML/BeforeBattleMenu1.fxml","ltr");
    }

    public void setCustomButtonOnMouseEntered(){
        nodeFadeAnimation(customPaneDetail,200,0,1).play();
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
    }

    public void setCustomButtonOnMouseExited(){
        nodeFadeAnimation(customPaneDetail,200,1,0).play();
    }

    public void setCustomButtonOnMouseReleased() throws IOException {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        loadPageOnStackPane(back.getParent(),"../View/FXML/BeforeBattleMenu4.fxml","ltr");
    }

    public void setStoryButtonOnMouseEntered(){
        nodeFadeAnimation(storyPaneDetail,200,0,1).play();
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
    }

    public void setStoryButtonOnMouseExited(){
        nodeFadeAnimation(storyPaneDetail,200,1,0).play();
    }

    public void setStoryButtonOnMouseReleased() throws IOException {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        loadPageOnStackPane(back.getParent(),"../View/FXML/BeforeBattleMenu3.fxml","rtl");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customPaneDetail.setOpacity(0);
        storyPaneDetail.setOpacity(0);

        JFXRippler ripple1 = new JFXRippler(customPaneDetail);
        JFXRippler ripple2 = new JFXRippler(storyPaneDetail);
        ripple1.resizeRelocate(customPaneDetail.getLayoutX(), customPaneDetail.getLayoutY(), customPaneDetail.getPrefWidth(), customPaneDetail.getPrefHeight());
        ripple2.resizeRelocate(storyPaneDetail.getLayoutX(), storyPaneDetail.getLayoutY(), storyPaneDetail.getPrefWidth(), storyPaneDetail.getPrefHeight());
        ripple1.setRipplerFill(Paint.valueOf("#000000"));
        ripple2.setRipplerFill(Paint.valueOf("#000000"));
        anchorPane.getChildren().add(ripple1);
        anchorPane.getChildren().add(ripple2);

    }
}
