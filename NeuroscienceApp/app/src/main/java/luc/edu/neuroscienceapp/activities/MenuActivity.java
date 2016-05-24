package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;
import luc.edu.neuroscienceapp.imageprocessing.FileManagement;

public class MenuActivity extends Activity {
    public static final int CAMERA_REQUEST = 1;
    public static final int GALLERY_REQUEST = 2;
    public boolean imageSelected = false;
    private String selectedImagePath;
    private Bitmap imageBitmap;
    private ProgressDialog mProgressDialog;

    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "NeuroscienceApp";
    ImageButton btExamples, btChoose, btGallery;
    FileManagement fileManagement = new FileManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btExamples = (ImageButton) findViewById(R.id.bt_examples);
        btChoose = (ImageButton) findViewById(R.id.bt_choose);
        btGallery = (ImageButton) findViewById(R.id.bt_gallery);

        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileManagement.getPhotoFileUri(photoFileName,getApplicationContext(),APP_TAG));
                startActivityForResult(takePhotoIntent, CAMERA_REQUEST);
                imageSelected = true;
            }
        });

        btExamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(MenuActivity.this, GalleryActivity.class);
                startActivity(intentGallery);
            }
        });

        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select File"), GALLERY_REQUEST);
                imageSelected = true;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = fileManagement.getPath(selectedImageUri,this);
                //btStart.setBackgroundColor(Color.parseColor("#00E676"));

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    imageBitmap = bitmap;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if (requestCode == CAMERA_REQUEST) {
                Uri takenPhotoUri = fileManagement.getPhotoFileUri(photoFileName,getApplicationContext(),APP_TAG);
                Bitmap bitmap = null;
                try{
                    bitmap = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                    imageBitmap = bitmap;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                imageBitmap = bitmap;

            }
            mProgressDialog = new ProgressDialog(MenuActivity.this);
            new ImageLoader().execute();
            //Toast.makeText(getApplicationContext(),imageBitmap.getHeight() + " / " + imageBitmap.getWidth(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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

    private class ImageLoader extends AsyncTask<Context, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            //mProgressDialog = new ProgressDialog(getBaseContext());
            // Set your ProgressBar Title
            mProgressDialog.setTitle("Loading");
            mProgressDialog.setIcon(R.drawable.camera_icon);
            // Set your ProgressBar Message
            mProgressDialog.setMessage("Converting image to grayscale channel");
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Context... arg0) {

            return imageBitmap;
        }

        @Override
        protected void onPostExecute( Bitmap result )  {
            Intent intent = new Intent(getApplicationContext(), ImageChannelConversionActivity.class);
            //intent.putExtra("initial_image",result);

            Global.img = result;
            startActivity(intent);
            mProgressDialog.dismiss();

        }

    }
}
