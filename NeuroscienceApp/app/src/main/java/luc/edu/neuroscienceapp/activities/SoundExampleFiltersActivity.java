package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class SoundExampleFiltersActivity extends AppCompatActivity {
    ImageView sound_filters;
    ImageButton btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_example_filters);

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        final int cardId = Integer.parseInt(extras.getString("card_id"));
        final String category = extras.getString("category");

        int[] filters;
        if(category.equals(Global.IMAGE_GROUP)){
            filters = Global.sound_groups_ica_thumbnails;
        }else
        {
            filters = Global.sound_ica_thumbnails;
        }

        sound_filters = (ImageView) findViewById(R.id.patches_picture);
        sound_filters.setBackground(getDrawable(filters[cardId]));

        btFinish = (FloatingActionButton) findViewById(R.id.fab_finish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoundExampleFiltersActivity.this, SoundGalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // call this to finish the current activitys
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example_patches, menu);
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
            Intent intentAbout = new Intent(SoundExampleFiltersActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(SoundExampleFiltersActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(SoundExampleFiltersActivity.this, WelcomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
