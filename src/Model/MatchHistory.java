package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatchHistory {
    public enum Result {WIN, LOOSE, DRAW}

    private long time;
    private Result result;
    private String opponentName;

    public MatchHistory(long time, Result result, String opponentName) {
        this.time = time;
        this.result = result;
        this.opponentName = opponentName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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
}
