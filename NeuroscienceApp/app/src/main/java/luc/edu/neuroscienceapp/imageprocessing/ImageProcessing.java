package luc.edu.neuroscienceapp.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

import org.fastica.math.Matrix;
import luc.edu.neuroscienceapp.fastica.FastICA;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

    // Convert Bitmap to a 2D double array
    public static double[][] bmpToMatrix(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        double[][] result = new double[height][width];
        int[] pixels = new int[width*height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixelsIndex = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = pixels[pixelsIndex];
                int r = (p & 0xff0000) >> 16;
                // int g = (p & 0x00ff00) >> 8;
                // int b = (p & 0x0000ff) >> 0;
                //// We don't need g and b values, since the image is already in grayscale (r = g = b)
                result[i][j] = r;
                pixelsIndex++;
            }
        }
        return result;
    }

    // Convert a 2D double array to grayscale Bitmap
    private static Bitmap matrixToBmpScaled(double[][] image) {
        int width = image[0].length;
        int height = image.length;
        Bitmap bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int alpha = 255;
        Pair<Double,Double> minmax = elementMinMax(image);
        double min = minmax.getLeft();
        double max = minmax.getRight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double c = image[j][i];
                int color = (int) (255 * (c - min) / (max - min));
                // this is how Bitmap stores the color
                int newcolor = (alpha << 24) | (color << 16) | (color << 8) | color;
                bit.setPixel(i,j,newcolor);
            }
        }
        return bit;
    }

    // Get the minimum and maximum values of a matrix
    private static Pair<Double,Double> elementMinMax(double[][] image) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < image[0].length; ++i) {
            for (int j = 0; j < image.length; ++j) {
                double curr = image[i][j];
                if (min > curr)
                    min = curr;
                if (curr > max)
                    max = curr;
            }
        }
        return new Pair(min,max);
    }

    //
    private static double calculateStd(double[][] window) {

        double sum = 0;
        for (int i = 0; i < window.length; ++i) {
            for (int j = 0; j < window[0].length; ++j) {
                sum += window[i][j];
            }
        }

        double numel = window.length * window[0].length;
        double mean = sum / numel;

        double var = 0;
        for (int i = 0; i < window.length; ++i) {
            for (int j = 0; j < window[0].length; ++j) {
                var += Math.pow((window[i][j] - mean), 2);
            }
        }

        return Math.sqrt(var/(numel-1));
    }

    public static Set<Pair<Integer,Integer>> pickRandom(int n, int k1, int k2, int l1, int l2) {
        Random random = new Random(); // if this method is used often, perhaps define random at class level
        Set<Pair<Integer,Integer>> picked = new HashSet<Pair<Integer,Integer>>();
        while(picked.size() < n) {
            Pair<Integer,Integer> p =
                    new Pair<Integer,Integer>(k1 + random.nextInt(k2-k1),
                            l1 + random.nextInt(l2-l1));
            picked.add(p);
        }
        return picked;
    }


    public static Bitmap[] process(Bitmap bmp) throws Exception {
        return process(bmp, 500, 20);
    }

    public static Bitmap[] process(Bitmap bmp, int numPatches, int numIca) throws Exception {

        double[][] image = bmpToMatrix(bmp);

        int imageRows = image.length;
        int imageCols = image[0].length;

        // Collecting random patches
        int patchSize = 8;
        int numMaxPossiblePatches = (imageCols-patchSize)*(imageRows-patchSize);
        int numMaxPatches = (numPatches <= numMaxPossiblePatches) ? numPatches : numMaxPossiblePatches;
        int numTries = numPatches*2;
        int numMaxTries = (numTries <= numMaxPossiblePatches) ? numTries : numMaxPossiblePatches;

        double[][] imagePatches = new double[numMaxPatches][patchSize*patchSize];

        // Generating pairs with non-repeating indices in the image
        Set<Pair<Integer,Integer>> indices =
                pickRandom(numMaxTries, 1, imageRows-patchSize, 1, imageCols-patchSize);

        Iterator<Pair<Integer, Integer>> iterator = indices.iterator();

        int row = 0, col = 0;
        while (iterator.hasNext() && row < numMaxPatches) {
            Pair<Integer,Integer> p = iterator.next();
            int x = p.getLeft();
            int y = p.getRight();
            // still have to check if (std > 0)
            for (int xx = x; xx < (x + patchSize); xx++) {
                for (int yy = y; yy < (y + patchSize); yy++) {
                    imagePatches[row][col] = image[xx][yy];
                    col++;
                }
            }
            col = 0; row++;
        }

        // ICA
        FastICA ica = new FastICA();
        ica.fit(imagePatches, numIca);
        double[][] icaMatrix = Matrix.mult(ica.getK(), ica.getW());


        // The columns of icaMatrix are the independent components
        // Here we convert them to 8x8 Bitmap windows
        Bitmap[] icaImages = new Bitmap[numIca];
        double[][] column = new double[patchSize][patchSize];
        for (int c = 0; c < numIca; c++) {
            for (int i = 0, cnt = 0; i < patchSize; i++) {
                for (int j = 0; j < patchSize; j++) {
                    column[i][j] = icaMatrix[cnt][c];
                    cnt++;
                }
            }
            icaImages[c] = matrixToBmpScaled(column);
        }

        return icaImages;
    }

}
