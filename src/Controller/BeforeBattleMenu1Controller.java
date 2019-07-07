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

public class BeforeBattleMenu1Controller implements Initializable, ScreenManager, Animationable {
    public ImageView back;
    public ImageView soloButton;
    public Pane soloPaneDetail;
    public ImageView multiButton;
    public Pane multiPaneDetail;
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
        loadPageOnStackPane(back.getParent(),"../View/FXML/MainMenu.fxml","ltr");
    }

    public void setSoloButtonOnMouseEntered(){
        nodeFadeAnimation(soloPaneDetail,200,0,1).play();
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
    }

    public void setSoloButtonOnMouseExited(){
        nodeFadeAnimation(soloPaneDetail,200,1,0).play();
    }

    public void setSoloButtonOnMouseReleased() throws IOException {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        loadPageOnStackPane(back.getParent(),"../View/FXML/BeforeBattleMenu2.fxml","rtl");
    }

    public void setMultiButtonOnMouseEntered(){
        nodeFadeAnimation(multiPaneDetail,200,0,1).play();
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
    }

    public void setMultiButtonOnMouseExited(){
        nodeFadeAnimation(multiPaneDetail,200,1,0).play();
    }

    public void setMultiButtonOnMouseReleased(){
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
      //todo
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soloPaneDetail.setOpacity(0);
        multiPaneDetail.setOpacity(0);

        JFXRippler ripple1 = new JFXRippler(soloPaneDetail);
        JFXRippler ripple2 = new JFXRippler(multiPaneDetail);
        ripple1.resizeRelocate(soloPaneDetail.getLayoutX(),soloPaneDetail.getLayoutY(),soloPaneDetail.getPrefWidth(),soloPaneDetail.getPrefHeight());
        ripple2.resizeRelocate(multiPaneDetail.getLayoutX(),multiPaneDetail.getLayoutY(),multiPaneDetail.getPrefWidth(),multiPaneDetail.getPrefHeight());
        ripple1.setRipplerFill(Paint.valueOf("#000000"));
        ripple2.setRipplerFill(Paint.valueOf("#000000"));
        anchorPane.getChildren().add(ripple1);
        anchorPane.getChildren().add(ripple2);

    }
}
