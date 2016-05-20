package luc.edu.neuroscienceapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import org.ejml.data.DenseMatrix64F;

/**
 * Created by diegotavarez on 5/19/16.
 */
public class ImageProcessing {

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public DenseMatrix64F bitmap2Matrix(Bitmap bmp)
    {
        int width, height;
        height = bmp.getHeight();
        width = bmp.getWidth();
        DenseMatrix64F matrix = new DenseMatrix64F(width, height);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                matrix.add(i,j,bmp.getPixel(i,j));
            }
        }
        return matrix;
    }


    public void process (Bitmap bmp) {
        DenseMatrix64F img = bitmap2Matrix(bmp);

    }

}
