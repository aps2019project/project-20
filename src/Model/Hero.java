package Model;

import Exceptions.HeroNotFoundException;

import java.io.IOException;

public class Hero extends Warrior{
    private int cooldown = -1;
    private int specialPowerCountdown = 0;

    public Hero(String name, int price, int ID, int range, int AP, int HP, boolean doesHaveAction, AttackType attackType) {
        super(name, name, price, ID, AP, HP,0, range,  doesHaveAction, attackType);
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, int MP, int cooldown, AttackType attackType) {
        this(name, price, ID, range, AP, HP, true, attackType);
        this.setMP(MP);
        this.cooldown = cooldown;
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, int MP, int cooldown, AttackType attackType, BufferOfSpells buff, boolean isTargetFriend) {
        super(name, name, price, ID, MP, true, buff, range, HP, AP, attackType, isTargetFriend);
        this.cooldown = cooldown;
    }

    public Hero(String name, int price, int ID, int range, int AP, int HP, AttackType attackType) {
        this(name, price, ID, range, AP, HP, false, attackType);
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getSpecialPowerCountdown() {
        return specialPowerCountdown;
    }

    public void changeSpecialPowerCountdown(int value) {
        this.specialPowerCountdown += value;
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
