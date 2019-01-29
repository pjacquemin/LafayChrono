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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    final int HUNDRED_MILLIS = 100;
    final int ONE_SECOND = 1000;
    final int SERIES_NUMBER_ZERO = 0;
    final int SERIES_NUMBER_SIX = 6;
    final String SHARED_PREF_VIBRATE_TOUCH_KEY = "vibrate_on_touch";
    final String SHARED_PREF_VIBRATE_FINISHED_KEY = "vibrate_when_countdown_finished";
    final String SHARED_PREF_ALARM_FINISHED_KEY = "alarm_when_countdown_finished";
    final String SHARED_PREF_BATTERY_SAVE_THEME_KEY = "battery_save_theme";
    final String SHARED_PREF_FIRST_COUNTDOWN_KEY = "countdown_first";
    final String SHARED_PREF_SECOND_COUNTDOWN_KEY = "countdown_second";
    final String SHARED_PREF_THIRD_COUNTDOWN_KEY = "countdown_third";
    final String SHARED_PREF_FOURTH_COUNTDOWN_KEY = "countdown_fourth";
    final String SHARED_PREF_FIFTH_COUNTDOWN_KEY = "countdown_fifth";
    final String SHARED_PREF_SIXTH_COUNTDOWN_KEY = "countdown_sixth";

    private Button button_cancel_countdown;
    private Boolean countdown_started = Boolean.FALSE;
    private int series_number = SERIES_NUMBER_SIX;
    private RatingBar rating_bar;
    private Vibrator vibrator;
    private CountDownButton touched_button;
    private boolean vibrate_countdown_button;
    private boolean vibrate_countdown_finished;
    private boolean alarm_countdown_finished;
    private boolean energy_save_theme;
    private ConstraintLayout layout;
    private OnClickListener count_down_button_on_click_listener;
    private OnClickListener cancel_button_on_click_listener;
    private SharedPreferences shared_preferences;
    private Intent settings_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeSharedPreferences();

        if(energy_save_theme) {
            setTheme(R.style.SaveBatteryTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponentsVariables();
        initializeListeners();
        setListenerOnCountDownButtons();
        setListenerOnOthersButtons();
        resetCountDowns();

        Toolbar myToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
    }

    private void initializeSharedPreferences() {
        shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        vibrate_countdown_button = shared_preferences.getBoolean(SHARED_PREF_VIBRATE_TOUCH_KEY, true);
        vibrate_countdown_finished = shared_preferences.getBoolean(SHARED_PREF_VIBRATE_FINISHED_KEY, true);
        alarm_countdown_finished = shared_preferences.getBoolean(SHARED_PREF_ALARM_FINISHED_KEY, true);
        energy_save_theme = shared_preferences.getBoolean(SHARED_PREF_BATTERY_SAVE_THEME_KEY, true);
    }

    private void initializeComponentsVariables() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        rating_bar = findViewById(R.id.ratingBar);
        layout = findViewById(R.id.activity_main);
        button_cancel_countdown = findViewById(R.id.buttonCancelCountDown);
    }

    private void initializeListeners() {
        count_down_button_on_click_listener = new OnClickListener() {
            public void onClick(View view) {
                if(!countdown_started) {
                    countdown_started = true;
                    touched_button = findViewById(view.getId());

                    touched_button.getCountDownTimer().start();
                    updateSeriesNumber();
                }

                if(vibrate_countdown_button) {
                    vibrator.vibrate(HUNDRED_MILLIS);
                }
            }
        };

        cancel_button_on_click_listener = new OnClickListener() {
            public void onClick(View arg0) {
                resetCountDowns();
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

    public void resetCountDowns() {
        if(countdown_started) {
            touched_button.getCountDownTimer().cancel();
        }

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof Button && view.getId() != R.id.buttonCancelCountDown) {
                CountDownButton button_to_reset = findViewById(view.getId());
                button_to_reset.setTextWithTime();
            }
        }

        countdown_started = false;

        if(isAllSeriesDone()) {
            updateSeriesNumber();
        }
    }

    public void updateSeriesNumber() {
        series_number = (int) rating_bar.getRating();

        if(isAllSeriesDone()) {
            series_number = SERIES_NUMBER_SIX;
        } else {
            series_number--;
        }

        rating_bar.setRating(series_number);
    }

    public boolean isAllSeriesDone() {
        return series_number == SERIES_NUMBER_ZERO;
    }

    public SharedPreferences getSharedPreferences() {
        return shared_preferences;
    }

    public void vibrateCountDownFinished() {
        if(vibrate_countdown_finished) {
            vibrator.vibrate(ONE_SECOND);
        }
    }

    public void setRemainingTimeOnCountdownButton(String remaining_time) {
        touched_button.setText(remaining_time);
    }

    public void playCountdownFinishedSound() {
        if(alarm_countdown_finished) {
            MediaPlayerSingleton.getInstance(this).getMediaPlayer().start();
        }
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
}

