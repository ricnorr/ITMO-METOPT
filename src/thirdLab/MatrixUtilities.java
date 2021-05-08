package thirdLab;

import thirdLab.matrix.ProfileMatrix;

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
        return 10 + abs(random.nextInt(991));
    }
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
    private static final double[] el = new double[]{0.0, -1.0, -2.0, -3.0, -4.0};
    private static double getAij() {
        return el[abs(random.nextInt()) % 5];
    }
    //TODO: видимо мы должны взять одну и ту же матрицу A, и прибавить к A[0][0] 10^(разные k)?
    private static double[][] generateAk(int k) {
        int n = randSize();
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                res[i][j] = getAij();
                res[j][i] = res[i][j]; //TODO: Ak тоже симметричная?
            }
        }
        for (int i = 0; i < n; i++) {
            res[i][i] = -Arrays.stream(res[i]).sum();
        }
        res[0][0] += Math.pow(10, -k);
        return res;
    }
    private static double[][] generateGilbert() {
        int n = randSize();
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = 1.0/(i + j + 1);
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
    private static double[] multMatrVect(double[][] m, double[] v) {
        double [] res = new double[v.length];
        for (int i = 0; i < res.length; i++) {
            final double vi = v[i];
            for (int j = 0; j < res.length; j++) {
                res[i] += v[j]*m[i][j];
            }
        }
        return res;
    }
    public static String vToString(double[] v) {
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
            writer.write(vToString(f) + vToString(x));
            mWriter.write(n + "\n");
            Arrays.stream(m).forEach(v -> {
                try {
                    mWriter.write(MatrixUtilities.vToString(v));
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
    public static void genWriteMatrix(String fileName) {
        ProfileMatrix a = new ProfileMatrix(generateMatrix());
        a.writeInFile(fileName);
    }
    public static double[] readLineVector(BufferedReader reader) {
        try {
            return Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToDouble(Double::valueOf).toArray();
        } catch (IOException ignored) {
        }
        return new double[0];
    }
    public static double dist(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.pow(v1[i] - v2[i], 2);
        }
        return sqrt(result);
    }
    public static double[][] readMatrix(String fileName) {
        double[][] res = new double[0][];
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            int n = Integer.parseInt(reader.readLine());
            res = new double[n][];
            for (int i = 0; i < n; i++) {
                res[i] = readLineVector(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
