package luc.edu.neuroscienceapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    public static class PreferencesScreen extends PreferenceFragment implements  SharedPreferences.OnSharedPreferenceChangeListener{
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
            pref.setCurrentValue(PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("luc.edu.neuroscienceapp.precision",150));

            pref.setKey("luc.edu.neuroscienceapp.precision");
            pref.setMeasurementUnit("patches");
            getPreferenceScreen().addPreference(pref);

            pref.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("luc.edu.neuroscienceapp.precision", (int)newValue);
                    Toast.makeText(getActivity(),"NEW VALUE: " + (int) newValue, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
            findPreference("luc.edu.neuroscienceapp.precision").setDefaultValue(150);
        }


    }
}
