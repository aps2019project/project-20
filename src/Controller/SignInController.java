package Controller;

import View.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {


    public JFXTextField LoginUserName;
    public JFXTextField loginPassword;
    public JFXButton signIn;
    public JFXButton back;

    public void setSignIn(){
       if(LoginUserName.getText().equals("") || loginPassword.getText().equals("")){
           Main.showOneButtonDialog((StackPane)back.getParent().getParent(),"SignInError","some of fields are empty.","file:images/error.png");
       }
       //todo
    }

    public void setBack() throws IOException {
        Main.loadPageOnStackPane(back.getScene(),"FXML/FirstPage.fxml","ltr");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Main.setTextFieldRequiredFieldValidator(LoginUserName,"\\s+","");
       Main.setTextFieldRequiredFieldValidator(loginPassword,"\\s+","");
    }
}
