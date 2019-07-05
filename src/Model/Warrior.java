package Model;

public class Warrior extends Card {
    private int range;
    private int HP;
    private int AP;
    private boolean isDisarm;
    private boolean isPoison;
    private boolean isStun;
    private boolean isAttackedThisTurn;
    private int lifeTimeChangedAP = 0;
    private int amountOfChangedAP = 0;
    private AttackType attackType;
    private Flag collectedFlag = null;
    private boolean isMovedThisTurn = false;
    private String attackImageAddress;
    private String breathingImageAddress;
    private String deathImageAddress;
    private String idleImageAddress;
    private String runImageAddress;
    private String castLoopImageAddress;

    public Warrior(String name, String desc, int price, int ID, int AP, int HP, int MP, int range, boolean doesHaveAction, AttackType attackType) {
        super(name, desc, price, ID, MP, doesHaveAction);
        this.AP = AP;
        this.HP = HP;
        this.range = range;
        this.attackType = attackType;
        setImageAddresses(name);
    }

    public Warrior(String name, String desc, int price, int ID, int MP, boolean doesHaveAction, BufferOfSpells buff, int range, int HP, int AP, AttackType attackType, boolean isTargetFriend) {
        super(name, desc, price, ID, MP, doesHaveAction, buff, isTargetFriend);
        this.range = range;
        this.HP = HP;
        this.AP = AP;
        this.attackType = attackType;
        setImageAddresses(name);
    }

    public Flag getCollectedFlag() {
        return collectedFlag;
    }

    public void setCollectedFlag(Flag collectedFlag) {
        this.collectedFlag = collectedFlag;
    }

    public int getRange() {
        return range;
    }

    public int getHP() {
        return HP;
    }

    public int getAP() {
        return AP;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void changeAP(int valueOfChange) {
        this.AP += valueOfChange;
    }

    public void changeHP(int valueOfChange) {
        this.HP += valueOfChange;
    }

    public int getAmountOfChangedAP() {
        return amountOfChangedAP;
    }

    public boolean isStun() {
        return isStun;
    }

    public void setStun(boolean stun) {
        isStun = stun;
    }

    public boolean isDisarm() {
        return isDisarm;
    }

    public void setDisarm(boolean disarm) {
        isDisarm = disarm;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public boolean isAttackedThisTurn() {
        return isAttackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.isAttackedThisTurn = attackedThisTurn;
    }

    public void setAmountOfChangedAP(int amountOfChangedAP) {
        this.amountOfChangedAP = amountOfChangedAP;
    }

    public int getLifeTimeChangedAP() {
        return lifeTimeChangedAP;
    }

    public void setLifeTimeChangedAP(int lifeTimeChangedAP) {
        this.lifeTimeChangedAP = lifeTimeChangedAP;
    }

    public boolean isMovedThisTurn() {
        return isMovedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.isMovedThisTurn = movedThisTurn;
    }

    public String getAttackImageAddress() {
        return attackImageAddress;
    }

    public String getBreathingImageAddress() {
        return breathingImageAddress;
    }

    public String getDeathImageAddress() {
        return deathImageAddress;
    }

    public String getIdleImageAddress() {
        return idleImageAddress;
    }

    public String getRunImageAddress() {
        return runImageAddress;
    }

    public String getCastLoopImageAddress() {
        return castLoopImageAddress;
    }

    @Override
    public void setImageAddresses(String name) {
        String subClass;
        if (this instanceof Hero)
            subClass = "hero";
        else
            subClass = "minion";
        attackImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_attack.gif";
        breathingImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_breathing.gif";
        castLoopImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_castloop.gif";
        deathImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_death.gif";
        idleImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_idle.gif";
        runImageAddress = "file:images/cards/" + subClass + "/" + name + "/" + name + "_run.gif";
    }
}