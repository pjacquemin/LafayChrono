package com.example.patrick.lafaychrono_lite;

public class CountDownButtonTimerFactory {

    public static CountDownButtonTimer makeTimer(MainActivity activity, int id) {
        CountDownButtonTimer counter = new CountDownButtonTimer(activity, activity.pref_first_countdown_millis, activity.ONE_SECOND);

        switch(id) {
            case R.id.button25:
                counter = new CountDownButtonTimer(activity, activity.pref_first_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button1min:
                counter = new CountDownButtonTimer(activity, activity.pref_second_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button1min30:
                counter = new CountDownButtonTimer(activity, activity.pref_third_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button2min:
                counter = new CountDownButtonTimer(activity, activity.pref_fourth_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button3min:
                counter = new CountDownButtonTimer(activity, activity.pref_fifth_countdown_millis, activity.ONE_SECOND);
                break;
            case R.id.button4min:
                counter = new CountDownButtonTimer(activity, activity.pref_sixth_countdown_millis, activity.ONE_SECOND);
                break;
        }

        return counter;
    }
}
