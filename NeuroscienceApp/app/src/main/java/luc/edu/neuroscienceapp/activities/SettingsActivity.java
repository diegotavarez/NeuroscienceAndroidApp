package luc.edu.neuroscienceapp.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.pavelsikun.seekbarpreference.SeekBarPreference;

import luc.edu.neuroscienceapp.R;

/**
 * Created by diegotavarez on 6/8/16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesScreen())
                .commit();

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PreferencesScreen extends PreferenceFragment {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            SeekBarPreference pref = new SeekBarPreference(getActivity());
            pref.setTitle("Precision");
            pref.setMinValue(50);
            pref.setMaxValue(10000);
            pref.setDefaultValue(150);
            pref.setInterval(50);
            pref.setMeasurementUnit("patches");
            getPreferenceScreen().addPreference(pref);
        }


    }
}
