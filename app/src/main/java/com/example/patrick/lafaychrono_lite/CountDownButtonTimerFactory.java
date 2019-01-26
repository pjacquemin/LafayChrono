package com.example.patrick.lafaychrono_lite;

import android.content.SharedPreferences;

public class CountDownButtonTimerFactory {

    public static CountDownButtonTimer makeTimer(MainActivity activity, int id) {
        SharedPreferences shared_preferences = activity.getSharedPreferences();
        long pref_first_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_FIRST_COUNTDOWN_KEY, CountDownButtonTimer.TWEETY_FIVE_SECONDS);
        CountDownButtonTimer counter = new CountDownButtonTimer(activity, pref_first_countdown_millis, activity.ONE_SECOND);

        switch(id) {
            case R.id.button1min:
                long pref_second_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_SECOND_COUNTDOWN_KEY, CountDownButtonTimer.ONE_MINUTE);
                counter = new CountDownButtonTimer(activity, pref_second_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button1min30:
                long pref_third_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_THIRD_COUNTDOWN_KEY, CountDownButtonTimer.ONE_MINUTE_THIRTY_SECONDS);
                counter = new CountDownButtonTimer(activity, pref_third_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button2min:
                long pref_fourth_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_FOURTH_COUNTDOWN_KEY, CountDownButtonTimer.TWO_MINUTES);
                counter = new CountDownButtonTimer(activity, pref_fourth_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button3min:
                long pref_fifth_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_FIFTH_COUNTDOWN_KEY, CountDownButtonTimer.THREE_MINUTES);
                counter = new CountDownButtonTimer(activity, pref_fifth_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button4min:
                long pref_sixth_countdown_millis = shared_preferences.getLong(activity.SHARED_PREF_SIXTH_COUNTDOWN_KEY, CountDownButtonTimer.FOUR_MINUTES);
                counter = new CountDownButtonTimer(activity, pref_sixth_countdown_millis, activity.ONE_SECOND);
                break;
        }

        return counter;
    }
}
