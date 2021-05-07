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
            for (int j = 0; j < LU.getColumnNumbers(); j++) {
                System.out.print(getElemFromL(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println("U:");
        for (int i = 0; i < LU.getRowNumbers(); i++) {
            for (int j = 0; j < LU.getColumnNumbers(); j++) {
                System.out.print(getElemFromU(i, j) + " ");
            }
            System.out.println();
        }
    }

    // Матрица квадратная
    public int size() {
        return LU.getColumnNumbers();
    }

    public static void main(String[] args) {
        double[][] manySolutions = {{2, 3, -1, 1}, {8, 12, -9, 8}, {4, 6, 3, -2}, {2, 3, 9, -7}};
        System.out.println("Matrix:");
        for (double[] manySolution : manySolutions) {
            for (double v : manySolution) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        Matrix m = new StandardMatrix(manySolutions);
        LUMatrix LU = m.LUDecomposition();
        LU.testPrint();
        double[][] c = new double[manySolutions.length][manySolutions.length];
        for (int i=0; i<manySolutions.length; ++i) {
            for (int j = 0; j < manySolutions.length; ++j) {
                for (int k = 0; k < manySolutions.length; ++k) {
                    c[i][j] += LU.getElemFromL(i, k) * LU.getElemFromU(k, j);
                }
            }
        }
        System.out.println("Multiply (not working probably):");
        for (double[] doubles : c) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
    }
}
