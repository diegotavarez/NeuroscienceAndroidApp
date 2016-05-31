package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.fastica.FastICAException;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;
import luc.edu.neuroscienceapp.imageprocessing.ImageProcessing;

public class PatchesActivity extends AppCompatActivity {
    FloatingActionButton btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patches);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        Snackbar snackbar = Snackbar
                .make(viewGroup, getResources().getString(R.string.step_3), Snackbar.LENGTH_SHORT);

        snackbar.show();

        btFinish = (FloatingActionButton) findViewById(R.id.fab_finish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatchesActivity.this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // call this to finish the current activity
            }
        });

        Bitmap bt = Global.imgGrayscale;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bt, bt.getWidth(), bt.getHeight(), false);
        Bitmap[] bitmaps = new Bitmap[0];
        try {
            bitmaps = ImageProcessing.process(scaledBitmap);
        } catch (FastICAException e) {
            e.printStackTrace();
        }

        ImageView p00 = (ImageView) findViewById(R.id.grayscale_picture_0_0);
        ImageView p01 = (ImageView) findViewById(R.id.grayscale_picture_0_1);
        ImageView p02 = (ImageView) findViewById(R.id.grayscale_picture_0_2);
        ImageView p03 = (ImageView) findViewById(R.id.grayscale_picture_0_3);
        ImageView p10 = (ImageView) findViewById(R.id.grayscale_picture_1_0);
        ImageView p11 = (ImageView) findViewById(R.id.grayscale_picture_1_1);
        ImageView p12 = (ImageView) findViewById(R.id.grayscale_picture_1_2);
        ImageView p13 = (ImageView) findViewById(R.id.grayscale_picture_1_3);
        ImageView p20 = (ImageView) findViewById(R.id.grayscale_picture_2_0);
        ImageView p21 = (ImageView) findViewById(R.id.grayscale_picture_2_1);
        ImageView p22 = (ImageView) findViewById(R.id.grayscale_picture_2_2);
        ImageView p23 = (ImageView) findViewById(R.id.grayscale_picture_2_3);
        ImageView p30 = (ImageView) findViewById(R.id.grayscale_picture_3_0);
        ImageView p31 = (ImageView) findViewById(R.id.grayscale_picture_3_1);
        ImageView p32 = (ImageView) findViewById(R.id.grayscale_picture_3_2);
        ImageView p33 = (ImageView) findViewById(R.id.grayscale_picture_3_3);
        p00.setImageBitmap(bitmaps[0]);
        p01.setImageBitmap(bitmaps[1]);
        p02.setImageBitmap(bitmaps[2]);
        p03.setImageBitmap(bitmaps[3]);
        p20.setImageBitmap(bitmaps[4]);
        p10.setImageBitmap(bitmaps[5]);
        p11.setImageBitmap(bitmaps[6]);
        p12.setImageBitmap(bitmaps[7]);
        p13.setImageBitmap(bitmaps[8]);
        p20.setImageBitmap(bitmaps[9]);
        p21.setImageBitmap(bitmaps[10]);
        p22.setImageBitmap(bitmaps[11]);
        p23.setImageBitmap(bitmaps[12]);
        p30.setImageBitmap(bitmaps[13]);
        p31.setImageBitmap(bitmaps[14]);
        p32.setImageBitmap(bitmaps[15]);
        p33.setImageBitmap(bitmaps[16]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patches, menu);
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
            Intent intentAbout = new Intent(PatchesActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
