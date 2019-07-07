package Controller;

import Presenter.ScreenManager;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
