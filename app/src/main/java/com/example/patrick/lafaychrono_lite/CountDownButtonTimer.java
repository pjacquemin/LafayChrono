package com.example.patrick.lafaychrono_lite;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

public class CountDownButtonTimer extends CountDownTimer {
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
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisInFuture));
        time_until_finished = String.format("%02d:%02d", minutes, seconds);

        return time_until_finished;
    }

    @Override
    public void onFinish() {
        activity.countdown_started = false;

        activity.resetButtonsText();

        Uri notification = Uri.parse("android.resource://"
                + activity.getPackageName() + "/" + R.raw.countdown_finished);
        MediaPlayer mp = MediaPlayer.create(activity.getApplicationContext(), notification);
        mp.start();

        if(activity.vibrate_countdown_finished) {
            activity.countdown_finished_vibrator.vibrate(activity.ONE_SECOND);
        }

        if(activity.isAllSeriesDone()) {
            activity.updateSeriesNumber();
            activity.rating_bar.setRating(activity.serie_number);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        millisInFuture = millisUntilFinished;
        time_until_finished = getRemainingTimeInString();

        activity.touched_button.setText(time_until_finished);
    }
}
