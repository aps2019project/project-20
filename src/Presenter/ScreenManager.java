package Presenter;

import Controller.BattleGroundController;
import Controller.SlideShowThread;
import Model.Battle;
import View.Main;
import com.jfoenix.controls.JFXDecorator;
import javafx.animation.ParallelTransition;
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
        Main.setStackPane(new StackPane());
        Stage stage = new Stage();
        JFXDecorator jfxDecorator = new JFXDecorator(stage, Main.getStackPane());
        jfxDecorator.getStylesheets().add(Main.class.getResource("CSS/Screens.Css").toExternalForm());
        Scene scene = new Scene(jfxDecorator);
        AnchorPane root = FXMLLoader.load(Main.class.getResource(FXMLAddress));
        stage.setScene(scene);
        if (isInFullScreen) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image("file:images/codex/chapter" + (new Random().nextInt(24) + 1) + "_background@2x.jpg"));
            imageView.setLayoutX(0);imageView.setLayoutY(0);imageView.setFitHeight(1080);imageView.setFitWidth(1930);
            Main.getStackPane().getChildren().add(imageView);
            SlideShowThread slideShower = new SlideShowThread(Main.getStackPaneBackGroundImage());
            stage.getScene().getWindow().setOnHidden(event -> {
                slideShower.finalize();
            });
            slideShower.start();
        }
        Main.getStackPane().getChildren().add(root);
        stage.setTitle("Duelyst");
        stage.getIcons().add(new Image("file:images/icon.png"));
        if (isInFullScreen) {
            stage.setFullScreen(true);
        }
        stage.show();


    }

    default void  loadPageOnStackPane(Parent prevPane, String FXMLAddress, String type) throws IOException {
        Pane lastPane = Main.getPaneOfMainStackPane();
        AnchorPane root = FXMLLoader.load(Main.class.getResource(FXMLAddress));
        Main.getStackPane().getChildren().add(root);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(slideAnimation(prevPane.getScene(), 200, root, type), nodeFadeAnimation(prevPane, 200, 1, 0));
        parallelTransition.setOnFinished(t -> Main.getStackPane().getChildren().remove(lastPane));
        parallelTransition.play();
    }

    default void startNewGame(Parent prevPane,Battle battle){
        Main.getStackPane().getChildren().remove(prevPane);
        FXMLLoader loader = new FXMLLoader();
        BattleGroundController battleGroundController = new BattleGroundController(battle);
        loader.setController(battleGroundController);
        AnchorPane root = new AnchorPane();
        try {
          root = loader.load(getClass().getResource("../View/FXML/BattleGround.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getStackPane().getChildren().add(root);
    }

}
