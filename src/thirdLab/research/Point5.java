package thirdLab.research;

import thirdLab.matrix.MatrixUtilities;
import thirdLab.matrix.SparseMatrix;
import thirdLab.method.ConjugateMethod;

public class Point5 {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            SparseMatrix sparseMatrix = MatrixUtilities.generateSparseMatrix(i);
            double[] x = MatrixUtilities.generateX(sparseMatrix.getColumnNumbers());
            double[] res = sparseMatrix.smartMultiplication(x);
            double[] myRes = new ConjugateMethod().solve(sparseMatrix, res, 0.0000000001);
            Point23.printRes(sparseMatrix.getColumnNumbers(), i, x, myRes, res, sparseMatrix.smartMultiplication(myRes));
        }
    }
}
