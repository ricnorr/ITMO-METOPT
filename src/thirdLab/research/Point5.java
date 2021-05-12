package thirdLab.research;

import thirdLab.matrix.MatrixUtilities;
import thirdLab.matrix.SparseMatrix;
import thirdLab.method.ConjugateMethod;

public class Point5 {
    public static void main(String[] args) {
        for (int i = 10; i < 100000; i += (i / 100) + 1) {
            SparseMatrix sparseMatrix = MatrixUtilities.generateSparseMatrix(1, i);
            double[] x = MatrixUtilities.generateX(sparseMatrix.getColumnNumbers());
            double[] res = sparseMatrix.smartMultiplication(x);
            double[] myRes = new ConjugateMethod().solve(sparseMatrix, res, 0.0000000001);
            Point23.printRes(sparseMatrix.getColumnNumbers(), i, x, myRes, res, sparseMatrix.smartMultiplication(myRes), ConjugateMethod.lastIterations);
        }
    }
}
