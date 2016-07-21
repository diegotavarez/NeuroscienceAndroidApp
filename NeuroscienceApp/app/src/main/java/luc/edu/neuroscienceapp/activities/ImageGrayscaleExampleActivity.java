package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class ImageGrayscaleExampleActivity extends AppCompatActivity {
    ImageView grayscalePicture;
    private FloatingActionButton btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grayscale_card);

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        final int cardId = Integer.parseInt(extras.getString("card_id"));
        final String category = extras.getString("category");

        int[] covers;
        if(category.equals(Global.IMAGE_GROUP)){
            covers = Global.image_groups_covers_grayscale;
        }else
        {
            covers = Global.image_grayscale_thumbnails;
        }

        grayscalePicture = (ImageView) findViewById(R.id.grayscale_picture);
        grayscalePicture.setBackground(getDrawable(covers[cardId]));

        btNext = (FloatingActionButton) findViewById(R.id.fab_step2);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageGrayscaleExampleActivity.this, ImageExamplePatchesActivity.class);
                intent.putExtra("grayscale_card_id",cardId);

                if(category.equals(Global.IMAGE_GROUP)) {
                    intent.putExtra("category", Global.IMAGE_GROUP);
                }
                else
                {
                    intent.putExtra("category", Global.NORMAL);

                }

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grayscale_card, menu);
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
            Intent intentAbout = new Intent(ImageGrayscaleExampleActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(ImageGrayscaleExampleActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_info) {
            Intent intent = new Intent(ImageGrayscaleExampleActivity.this, WelcomeActivity.class);
            intent.putExtra("menu","menu");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
