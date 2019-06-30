package Presenter;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public interface Animationable  {

    default Timeline slideAnimation(Scene prevScene, double millis, Pane root, String type) {
        Timeline timeline = new Timeline();
        KeyValue kv = null;
        if (type.equals("rtl")) {
            root.translateXProperty().set(prevScene.getWidth());
            kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        } else if (type.equals("ltr")) {
            root.translateXProperty().set(-prevScene.getWidth());
            kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        }
        KeyFrame kf = new KeyFrame(Duration.millis(millis), kv);
        timeline.getKeyFrames().add(kf);
        return timeline;
    }

    default FadeTransition nodeFadeAnimation(Node node, int millis, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setNode(node);
        fadeTransition.setDuration(new Duration(millis));
        return fadeTransition;
    }

}
