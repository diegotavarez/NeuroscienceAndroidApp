package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Surface;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.utils.FileManager;
import luc.edu.neuroscienceapp.views.CameraPreview;

/**
 * Created by vinicius on 6/7/16.
 *
 * This approach is deprecated in API level 21
 */
public class CameraActivity extends Activity {
    public static final int PICTURE_CONFIRMATION = 0;
    public String photoFileName = "photo.bmp";
    public final String APP_TAG = "NeuroscienceApp";
    FileManager fileManager = new FileManager();
    static final int MEDIA_TYPE_IMAGE = 1;

    private Camera mCamera;
    private CameraPreview mPreview;
    private ImageButton btCapture;
    private int PICTURE_WIDTH = 2048;
    private int PICTURE_HEIGHT = 1152;

    private void createCamera(){
        // Create an instance of Camera
        try{
            if(mCamera == null){
                mCamera = Camera.open();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private File getOutputPhotoFile() {

        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return null;
            }
        }

        return new File(directory.getPath() + File.separator + photoFileName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        createCamera();

        // To make camera parameters take effect, applications have to call setParameters(Camera.Parameters).
        Camera.Parameters camParams = mCamera.getParameters();
        camParams.setColorEffect(Camera.Parameters.EFFECT_MONO);
        camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camParams.setPictureSize(PICTURE_WIDTH, PICTURE_HEIGHT);
        camParams.setJpegQuality(90);
        mCamera.setParameters(camParams);
//        mCamera.setDisplayOrientation(90);


        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        btCapture = (ImageButton) findViewById(R.id.bt_capture);
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** This method is asynchronous. Therefore, the following approach tries to solve the communication problem */
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // Load the next Activity used to show the picture taken
                        Intent confirmationIntent = new Intent(CameraActivity.this, CameraDisplayActivity.class);
                        confirmationIntent.putExtra("picture", getOutputPhotoFile().toString());
                        startActivityForResult(confirmationIntent, PICTURE_CONFIRMATION);
                    }
                };

                Runnable runnable = new Runnable() {
                    public void run() {
                        // Insert network call here!
                        mCamera.takePicture(null, null, null, mPicture); // Using jpeg
                        handler.sendEmptyMessage(0);
                    }
                };
                Thread mythread = new Thread(runnable);
                mythread.start();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == PICTURE_CONFIRMATION) {
            if (requestCode == RESULT_OK) {

            } else if (requestCode == RESULT_CANCELED) {   // User
                System.out.println("voltando");
            }
        }
    }


    /** Responsible to process the returned image from the camera */
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputPhotoFile();
            if (pictureFile == null){
                // Error
                return;
            }

            try{
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();          // release the camera immediately on pause event
    }

    @Override
    protected void onResume(){
        super.onResume();
        createCamera();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }
        int result = (info.orientation - degrees + 360) % 360;
        mCamera.setDisplayOrientation(result);
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
