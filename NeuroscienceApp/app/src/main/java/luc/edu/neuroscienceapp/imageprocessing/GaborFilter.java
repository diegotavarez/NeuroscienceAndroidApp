package luc.edu.neuroscienceapp.imageprocessing;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by llaryssa on 5/19/16.
 */
public class GaborFilter {
    public SimpleMatrix filter;

    public GaborFilter(int patchsize, double sx, double sy, double fx, double fy) {
        this.filter = new SimpleMatrix(patchsize, patchsize);
        double p1 = (1/(2*Math.PI*sx*sy));
        for (int x = 0; x < patchsize; ++x) {
            for (int y = 0; y < patchsize; ++y) {
                double xx = x - ((double)(patchsize-1))/2;
                double yy = y - ((double)(patchsize-1))/2;
                double p2 = Math.exp(-0.5*(((xx*xx)/(sx*sx))+((yy*yy)/(sy*sy))));
                double p3 = Math.cos(2*Math.PI*(fx*xx+fy*yy));
                double value = p1*p2*p3;
                this.filter.set(y, x, value);
            }
        }

    }

}

