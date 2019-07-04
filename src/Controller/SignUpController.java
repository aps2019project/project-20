package Controller;

import Exceptions.RepeatedUserNameException;
import Presenter.AccountManageable;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import Presenter.TextFieldValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable, AccountManageable, ScreenManager, DialogThrowable, TextFieldValidator {
    public JFXTextField SignUpUserName;
    public JFXPasswordField SignUpPassword;
    public JFXButton signUp;
    public JFXButton back;

    public void setSignUp() throws IOException {
        if(SignUpPassword.getText().equals("") || SignUpUserName.getText().equals("")){
            showOneButtonErrorDialog("SignUp Error","some of fields are empty.");
            return;
        }
        try {
            createAccountPresenter(SignUpUserName.getText(), SignUpPassword.getText());
        }catch (RepeatedUserNameException e){
            showOneButtonErrorDialog("signUp Error","This Account Has Already Been Made!!!");
            return;
        }
        loadPageInNewStage(signUp.getScene(),"FXML/MainMenu.fxml",true);
    }

    public void setBack() throws IOException {
        loadPageOnStackPane(back.getParent(),"FXML/FirstPage.fxml","rtl");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextFieldRequiredFieldValidator(SignUpUserName,"\\s+","");
        setPasswordFieldRequiredFieldValidator(SignUpPassword,"(.{0,8})|(\\s+)","atLeast 8 Characters");


    }
}
