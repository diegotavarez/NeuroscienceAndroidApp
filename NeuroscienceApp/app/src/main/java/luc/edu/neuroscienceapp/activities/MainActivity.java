package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.entities.Global;

public class MainActivity extends Activity {
    public static final int CAMERA_REQUEST = 1;
    public static final int GALLERY_REQUEST = 2;

    public boolean imageSelected = false;

    private ImageButton btSelectPicture;
    private Button btStart;
    private String selectedImagePath;
    private Bitmap imageBitmap;
    private ProgressDialog mProgressDialog;

    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "NeuroscienceApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSelectPicture = (ImageButton)findViewById(R.id.select_picture);
        btSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btStart = (Button)findViewById(R.id.bt_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();
                mProgressDialog = new ProgressDialog(MainActivity.this);
                new ImageLoader().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void selectImage(){
        final String[] itemArray = getResources().getStringArray(R.array.dialog_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setItems(itemArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (itemArray[which]){
                    case "Take Photo":
                    {
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
                        startActivityForResult(takePhotoIntent, CAMERA_REQUEST);
                        imageSelected = true;
                        break;
                    }

                    case "Choose from Library":
                    {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select File"), GALLERY_REQUEST);
                        imageSelected = true;
                        break;
                    }
                    case "Cancel":
                        dialog.cancel();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                btStart.setBackgroundColor(Color.parseColor("#00E676"));

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    btSelectPicture.setImageBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                imageBitmap = ((BitmapDrawable)btSelectPicture.getDrawable()).getBitmap();
            }
            else if (requestCode == CAMERA_REQUEST) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                btStart.setBackgroundColor(Color.parseColor("#00E676"));
                Bitmap bitmap = null;
                try{
                    bitmap = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                    btSelectPicture.setImageBitmap(bitmap);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                imageBitmap = bitmap;

            }

            Toast.makeText(getApplicationContext(),imageBitmap.getHeight() + " / " + imageBitmap.getWidth(),Toast.LENGTH_SHORT).show();
        }
    }


    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private class ImageLoader extends AsyncTask <Context, Integer, Bitmap>{

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

//            pdia = new ProgressDialog(yourContext);
//            pdia.setMessage("Loading...");
//            pdia.show();
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
