package Presenter;

import View.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;

public interface DialogThrowable extends ScreenManager {

    default void showOneButtonErrorDialog(String title, String body) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        Text header = new Text(title);
        Text footer = new Text(body);
        header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        footer.setStyle("-fx-text-fill: #000000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        dialogLayout.setHeading(header);
        dialogLayout.setBody(footer);
        dialogLayout.setStyle("-fx-background-color: #ffee00; -fx-text-fill: #ffffff");
        JFXDialog dialog = new JFXDialog(Main.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
        dialog.setStyle("-fx-background-image: url('file:images/error.png')");
        JFXButton button = new JFXButton("Okey");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        button.setOnAction(event -> dialog.close());
        dialog.setOnDialogClosed(event -> Main.getStackPane().getChildren().remove(dialog));
        dialogLayout.setActions(button);
        dialog.show();
    }

    default void showOneButtonInformationDialog(String title, String body, boolean isShowImage) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        Text header = new Text(title);
        Text footer = new Text(body);
        header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        footer.setStyle("-fx-text-fill: #000000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        dialogLayout.setHeading(header);
        dialogLayout.setBody(footer);
        dialogLayout.setStyle("-fx-background-color: #acf5ff; -fx-text-fill: #ffffff");
        JFXDialog dialog = new JFXDialog(Main.getStackPane(), dialogLayout, JFXDialog.DialogTransition.CENTER, true);
        if(isShowImage) {
            dialog.setStyle("-fx-background-image: url('file:images/information.png')");
        }
        JFXButton button = new JFXButton("Okey");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #37b400; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        button.setOnAction(event -> dialog.close());
        dialog.setOnDialogClosed(event -> Main.getStackPane().getChildren().remove(dialog));
        dialogLayout.setActions(button);
        dialog.show();
    }
}


