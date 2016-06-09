package luc.edu.neuroscienceapp.views;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by captain on 6/9/16.
 */
public class CameraDisplayPreview extends SurfaceView implements SurfaceHolder.Callback {

    public CameraDisplayPreview(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

    }
}
