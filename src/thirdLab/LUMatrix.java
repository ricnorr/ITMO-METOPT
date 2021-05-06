package thirdLab;

import thirdLab.matrix.Matrix;

/**
 * Отдельный класс для LU матрицы, хронящей одновременно нижний и верхний триугольники
 * Получает матрицу в готовом виде
 */
public class LUMatrix {
    private final Matrix LU;

    public LUMatrix(Matrix LU) {
        this.LU = LU;
    }

    public double getElemFromL(int i, int j) {
        if (i < j) {
            return 0;
        }
        if (i == j) {
            return 1;
        }
        return LU.getElement(i, j);
    }

    public double getElemFromU(int i, int j) {
        if (i > j) {
            return 0;
        }
        return LU.getElement(i, j);
    }
}
