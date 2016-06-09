package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.utils.FileManager;

public class SoundMenuActivity extends AppCompatActivity {

    ImageButton btExamples, btChoose, btGallery;
    FileManager fileManager = new FileManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_menu);

        btExamples = (ImageButton) findViewById(R.id.bt_examples);
        btChoose = (ImageButton) findViewById(R.id.bt_choose);
        btGallery = (ImageButton) findViewById(R.id.bt_gallery);


        btExamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(SoundMenuActivity.this, SoundGalleryActivity.class);
                startActivity(intentGallery);
            }
        });


    }
}
