package luc.edu.neuroscienceapp.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import luc.edu.neuroscienceapp.R;

/**
 * Created by diegotavarez on 6/8/16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
