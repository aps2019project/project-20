package View;

import Controller.SlideShowController;
import Datas.AccountDatas;
import Model.Shop;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class Main extends Application {

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
        //loadPageInNewStage(null, "FXML/FirstPage.fxml");
        loadPageInNewStage(null, "FXML/MainMenu.fxml");
    }

    public static void loadPageInNewStage(Scene prevScene, String FXMLAddress) throws IOException {
        if (prevScene != null) {
            prevScene.getWindow().hide();
        }
        if(!stackPane.getChildren().isEmpty()){
            stackPane.getChildren().removeAll();
        }
        Stage stage = new Stage();
        AnchorPane root = FXMLLoader.load(Main.class.getResource(FXMLAddress));
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("file:images/codex/chapter" + (new Random().nextInt(24) + 1) + "_background@2x.jpg"));
        imageView.setLayoutX(0);imageView.setLayoutY(0);imageView.setFitHeight(1080);imageView.setFitWidth(1930);
        stackPane.getChildren().addAll(imageView,root);

        JFXDecorator jfxDecorator = new JFXDecorator(stage, stackPane);
        jfxDecorator.getStylesheets().add(Main.class.getResource("CSS/Screens.Css").toExternalForm());
        Scene scene = new Scene(jfxDecorator);
        stage.setTitle("Duelyst");
        stage.getIcons().add(new Image("file:images/icon.png"));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        new SlideShowController().start();
    }

    public static void loadPageOnStackPane(Scene prevScene, String FXMLAddress, String type) throws IOException {
        Pane root = FXMLLoader.load(Main.class.getResource(FXMLAddress));
        stackPane.getChildren().add(root);
        slideAnimation(prevScene, root, type);

    }

    public static void slideAnimation(Scene prevScene, Pane root, String type) {
        Timeline timeline = new Timeline();
        KeyValue kv = null;
        if (type.equals("rtl")) {
            root.translateXProperty().set(prevScene.getWidth());
            kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        } else
        if (type.equals("ltr")) {
            root.translateXProperty().set(-prevScene.getWidth());
            kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        }
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stackPane.getChildren().remove(prevScene.getRoot());
            }
        });
        timeline.play();
    }

    public static StackPane getStackPane() {
        return stackPane;
    }

    public static void setStackPane(StackPane stackPane) {
        Main.stackPane = stackPane;
    }

    public static void showOneButtonDialog(StackPane parentContainer , String title , String body , String imageIcon){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        Text header = new Text(title);
        Text footer = new Text(body);
        header.setStyle("-fx-text-fill: #ff0000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        footer.setStyle("-fx-text-fill: #000000;  -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        dialogLayout.setHeading(header);
        dialogLayout.setBody(footer);
        dialogLayout.setStyle("-fx-background-color: #ffee00; -fx-text-fill: #ffffff");
        JFXDialog dialog = new JFXDialog(parentContainer,dialogLayout,JFXDialog.DialogTransition.CENTER,true);
        dialog.setStyle("-fx-background-image: url("+imageIcon+")");
        JFXButton button = new JFXButton("Okey");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-family: 'Microsoft Tai Le'; -fx-font-weight:bold;");
        button.setOnAction(event -> dialog.close());
        dialogLayout.setActions(button);
        dialog.show();
    }

    public static void setTextFieldRequiredFieldValidator(JFXTextField textField,String inValidRegex,String message){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        ImageView imageView =new ImageView(new Image("file:images/iconfinder_Error.png"));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        validator.setIcon(imageView);
        validator.setMessage(message);
        validator.setStyle("-fx-background-color: #ff0000");
        textField.setValidators(validator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if(textField.getText().matches(inValidRegex)){
                    textField.setText("");
                }
                textField.validate();
            }
        });
    }

    public static double computeSnapshotSimilarity(final Image image1, final Image image2) {
        final int width = (int) image1.getWidth();
        final int height = (int) image1.getHeight();
        final PixelReader reader1 = image1.getPixelReader();
        final PixelReader reader2 = image2.getPixelReader();

        final double nbNonSimilarPixels = IntStream.range(0, width).parallel().
                mapToLong(i -> IntStream.range(0, height).parallel().filter(j -> reader1.getArgb(i, j) != reader2.getArgb(i, j)).count()).sum();

        return 100d - nbNonSimilarPixels / (width * height) * 100d;
    }

    public static ImageView getStackPaneImageView (){
        for (Node stackPaneChild : stackPane.getChildren()) {
            if(stackPaneChild instanceof ImageView){
                return (ImageView)stackPaneChild;
            }
        }
        return null;
    }
}
