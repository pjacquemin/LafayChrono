package com.example.patrick.lafaychrono_lite;

public class CountDownButtonTimerFactory {

    final static int TWEETY_FIVE_SECONDS = 25000;
    final static int ONE_MINUTE = 60000;
    final static int ONE_MINUTE_THIRTY_SECONDS = 90000;
    final static int TWO_MINUTES = 120000;
    final static int THREE_MINUTES = 180000;
    final static int FOUR_MINUTES = 240000;

    public static CountDownButtonTimer makeButton(MainActivity activity, int id) {
        CountDownButtonTimer counter = new CountDownButtonTimer(activity, TWEETY_FIVE_SECONDS, activity.ONE_SECOND);

        switch(id) {
            case R.id.button25:
                counter = new CountDownButtonTimer(activity, TWEETY_FIVE_SECONDS, activity.ONE_SECOND);
                break;
            case R.id.button1min:
                counter = new CountDownButtonTimer(activity, ONE_MINUTE, activity.ONE_SECOND);
                break;
            case R.id.button1min30:
                counter = new CountDownButtonTimer(activity, ONE_MINUTE_THIRTY_SECONDS, activity.ONE_SECOND);
                break;
            case R.id.button2min:
                counter = new CountDownButtonTimer(activity, TWO_MINUTES, activity.ONE_SECOND);
                break;
            case R.id.button3min:
                counter = new CountDownButtonTimer(activity, THREE_MINUTES, activity.ONE_SECOND);
                break;
            case R.id.button4min:
                counter = new CountDownButtonTimer(activity, FOUR_MINUTES, activity.ONE_SECOND);
                break;
        }

        return counter;
    }
}
