package luc.edu.neuroscienceapp.entities;

import org.ejml.data.DenseMatrix64F;

/**
 * Created by llaryssa on 5/19/16.
 */
public class GaborFilter {
    public DenseMatrix64F filter;

    public GaborFilter(int sizex, int sizey, double sx, double sy, double fx, double fy) {
        this.filter = new DenseMatrix64F(sizex, sizey);
        double p1 = (1/(2*Math.PI*sx*sy));
        for (int x = 0; x < sizex; ++x) {
            for (int y = 0; y < sizey; ++y) {
                double xx = x - ((double)(sizex-1))/2;
                double yy = y - ((double)(sizey-1))/2;
                double p2 = Math.exp(-0.5*(((xx*xx)/(sx*sx))+((yy*yy)/(sy*sy))));
                double p3 = Math.cos(2*Math.PI*(fx*xx+fy*yy));
                double value = p1*p2*p3;
                this.filter.set(y, x, value);
            }
        }

    }

}

