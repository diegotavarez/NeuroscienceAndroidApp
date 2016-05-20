package luc.edu.neuroscienceapp.entities;

import org.ejml.data.DenseMatrix64F;

/**
 * Created by llaryssa on 5/19/16.
 */
public class GaborFilter {
    public DenseMatrix64F filter;
    public double mean;

    public GaborFilter(int patchsize, double sx, double sy, double fx, double fy) {
        this.filter = new DenseMatrix64F(patchsize, patchsize);
        double accumulator = 0;
        double pos_sum = 0;
        double neg_sum = 0;

        double p1 = (1/(2*Math.PI*sx*sy));
        for (int x = 0; x < patchsize; ++x) {
            for (int y = 0; y < patchsize; ++y) {
                double xx = x - ((double)(patchsize-1))/2;
                double yy = y - ((double)(patchsize-1))/2;
                double p2 = Math.exp(-0.5*(((xx*xx)/(sx*sx))+((yy*yy)/(sy*sy))));
                double p3 = Math.cos(2*Math.PI*(fx*xx+fy*yy));
                double value = p1*p2*p3;
                this.filter.set(y, x, value);

                if (value >= 0) pos_sum += value;
                else neg_sum += value;
                accumulator += value;
            }
        }

        this.mean = accumulator/(patchsize*patchsize);

        for (int x = 0; x < patchsize; ++x) {
            for (int y = 0; y < patchsize; ++y) {
                double value = this.filter.get(y, x);
                if (value < 0) {
                    this.filter.set(y, x, -pos_sum/neg_sum*value);
                }
            }
        }

    }

}

