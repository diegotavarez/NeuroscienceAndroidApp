package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;
import luc.edu.neuroscienceapp.imageprocessing.ImageProcessing;

public class ImageChannelConversionActivity extends Activity {
    Button btStep2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_channel_conversion);
        ImageView iv_grayscaleImage = (ImageView)findViewById(R.id.grayscale_picture);
        btStep2 = (Button) findViewById(R.id.bt_step2);

        //byte[] byteArray = getIntent().getByteArrayExtra("initial_image");
        //byte[] byteArray = Global.bytesBitmap;
        //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        Bitmap bmp = Global.img;
        Bitmap grayscaleBitmap = new ImageProcessing().toGrayscale(bmp);
        iv_grayscaleImage.setImageBitmap(grayscaleBitmap);

        //Toast.makeText(getApplicationContext(), grayscaleBitmap.getHeight() + " / " + grayscaleBitmap.getWidth(), Toast.LENGTH_SHORT).show();

        btStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPatches = new Intent(ImageChannelConversionActivity.this, PatchesActivity.class);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
