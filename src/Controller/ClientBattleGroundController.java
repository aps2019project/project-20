package Controller;

import Model.*;
import Presenter.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientBattleGroundController extends BattleGroundController implements Initializable, ScreenManager , DialogThrowable, Animationable, DateGetter {
    public Button specialPowerButton0;
    public Button specialPowerButton1;

    public Label successfulSpecialPowerBanner0;
    public Label successfulSpecialPowerBanner1;

    public ImageView deckItem0;
    public ImageView deckItem1;

    public GridPane manaGemsRibbon0;
    public GridPane manaGemsRibbon1;

    public GridPane collectedItemsGrid0;
    public GridPane collectedItemsGrid1;

    public ProgressBar progressbar0;
    public ProgressBar progressbar1;

    public ClientBattleGroundController(Battle battle, int clientIndex) {
        super(battle);
        this.clientIndex = clientIndex;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        initializePairComponentsOfPage();
    }

    private void initializePairComponentsOfPage() {
            specialPowerButton = clientIndex == 0 ? specialPowerButton0 : specialPowerButton1;
            successfulSpecialPowerBanner = clientIndex == 0 ? successfulSpecialPowerBanner0: successfulSpecialPowerBanner1;
            deckItem = clientIndex == 0 ? deckItem0 : deckItem1;
            manaGemsRibbon = clientIndex == 0 ? manaGemsRibbon0 : manaGemsRibbon1;
            collectedItemsGrid = clientIndex == 0 ? collectedItemsGrid0 : collectedItemsGrid1;
            progressbar = clientIndex == 0 ? progressbar0 : progressbar1;
            if (clientIndex == 0) {
                specialPowerButton1.setVisible(false);
                successfulSpecialPowerBanner1.setVisible(false);
                deckItem1.setVisible(false);
                manaGemsRibbon1.setVisible(false);
                collectedItemsGrid1.setVisible(false);
            }
            else if (clientIndex == 1) {
                specialPowerButton0.setVisible(false);
                successfulSpecialPowerBanner0.setVisible(false);
                deckItem0.setVisible(false);
                manaGemsRibbon0.setVisible(false);
                collectedItemsGrid0.setVisible(false);
            }
    }
}