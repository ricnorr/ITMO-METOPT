package thirdLab;

import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

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

    public void testPrint() {
        System.out.println("L:");
        for (int i = 0; i < LU.getRowNumbers(); i++) {
            for (int j = 0; j < LU.getColumnNumbers(); i++) {
                System.out.print(getElemFromL(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println("U:");
        for (int i = 0; i < LU.getRowNumbers(); i++) {
            for (int j = 0; j < LU.getColumnNumbers(); i++) {
                System.out.print(getElemFromU(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[][] manySolutions = {{2, 3, -1, 1}, {8, 12, -9, 8}, {4, 6, 3, -2}, {2, 3, 9, -7}};
        Matrix m = new StandardMatrix(manySolutions);
        LUMatrix LU = m.LUDecomposition();
        LU.testPrint();
    }
}
