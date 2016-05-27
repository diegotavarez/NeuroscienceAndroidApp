package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
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

import luc.edu.neuroscienceapp.R;

public class GrayscaleCardActivity extends AppCompatActivity {
    ImageView grayscalePicture;
    Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grayscale_card);

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snackbar = Snackbar
                .make(viewGroup, getResources().getString(R.string.step_2), Snackbar.LENGTH_SHORT);
        snackbar.show();

        int[] covers = new int[]{
                R.drawable.carpet_grayscale,
                R.drawable.cat_grayscale,
                R.drawable.flowers_grayscale,
                R.drawable.grass_grayscale,
                R.drawable.grasshopper_grayscale,
                R.drawable.newspaper_grayscale,
                R.drawable.starry_night_grayscale,
                R.drawable.tv_static_grayscale};

        Bundle extras = getIntent().getExtras();
        final int cardId = Integer.parseInt(extras.getString("card_id"));

        grayscalePicture = (ImageView) findViewById(R.id.grayscale_picture);
        grayscalePicture.setBackground(getDrawable(covers[cardId]));

        btNext = (Button) findViewById(R.id.bt_step2);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrayscaleCardActivity.this, ExamplePatchesActivity.class);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
