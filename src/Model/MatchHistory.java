package Model;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatchHistory {
    public enum Result {WIN, LOOSE, DRAW, UNFINISHED}

    private String time;
    private int number;
    private Result result;
    private String opponentName;

    public MatchHistory(String time, Result result, String opponentName) {
        this.time = time;
        this.result = result;
        this.opponentName = opponentName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
