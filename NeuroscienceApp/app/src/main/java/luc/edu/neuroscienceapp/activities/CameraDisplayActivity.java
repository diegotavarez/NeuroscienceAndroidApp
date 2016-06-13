package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import luc.edu.neuroscienceapp.R;

/**
 * Created by captain on 6/9/16.
 */
public class CameraDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_display);

        Intent intent = getIntent();
        String picturePath = intent.getExtras().getString("picture");
        System.out.println(picturePath);
        ImageView imageview;
        imageview = (ImageView) findViewById(R.id.picture_preview);
        imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        ImageButton btCancel = (ImageButton) findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
            }
        });
    }
}
