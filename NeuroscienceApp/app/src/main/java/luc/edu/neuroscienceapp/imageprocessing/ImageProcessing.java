package luc.edu.neuroscienceapp.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

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

    public static SimpleMatrix bmpToMatrix(Bitmap source)
    {
        int width = source.getWidth();
        int height = source.getHeight();
        SimpleMatrix result = new SimpleMatrix(width,height);
        int[] pixels = new int[width*height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixelsIndex = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
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
                double curr = image.get(j,i);
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


    public static Bitmap[] process(Bitmap bmp) {

        SimpleMatrix image = bmpToMatrix(bmp);

        int filter_size = 16;
        double gabor_sx = 2.0;
        double gabor_sy = 3.5;
        double gabor_fx = 0.2;
        double gabor_fy = 0.0;

        Log.v("bla bla", "initializing gabor filter...");
        SimpleMatrix gb_filter = new GaborFilter(filter_size,
                gabor_sx,
                gabor_sy,
                gabor_fx,
                gabor_fy).filter;
        SimpleMatrix imGb = Convolution.convolution2D(image, image.numRows(), image.numCols(), gb_filter, filter_size, filter_size);

        Log.v("bla bla", "done with gabor filter and convolution");
        Log.v("bla bla", "starting to get the patches...");

        // Finding images patches
        int patch_size = 8;
        int numPatches = 50000;
        int numMaxPossiblePatches = (imGb.numCols() - patch_size) * (imGb.numRows() - patch_size);
        int numMaxPatches = (numPatches <= numMaxPossiblePatches) ? numPatches : numMaxPossiblePatches;
        int numTries = numPatches * 2;
        int numMaxTries = (numTries <= numMaxPossiblePatches) ? numTries : numMaxPossiblePatches;

        SimpleMatrix image_patches = new SimpleMatrix(patch_size * patch_size, numMaxPatches);
        // Getting the random pairs (x,y)
        Set<Pair<Integer, Integer>> indices = pickRandom(numMaxTries, 1, imGb.numRows() - patch_size,
                                                                      1, imGb.numCols() - patch_size);
        Iterator<Pair<Integer, Integer>> iterator = indices.iterator();

        int cnt = 0;
        while (iterator.hasNext() && cnt < numMaxPatches) {
            Pair<Integer, Integer> p = iterator.next();
            int x = p.getLeft();
            int y = p.getRight();
            SimpleMatrix window = imGb.extractMatrix(x, x + patch_size, y, y + patch_size);
            double std = calculateStd(window);
            if (std > 0) {
                window.reshape(patch_size * patch_size, 1);
                // Set the hole column in the matrix
                image_patches.insertIntoThis(0, cnt, window);
            }
            cnt++;
        }

        Log.v("bla bla", "patches completed...");
        Log.v("bla bla", "initializing PCA...");

        // This is PCA
        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
        int num_pca = 20;
        SimpleMatrix patches_trans = image_patches.transpose();
        DenseMatrix64F pc = pca.pca(patches_trans);
        SimpleMatrix principalComponents = new SimpleMatrix(pc);

        // The columns now are the principal components, rows are features
        principalComponents = principalComponents.transpose();
        principalComponents = principalComponents.extractMatrix(0, principalComponents.numCols(), 0, num_pca);

        Log.v("bla bla", "finished PCA...");

        Log.v("bla bla", "making Bitmap array out of PCA results...");

        Bitmap[] pca_images = new Bitmap[num_pca];


        for (int i = 0; i < num_pca; ++i) {
            SimpleMatrix column = principalComponents.extractMatrix(0, principalComponents.numRows(), i, i+1);
            column.reshape(patch_size,patch_size);
            pca_images[i] = matrixToBmpScaled(column);
        }

        Log.v("bla bla", "finished process!");

        // This is ICA
//        int ica_comp = 20; // components we want to use
//        double[][] X = toMatrix(image_patches);
//        FastICA ica = new FastICA(X, ica_comp);
//        double[][] mixing = ica.getMixingMatrix();

        return pca_images;

    }

}
