package thirdLab.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class MatrixUtilities {
    private static final Random random = new Random();

    private static int randSize() {
        return 10 + abs(random.nextInt(211));
        //return 220;
    }

    public static double EPSILON = 0.0000000000000000000000000000000000001;

    private static double[][] matrixA = null;

    public static double[][] generateMatrix() {
        int n = abs(random.nextInt()) % 100;
        double[][] matrix = new double[n][n];
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

    private static double[] el = new double[]{0.0, -1.0, -2.0, -3.0, -4.0};

    private static double getAij() {
        return el[abs(random.nextInt(5))];
    }

    private static double[][] generateAk(int k) {
        if (matrixA != null) {
            matrixA[0][0] = 0;
            matrixA[0][0] = -Arrays.stream(matrixA[0]).sum();
            matrixA[0][0] += Math.pow(0.1, k);
            return matrixA;
        }
        int n = randSize();
        matrixA = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                matrixA[i][j] = getAij();
                matrixA[j][i] = getAij();
                if (!equals(matrixA[i][j], 0) && equals(matrixA[j][i], 0)) {
                    matrixA[j][i] = -1.0;
                }
                if (equals(matrixA[i][j], 0) && !equals(matrixA[j][i], 0)) {
                    matrixA[i][j] = -1.0;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            matrixA[i][i] = -Arrays.stream(matrixA[i]).sum();
        }
        matrixA[0][0] += Math.pow(0.1, k); // обычно k = 0, но для общего случая пусть так
        return matrixA;
    }

    private static double[][] generateGilbert() {
        int n = randSize();
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = 1.0 / (i + j + 1);
            }
        }
        return res;
    }

    private static double[] generateX(int n) {
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = 1 + i;
        }
        return res;
    }
    /*private static double[] multMatrVect(double[][] m, double[] v) {
        double [] res = new double[v.length];
        for (int i = 0; i < res.length; i++) {
            final double vi = v[i];
            res[i] = Arrays.stream(m[i]).map(x -> x * vi).sum();
        }
        return res;
    }*/


    public static double[] multMatrVect(double[][] m, double[] v) {
        double[] res = new double[v.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res.length; j++) {
                res[i] += m[i][j] * v[j];
            }
        }
        return res;
    }


    public static String vectorToString(double[] v) {
        return Arrays.stream(v).mapToObj(Double::toString).collect(Collectors.joining(" ", "", "\n"));
    }
    private static void genWriteSoLE(String fileName, double[][] m) {
        int n = m.length;
        double[] x = generateX(n);
        double[] f = multMatrVect(m, x);
        ProfileMatrix matrix = new ProfileMatrix(m);
        matrix.writeInFile(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName), StandardOpenOption.APPEND);
             BufferedWriter mWriter = Files.newBufferedWriter(Path.of(fileName.substring(0, fileName.indexOf('.')) + "matr" + ".txt"))) {
            writer.write(vectorToString(f) + vectorToString(x));
            mWriter.write(n + "\n");
            Arrays.stream(m).forEach(v -> {
                try {
                    mWriter.write(MatrixUtilities.vectorToString(v));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void genWriteAkSoLE(String fileName, int k) {
        genWriteSoLE(fileName, generateAk(k));
    }

    public static void genWriteGilbertSoLE(String fileName, int k) {
        genWriteSoLE(fileName, generateGilbert());
    }

    public static void generateMatrixWriteInFile(String fileName) {
        ProfileMatrix a = new ProfileMatrix(generateMatrix());
        a.writeInFile(fileName);
    }

    public static double[] readDoubleVector(BufferedReader reader) {
        try {
            return Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToDouble(Double::valueOf).toArray();
        } catch (IOException ignored) {
        }
        return new double[0];
    }

    public static int[] readIntVector(BufferedReader reader) {
        try {
            return Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToInt(Integer::valueOf).toArray();
        } catch (IOException ignored) {
        }
        return new int[0];
    }

    public static double dist(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.pow(v1[i] - v2[i], 2);
        }
        return sqrt(result);
    }

    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }


    public static void swapLines(double[][] matrix, int fromLine, int toLine) {
        double[] temp = new double[matrix[0].length];
        System.arraycopy(matrix[fromLine], 0, temp, 0, matrix[0].length);
        matrix[fromLine] = matrix[toLine];
        matrix[toLine] = temp;
    }

    public static void swapElements(double[] a, int indexA, int indexB) {
        double x = a[indexA];
        a[indexA] = a[indexB];
        a[indexB] = x;
    }

    public static void subtract(double[] where, double[] what, double coefficient) {
        for (int i = 0; i < where.length; i++) {
            where[i] -= coefficient * what[i];
        }
    }

    public static void subtract(double[] a, int where, int what, double coefficient) {
        a[where] -= a[what] * coefficient;
    }

    public static int countNoZeroElements(double[][] matrix, int row) {
        int cnt = 0;
        for (int i = 0; i < matrix.length - 1; i++) {
            if (!equals(matrix[row][i], 0)) {
                cnt++;
            }
        }
        return cnt;
    }


    private static double[][] baseLUDecomposition(Matrix m) {
        double[][] LU = new double[m.getRowNumbers()][m.getColumnNumbers()];
        /* for (int i = 0; i < LU.length; i++) {
            LU[i][i] = 1;
        }*/
        // Вариант кода с лекции
        /*for (int i = 0; i < LU.length; i++) {
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
        }*/

        // Вариант кода с википедии
        for (int i = 0; i < LU.length; i++) {
            for (int j = 0; j < LU[i].length; j++) {
                double sum = 0;
                if (i <= j) {
                    for (int k = 0; k < i; k++) {
                        sum += LU[i][k] * LU[k][j];
                    }
                    LU[i][j] = m.getElement(i, j) - sum;
                } else {
                    for (int k = 0; k < j; k++) {
                        sum += LU[i][k] * LU[k][j];
                    }
                    // Не уверен в правильности этой строчки, но нигде информацию не нашел про деление на 0
                    if (LU[j][j] == 0) {
                        LU[i][j] = 0;
                        continue;
                    }
                    LU[i][j] = (m.getElement(i, j) - sum) / LU[j][j];
                }
            }
        }

        return LU;
    }


    public static double[][] readMatrix(String fileName) {
        double[][] res = new double[0][];
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            int n = Integer.parseInt(reader.readLine());
            res = new double[n][];
            for (int i = 0; i < n; i++) {
                res[i] = readDoubleVector(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // Пока StandardMatrix, не учел, что ProfileMatrix не поддерживает несимметричные матрицы
    public static LUMatrix LUDecomposition(Matrix matrix) {
        return new LUMatrix(new StandardMatrix(baseLUDecomposition(matrix)));
    }
}
