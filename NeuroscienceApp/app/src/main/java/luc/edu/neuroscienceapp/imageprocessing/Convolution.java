package luc.edu.neuroscienceapp.imageprocessing;

import org.ejml.simple.SimpleMatrix;

/**
 * Convolution is the code for applying the convolution operator.
 *
 * @author: Simon Horne
 */
public class Convolution extends Thread {

    /**
     * Default no-arg constructor.
     */
    public Convolution() {
    }

    /**
     * Takes an image (grey-levels) and a kernel and a position,
     * applies the convolution at that position and returns the
     * new pixel value.
     *
     * @param input The 2D double array representing the image.
     * @param x The x coordinate for the position of the convolution.
     * @param y The y coordinate for the position of the convolution.
     * @param k The 2D array representing the kernel.
     * @param kernelWidth The width of the kernel.
     * @param kernelHeight The height of the kernel.
     * @return The new pixel value after the convolution.
     */
    public static double singlePixelConvolution(SimpleMatrix input,
                                                int x, int y,
                                                SimpleMatrix k,
                                                int kernelWidth,
                                                int kernelHeight){
        double output = 0;
        for(int i=0;i<kernelWidth;++i){
            for(int j=0;j<kernelHeight;++j){
                output = output + (input.get(x+i,y+j) * k.get(i,j));
            }
        }
        return output;
    }

    /**
     * Takes a 2D array of grey-levels and a kernel and applies the convolution
     * over the area of the image specified by width and height.
     *
     * @param input the 2D double array representing the image
     * @param width the width of the image
     * @param height the height of the image
     * @param kernel the 2D array representing the kernel
     * @param kernelWidth the width of the kernel
     * @param kernelHeight the height of the kernel
     * @return the 2D array representing the new image
     */
    public static SimpleMatrix convolution2D(SimpleMatrix input,
                                            int width, int height,
                                            SimpleMatrix kernel,
                                            int kernelWidth,
                                            int kernelHeight){
        int smallWidth = width - kernelWidth + 1;
        int smallHeight = height - kernelHeight + 1;
        SimpleMatrix output = new SimpleMatrix(smallWidth,smallHeight);
        for(int i=0;i<smallWidth;++i){
            for(int j=0;j<smallHeight;++j){
                output.set(i, j, 0);
            }
        }
        for(int i=0;i<smallWidth;++i){
            for(int j=0;j<smallHeight;++j){
            	output.set(i, j, singlePixelConvolution(input,i,j,kernel,
                        kernelWidth,kernelHeight));
            }
        }
        return output;
    }

}

