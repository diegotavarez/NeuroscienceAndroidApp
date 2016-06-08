package luc.edu.neuroscienceapp.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;

import luc.edu.neuroscienceapp.R;
import luc.edu.neuroscienceapp.views.CameraPreview;

/**
 * Created by vinicius on 6/7/16.
 *
 * This approach is deprecated in API level 21
 */
public class CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;

    /** Responsible to process the returned image from the camera */
//    private PictureCallback mPicture = new PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//
//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if (pictureFile == null){
//                // Error
//                return;
//            }
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
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        try{
            mCamera = Camera.open();

        } catch (Exception e){
            e.printStackTrace();
        }

        // To make camera parameters take effect, applications have to call setParameters(Camera.Parameters).
//        Parameters camParams = mCamera.Parameters();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }



    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
