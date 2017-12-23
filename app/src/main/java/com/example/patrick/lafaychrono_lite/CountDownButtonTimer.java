package com.example.patrick.lafaychrono_lite;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;


public class CountDownButtonTimer extends CountDownTimer {
    private MainActivity activity;

    public CountDownButtonTimer(MainActivity activity, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.activity = activity;
    }

    @Override
    public void onFinish() {
        activity.countdown_started = false;

        activity.resetButtonsText();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(activity.getApplicationContext(), notification);

        r.play();
        activity.countdown_finished_vibrator.vibrate(activity.ONE_SECOND);

        if(activity.isAllSeriesDone()) {
            activity.updateSeriesNumber();
            activity.rating_bar.setRating(activity.serie_number);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int minutes = (int) millisUntilFinished / CountDownButtonTimerFactory.ONE_MINUTE ;
        int seconds = (int)millisUntilFinished % CountDownButtonTimerFactory.ONE_MINUTE;
        String time_until_finished = String.format("%02d:%02d", minutes, seconds / activity.ONE_SECOND);

        activity.touched_button.setText(time_until_finished);
    }
}
