package Model;

import javafx.scene.control.ProgressBar;

public class TimeLine extends Thread {

    private ProgressBar progressbar;

    public TimeLine(ProgressBar progressbar) {
        this.progressbar = progressbar;
    }

    @Override
    public void run() {
        for (int progress = 0; progress < 100; progress++) {
            progressbar.setProgress(progress / 100.0);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        progressbar.setProgress(0);
    }

    public ProgressBar getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(ProgressBar progressbar) {
        this.progressbar = progressbar;
    }

}