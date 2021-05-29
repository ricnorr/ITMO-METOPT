package fourthLab.point2;

import firstLab.method.GoldenRatioMethod;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.sqrt;

// 1 var
//метод Давидона-Флетчера-Пауэлла и метод Пауэлла

public class KvasiNewton {
    private static double EPSILON = 0.0000001;
    private static int MAX_ITERATIONS = 2048;

    public double[] runDavidonFletcherPauell(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[][] Hk = generateI(point.length);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double[] way = multMatrix(Hk, point);
            double[] finalPoint = point;
            double alpha = new GoldenRatioMethod(x -> function.apply(subtract(finalPoint, multVector(way, x)))).run(-100, 100, EPSILON);
            double[] newPoint = subtract(point, multVector(way, alpha));
            if (length(subtract(newPoint, point)) < EPSILON) {
                break;
            }
            Hk = getNextDavidonFLetcherPauellH(Hk, subtract(newPoint, point), subtract(getGradient(derivative, newPoint), getGradient(derivative, point)));
            point = newPoint;
        }
        return point;
    }

    /**
     * Returns gradient in point
     */
    public double[] getGradient(BiFunction<Integer, double[], Double> derivative, double[] point) {
        double[] gradient = new double[point.length];
        for (int i = 0; i < gradient.length; i++) {
            gradient[i] = derivative.apply(i, point);
        }
        return gradient;
    }

    /**
     * Returns length of vector
     */
    public double length(double[] vector) {
        return sqrt(Arrays.stream(vector).map(x -> x * x).sum());
    }

    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextDavidonFLetcherPauellH(double[][] Hk, double[] sk, double[] yk) {
        return getNextDavidonFLetcherPauellH(Hk, vectorToMatrix(sk), vectorToMatrix(yk));
    }

    /**
     * Generates matrix with 1 on diagonal
     */
    public double[][] generateI(int n) {
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            result[i][i] = 1;
        }
        return result;
    }

    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextDavidonFLetcherPauellH(double[][] Hk, double[][] sk, double[][] yk) {
        return sumMatrix(
                subtractMatrix(
                        Hk, divideMatrix(multMatrix(multMatrix(multMatrix(Hk, yk), transpose(yk)), Hk), scalarMult(multMatrix(Hk, yk), yk))),
                divideMatrix(multMatrix(sk, transpose(sk)), scalarMult(yk, sk)));
    }

    public double[] getNextPauellH(double[][] Hk, double[][] sk, double[][] yk) {
        subtract(Hk,
                divideMatrix(multMatrix(sk, transpose(sk))
        )
    }

    /**
     * Converts matrix to vector if possible
     */
    public double[] fromMatrixToVector(double[][] a) {
        assert (a[0].length == 1);
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i][0];
        }
        return result;
    }

    /**
     * Subtract vectors
     */
    public double[] subtract(double[] a, double[] b) {
        return fromMatrixToVector(subtractMatrix(vectorToMatrix(a), vectorToMatrix(b)));
    }

    /**
     * Sum vectors
     */
    public double[][] sumMatrix(double[][] a, double[][] b) {
        assert (a.length == b.length);
        assert (a[0].length == b[0].length);
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    /**
     * Mult matrixes
     */
    public double[][] multMatrix(double[][] a, double[][] b) {
        assert (a[0].length == b.length);
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] += a[i][j] * b[j][i];
            }
        }
        return result;
    }

    /**
     * Mult matrix and vector
     */
    public double[] multMatrix(double[][] a, double[] b) {
        return fromMatrixToVector(multMatrix(a, vectorToMatrix(b)));
    }

    /**
     * Mult vector and scalar
     */
    public double[] multVector(double[] a, double b) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] += a[i] * b;
        }
        return result;
    }

    /**
     * Divides matrix on constant
     */
    public double[][] divideMatrix(double[][] a, double b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] / b;
            }
        }
        return result;
    }

    /**
     * Converts vector to matrix
     * [1,2,3] -> [[1], [2], [3]]
     */
    public double[][] vectorToMatrix(double[] a) {
        double[][] result = new double[a.length][1];
        for (int i = 0; i < a.length; i++) {
            result[i][0] = a[i];
        }
        return result;
    }

    /**
     * Subtracts matrixes
     */
    public double[][] subtractMatrix(double[][] a, double[][] b) {
        assert (a.length == b.length);
        assert (a[0].length == b[0].length);
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    /**
     * Scalar multiplication for vectors in "matrix form".
     */
    public double scalarMult(double[][] a, double[][] b) {
        assert (a.length == b.length);
        assert (a[0].length == 1);
        assert (b[0].length == 1);
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i][0] * b[i][0];
        }
        return result;
    }

    /**
     * Transpose matrix
     */
    public double[][] transpose(double[][] a) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[j][i] = a[i][j];
            }
        }
        return result;
    }
}

