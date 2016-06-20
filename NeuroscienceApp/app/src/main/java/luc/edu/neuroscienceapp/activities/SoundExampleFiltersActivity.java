package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class SoundExampleFiltersActivity extends Activity {
    ImageView sound_filters;
    ImageButton btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_example_filters);


        int[] filters = Global.sound_covers_ica;

        Bundle extras = getIntent().getExtras();
        final int cardId = Integer.parseInt(extras.getString("card_id"));

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
}
