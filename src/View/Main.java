package View;

import Datas.AccountDatas;
import Model.Shop;
import Presenter.ScreenManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements ScreenManager {

    private static AccountDatas accountDatas = new AccountDatas();
    private static Shop shop = new Shop();
    private static StackPane stackPane = new StackPane();

    public static void main(String[] args) {

        accountDatas.SetAccount();
        shop.fillShopData();

        launch(args);

    }

    public static AccountDatas getAccountDatas() {
        return accountDatas;
    }

    public static Shop getShop() {
        return shop;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadPageInNewStage(null, "FXML/FirstPage.fxml", false);
      //  loadPageInNewStage(null, "FXML/BeforeBattleMenu4.fxml",true);
    }


    public static StackPane getStackPane() {
        return stackPane;
    }

    public static void setStackPane(StackPane stackPane) {
        Main.stackPane = stackPane;
    }

    public static ImageView getStackPaneBackGroundImage() {
        for (Node stackPaneChild : stackPane.getChildren()) {
            if (stackPaneChild instanceof ImageView) {
                return (ImageView) stackPaneChild;
            }
        }
        return null;
    }

    public static Pane getPaneOfMainStackPane() {
        for (Node child : stackPane.getChildren()) {
            if (child instanceof Pane) {
                return (Pane) child;
            }
        }
        return null;
    }
}
