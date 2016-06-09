package luc.edu.neuroscienceapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.fastica.FastICAException;

import java.io.ByteArrayOutputStream;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;
import luc.edu.neuroscienceapp.imageprocessing.ImageProcessing;

public class ImagePatchesActivity extends AppCompatActivity {
    FloatingActionButton btFinish;
    private ProgressDialog mProgressDialog;
    public Bitmap[] processedBitmaps = new Bitmap[0];
    public Bitmap scaledBitmap;
    public int numberOfPatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_patches);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        Snackbar snackbar = Snackbar
                .make(viewGroup, getResources().getString(R.string.step_3), Snackbar.LENGTH_SHORT);

        snackbar.show();

        btFinish = (FloatingActionButton) findViewById(R.id.fab_finish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImagePatchesActivity.this, ImageMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // call this to finish the current activity
            }
        });

        Bitmap bt = Global.imgGrayscale;
        scaledBitmap = Bitmap.createScaledBitmap(bt, bt.getWidth(), bt.getHeight(), false);
        processedBitmaps = new Bitmap[0];
        mProgressDialog = new ProgressDialog(ImagePatchesActivity.this);

        numberOfPatches = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("luc.edu.neuroscienceapp.precision",150);
        Toast.makeText(getApplicationContext(),String.valueOf(numberOfPatches) + " patches", Toast.LENGTH_SHORT).show();

        new PatchesExtractor().execute();

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
            Intent intentAbout = new Intent(ImagePatchesActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PatchesExtractor extends AsyncTask<Context, Integer, Bitmap[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            //mProgressDialog = new ProgressDialog(getBaseContext());
            // Set your ProgressBar Title
            mProgressDialog.setTitle("Loading");
            mProgressDialog.setIcon(R.drawable.camera_icon);
            // Set your ProgressBar Message
            mProgressDialog.setMessage("Getting patches from image");
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap[] doInBackground(Context... arg0) {

            Bitmap[] bitmaps = null;
            try {
                bitmaps = ImageProcessing.process(scaledBitmap, numberOfPatches,20);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmaps;
        }

        @Override
        protected void onPostExecute( Bitmap[] result )  {
            processedBitmaps = result;
            mProgressDialog.dismiss();

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
            p00.setImageBitmap(processedBitmaps[0]);
            p01.setImageBitmap(processedBitmaps[1]);
            p02.setImageBitmap(processedBitmaps[2]);
            p03.setImageBitmap(processedBitmaps[3]);
            p20.setImageBitmap(processedBitmaps[4]);
            p10.setImageBitmap(processedBitmaps[5]);
            p11.setImageBitmap(processedBitmaps[6]);
            p12.setImageBitmap(processedBitmaps[7]);
            p13.setImageBitmap(processedBitmaps[8]);
            p20.setImageBitmap(processedBitmaps[9]);
            p21.setImageBitmap(processedBitmaps[10]);
            p22.setImageBitmap(processedBitmaps[11]);
            p23.setImageBitmap(processedBitmaps[12]);
            p30.setImageBitmap(processedBitmaps[13]);
            p31.setImageBitmap(processedBitmaps[14]);
            p32.setImageBitmap(processedBitmaps[15]);
            p33.setImageBitmap(processedBitmaps[16]);

        }

    }
}
