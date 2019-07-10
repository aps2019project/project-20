package Model;

import Controller.ScreenRecordController;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MatchHistory {
    public enum Result {WIN, LOSE, DRAW}

    private String time;
    private int number;
    private Result result;
    private String opponentName;
    private String videoPath; //todo --> json


    public MatchHistory(String time, Result result, String opponentName, String videoPath) {
        this.time = time;
        this.result = result;
        this.opponentName = opponentName;
        this.videoPath = videoPath;
    }

    public static void buildMatchHistory(String time, Account firstAccount, Account secondAccount, Result firstAgainstSecond, ScreenRecordController controller) {
        MatchHistory firstPlayerMatchHistory = new MatchHistory(time, firstAgainstSecond, secondAccount.getName(), controller.getPath());
        Result secondAgainstFirst = null;
        switch (firstAgainstSecond) {
            case WIN:
                secondAgainstFirst = Result.LOSE;
                break;
            case LOSE:
                secondAgainstFirst = Result.WIN;
                break;
            case DRAW:
                secondAgainstFirst = Result.DRAW;
        }
        MatchHistory secondPlayerMatchHistory = new MatchHistory(time, secondAgainstFirst, firstAccount.getName(), controller.getPath());
        firstAccount.getMatchHistories().add(0,firstPlayerMatchHistory);
        secondAccount.getMatchHistories().add(0, secondPlayerMatchHistory);
        synchronized (controller.getFinish()) {
            controller.getFinish().notify();
        }
        firstPlayerMatchHistory.saveMatchHistoryInToFile(firstAccount.getName(), "Data/AccountsData.json");
        secondPlayerMatchHistory.saveMatchHistoryInToFile(secondAccount.getName(), "Data/AccountsData.json");
    }

    public void saveMatchHistoryInToFile(String accountName, String path) {
        try {
            Account account = Account.searchAccountInFile(accountName, path);
            account.getMatchHistories().add(0, this);
            account.saveInToFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
