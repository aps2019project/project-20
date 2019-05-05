package Model;

import java.util.ArrayList;

public class Card extends Asset {
    private int MP;
    private ArrayList<BufferOfSpells> bufferEffected = new ArrayList<>();

    public Card(){}

    public Card(String name, String desc, int price, int ID, int MP, boolean doesHaveAction) {
        super(name, desc, price, ID, doesHaveAction);
        this.MP = MP;
    }

    public ArrayList<BufferOfSpells> getBufferEffected() {
        return bufferEffected;
    }

    public void setBufferEffected(ArrayList<BufferOfSpells> bufferEffected) {
        this.bufferEffected = bufferEffected;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

}