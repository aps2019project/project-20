package View;

import Chat.Chat;
import javafx.scene.Parent;


public abstract class BaseView {

    private static Chat mainApp = null;

    public abstract Parent getView();

    public static Chat getMainApp() {
        if (mainApp == null) {
            try {
                throw new Exception("No reference to mainApp in BaseView.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mainApp;
    }

    public static void setMainApp(Chat mainApp) {
        BaseView.mainApp = mainApp;
    }

}
