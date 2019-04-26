package Model;

public class BufferOfSpells {
    private int lifeTime;
    private boolean lifeEndless;
    private Type type;
    private boolean isUsed;
    private int value;

    public enum Type{
        POWER_BUFF,HOLY_BUFF,DISARM_BUFF,STUN_BUFF,WEAKNESS_BUFF_HEALTH,WEAKNESS_BUFF_POWER,POISON_BUFF
    }

    public BufferOfSpells(int lifeTime,boolean lifeEndless,Type type) {
        this.lifeTime = lifeTime;
        this.lifeEndless = lifeEndless;
        this.type = type;
        this.value = 1;
    }

    public BufferOfSpells(int lifeTime,boolean lifeEndless,Type type,int value) {
        this.lifeTime = lifeTime;
        this.lifeEndless = lifeEndless;
        this.type = type;
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
