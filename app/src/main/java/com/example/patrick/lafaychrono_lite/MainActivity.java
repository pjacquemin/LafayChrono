package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
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
    final int SERIE_NUMBER_ZERO = 0;
    final int SERIE_NUMBER_SIX = 6;
    final int RINGTONE_REQUEST_CODE = 5;
    final String SHARED_PREF_RINGTONE_KEY = "lafay_ringtone";
    final String SHARED_PREF_VIBRATE_TOUCH_KEY = "vibrate_on_touch";
    final String SHARED_PREF_VIBRATE_FINISHED_KEY = "vibrate_when_countdown_finished";
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
    public Button ringtone_button;
    public Boolean countdown_started;
    public CountDownButtonTimer counter;
    public int serie_number = SERIE_NUMBER_SIX;
    public RatingBar rating_bar;
    public Vibrator countdown_finished_vibrator;
    public Vibrator touching_button_vibrator;
    public CountDownButton touched_button;
    public Uri prefered_ringtone_uri;
    public boolean vibrate_countdown_button;
    public boolean vibrate_countdown_finished;
    public long pref_first_countdown_millis;
    public long pref_second_countdown_millis;
    public long pref_third_countdown_millis;
    public long pref_fourth_countdown_millis;
    public long pref_fifth_countdown_millis;
    public long pref_sixth_countdown_millis;

    private RelativeLayout layout;
    private OnClickListener count_down_button_on_click_listener;
    private OnClickListener cancel_button_on_click_listener;
    private OnClickListener ringtone_button_on_click_listener;
    private SharedPreferences shared_preferences;
    private String prefered_ringtone_uri_as_string;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        shared_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefered_ringtone_uri_as_string = shared_preferences.getString(SHARED_PREF_RINGTONE_KEY, "");
        prefered_ringtone_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(!prefered_ringtone_uri_as_string.isEmpty()) {
            prefered_ringtone_uri = Uri.parse(prefered_ringtone_uri_as_string);
        }
        vibrate_countdown_button = shared_preferences.getBoolean(SHARED_PREF_VIBRATE_TOUCH_KEY, true);
        pref_first_countdown_millis = shared_preferences.getLong(SHARED_PREF_FIRST_COUNTDOWN_KEY, 25000);
        pref_second_countdown_millis = shared_preferences.getLong(SHARED_PREF_SECOND_COUNTDOWN_KEY, 60000);
        pref_third_countdown_millis = shared_preferences.getLong(SHARED_PREF_THIRD_COUNTDOWN_KEY, 90000);
        pref_fourth_countdown_millis = shared_preferences.getLong(SHARED_PREF_FOURTH_COUNTDOWN_KEY, 120000);
        pref_fifth_countdown_millis = shared_preferences.getLong(SHARED_PREF_FIFTH_COUNTDOWN_KEY, 180000);
        pref_sixth_countdown_millis = shared_preferences.getLong(SHARED_PREF_SIXTH_COUNTDOWN_KEY, 240000);
    }

    private void initializeListeners() {
        count_down_button_on_click_listener = new OnClickListener() {
            public void onClick(View view) {
                if(!countdown_started) {
                    countdown_started = true;
                    touched_button = (CountDownButton)findViewById(view.getId());

                    touched_button.getCountDownTimer().start();
                    updateSeriesNumber();
                    rating_bar.setRating(serie_number);
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

        ringtone_button_on_click_listener = new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, RINGTONE_REQUEST_CODE);
            }
        };
    }

    private void setListenerOnCountDownButtons() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof Button && view.getId() != R.id.buttonCancelCountDown) {
                Button button_to_set_listener = (Button)findViewById(view.getId());

                button_to_set_listener.setOnClickListener(count_down_button_on_click_listener);
            }
        }
    }

    private void setListenerOnOthersButtons() {
        button_cancel_countdown.setOnClickListener(cancel_button_on_click_listener);
        ringtone_button.setOnClickListener(ringtone_button_on_click_listener);
    }

    public void resetButtonsText() {
        button25.setTextWithTime();
        button1min.setTextWithTime();
        button1min30.setTextWithTime();
        button2min.setTextWithTime();
        button3min.setTextWithTime();
        button4min.setTextWithTime();
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

            if (!isPreferedRingtoneUriNull())
            {
                saveRingtoneUriIntoSharedPreferences();
            }
        }
    }

    private boolean isPreferedRingtoneUriNull() {
        return prefered_ringtone_uri == null;
    }

    private void saveRingtoneUriIntoSharedPreferences() {
        SharedPreferences.Editor editor = shared_preferences.edit();
        String prefered_ringtone_uri_string = prefered_ringtone_uri.toString();

        editor.putString(SHARED_PREF_RINGTONE_KEY, prefered_ringtone_uri_string);
        editor.apply();
    }
}

