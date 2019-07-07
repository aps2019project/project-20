
package Model;

import Controller.AIController;

public class AI extends Account {

    public AI(String userName, String password) {
        super(userName, password);
    }

    public void handleAIEvent(Account player, Battle battle) {
        AIController aiController = new AIController(battle);
        aiController.handleAIEvent(battle);
    }

}