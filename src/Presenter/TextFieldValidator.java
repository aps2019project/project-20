package Presenter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface TextFieldValidator {

    default void setTextFieldRequiredFieldValidator(JFXTextField textField, String inValidRegex, String message) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        ImageView imageView = new ImageView(new Image("file:images/iconfinder_Error.png"));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        validator.setIcon(imageView);
        validator.setMessage(message);
        validator.setStyle("-fx-background-color: #ff0000");
        textField.setValidators(validator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (textField.getText().matches(inValidRegex)) {
                    textField.setText("");
                }
                textField.validate();
            }
        });
    }

    default void setPasswordFieldRequiredFieldValidator(JFXPasswordField passsField, String inValidRegex, String message) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        ImageView imageView = new ImageView(new Image("file:images/iconfinder_Error.png"));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        validator.setIcon(imageView);
        validator.setMessage(message);
        validator.setStyle("-fx-background-color: #ff0000");
        passsField.setValidators(validator);
        passsField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (passsField.getText().matches(inValidRegex)) {
                    passsField.setText("");
                }
                passsField.validate();
            }
        });
    }
}
