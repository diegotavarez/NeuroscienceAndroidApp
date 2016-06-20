package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import luc.edu.neuroscienceapp.imageprocessing.ImageProcessing;

public class ImageChannelConversionActivity extends AppCompatActivity {
    FloatingActionButton btStep2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_channel_conversion);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        ImageView iv_grayscaleImage = (ImageView)findViewById(R.id.grayscale_picture);
        btStep2 = (FloatingActionButton) findViewById(R.id.fab_step2);

        Snackbar snackbar = Snackbar
                .make(viewGroup, getResources().getString(R.string.step_2), Snackbar.LENGTH_SHORT);
        snackbar.show();

        //byte[] byteArray = getIntent().getByteArrayExtra("initial_image");
        byte[] byteArray = Global.bytesBitmap;
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //Bitmap bmp = Global.img;

        Bitmap grayscaleBitmap = new ImageProcessing().toGrayscale(bmp);
        iv_grayscaleImage.setImageBitmap(grayscaleBitmap);

        Global.imgGrayscale = grayscaleBitmap;

        btStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPatches = new Intent(ImageChannelConversionActivity.this, ImagePatchesActivity.class);
                intentPatches.putExtra("fromCamera", false);
                startActivity(intentPatches);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_channel_conversion, menu);
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
            Intent intentAbout = new Intent(ImageChannelConversionActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(ImageChannelConversionActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }
        if (id == R.id.action_what_is) {
            Intent intent = new Intent(ImageChannelConversionActivity.this, WelcomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
