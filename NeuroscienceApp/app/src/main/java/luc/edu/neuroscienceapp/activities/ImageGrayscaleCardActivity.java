package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class ImageGrayscaleCardActivity extends AppCompatActivity {
    ImageView grayscalePicture;
    private FloatingActionButton btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grayscale_card);

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
//        Snackbar snackbar = Snackbar
//                .make(viewGroup, getResources().getString(R.string.step_2), Snackbar.LENGTH_SHORT);
//        snackbar.show();

        int[] covers = Global.covers_grayscale;

        Bundle extras = getIntent().getExtras();
        final int cardId = Integer.parseInt(extras.getString("card_id"));

        grayscalePicture = (ImageView) findViewById(R.id.grayscale_picture);
        grayscalePicture.setBackground(getDrawable(covers[cardId]));

        btNext = (FloatingActionButton) findViewById(R.id.fab_step2);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageGrayscaleCardActivity.this, ImageExamplePatchesActivity.class);
                intent.putExtra("grayscale_card_id",cardId);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(ImageGrayscaleCardActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(ImageGrayscaleCardActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(ImageGrayscaleCardActivity.this, WelcomeActivity.class);
            intent.putExtra("menu","menu");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
