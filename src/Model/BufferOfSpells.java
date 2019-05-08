package Model;

import Model.Status.*;

import static Model.Status.COUNTER_ATTACK;
import static Model.Status.END_TURN;

public class BufferOfSpells {
    private int lifeTime;
    private boolean lifeEndless;
    private boolean isContinuous;
    private Type type;
    private boolean enabled = true;
    private int value;
    private int turnCountdownUntilActivation = 0;

    public enum Type {
        POWER_BUFF_ATTACK,POWER_BUFF_HEALTH,HOLY_BUFF,DISARM_BUFF,STUN_BUFF,WEAKNESS_BUFF_HEALTH, WEAKNESS_BUFF_ATTACK,
        POISON_BUFF, MANA_BUFF;
    }

    public BufferOfSpells(int lifeTime, Type type) {
        this.lifeTime = lifeTime;
        this.lifeEndless = false;
        this.isContinuous = false;
        this.type = type;
        this.value = 1;
    }

    public BufferOfSpells(int lifeTime, Type type, int value) {
        this(lifeTime, type);
        this.isContinuous = false;
        this.value = value;
    }

    public BufferOfSpells(int lifeTime, Type type, int value, int turnCountdownUntilActivation) {
        this(lifeTime, type);
        this.turnCountdownUntilActivation = turnCountdownUntilActivation;
        this.value = value;
    }

    public BufferOfSpells(Type type, boolean isContinuous) {
        this.lifeTime = -1;
        this.lifeEndless = true;
        this.isContinuous = isContinuous;
        this.type = type;
        this.value = 1;
    }

    public BufferOfSpells(Type type, int value, boolean isContinuous) {
        this(type, isContinuous);
        this.value = value;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void changeLifeTime(int valueOfChange) {
        this.lifeTime += valueOfChange;
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

    public int getTurnCountdownUntilActivation() {
        return turnCountdownUntilActivation;
    }

    public void changeTurnCountdownUntilActivation(int valueOfChange) {
        this.turnCountdownUntilActivation += valueOfChange;
    }

    public void applyBufferOfSpells(Warrior warrior, Status status){
        if (turnCountdownUntilActivation == 0) {
            switch (type){
                case HOLY_BUFF:
                    if (status == COUNTER_ATTACK) {
                        warrior.changeHP(value);
                        break;
                    }
                case STUN_BUFF:
                    if (status == END_TURN && !warrior.getName().equals("giv")) {
                        warrior.setStun(true);
                        break;
                    }
                case DISARM_BUFF:
                    if (status == END_TURN && !warrior.getName().equals("wildBoar") && !warrior.getName().equals("giv")) {
                        warrior.setDisarm(true);
                        break;
                    }
                case POISON_BUFF:
                    if (status == END_TURN && !warrior.getName().equals("piran") && !warrior.getName().equals("giv")) {
                        warrior.changeHP(-1);
                        break;
                    }
                case POWER_BUFF_ATTACK:
                    if (status == END_TURN) {
                        warrior.changeAP(value);
                        turnCountdownUntilActivation = -1;
                        break;
                    }
                case POWER_BUFF_HEALTH:
                    if (status == END_TURN) {
                        warrior.changeHP(value);
                        turnCountdownUntilActivation = -1;
                        break;
                    }
                case WEAKNESS_BUFF_ATTACK:
                    if (status == END_TURN && !warrior.getName().equals("giv")) {
                        warrior.changeAP(-1 * value);
                        turnCountdownUntilActivation = -1;
                        break;
                    }
                case WEAKNESS_BUFF_HEALTH:
                    if (status == END_TURN && !warrior.getName().equals("giv")) {
                        warrior.changeHP(-1 * value);
                        turnCountdownUntilActivation = -1;
                    }
            }
        }
    }

    public void deactivate(Warrior warrior) {
        switch (type) {
            case DISARM_BUFF:
                warrior.setDisarm(false);
                break;
            case STUN_BUFF:
                warrior.setStun(false);
                break;
            case WEAKNESS_BUFF_HEALTH:
                warrior.changeHP(value);
                break;
            case WEAKNESS_BUFF_ATTACK:
                warrior.changeAP(value);
                break;
            case POWER_BUFF_HEALTH:
                warrior.changeHP(-1 * value);
                break;
            case POWER_BUFF_ATTACK:
                warrior.changeHP(-1 * value);
        }
    }
}
