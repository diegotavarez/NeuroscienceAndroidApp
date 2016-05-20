package luc.edu.neuroscienceapp.imageprocessing;

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

    public static DenseMatrix64F bmpToMatrix(Bitmap source)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        DenseMatrix64F result = new DenseMatrix64F(width,height);
        int[] pixels = new int[width*height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixelsIndex = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result.add(i,j,pixels[pixelsIndex]);
                pixelsIndex++;
            }
        }
        return result;
    }


    public void process (Bitmap bmp) {
        DenseMatrix64F img = bmpToMatrix(bmp);

    }

}
