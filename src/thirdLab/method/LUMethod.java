package thirdLab.method;

import thirdLab.matrix.LUMatrix;
import thirdLab.matrix.MatrixUtilities;
import thirdLab.exception.NoExactSolutionException;
import thirdLab.exception.NoSolutionException;
import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

public class LUMethod {
    private final double EPSILON = 0.0000000001;

    private boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
    public double[] solve(Matrix m, double[] b) {
        // Нужна проверка на совпадение размерностей
        LUMatrix LU = MatrixUtilities.LUDecomposition(m);
        return reverseWalk(LU, directWalk(LU, b));
    }

    /**
     * Решение системы Ly = b
     * В L в диагонали всегда единицы - решение будет всегда
     */
    private double[] directWalk(LUMatrix LU, double[] b) {
        int n = LU.size();
        double[] y = new double[n];
        y[0] = b[0];
        for (int i = 1; i < n; i++) {
            double sum = 0;
            for (int k = 0; k < i; k++) {
                sum += LU.getElemFromL(i, k) * y[k];
            }
            y[i] = b[i] - sum;
        }
        return y;
    }

    /**
     * Решение системы Ux = y
     */
    private double[] reverseWalk(LUMatrix LU, double[] y) {
        int n = LU.size();
        double[] x = new double[n];
        if (equals(LU.getElemFromU(n - 1, n - 1), 0)) {
            if (equals(y[n - 1],0)) {
                throw new NoExactSolutionException();
            } else {
                throw new NoSolutionException();
            }
        }
        x[n - 1] = y[n - 1] / LU.getElemFromU(n - 1, n - 1);
        for (int i = n - 2; i >= 0; i--) {
            double sum = 0;
            for (int k = n - 1; k > i; k--) {
                sum += LU.getElemFromU(i, k) * x[k];
            }
            if (equals(LU.getElemFromU(i, i), 0)) {
                if (equals(y[i] - sum,0)) {
                    throw new NoExactSolutionException();
                } else {
                    throw new NoSolutionException();
                }
            }
            x[i] = (y[i] - sum) / LU.getElemFromU(i, i);
        }
        return x;
    }

    public static void main(String[] args) {
        double[][] manySolutions = {{2, 3, -1, 1}, {8, 12, -9, 8}, {4, 6, 3, -2}, {2, 3, 9, -7}};
        double[] b1 = {1, 3, 3, 3};
        double[][] matrixOneSolution2 = {{2, -1, 5}, {1, 1, -3}, {2, 4, 1}};
        double[] b2 = {10, -2, 1};
        double[][] matrixNoSolutions2 = {{1, 1, -1}, {2, -1, -1}, {4, 1, -3}};
        double[] b3 = {0, -2, 5};
        double[][] matrixOneSolution = {{2, 1, 1}, {1, -1, 0}, {3, -1, 2}};
        double[] b4 = {2, -2, 2};
        Matrix m = new StandardMatrix(matrixOneSolution);
        LUMethod method = new LUMethod();
        double[] res = method.solve(m, b4);
        for (double re : res) {
            System.out.print(re + " ");
        }
    }
}
