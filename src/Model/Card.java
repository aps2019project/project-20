package Model;

import java.util.ArrayList;

public class Card extends Asset {
    private int MP;
    private int xInGround;
    private int yInGround;
    private boolean isPoison = false;
    private boolean isStun = false;

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
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

    private boolean isDisarm =false;

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getxInGround() {
        return xInGround;
    }

    public void setxInGround(int xInGround) {
        this.xInGround = xInGround;
    }

    public int getyInGround() {
        return yInGround;
    }

    public void setyInGround(int yInGround) {
        this.yInGround = yInGround;
    }
}
