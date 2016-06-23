package luc.edu.neuroscienceapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;

public class MainActivity extends AppCompatActivity {

    ImageButton image_button;
    ImageButton sound_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("luc.edu.neuroscienceapp.precision", 150);

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        image_button = (ImageButton) findViewById(R.id.image_button);
        sound_button = (ImageButton) findViewById(R.id.sound_button);

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image_intent = new Intent(MainActivity.this, ImageMenuActivity.class);
                startActivity(image_intent);

            }
        });
        sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sound_intent = new Intent(MainActivity.this, SoundMenuActivity.class);
                startActivity(sound_intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("menu","menu");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
