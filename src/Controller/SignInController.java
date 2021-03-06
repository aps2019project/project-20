package Controller;

import Presenter.AccountManageable;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import Presenter.TextFieldValidator;
import Client.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable, AccountManageable, ScreenManager, DialogThrowable, TextFieldValidator {

    public JFXTextField LoginUserName;
    public JFXPasswordField loginPassword;
    public JFXButton signIn;
    public JFXButton back;
    public MediaView mediaPlayer;

    public void setSignIn() throws IOException {
        if (LoginUserName.getText().equals("") || loginPassword.getText().equals("")) {
            showOneButtonErrorDialog("SignIn Error", "some of fields are empty.");
            return;
        }
        try {
            Client.connectToServer();
        } catch (Exception e) {
            showOneButtonErrorDialog("Login Error", "You are disconnected!!!");
            return;
        }
        Client.getWriter().println(("logIn " + LoginUserName.getText() + " " + loginPassword.getText()));

    }

    public void setBack() throws IOException {
        loadPageOnStackPane(back.getParent(), "../View/FXML/FirstPage.fxml", "ltr");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextFieldRequiredFieldValidator(LoginUserName, "\\s+", "");
        setPasswordFieldRequiredFieldValidator(loginPassword, "\\s+", "");
    }
}
