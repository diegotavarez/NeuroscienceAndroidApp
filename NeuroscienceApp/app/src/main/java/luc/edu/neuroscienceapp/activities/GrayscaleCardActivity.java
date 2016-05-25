package luc.edu.neuroscienceapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import luc.edu.neuroscienceapp.R;

public class GrayscaleCardActivity extends AppCompatActivity {
    ImageView grayscalePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grayscale_card);

        int[] covers = new int[]{
                R.drawable.carpet_grayscale,
                R.drawable.cat_grayscale,
                R.drawable.flowers_grayscale,
                R.drawable.grass_grayscale,
                R.drawable.grasshopper_grayscale,
                R.drawable.newspaper_grayscale,
                R.drawable.starry_night_grayscale,
                R.drawable.tv_static_grayscale};

        grayscalePicture = (ImageView) findViewById(R.id.grayscale_picture);
        grayscalePicture.setBackground(getDrawable(covers[0]));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
