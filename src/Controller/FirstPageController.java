package Controller;

import View.Main;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.jfoenix.controls.JFXPopup.*;

public class FirstPageController implements Initializable {

    public JFXButton login;
    public JFXButton signup;

    public void setLogin() throws IOException {
        Main.loadPageOnStackPane(login.getScene(),"FXML/SignIn.fxml","rtl");
    }

    public void setSignup() throws IOException {
        Main.loadPageOnStackPane(login.getScene(),"FXML/SignUp.fxml","ltr");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
