package Model;

public class BufferOfSpells {
    private int lifeTime;
    private boolean lifeEndless;
    private Type type;
    private boolean isUsed;
    private int value;
    private int turnCountdownUntilActivation;

    public enum Type{
        POWER_BUFF_ATTACK,POWER_BUFF_HEALTH,HOLY_BUFF,DISARM_BUFF,STUN_BUFF,WEAKNESS_BUFF_HEALTH,WEAKNESS_BUFF_POWER,POISON_BUFF
    }

    public BufferOfSpells(int lifeTime, Type type) {
        this.lifeTime = lifeTime;
        this.lifeEndless = false;
        this.type = type;
        this.value = 1;
    }

    public BufferOfSpells(int lifeTime, Type type, int value) {
        this(lifeTime, type);
        this.value = value;
    }

    public BufferOfSpells(Type type) {
        this.lifeTime = -1;
        this.lifeEndless = true;
        this.type = type;
        this.value = 1;
    }

    public BufferOfSpells(Type type, int value) {
        this(type);
        this.value = value;
    }

    public BufferOfSpells(int lifeTime,Type type,int value,int turnCountdownUntilActivation) {
        this(lifeTime, type);
        this.turnCountdownUntilActivation = turnCountdownUntilActivation;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public boolean isLifeEndless() {
        return lifeEndless;
    }

    public void setLifeEndless(boolean lifeEndless) {
        this.lifeEndless = lifeEndless;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
