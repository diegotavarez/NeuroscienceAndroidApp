package luc.edu.neuroscienceapp.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import org.ejml.simple.SimpleMatrix;
import org.fastica.FastICA;
import org.fastica.FastICAException;

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

        System.out.println("Antes: "+width+" "+height);

        /*
        This section is an effort to solve the bug (crash) the app has with large images

            =>  Check the image dimensions to check either it is too big. If it's the case,
                reduce image dimensions
            =>  Those dimensions were chosen arbitrarily
         */
        int max_width, max_height;
        max_width = 1024;
        max_height = height/(width/max_width);
        if(width > max_width && height > max_height){
            bmpOriginal = Bitmap.createScaledBitmap(bmpOriginal, max_width, max_height, false);
            width = bmpOriginal.getWidth();
            height = bmpOriginal.getHeight();
        }
        /*
        End of bug solving effort
         */

        System.out.println("Depois: "+width+" "+height);

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

    public static SimpleMatrix bmpToMatrix(Bitmap source)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        SimpleMatrix result = new SimpleMatrix(height,width);
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
                result.set(i,j,r);
                pixelsIndex++;
            }
        }
        return result;
    }

    private static Bitmap matrixToBmp(SimpleMatrix image) {
        int width = image.numCols();
        int height = image.numRows();
        Bitmap bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int alpha = 255;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = (int) image.get(j,i);
                int newcolor = (alpha << 24) | (color << 16) | (color << 8) | color;
                bit.setPixel(i,j,newcolor);
            }
        }
        return bit;
    }

    private static Bitmap matrixToBmpScaled(SimpleMatrix image) {
        int width = image.numCols();
        int height = image.numRows();
        Bitmap bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int alpha = 255;
        Pair<Double,Double> minmax = elementMinMax(image);
        double min = minmax.getLeft();
        double max = minmax.getRight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double c = image.get(j,i);
                int color = (int) (255 * (c - min) / (max - min));
                int newcolor = (alpha << 24) | (color << 16) | (color << 8) | color;
                bit.setPixel(i,j,newcolor);
            }
        }
        return bit;
    }

    private static Pair<Double,Double> elementMinMax(SimpleMatrix image) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < image.numRows(); ++i) {
            for (int j = 0; j < image.numCols(); ++j) {
                double curr = image.get(i,j);
//                double curr = image.get(j,i);
                if (min > curr)
                    min = curr;
                if (curr > max)
                    max = curr;
            }
        }
        return new Pair(min,max);
    }

    static double[][] toDoubleMatrix (SimpleMatrix A) {
        double[][] result = new double[A.numRows()][A.numCols()];
        for (int i = 0; i < A.numRows(); ++i) {
            for (int j = 0; j < A.numCols(); ++j) {
                result[i][j] = A.get(i,j);
            }
        }
        return result;
    }

    private static double calculateStd(SimpleMatrix window) {
        double mean = window.elementSum() / window.getNumElements();
        double var = 0;
        for (int i = 0; i < window.numRows(); ++i) {
            for (int j = 0; j < window.numCols(); ++j) {
                var += Math.pow((window.get(i,j) - mean), 2);
            }
        }
        return Math.sqrt(var/(window.getNumElements()-1));
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

    static double[][] toMatrix (SimpleMatrix A) {
        double[][] result = new double[A.numRows()][A.numCols()];
        for (int i = 0; i < A.numRows(); ++i) {
            for (int j = 0; j < A.numCols(); ++j) {
                result[i][j] = A.get(i,j);
            }
        }
        return result;
    }

    public static Bitmap[] process(Bitmap bmp) throws FastICAException {
        return process(bmp, 150, 20);
    }

    public static Bitmap[] process(Bitmap bmp, int numPatches, int num_ica) throws FastICAException {

        SimpleMatrix image = bmpToMatrix(bmp);


        // Collecting patches
        int patch_size = 8;
        int numMaxPossiblePatches = (image.numCols()-patch_size)*(image.numRows()-patch_size);
        int numMaxPatches = (numPatches <= numMaxPossiblePatches) ? numPatches : numMaxPossiblePatches;
        int numTries = numPatches*2;
        int numMaxTries = (numTries <= numMaxPossiblePatches) ? numTries : numMaxPossiblePatches;

        SimpleMatrix image_patches = new SimpleMatrix(patch_size*patch_size, numMaxPatches);
        Set<Pair<Integer,Integer>> indices = pickRandom(numMaxTries, 1, image.numRows()-patch_size,
                1, image.numCols()-patch_size);

        Iterator<Pair<Integer, Integer>> iterator = indices.iterator();

        int cnt = 0;
        while (iterator.hasNext() && cnt < numMaxPatches) {
            Pair<Integer,Integer> p = iterator.next();
            int x = p.getLeft();
            int y = p.getRight();
            SimpleMatrix window = image.extractMatrix(x, x+patch_size, y, y+patch_size);
            double std = calculateStd(window);
            if (std > 0) {
                window.reshape(patch_size*patch_size, 1);
                image_patches.insertIntoThis(0, cnt, window);
            }
            cnt++;
        }

//        // PCA
//        int num_pca = 20;
//        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
//        DenseMatrix64F pc = pca.pca(image_patches.transpose());
//        SimpleMatrix principalComponents = new SimpleMatrix(pc);
//        principalComponents = principalComponents.transpose();
//        principalComponents = principalComponents.extractMatrix(0, principalComponents.numCols(), 0, num_pca);
//
//        Bitmap[] pca_images = new Bitmap[num_pca];
//        for (int i = 0; i < num_pca; ++i) {
//            SimpleMatrix column = principalComponents.extractMatrix(0, principalComponents.numRows(), i, i + 1);
//            column.reshape(patch_size, patch_size);
//            pca_images[i] = matrixToBmpScaled(column);
//        }

        // ICA
        double[][] X = toMatrix(image_patches);
        FastICA ica = new FastICA(X, num_ica);
        SimpleMatrix icaMatrix = new SimpleMatrix(ica.getSeparatingMatrix());
//        SimpleMatrix icaMatrix = new SimpleMatrix(ica.getMixingMatrix());

        Bitmap[] ica_images = new Bitmap[num_ica];
        for (int i = 0; i < num_ica; ++i) {
            SimpleMatrix column = icaMatrix.extractMatrix(i, i+1, 0, icaMatrix.numCols());
            column.reshape(patch_size, patch_size);
            ica_images[i] = matrixToBmpScaled(column);
        }

        return ica_images;

    }

}
