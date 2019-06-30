package Controller;

import Presenter.ScreenManager;
import View.Main;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class FirstPageController implements Initializable, ScreenManager {

    public JFXButton login;
    public JFXButton signup;
    public AnchorPane anchorPane;

    public void setLogin() throws IOException {
        loadPageOnStackPane(anchorPane,"FXML/SignIn.fxml","rtl");
    }

    public void setSignup() throws IOException {
        loadPageOnStackPane(anchorPane,"FXML/SignUp.fxml","ltr");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
