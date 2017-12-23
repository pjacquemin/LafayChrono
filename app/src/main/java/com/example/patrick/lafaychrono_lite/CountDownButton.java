package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.*;

public class CountDownButton extends Button {
    private CountDownButtonTimer count_down_timer;

    public CountDownButton(Context context) {
        super(context, null);
        this.initCountDownTimer();
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initCountDownTimer();
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initCountDownTimer();
    }

    public void initCountDownTimer() {
        this.count_down_timer = CountDownButtonTimerFactory.makeButton((MainActivity) getContext(), this.getId());
    }

    public CountDownButtonTimer getCountDownTimer() {
        return this.count_down_timer;
    }
}
