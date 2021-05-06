package thirdLab.matrix;

/**
 * Сделано для возможности использования в разных форматах
 */
public abstract class AbstractMatrix implements Matrix {
    public double[][] baseLUDecomposition(Matrix m) {
        double[][] LU = new double[m.getRowNumbers()][m.getColumnNumbers()];
        for (int i = 0; i < LU.length; i++) {
            for (int j = 0; j < LU.length; j++) {
                LU[i][j] = 0;
            }
        }
        for (int i = 0; i < LU.length; i++) {
            for (int j = 0; j < LU.length; j++) {
                double sum = 0;
                for (int k = 0; k < i - 1; k++) {
                    sum += LU[i][k] * LU[k][j];
                }
                LU[i][j] -= sum;
                if (i > j) {
                    LU[i][j] /= LU[j][j];
                }
            }
        }
        return LU;
    }
}
