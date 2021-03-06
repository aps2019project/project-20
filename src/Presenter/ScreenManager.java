package Presenter;

import Controller.BattleGroundController;
import Controller.ClientBattleGroundController;
import Controller.SlideShowThread;
import Model.Battle;
import Client.Client;
import com.jfoenix.controls.JFXDecorator;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;


public interface ScreenManager extends Animationable {

    default void loadPageInNewStage(Scene prevScene, String FXMLAddress, boolean isInFullScreen) throws IOException {
        if (prevScene != null) {
            prevScene.getWindow().hide();
        }
        Client.setStackPane(new StackPane());
        Stage stage = new Stage();
        JFXDecorator jfxDecorator = new JFXDecorator(stage, Client.getStackPane());
        jfxDecorator.getStylesheets().add(Client.class.getResource("../View/CSS/Screens.Css").toExternalForm());
        Scene scene = new Scene(jfxDecorator);
        AnchorPane root = FXMLLoader.load(Client.class.getResource(FXMLAddress));
        stage.setScene(scene);
        if (isInFullScreen) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image("file:images/codex/chapter" + (new Random().nextInt(24) + 1) + "_background@2x.jpg"));
            imageView.setLayoutX(0);imageView.setLayoutY(0);imageView.setFitHeight(1080);imageView.setFitWidth(1930);
            Client.getStackPane().getChildren().add(imageView);
            SlideShowThread slideShower = new SlideShowThread(Client.getStackPaneBackGroundImage());
            stage.getScene().getWindow().setOnHidden(event -> {
                slideShower.finalize();
            });
            slideShower.start();
        }
        Client.getStackPane().getChildren().add(root);
        stage.setTitle("Duelyst");
        stage.getIcons().add(new Image("file:images/icon.png"));
        if (isInFullScreen) {
            stage.setFullScreen(true);
        }
        stage.show();


    }

    default void  loadPageOnStackPane(Parent prevPane, String FXMLAddress, String type) throws IOException {
        Pane lastPane = Client.getPaneOfMainStackPane();
        AnchorPane root = FXMLLoader.load(Client.class.getResource(FXMLAddress));
        Client.getStackPane().getChildren().add(root);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(slideAnimation(prevPane.getScene(), 200, root, type), nodeFadeAnimation(prevPane, 200, 1, 0));
        parallelTransition.setOnFinished(t -> Client.getStackPane().getChildren().remove(lastPane));
        parallelTransition.play();
    }

    default void startNewGame(Battle battle, int clientIndex) {
        Client.getStackPane().getChildren().remove(Client.getPaneOfMainStackPane());
        FXMLLoader loader = new FXMLLoader();
        BattleGroundController battleGroundController = null;
        if (clientIndex == -1) //No network game.
            battleGroundController = new BattleGroundController(battle);
        else if (clientIndex == 0 || clientIndex == 1) {
            battleGroundController = new ClientBattleGroundController(battle, clientIndex);
            Client.setBattleGroundController((ClientBattleGroundController) battleGroundController);
        }
        loader.setController(battleGroundController);
        AnchorPane root = new AnchorPane();
        try {
          root = loader.load(getClass().getResource("../View/FXML/BattleGround.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client.getStackPane().getChildren().add(root);
    }

    default void startNewGameInThread(Battle battle, int clientIndex) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                startNewGame(battle, clientIndex);
            }
        });
    }

    default void openPageOnNewStageInThread(Scene prevScene, String FXMLAddress, boolean isInFullScreen){
        Platform.runLater(() -> {
            try {
                loadPageInNewStage(prevScene,FXMLAddress,isInFullScreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    default void loadPageOnStackPaneInThread(Parent prevPane, String FXMLAddress, String type){
        Platform.runLater(() -> {
            try {
                loadPageOnStackPane(prevPane,FXMLAddress,type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
