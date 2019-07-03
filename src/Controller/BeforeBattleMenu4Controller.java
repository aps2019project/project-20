package Controller;

import Model.Battle;
import Presenter.Animationable;
import Presenter.ScreenManager;
import Presenter.TextFieldValidator;
import View.Main;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BeforeBattleMenu4Controller implements Initializable, ScreenManager, Animationable, TextFieldValidator {
    public ImageView back;
    public ImageView mode1Button;
    public Pane mode1PaneDetail;
    public ImageView mode2Button;
    public Pane mode2PaneDetail;
    public AnchorPane anchorPane;
    public Pane mode3PaneDetail;


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


    public void setMode1ButtonOnMouseEntered(){
        nodeFadeAnimation(mode1PaneDetail,200,0,1).play();
    }

    public void setMode1ButtonOnMouseExited(){
        nodeFadeAnimation(mode1PaneDetail,200,1,0).play();
    }

    public void setMode1ButtonOnMouseReleased() throws IOException {
        loadPageOnStackPane(back.getParent(),"FXML/AIHeroChooseMenu.fxml","rtl");
    }

    public void setMode2ButtonOnMouseEntered(){
        nodeFadeAnimation(mode2PaneDetail,200,0,1).play();
    }

    public void setMode2ButtonOnMouseExited(){
        nodeFadeAnimation(mode2PaneDetail,200,1,0).play();
    }

    public void setMode2ButtonOnMouseReleased() {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setPrefHeight(60);
        Text header = new Text("Set Number Of Flags");
        header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        dialogLayout.setHeading(header);
        dialogLayout.setStyle("-fx-background-color: #acf5ff; -fx-text-fill: #ffffff");
        JFXDialog dialog = new JFXDialog(Main.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
        JFXTextField textField = new JFXTextField();
        textField.setLabelFloat(true);;
        textField.setPromptText("Number Of Flags");
        setTextFieldRequiredFieldValidator(textField, "\\D+", "Invalid Input");
        JFXButton startButton = new JFXButton("start");
        startButton.setButtonType(JFXButton.ButtonType.RAISED);
        startButton.setStyle("-fx-background-color: #37b400; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        startButton.setOnAction(event -> {
            if(!textField.getText().equals("") && Integer.parseInt(textField.getText())<=20 && Integer.parseInt(textField.getText())>=1) {
                startNewGame(anchorPane,Battle.soloCustomFlagModeConstructor(Integer.parseInt(textField.getText())));
                dialog.close();
            }
        });

        JFXButton CancleButton = new JFXButton("cancel");
        CancleButton.setButtonType(JFXButton.ButtonType.RAISED);
        CancleButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        CancleButton.setOnAction(event -> dialog.close());
        dialog.setOnDialogClosed(event -> Main.getStackPane().getChildren().remove(dialog));
        dialogLayout.setActions(textField,startButton,CancleButton);
        dialog.show();
    }

    public void setMode3ButtonOnMouseEntered(){
        nodeFadeAnimation(mode3PaneDetail,200,0,1).play();
    }

    public void setMode3ButtonOnMouseExited(){
        nodeFadeAnimation(mode3PaneDetail,200,1,0).play();
    }

    public void setMode3ButtonOnMouseReleased() throws IOException {
        startNewGame(anchorPane,Battle.soloCustomFlagModeConstructor(0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mode1PaneDetail.setOpacity(0);
        mode2PaneDetail.setOpacity(0);
        mode3PaneDetail.setOpacity(0);

        JFXRippler ripple1 = new JFXRippler(mode1PaneDetail);
        JFXRippler ripple2 = new JFXRippler(mode2PaneDetail);
        JFXRippler ripple3 = new JFXRippler(mode3PaneDetail);
        ripple1.resizeRelocate(mode1PaneDetail.getLayoutX(), mode1PaneDetail.getLayoutY(), mode1PaneDetail.getPrefWidth(), mode1PaneDetail.getPrefHeight());
        ripple2.resizeRelocate(mode2PaneDetail.getLayoutX(), mode2PaneDetail.getLayoutY(), mode2PaneDetail.getPrefWidth(), mode2PaneDetail.getPrefHeight());
        ripple3.resizeRelocate(mode3PaneDetail.getLayoutX(), mode3PaneDetail.getLayoutY(), mode3PaneDetail.getPrefWidth(), mode3PaneDetail.getPrefHeight());
        ripple1.setRipplerFill(Paint.valueOf("#000000"));
        ripple2.setRipplerFill(Paint.valueOf("#000000"));
        ripple3.setRipplerFill(Paint.valueOf("#000000"));
        anchorPane.getChildren().addAll(ripple1,ripple2,ripple3);

    }
}
