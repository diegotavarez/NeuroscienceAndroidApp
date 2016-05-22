package luc.edu.neuroscienceapp.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

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
                result.set(i,j,pixels[pixelsIndex]);
                pixelsIndex++;
            }
        }
        return result;
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


    public void process (Bitmap bmp) {
        SimpleMatrix image = bmpToMatrix(bmp);

        int filter_size = 16;
        double gabor_sx = 2.0;
        double gabor_sy = 3.5;
        double gabor_fx = 0.2;
        double gabor_fy = 0.0;

        SimpleMatrix gb_filter = new GaborFilter(filter_size,
                                                  gabor_sx,
                                                  gabor_sy,
                                                  gabor_fx,
                                                  gabor_fy).filter;
        SimpleMatrix imGb = Convolution.convolution2D(image, image.numRows(), image.numCols(), gb_filter, filter_size, filter_size);


        // Finding images patches
        int patch_size = 8;
		int numPatches = 50000;
        int numMaxPossiblePatches = (imGb.numCols()-patch_size)*(imGb.numRows()-patch_size);
        int numMaxPatches = (numPatches <= numMaxPossiblePatches) ? numPatches : numMaxPossiblePatches;
        int numTries = numPatches*2;
        int numMaxTries = (numTries <= numMaxPossiblePatches) ? numTries : numMaxPossiblePatches;

        SimpleMatrix image_patches = new SimpleMatrix(patch_size*patch_size, numMaxPatches);
        // Getting the random pairs (x,y)
        Set<Pair<Integer,Integer>> indices = pickRandom(numMaxTries, 1, imGb.numRows()-patch_size,
                                                                     1, imGb.numCols() - patch_size);
        Iterator<Pair<Integer, Integer>> iterator = indices.iterator();

        int cnt = 0;
        while (iterator.hasNext() && cnt < numMaxPatches) {
            Pair<Integer,Integer> p = iterator.next();
            int x = p.getLeft();
            int y = p.getRight();
            SimpleMatrix window = imGb.extractMatrix(x, x+patch_size, y, y+patch_size);
            double std = calculateStd(window);
            if (std > 0) {
                window.reshape(patch_size*patch_size, 1);
                // Set the hole column in the matrix
                image_patches.insertIntoThis(0, cnt, window);
            }
            cnt++;
        }

        // This is PCA
        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
        int num_pca = 20;
        DenseMatrix64F pc = pca.pca(toDoubleMatrix(image_patches.transpose()));
        SimpleMatrix principalComponents = new SimpleMatrix(pc);
        // The columns now are the principal components, rows are features
        principalComponents = principalComponents.transpose();
        principalComponents = principalComponents.extractMatrix(0, principalComponents.numCols(), 0, num_pca);


        // This is ICA

    }

}
