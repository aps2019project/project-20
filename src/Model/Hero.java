package Model;

import Exceptions.HeroNotFoundException;

import java.io.IOException;

public class Hero extends Warrior{
    private int coolDown;

    public Hero(String name, int price, int ID, int range, int AP, int HP, boolean doesHaveAction, AttackType attackType) {
        super(name, name, price, ID, AP, HP,0, range,  doesHaveAction,attackType);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, int MP, int coolDown, AttackType attackType) {
        this(name, price, ID, range, AP, HP, true, attackType);
        this.setMP(MP);
        this.setCoolDown(coolDown);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, AttackType attackType) {
        this(name, price, ID, range, AP, HP, false, attackType);
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public static Hero searchHeroForCustomGame(String heroName){
        try {
            for (Asset asset : Asset.getAssetsFromFile()) {
                if(asset instanceof Hero && asset.getName().compareTo(heroName)==0){
                    return (Hero) asset.clone();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HeroNotFoundException();
    }
}
