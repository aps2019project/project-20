package Model;

import Exceptions.HeroNotFoundException;

public class Hero extends Warrior{
    // extending 10 heroes
    // private Spell specialPower;
    private int coolDown;

    public Hero(String name, int price, int ID, int range, int AP, int HP, boolean doesHaveAction, AttackType attackType) {
        super(name, name, price, ID, AP, HP, range, attackType, doesHaveAction);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, int MP, int coolDown, AttackType attackType) {
        this(name, price, ID, range, AP, HP, true, attackType);
        this.setMP(MP);
        this.setCoolDown(coolDown);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, AttackType attackType) {
        this(name, price, ID, range, AP, HP, false, attackType);
    }
//
//    public Spell getSpecialPower() {
//        return specialPower;
//    }
//
//    public void setSpecialPower(Spell specialPower) {
//        this.specialPower = specialPower;
//    }
    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public static Hero searchHeroForCustomGame(String heroName){
        for (Asset asset : Shop.getAssets()) {
            if(asset instanceof Hero && asset.getName().compareTo(heroName)==0){
                return (Hero)asset.clone();
            }
        }
        throw new HeroNotFoundException();
    }
}
