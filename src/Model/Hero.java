package Model;

public class Hero extends Warrior{
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
    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }
}
