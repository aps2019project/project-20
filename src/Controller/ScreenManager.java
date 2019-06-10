package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenManager extends StackPane {
    private HashMap<String, Node> screens = new HashMap<>();

    public void addScreen(String ID, Node screen) {
        screens.put(ID, screen);
    }

    public void getScreen(String ID) {
        screens.get(ID);
    }

    public void unloadScreen(String ID) {
        screens.remove(ID);
    }

    public void loadScreen(String ID, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Pane loadScreen = loader.load();
            Controllable myScreenController = loader.getController();
            myScreenController.setScreenParent(this);
            addScreen(ID,loadScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScreen(String ID){
        if(!getChildren().isEmpty()){
            getChildren().remove(0);
            getChildren().add(0,screens.get((ID)));

        }else {
            getChildren().add(screens.get(ID));
        }
    }
}
