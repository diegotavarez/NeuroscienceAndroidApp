package luc.edu.neuroscienceapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;

public class ExamplePatchesActivity extends AppCompatActivity {
    ImageView grayscalePicture;
    Button btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_patches);

        int[] covers = new int[]{
                R.drawable.carpet_ica,
                R.drawable.cat_ica,
                R.drawable.flowers_ica,
                R.drawable.grass_ica,
                R.drawable.grass_ica,
                R.drawable.newspaper_ica,
                R.drawable.starry_night_ica,
                R.drawable.tv_static_ica};

        int cardId = getIntent().getIntExtra("grayscale_card_id",0);
        grayscalePicture = (ImageView) findViewById(R.id.patches_picture);
        grayscalePicture.setBackground(getDrawable(covers[cardId]));

        btFinish = (Button) findViewById(R.id.bt_finish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
