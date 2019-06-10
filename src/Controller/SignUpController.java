package Controller;

import View.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public JFXTextField SignUpUserName;
    public JFXTextField signUpPassword;
    public JFXButton signUp;
    public JFXButton back;

    public void setSignUp(){
        if(signUpPassword.getText().equals("") || SignUpUserName.getText().equals("")){
            Main.showOneButtonDialog((StackPane)back.getParent().getParent(),"SignInError","some of fields are empty.","file:images/error.png");
        }
        //todo
    }

    public void setBack() throws IOException {
        Main.loadPageOnStackPane(back.getScene(),"FXML/FirstPage.fxml","rtl");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.setTextFieldRequiredFieldValidator(SignUpUserName,"\\s+","");
        Main.setTextFieldRequiredFieldValidator(signUpPassword,"(.{0,8})|(\\s+)","atLeast 8 Characters");
    }
}
