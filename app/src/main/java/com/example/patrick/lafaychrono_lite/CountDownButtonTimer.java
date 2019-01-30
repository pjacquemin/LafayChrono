package com.example.patrick.lafaychrono_lite;

import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

public class CountDownButtonTimer extends CountDownTimer {
    final static int TWEETY_FIVE_SECONDS = 25000;
    final static int ONE_MINUTE = 60000;
    final static int ONE_MINUTE_THIRTY_SECONDS = 90000;
    final static int TWO_MINUTES = 120000;
    final static int THREE_MINUTES = 180000;
    final static int FOUR_MINUTES = 240000;

    private MainActivity activity;
    private long millisInFuture;
    private String time_until_finished;

    public CountDownButtonTimer(MainActivity activity, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.activity = activity;
        this.millisInFuture = millisInFuture;
    }

    public String getRemainingTimeInString() {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisInFuture));
        time_until_finished = String.format("%02d:%02d", minutes, seconds);

        return time_until_finished;
    }

    @Override
    public void onFinish() {
        activity.resetCountDowns();
        activity.vibrateCountDownFinished();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        millisInFuture = millisUntilFinished;

        if(isLastThreeSeconds()) {
            activity.playCountdownFinishingSound();
        }

        time_until_finished = getRemainingTimeInString();

        activity.setRemainingTimeOnCountdownButton(time_until_finished);
    }

    private boolean isLastThreeSeconds() {
        return millisInFuture < 4000;
    }
}
