package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    final int HUNDRED_MILLIS = 100;
    final int ONE_SECOND = 1000;
    final int SERIES_NUMBER_ZERO = 0;
    final int SERIES_NUMBER_SIX = 6;
    final String SHARED_PREF_VIBRATE_TOUCH_KEY = "vibrate_on_touch";
    final String SHARED_PREF_VIBRATE_FINISHED_KEY = "vibrate_when_countdown_finished";
    final String SHARED_PREF_ALARM_FINISHED_KEY = "alarm_when_countdown_finished";
    final String SHARED_PREF_FIRST_COUNTDOWN_KEY = "countdown_first";
    final String SHARED_PREF_SECOND_COUNTDOWN_KEY = "countdown_second";
    final String SHARED_PREF_THIRD_COUNTDOWN_KEY = "countdown_third";
    final String SHARED_PREF_FOURTH_COUNTDOWN_KEY = "countdown_fourth";
    final String SHARED_PREF_FIFTH_COUNTDOWN_KEY = "countdown_fifth";
    final String SHARED_PREF_SIXTH_COUNTDOWN_KEY = "countdown_sixth";

    public CountDownButton button25;
    public CountDownButton button1min;
    public CountDownButton button1min30;
    public CountDownButton button2min;
    public CountDownButton button3min;
    public CountDownButton button4min;
    public Button button_cancel_countdown;
    public Boolean countdown_started;
    public int series_number = SERIES_NUMBER_SIX;
    public RatingBar rating_bar;
    public Vibrator countdown_finished_vibrator;
    public Vibrator touching_button_vibrator;
    public CountDownButton touched_button;
    public boolean vibrate_countdown_button;
    public boolean vibrate_countdown_finished;
    public boolean alarm_countdown_finished;
    public long pref_first_countdown_millis;
    public long pref_second_countdown_millis;
    public long pref_third_countdown_millis;
    public long pref_fourth_countdown_millis;
    public long pref_fifth_countdown_millis;
    public long pref_sixth_countdown_millis;

    private RelativeLayout layout;
    private OnClickListener count_down_button_on_click_listener;
    private OnClickListener cancel_button_on_click_listener;
    private SharedPreferences shared_preferences;
    private Intent settings_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSharedPreferences();
        setContentView(R.layout.activity_main);
        initializeComponentsVariables();
        initializeListeners();
        setListenerOnCountDownButtons();
        setListenerOnOthersButtons();
        resetButtonsText();

        Toolbar myToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                settings_intent = new Intent(this, SettingsActivity.class);

                startActivity(settings_intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeComponentsVariables() {
        countdown_started = false;
        countdown_finished_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        touching_button_vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        button25 = findViewById(R.id.button25);
        button1min = findViewById(R.id.button1min);
        button1min30 = findViewById(R.id.button1min30);
        button2min = findViewById(R.id.button2min);
        button3min = findViewById(R.id.button3min);
        button4min = findViewById(R.id.button4min);
        rating_bar = findViewById(R.id.ratingBar);
        layout = findViewById(R.id.activity_main);
        button_cancel_countdown = findViewById(R.id.buttonCancelCountDown);
    }

    private void initializeSharedPreferences() {
        shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        vibrate_countdown_button = shared_preferences.getBoolean(SHARED_PREF_VIBRATE_TOUCH_KEY, true);
        vibrate_countdown_finished = shared_preferences.getBoolean(SHARED_PREF_VIBRATE_FINISHED_KEY, true);
        alarm_countdown_finished = shared_preferences.getBoolean(SHARED_PREF_ALARM_FINISHED_KEY, true);
        pref_first_countdown_millis = shared_preferences.getLong(SHARED_PREF_FIRST_COUNTDOWN_KEY, CountDownButtonTimer.TWEETY_FIVE_SECONDS);
        pref_second_countdown_millis = shared_preferences.getLong(SHARED_PREF_SECOND_COUNTDOWN_KEY, CountDownButtonTimer.ONE_MINUTE);
        pref_third_countdown_millis = shared_preferences.getLong(SHARED_PREF_THIRD_COUNTDOWN_KEY, CountDownButtonTimer.ONE_MINUTE_THIRTY_SECONDS);
        pref_fourth_countdown_millis = shared_preferences.getLong(SHARED_PREF_FOURTH_COUNTDOWN_KEY, CountDownButtonTimer.TWO_MINUTES);
        pref_fifth_countdown_millis = shared_preferences.getLong(SHARED_PREF_FIFTH_COUNTDOWN_KEY, CountDownButtonTimer.THREE_MINUTES);
        pref_sixth_countdown_millis = shared_preferences.getLong(SHARED_PREF_SIXTH_COUNTDOWN_KEY, CountDownButtonTimer.FOUR_MINUTES);
    }

    private void initializeListeners() {
        count_down_button_on_click_listener = new OnClickListener() {
            public void onClick(View view) {
                if(!countdown_started) {
                    countdown_started = true;
                    touched_button = findViewById(view.getId());

                    touched_button.getCountDownTimer().start();
                    updateSeriesNumber();
                    rating_bar.setRating(series_number);
                }

                if(vibrate_countdown_button) {
                    touching_button_vibrator.vibrate(HUNDRED_MILLIS);
                }
            }
        };

        cancel_button_on_click_listener = new OnClickListener() {
            public void onClick(View arg0) {
                if(countdown_started) {
                    touched_button.getCountDownTimer().cancel();
                }

                countdown_started = false;

                resetButtonsText();
            }
        };
    }

    private void setListenerOnCountDownButtons() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof Button && view.getId() != R.id.buttonCancelCountDown) {
                Button button_to_set_listener = findViewById(view.getId());

                button_to_set_listener.setOnClickListener(count_down_button_on_click_listener);
            }
        }
    }

    private void setListenerOnOthersButtons() {
        button_cancel_countdown.setOnClickListener(cancel_button_on_click_listener);
    }

    public void resetButtonsText() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof Button && view.getId() != R.id.buttonCancelCountDown) {
                CountDownButton button_to_reset = findViewById(view.getId());
                button_to_reset.setTextWithTime();
            }
        }
    }

    public void updateSeriesNumber() {
        series_number = (int) rating_bar.getRating();

        if(isAllSeriesDone()) {
            series_number = SERIES_NUMBER_SIX;
        } else {
            series_number--;
        }
    }

    public boolean isAllSeriesDone() {
        return series_number == SERIES_NUMBER_ZERO;
    }
}

