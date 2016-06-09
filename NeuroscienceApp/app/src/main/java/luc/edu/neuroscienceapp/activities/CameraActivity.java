package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Surface;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.utils.FileManager;
import luc.edu.neuroscienceapp.views.CameraPreview;

/**
 * Created by vinicius on 6/7/16.
 *
 * This approach is deprecated in API level 21
 */
public class CameraActivity extends Activity {
    public String photoFileName = "photo.jpg";
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
                mCamera.takePicture(null, null, null, mPicture); // Using jpeg
                // Load the next Activity used to show the picture taken

            }
        });
    }


    /** Responsible to process the returned image from the camera */
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
//
            File pictureFile = getOutputMediaFile();
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
//
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                fos.close();
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
        }
    };

    private static File getOutputMediaFile() {
        FileManager manager = new FileManager();
        File mediaStorageDir;
        try{
            mediaStorageDir = manager.createTemporaryFile("photo","jpg");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
//
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
//                .format(new Date());
//        File mediaFile;
//        mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                + "IMG_" + timeStamp + ".jpg");

        return mediaStorageDir;
    }

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
