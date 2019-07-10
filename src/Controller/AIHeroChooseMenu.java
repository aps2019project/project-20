package Controller;

import Datas.SoundDatas;
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

public class AIHeroChooseMenu implements Initializable , ScreenManager , Animationable {
    public ImageView back;
    public Pane rostamPaneDetail;
    public Pane rakhshPaneDetail;
    public Pane simorghPaneDetail;
    public Pane arashPaneDetail;
    public Pane kavehPaneDetail;
    public Pane zahhakPaneDetail;
    public Pane esfandiarPaneDetail;
    public Pane dragonPaneDetail;
    public Pane legendPaneDetail;
    public Pane whiteGhoulPaneDetail;
    public AnchorPane anchorPane;


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
        loadPageOnStackPane(back.getParent(), "../View/FXML/BeforeBattleMenu4.fxml", "ltr");
    }


    public void setRostamButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(rostamPaneDetail, 200, 0, 1).play();
    }

    public void setRostamButtonOnMouseExited() {
        nodeFadeAnimation(rostamPaneDetail, 200, 1, 0).play();
    }

    public void setRostamButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("rostam"));
    }

    public void setRakhshButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(rakhshPaneDetail, 200, 0, 1).play();
    }

    public void setRakhshButtonOnMouseExited() {
        nodeFadeAnimation(rakhshPaneDetail, 200, 1, 0).play();
    }

    public void setRakhshButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("rakhsh"));
    }

    public void setSimorghButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(simorghPaneDetail, 200, 0, 1).play();
    }

    public void setSimorghButtonOnMouseExited() {
        nodeFadeAnimation(simorghPaneDetail, 200, 1, 0).play();
    }

    public void setSimorghButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("simorgh"));
    }

    public void setArashButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(arashPaneDetail, 200, 0, 1).play();
    }

    public void setArashButtonOnMouseExited() {
        nodeFadeAnimation(arashPaneDetail, 200, 1, 0).play();
    }

    public void setArashButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("arash"));
    }

    public void setKavehButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(kavehPaneDetail, 200, 0, 1).play();
    }

    public void setKavehButtonOnMouseExited() {
        nodeFadeAnimation(kavehPaneDetail, 200, 1, 0).play();
    }

    public void setKavehButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("kaveh"));
    }

    public void setZahhakButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(zahhakPaneDetail, 200, 0, 1).play();
    }

    public void setZahhakButtonOnMouseExited() {
        nodeFadeAnimation(zahhakPaneDetail, 200, 1, 0).play();
    }

    public void setZahhakButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("zahhak"));
    }

    public void setEsfandiarButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(esfandiarPaneDetail, 200, 0, 1).play();
    }

    public void setEsfandiarButtonOnMouseExited() {
        nodeFadeAnimation(esfandiarPaneDetail, 200, 1, 0).play();
    }

    public void setEsfandiarButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("esfandiar"));
    }

    public void setDragonButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(dragonPaneDetail, 200, 0, 1).play();
    }

    public void setDragonButtonOnMouseExited() {
        nodeFadeAnimation(dragonPaneDetail, 200, 1, 0).play();
    }

    public void setDragonButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("sevenHeadDragon"));
    }

    public void setLegendButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(legendPaneDetail, 200, 0, 1).play();
    }

    public void setLegendButtonOnMouseExited() {
        nodeFadeAnimation(legendPaneDetail, 200, 1, 0).play();
    }

    public void setLegendButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("legend"));
    }

    public void setWhiteGhoulButtonOnMouseEntered() {
        SoundDatas.playSFX(SoundDatas.BUTTON_MOUSEOVER);
        nodeFadeAnimation(whiteGhoulPaneDetail, 200, 0, 1).play();
    }

    public void setWhiteGhoulButtonOnMouseExited() {
        nodeFadeAnimation(whiteGhoulPaneDetail, 200, 1, 0).play();
    }

    public void setWhiteGhoulButtonOnMouseReleased() {
        SoundDatas.playSFX(SoundDatas.BUTTON_PRESS);
        startNewGame(anchorPane,Battle.customKillHeroModeConstructor("whiteDamn"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rostamPaneDetail.setOpacity(0);
        rakhshPaneDetail.setOpacity(0);
        simorghPaneDetail.setOpacity(0);
        arashPaneDetail.setOpacity(0);
        kavehPaneDetail.setOpacity(0);
        zahhakPaneDetail.setOpacity(0);
        esfandiarPaneDetail.setOpacity(0);
        dragonPaneDetail.setOpacity(0);
        legendPaneDetail.setOpacity(0);
        whiteGhoulPaneDetail.setOpacity(0);

        JFXRippler[] ripples = new JFXRippler[10];
        ripples[0] = new JFXRippler(rostamPaneDetail);
        ripples[0].resizeRelocate(rostamPaneDetail.getLayoutX(), rostamPaneDetail.getLayoutY(), rostamPaneDetail.getPrefWidth(), rostamPaneDetail.getPrefHeight());
        ripples[1] = new JFXRippler(rakhshPaneDetail);
        ripples[1].resizeRelocate(rakhshPaneDetail.getLayoutX(), rakhshPaneDetail.getLayoutY(), rakhshPaneDetail.getPrefWidth(), rakhshPaneDetail.getPrefHeight());
        ripples[2] = new JFXRippler(simorghPaneDetail);
        ripples[2].resizeRelocate(simorghPaneDetail.getLayoutX(), simorghPaneDetail.getLayoutY(), simorghPaneDetail.getPrefWidth(), simorghPaneDetail.getPrefHeight());
        ripples[3] = new JFXRippler(arashPaneDetail);
        ripples[3].resizeRelocate(arashPaneDetail.getLayoutX(), arashPaneDetail.getLayoutY(), arashPaneDetail.getPrefWidth(), arashPaneDetail.getPrefHeight());
        ripples[4] = new JFXRippler(kavehPaneDetail);
        ripples[4].resizeRelocate(kavehPaneDetail.getLayoutX(), kavehPaneDetail.getLayoutY(), kavehPaneDetail.getPrefWidth(), kavehPaneDetail.getPrefHeight());
        ripples[5] = new JFXRippler(zahhakPaneDetail);
        ripples[5].resizeRelocate(zahhakPaneDetail.getLayoutX(), zahhakPaneDetail.getLayoutY(), zahhakPaneDetail.getPrefWidth(), zahhakPaneDetail.getPrefHeight());
        ripples[6] = new JFXRippler(esfandiarPaneDetail);
        ripples[6].resizeRelocate(esfandiarPaneDetail.getLayoutX(), esfandiarPaneDetail.getLayoutY(), esfandiarPaneDetail.getPrefWidth(), esfandiarPaneDetail.getPrefHeight());
        ripples[7] = new JFXRippler(dragonPaneDetail);
        ripples[7].resizeRelocate(dragonPaneDetail.getLayoutX(), dragonPaneDetail.getLayoutY(), dragonPaneDetail.getPrefWidth(), dragonPaneDetail.getPrefHeight());
        ripples[8] = new JFXRippler(legendPaneDetail);
        ripples[8].resizeRelocate(legendPaneDetail.getLayoutX(), legendPaneDetail.getLayoutY(), legendPaneDetail.getPrefWidth(), legendPaneDetail.getPrefHeight());
        ripples[9] = new JFXRippler(whiteGhoulPaneDetail);
        ripples[9].resizeRelocate(whiteGhoulPaneDetail.getLayoutX(), whiteGhoulPaneDetail.getLayoutY(), whiteGhoulPaneDetail.getPrefWidth(), whiteGhoulPaneDetail.getPrefHeight());
        for (JFXRippler ripple : ripples) {
            ripple.setRipplerFill(Paint.valueOf("#000000"));
        }
        anchorPane.getChildren().addAll(ripples);

    }
}
