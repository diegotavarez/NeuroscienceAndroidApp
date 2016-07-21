package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import luc.edu.neuroscienceapp.R;

/**
 * Created by vinicius on 6/9/16.
 * @author Vinicius Marques
 */
public class CameraDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_display);

        Intent intent = getIntent();
        final String picturePath = intent.getExtras().getString("picture");
        System.out.println(picturePath);
        ImageView imageview;
        imageview = (ImageView) findViewById(R.id.picture_preview);
        imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        ImageButton btCancel = (ImageButton) findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        ImageButton btConfirm = (ImageButton) findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(RESULT_OK);

                Intent intentPatches = new Intent(CameraDisplayActivity.this, ImagePatchesActivity.class);
                intentPatches.putExtra("picture", picturePath);
                intentPatches.putExtra("fromCamera", true);
                startActivity(intentPatches);
            }
        });
    }
}
