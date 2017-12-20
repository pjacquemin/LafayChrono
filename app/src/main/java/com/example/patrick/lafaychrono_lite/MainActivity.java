package com.example.patrick.lafaychrono_lite;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.RatingBar;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    public Button button25;
    public Button button1min;
    public Button button1min30;
    public Button button2min;
    public Button button3min;
    public Button button4min;
    public Button button_cancel_countdown;
    public Boolean started;
    public ButtonCountDown counter;
    public int serie_number = 6;
    public RatingBar rating_bar;
    public int button_countdown_touched;
    public Vibrator countdown_finished_vibrator;
    public Vibrator button_touched_vibrator;
    public Button touched_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        started = false;
        countdown_finished_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        button_touched_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        button25 = (Button)findViewById(R.id.button25);

        button25.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button25;
                    started = true;
                    counter = new ButtonCountDown(25000, 1000);

                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(100);
                }
            }
        });

        button1min = (Button)findViewById(R.id.button1min);

        button1min.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button1min;
                    started = true;
                    counter = new ButtonCountDown(60000, 1000);

                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(1000);
                }
            }
        });

        button1min30 = (Button)findViewById(R.id.button1min30);

        button1min30.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button1min30;
                    started = true;
                    counter = new ButtonCountDown(90000, 1000);

                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(100);
                }
            }
        });

        button2min = (Button)findViewById(R.id.button2min);

        button2min.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button2min;
                    started = true;
                    counter = new ButtonCountDown(120000, 1000);

                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(100);
                }
            }
        });

        button3min = (Button)findViewById(R.id.button3min);

        button3min.setOnClickListener(new OnClickListener() {

            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button3min;
                    started = true;
                    counter = new ButtonCountDown(180000, 1000);

                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(100);
                }
            }
        });

        button4min = (Button)findViewById(R.id.button4min);

        button4min.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                if(started == false){
                    touched_button = button4min;
                    started = true;
                    counter = new ButtonCountDown(240000, 1000);
                    
                    counter.start();
                    updateRatingBar();
                    button_touched_vibrator.vibrate(100);
                }
            }
        });

        button_cancel_countdown = (Button)findViewById(R.id.buttonCancelCountDown);

        button_cancel_countdown.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                started = false;

                counter.cancel();
                button25.setText("00:25");
                button1min.setText("01:00");
                button1min30.setText("01:30");
                button2min.setText("02:00");
                button3min.setText("03:00");
                button4min.setText("04:00");
            }
        });
    }

    private void updateRatingBar() {
        rating_bar = (RatingBar)findViewById(R.id.ratingBar);
        serie_number = (int) rating_bar.getRating();

        if(serie_number == 0) {
            serie_number = 6;
        } else {
            serie_number--;
        }

        rating_bar.setRating(serie_number);
    }

    public class ButtonCountDown extends CountDownTimer {
        public ButtonCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            started = false;

            button25.setText("00:25");
            button1min.setText("01:00");
            button1min30.setText("01:30");
            button2min.setText("02:00");
            button3min.setText("03:00");
            button4min.setText("04:00");

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);

            r.play();
            countdown_finished_vibrator.vibrate(1000);

            if(serie_number == 0) {
                updateRatingBar();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int minutes = (int) millisUntilFinished / 60000 ;
            int seconds = (int)millisUntilFinished % 60000;
            String time_until_finished = String.format("%02d:%02d", minutes, seconds / 1000);

            touched_button.setText(time_until_finished);
        }
    }
}

