package View;

import Model.*;
import Presenter.ScreenManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application implements ScreenManager {
    private static StackPane stackPane = new StackPane();

    public static void main(String[] args){
//        save default data
        try {
            Asset.saveCardsToJsonDatabase(null);
            Deck.saveDefaultDecksToJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        loadPageInNewStage(null, "FXML/FirstPage.fxml", false);
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
