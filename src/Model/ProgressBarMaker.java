package Model;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.concurrent.Service;


public class ProgressBarMaker extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        primaryStage.setTitle("progressBar");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 250);

        final Service thread = new Service<Integer>() {
            @Override
            public Task createTask() {
                return new Task<Integer>() {
                    @Override
                    public Integer call() throws InterruptedException {
                        int i;
                        for (i = 0; i < 1000; i++) {
                            System.out.println("i = " + i);
                            updateProgress(i, 1000);
                            Thread.sleep(10);
                        }
                        return i;
                    }
                };
            }
        };

        ProgressBar progressBar = new ProgressBar();
        progressBar.setMinSize(400, 60);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMinSize(60, 60);


        progressBar.progressProperty().bind(thread.progressProperty());
        progressIndicator.progressProperty().bind(thread.progressProperty());


        HBox hBox = new HBox(progressBar, progressIndicator);
        hBox.setPadding(new Insets(10, 20, 10, 20));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        Button startBtn = new Button("Start");
        startBtn.setMinSize(200, 60);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setMinSize(200, 60);
        cancelBtn.setDisable(true);


        startBtn.setOnAction(e -> {
            thread.restart();
            startBtn.setText("Restart");
            cancelBtn.setText("Cancel");
            cancelBtn.setDisable(false);
        });

        cancelBtn.setOnAction(e->{
            if(thread.isRunning()){
                thread.cancel();
                cancelBtn.setText("Reset");
            }else if(cancelBtn.getText().equals("Reset")){
                thread.reset();
                cancelBtn.setDisable(true);
                cancelBtn.setText("Cancel");
                startBtn.setText("Start");
            }
        });


        HBox hbButtons = new HBox(startBtn,cancelBtn , progressBar , progressIndicator );
        hbButtons.setPadding(new Insets(30,20,10,20));

        root.getChildren().add(hbButtons);

        scene.setRoot(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
