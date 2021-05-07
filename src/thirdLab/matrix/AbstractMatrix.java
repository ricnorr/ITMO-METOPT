package thirdLab.matrix;

import java.util.Arrays;

/**
 * Сделано для возможности использования в разных форматах
 */
public abstract class AbstractMatrix implements Matrix {
    public double[][] baseLUDecomposition(Matrix m) {
        double[][] LU = new double[m.getRowNumbers()][m.getColumnNumbers()];
        for (int i = 0; i < LU.length; i++) {
            Arrays.fill(LU[i], 0);
        }
        for (int i = 0; i < LU.length; i++) {
            double sum = 0;
            for (int k = 0; k < i; k++) {
                sum += LU[i][k] * LU[k][i];
            }
            LU[i][i] = m.getElement(i, i) - sum;
        }
        for (int i = 1; i < LU.length; i++) {
            for (int j = 0; j < i; j++) {
                double sum1 = 0;
                for (int k = 0; k < j; k++) {
                    sum1 += LU[i][k] * LU[k][j];
                }
                LU[i][j] = m.getElement(i, j) - sum1;
                double sum2 = 0;
                for (int k = 0; k < j; k++) {
                    sum2 += LU[j][k] * LU[k][i];
                }
                LU[j][i] = (m.getElement(j, i) - sum2) / LU[j][j];
            }
        }
        return LU;
    }
}
