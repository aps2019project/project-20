package Client;
import Model.Account;
import Presenter.CurrentAccount;
import Presenter.DialogThrowable;
import Presenter.ScreenManager;
import View.Client;
import com.gilecode.yagson.YaGson;

import java.io.IOException;
import java.util.Scanner;

public class ClientListener extends Thread implements ScreenManager, DialogThrowable {
    private String DataFromServer;

    public void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void run() {
        try {
            Scanner clientScanner = new Scanner(Client.getClient().getInputStream());
            while (true) {
                if (clientScanner.hasNextLine()) {
                    String serverMessage = clientScanner.nextLine();
                    System.out.println(serverMessage);

                    //Add Listeners
                    if (serverMessage.matches("loginSuccess .+")) {
                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(13), Account.class));
                        openPageOnNewStageInThread(Client.getStackPane().getScene(),"FXML/MainMenu.fxml",true);
                    }
                    if (serverMessage.matches("loginError userNotFound")) {
                        showOneButtonErrorDialogInThread("Login Error","User Not Found!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("loginError wrongPassword")) {
                        showOneButtonErrorDialogInThread("Login Error","Password Is Not Correct!!!");
                        Client.closeConnection();
                        return;
                    }
                    if (serverMessage.matches("signUpSuccess .+")) {
                        CurrentAccount.setCurrentAccount(new YaGson().fromJson(serverMessage.substring(14), Account.class));
                        openPageOnNewStageInThread(Client.getStackPane().getScene(),"FXML/MainMenu.fxml",true);
                    }
                    if (serverMessage.matches("signUpError repeatedAccount")) {
                        showOneButtonErrorDialogInThread("signUp Error","This Account Has Already Been Made!!!");
                        Client.closeConnection();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getDataFromServer() {
        return DataFromServer;
    }

    public void setDataFromServer(String dataFromServer) {
        this.DataFromServer = dataFromServer;
    }

}
