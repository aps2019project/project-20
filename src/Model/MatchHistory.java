package Model;

public class MatchHistory {
    enum Result
    {
        WIN, LOSE, DRAW
    }

    private long time;
    private Result result;
    private String opponentName;

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
