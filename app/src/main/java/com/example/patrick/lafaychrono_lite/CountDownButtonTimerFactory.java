package com.example.patrick.lafaychrono_lite;

public class CountDownButtonTimerFactory {
    public static CountDownButtonTimer makeButton(MainActivity activity, int id) {
        CountDownButtonTimer counter = new CountDownButtonTimer(activity, activity.TWEETY_FIVE_SECONDS, activity.ONE_SECOND);

        switch(id) {
            case R.id.button25:
                counter = new CountDownButtonTimer(activity, activity.TWEETY_FIVE_SECONDS, activity.ONE_SECOND);
                break;
            case R.id.button1min:
                counter = new CountDownButtonTimer(activity, activity.ONE_MINUTE, activity.ONE_SECOND);
                break;
            case R.id.button1min30:
                counter = new CountDownButtonTimer(activity, activity.ONE_MINUTE_THIRTY_SECONDS, activity.ONE_SECOND);
                break;
            case R.id.button2min:
                counter = new CountDownButtonTimer(activity, activity.TWO_MINUTES, activity.ONE_SECOND);
                break;
            case R.id.button3min:
                counter = new CountDownButtonTimer(activity, activity.THREE_MINUTES, activity.ONE_SECOND);
                break;
            case R.id.button4min:
                counter = new CountDownButtonTimer(activity, activity.FOUR_MINUTES, activity.ONE_SECOND);
                break;
        }

        return counter;
    }
}
