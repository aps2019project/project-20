package Model;

public class Card extends Asset {
    private int MP;
    private int xInGround;
    private int yInGround;

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
