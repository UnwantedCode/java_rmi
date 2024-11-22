package shared;

import java.io.Serializable;

public class MatrixFragment implements Serializable {
    public double[][] matrixA;
    public double[] vectorB;

    public MatrixFragment(double[][] matrixA, double[] vectorB) {
        this.matrixA = matrixA;
        this.vectorB = vectorB;
    }
}
