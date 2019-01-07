package com.example.patrick.lafaychrono_lite;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

public class CountdownPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private EditText minutes;
    private EditText seconds;

    /**
     * Creates a new Instance of the TimePreferenceDialogFragment and stores the key of the
     * related Preference
     *
     * @param key The key of the related Preference
     * @return A new Instance of the TimePreferenceDialogFragment
     */
    public static CountdownPreferenceDialogFragmentCompat newInstance(String key) {
        final CountdownPreferenceDialogFragmentCompat
                fragment = new CountdownPreferenceDialogFragmentCompat();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        DialogPreference preference = getPreference();
        long minutes = ((CountdownDialogPreference) preference).getMinutes();
        long seconds = ((CountdownDialogPreference) preference).getSeconds();

        this.minutes = (EditText) view.findViewById(R.id.minutes);
        this.minutes.setText(Long.toString(minutes));
        this.seconds = (EditText) view.findViewById(R.id.seconds);
        this.seconds.setText(Long.toString(seconds));
    }

    /**
     * Called when the Dialog is closed.
     *
     * @param positiveResult Whether the Dialog was accepted or canceled.
     */
    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            // generate value to save
            String minutes_in_string =  this.minutes.getText().toString();
            String seconds_in_string =  this.seconds.getText().toString();
            long minutes = Long.parseLong(minutes_in_string);
            long seconds = Long.parseLong(seconds_in_string);

            // Get the related Preference and save the value
            DialogPreference preference = getPreference();
            if (preference instanceof CountdownDialogPreference) {
                CountdownDialogPreference countdownPreference =
                        ((CountdownDialogPreference) preference);
                // This allows the client to ignore the user value.
                if (countdownPreference.callChangeListener(
                        minutes) || countdownPreference.callChangeListener(
                        seconds)) {
                    // Save the value
                    countdownPreference.setMinutes(minutes);
                    countdownPreference.setSeconds(seconds);
                }
            }
        }
    }
}
