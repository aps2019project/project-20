package Model;

public class Card extends Asset {
    private int MP;
    private int xInGround;
    private int yInGround;

    private boolean isPoisonBuff = false;
    private boolean isStunBuff = false;
    private boolean isWeaknessBuff = false;
    private boolean isDisarmBuff = false;
    private boolean isHolyBuff = false;
    private boolean isPowerBuff = false;

    public boolean isWeaknessBuff() {
        return isWeaknessBuff;
    }

    public void setWeaknessBuff(boolean weaknessBuff) {
        isWeaknessBuff = weaknessBuff;
    }

    public boolean isHolyBuff() {
        return isHolyBuff;
    }

    public void setHolyBuff(boolean holyBuff) {
        isHolyBuff = holyBuff;
    }

    public boolean isPowerBuff() {
        return isPowerBuff;
    }

    public void setPowerBuff(boolean powerBuff) {
        isPowerBuff = powerBuff;
    }

    public boolean isDisarm() {
        return isDisarm;
    }

    public void setDisarm(boolean disarm) {
        isDisarm = disarm;
    }

    public boolean isPoisonBuff() {
        return isPoisonBuff;
    }

    public void setPoisonBuff(boolean poisonBuff) {
        isPoisonBuff = poisonBuff;
    }

    public boolean isStunBuff() {
        return isStunBuff;
    }

    public void setStunBuff(boolean stunBuff) {
        isStunBuff = stunBuff;
    }

    public boolean isDisarmBuff() {
        return isDisarmBuff;
    }

    public void setDisarmBuff(boolean disarmBuff) {
        isDisarmBuff = disarmBuff;
    }

    private boolean isDisarm =false;

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getXInGround() {
        return xInGround;
    }

    public void setXInGround(int xInGround) {
        this.xInGround = xInGround;
    }

    public int getYInGround() {
        return yInGround;
    }

    public void setYInGround(int yInGround) {
        this.yInGround = yInGround;
    }
}
