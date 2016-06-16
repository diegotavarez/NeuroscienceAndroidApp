package luc.edu.neuroscienceapp.fastICAclone;


import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

import luc.edu.neuroscienceapp.imageprocessing.PrincipalComponentAnalysis;

/**
 * Created by vinicius on 6/15/16.
 */
public class FastICAclone {

    public FastICAclone(double[][] inVectors, int numICs){
        PrincipalComponentAnalysis pca = new PrincipalComponentAnalysis();
        SimpleMatrix pcaIn = new SimpleMatrix(inVectors.clone());

        DenseMatrix64F pc = pca.pca(pcaIn);
        SimpleMatrix principalComponents = new SimpleMatrix(pc);

        // Whitening Matrix
        SimpleMatrix whiteningMatrix = new SimpleMatrix(pc);

    }

    public double[][] getResultMatrix(){
        return null;
    }
}
