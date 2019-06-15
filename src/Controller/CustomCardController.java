package Controller;
//import

import Model.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

// Spells ID: starts from 4000+20
// Heroes ID: starts from 2000+10
// Items ID: starts from 1000+20
// Minions ID: starts from 3000+40
// attack Type : MELEE, RANGED, HYBRID
// target Type : ENEMY, PLAYER, CELLS, WHOLE_OF_GROUND


public class CustomCardController {
    public ImageView backgroundImage;
    public TextArea nameText;
    public TextArea typeText;
    public TextArea APText;
    public TextArea targetText;
    public TextArea buffsText;
    public TextArea HPText;
    public TextArea attackTypeText;
    public TextArea specialPowerText;
    public TextArea rangeText;
    public TextArea specialPowerActivationText;
    public TextArea specialPowerCoolDownText;
    public TextArea costText;
    public ImageView createCardButton;

    public Account account;

    public void CreateCard() {

        final int DEFAULT_MANA = 3;
        int minionID = 3040;
        int heroID = 2010;
        int spellID = 4020;

        int price = Integer.valueOf(costText.getText());
        int range = Integer.valueOf(rangeText.getText());
        int AP = Integer.valueOf(APText.getText());
        int HP = Integer.valueOf(HPText.getText());
        int coolDown = Integer.valueOf(specialPowerCoolDownText.getText());

        AttackType attackType = null;
        if (attackTypeText.getText().equals("melee")) {
            attackType = AttackType.MELEE;
        } else if (attackTypeText.getText().equals("ranged")) {
            attackType = AttackType.RANGED;
        } else if (attackTypeText.getText().equals("hybrid")) {
            attackType = AttackType.HYBRID;
        }

        Spell.TargetType targetType = null;
        if (targetText.getText().equals("enemy")) {
            targetType = Spell.TargetType.ENEMY;
        } else if (targetText.getText().equals("player")) {
            targetType = Spell.TargetType.PLAYER;
        } else if (targetText.getText().equals("cells")) {
            targetType = Spell.TargetType.CELLS;
        }
        //else if (targetText.getText().equals("whole of ground"))
        else {
            targetType = Spell.TargetType.WHOLE_OF_GROUND;
        }


        if (typeText.getText().equals("minion")) {

            Minion minion = new Minion(nameText.getText(), nameText.getText(), price, minionID, range, AP, HP, DEFAULT_MANA, attackType);
            minionID++;
            Shop.getAssets().add(minion);

            account.getCollection().getAssets().add(minion);
        }
        if (typeText.getText().equals("hero")) {
            Hero hero = new Hero(nameText.getText(), price, heroID, range, AP, HP, DEFAULT_MANA, coolDown, attackType);
            heroID++;
            Shop.getAssets().add(hero);

            account.getCollection().getAssets().add(hero);

        }
        if (typeText.getText().equals("spell")) {
            Spell spell = new Spell(nameText.getText(), nameText.getText(), price, spellID, DEFAULT_MANA, targetType);
            spellID++;
            Shop.getAssets().add(spell);

            account.getCollection().getAssets().add(spell);
        }
    }
}
