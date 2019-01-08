package com.example.patrick.lafaychrono_lite;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import java.util.concurrent.TimeUnit;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.AndroidResources;
import androidx.preference.DialogPreference;

public class CountdownDialogPreference extends DialogPreference {
    private int mDialogLayoutResId = R.layout.edit_timer;
    private long minutes;
    private long seconds;
    private long total_millis;

    public CountdownDialogPreference(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CountdownDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CountdownDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context,
                androidx.preference.R.attr.editTextPreferenceStyle,
                AndroidResources.ANDROID_R_EDITTEXT_PREFERENCE_STYLE));
    }

    public CountdownDialogPreference(Context context) {
        this(context, null);
    }

    @Override
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }

    public long getMinutes() {
        return this.minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
        persistInMillis();
    }

    public long getSeconds() {
        return this.seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
        persistInMillis();
    }

    protected void persistInMillis() {
        total_millis = TimeUnit.MINUTES.toMillis(minutes);
        total_millis += TimeUnit.SECONDS.toMillis(seconds);

        persistLong(total_millis);
    }

    protected void restoreMinutesAndSeconds() {
        minutes = TimeUnit.MILLISECONDS.toMinutes(total_millis);
        seconds = TimeUnit.MILLISECONDS.toSeconds(total_millis)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total_millis));
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // Default value from attribute. Fallback value is set to 0.
        return a.getInt(index, 0);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if(defaultValue != null) {
            Long default_value_long = Long.valueOf((int)defaultValue);
            total_millis = getPersistedLong(default_value_long);
        } else {
            total_millis = getPersistedLong(CountDownButtonTimer.TWEETY_FIVE_SECONDS);
        }

        restoreMinutesAndSeconds();
    }

}
