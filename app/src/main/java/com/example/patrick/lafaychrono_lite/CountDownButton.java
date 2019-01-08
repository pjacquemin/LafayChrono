package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

public class CountDownButton extends AppCompatButton {
    private CountDownButtonTimer count_down_timer;
    private String time;

    public CountDownButton(Context context) {
        super(context, null);
        initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCountDownTimer();
        time = count_down_timer.getRemainingTimeInString();
    }

    public void initCountDownTimer() {
        count_down_timer = CountDownButtonTimerFactory.makeTimer((MainActivity) getContext(), getId());
    }

    public CountDownButtonTimer getCountDownTimer() {
        return count_down_timer;
    }

    public void setTextWithTime() {
        setText(time);
    }

}
