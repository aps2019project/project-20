package Controller;

import Model.*;
import Presenter.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientBattleGroundController extends BattleGroundController implements Initializable, ScreenManager , DialogThrowable, Animationable, DateGetter {
    public ClientBattleGroundController(Battle battle, int clientIndex) {
        super(battle);
        this.clientIndex = clientIndex;
    }
}