package com.example.patrick.lafaychrono_lite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Vibrator;
import android.widget.RatingBar;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    final int HUNDRED_MILLIS = 100;
    final int ONE_SECOND = 1000;
    final int SERIE_NUMBER_ZERO = 0;
    final int SERIE_NUMBER_SIX = 6;
    final int RINGTONE_REQUEST_CODE = 5;
    final String SHARED_PREF_RINGTONE_KEY = "lafay_ringtone";

    public CountDownButton button25;
    public CountDownButton button1min;
    public CountDownButton button1min30;
    public CountDownButton button2min;
    public CountDownButton button3min;
    public CountDownButton button4min;
    public Button button_cancel_countdown;
    public Button ringtone_button;
    public Boolean countdown_started;
    public CountDownButtonTimer counter;
    public int serie_number = SERIE_NUMBER_SIX;
    public RatingBar rating_bar;
    public Vibrator countdown_finished_vibrator;
    public Vibrator touching_button_vibrator;
    public CountDownButton touched_button;
    public Uri prefered_ringtone_uri;

    private RelativeLayout layout;
    private OnClickListener countDownButtonClickListener;
    private SharedPreferences shared_preferences;
    private String prefered_ringtone_uri_as_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponentsVariables();
        initializeSharedPreferences();

        countDownButtonClickListener = new OnClickListener() {
            public void onClick(View view) {
                if(!countdown_started) {
                    countdown_started = true;
                    touched_button = (CountDownButton)findViewById(view.getId());

                    touched_button.getCountDownTimer().start();
                    updateSeriesNumber();
                    rating_bar.setRating(serie_number);
                }

                touching_button_vibrator.vibrate(HUNDRED_MILLIS);
            }
        };

        setListenerOnCountDownButtons();

        button_cancel_countdown.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                countdown_started = false;

                touched_button.getCountDownTimer().cancel();
                resetButtonsText();
            }
        });

        ringtone_button.setOnClickListener(new OnClickListener() {
            //@Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });
    }

    private void initializeComponentsVariables() {
        countdown_started = false;
        countdown_finished_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        touching_button_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        button25 = (CountDownButton)findViewById(R.id.button25);
        button1min = (CountDownButton)findViewById(R.id.button1min);
        button1min30 = (CountDownButton)findViewById(R.id.button1min30);
        button2min = (CountDownButton)findViewById(R.id.button2min);
        button3min = (CountDownButton)findViewById(R.id.button3min);
        button4min = (CountDownButton)findViewById(R.id.button4min);
        ringtone_button = (Button) findViewById(R.id.ringtone);
        rating_bar = (RatingBar)findViewById(R.id.ratingBar);
        layout = (RelativeLayout)findViewById(R.id.activity_main);
        button_cancel_countdown = (Button)findViewById(R.id.buttonCancelCountDown);
    }

    private void initializeSharedPreferences() {
        shared_preferences = getPreferences(MODE_PRIVATE);
        prefered_ringtone_uri_as_string = shared_preferences.getString(SHARED_PREF_RINGTONE_KEY, null);
        prefered_ringtone_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(prefered_ringtone_uri_as_string != null) {
            prefered_ringtone_uri = Uri.parse(prefered_ringtone_uri_as_string);
        }
    }

    private void setListenerOnCountDownButtons() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof Button && view.getId() != R.id.buttonCancelCountDown) {
                Button button_to_set_listener = (Button)findViewById(view.getId());

                button_to_set_listener.setOnClickListener(countDownButtonClickListener);
            }
        }
    }

    public void resetButtonsText() {
        button25.setText("00:25");
        button1min.setText("01:00");
        button1min30.setText("01:30");
        button2min.setText("02:00");
        button3min.setText("03:00");
        button4min.setText("04:00");
    }

    public void updateSeriesNumber() {
        serie_number = (int) rating_bar.getRating();

        if(isAllSeriesDone()) {
            serie_number = SERIE_NUMBER_SIX;
        } else {
            serie_number--;
        }
    }

    public boolean isAllSeriesDone() {
        return serie_number == SERIE_NUMBER_ZERO;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        if (resultCode == MainActivity.RESULT_OK && requestCode == RINGTONE_REQUEST_CODE)
        {
            prefered_ringtone_uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (prefered_ringtone_uri != null)
            {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(SHARED_PREF_RINGTONE_KEY, prefered_ringtone_uri.toString());
                editor.commit();
            }
        }
    }
}

