package thirdLab;

import thirdLab.matrix.ProfileMatrix;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class MatrixUtilities {
    private static final Random random = new Random();
    public static double[][] generateMatrix() {
        int n = abs(random.nextInt()) % 100;
        double [][] matrix = new double[n][n];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < j; k++) {
                if ((j + k) % 5 == 1) {
                    matrix[j][k] = 0;
                    matrix[k][j] = 0;
                } else {
                    matrix[j][k] = max(0, random.nextDouble());
                    matrix[k][j] = max(0, random.nextDouble());
                }
            }
            matrix[j][j] = random.nextDouble();
        }
        return matrix;
    }
    public static void genWriteMatrix(String fileName) {
        ProfileMatrix a = new ProfileMatrix(generateMatrix());
        a.writeInFile(fileName);
    }
}
