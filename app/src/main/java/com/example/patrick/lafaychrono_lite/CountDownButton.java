package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

public class CountDownButton extends AppCompatButton {
    private CountDownButtonTimer count_down_timer;
    private String time;

    public CountDownButton(Context context) {
        super(context, null);
        this.initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public void initCountDownTimer() {
        this.count_down_timer = CountDownButtonTimerFactory.makeTimer((MainActivity) getContext(), this.getId());
    }

    public CountDownButtonTimer getCountDownTimer() {
        return this.count_down_timer;
    }

    public void setTextWithTime() {
        this.setText(time);
    }

}
