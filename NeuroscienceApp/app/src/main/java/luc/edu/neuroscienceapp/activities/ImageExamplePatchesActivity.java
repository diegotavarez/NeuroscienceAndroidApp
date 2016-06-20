package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class ImageExamplePatchesActivity extends AppCompatActivity {
    ImageView grayscalePicture;
    FloatingActionButton btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_example_patches);

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
//        Snackbar snackbar = Snackbar
//                .make(viewGroup, getResources().getString(R.string.step_3), Snackbar.LENGTH_SHORT);
//        snackbar.show();

        int[] covers = Global.covers_ica;

        int cardId = getIntent().getIntExtra("grayscale_card_id",0);
        grayscalePicture = (ImageView) findViewById(R.id.patches_picture);
        grayscalePicture.setBackground(getDrawable(covers[cardId]));

        btFinish = (FloatingActionButton) findViewById(R.id.fab_finish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageExamplePatchesActivity.this, ImageGalleryActivity.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(ImageExamplePatchesActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(ImageExamplePatchesActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(ImageExamplePatchesActivity.this, WelcomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
