package secondLab.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Double.NaN;
import static java.lang.Double.min;

public class ConjugateGradientMethod extends AbstractGradientMethod {
    /*private double findBeta(double[] point, double[] nextPoint) {
        return Math.pow(norma(findGradient(nextPoint)) /
                        norma(findGradient(point)),
                2);
    }*/


    /* @Override
    public double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        double[] nextPoint = point;
        int iterations = 0;
        double[] p = findAntiGradient(nextPoint);
        List<Double> pointsForOutput = new ArrayList<>();
        pointsForOutput.add(point[0]);
        pointsForOutput.add(point[1]);
        int n = point.length;
        do {
            point = nextPoint;
            iterations++;
            double alpha = findArgMinDihotomiaConjugate(point, p, function);
            nextPoint = sum(point, multiplyValue(alpha, p));
            pointsForOutput.add(multiplyValue(alpha, p)[0]);
            pointsForOutput.add(multiplyValue(alpha, p)[1]);
            if (norma(findGradient(nextPoint)) < epsilon) break;
            double beta = iterations % point.length == 0 ? 0 : findBeta(point, nextPoint);
            p = sum(findAntiGradient(nextPoint), multiplyValue(beta, p));
        } while (iterations < MAX_ITERATIONS);
        System.out.println(iterations);
        writeInFile("CGM.out", pointsForOutput);
        return nextPoint;
    } */

    public double[] multMatrixVector(double[][] matrix, double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }


    public double[] runImpl(double[][] a, double[] b, double[] xk, double epsilon) {
        List<Double> pointsForOutput = new ArrayList<>();
        long steps = 0;
        pointsForOutput.add(xk[0]);
        pointsForOutput.add(xk[1]);
        double[] gradient = sum(multMatrixVector(a, xk), b);
        double[] pk = negate(gradient);
        while (steps++ < Long.MAX_VALUE) {
            double[] Apk = multMatrixVector(a, pk);
            double alphaK = Math.pow(length(gradient), 2) / scalarMult(Apk, pk);
            double[] gradientNext = sum(gradient, multiplyValue(alphaK, Apk));
            xk = sum(xk, multiplyValue(alphaK, pk));
            double betaK = Math.pow(length(gradientNext), 2) / Math.pow(length(gradient), 2);
            pk = sum(negate(gradientNext), multiplyValue(betaK, pk));
            if (length(gradientNext) < epsilon) {
                break;
            }
            pointsForOutput.add(gradientNext[0]);
            pointsForOutput.add(gradientNext[1]);
            gradient = gradientNext;
        }
        writeInFile("CGM.out", pointsForOutput);
        System.out.print(steps + " ");
        return xk;
    }

    public double[] runImpl(BiFunction<Integer, Integer, Double> matrix, double[] b, double[] xk, double epsilon) {
        List<Double> pointsForOutput = new ArrayList<>();
        pointsForOutput.add(xk[0]);
        pointsForOutput.add(xk[1]);
        double[] gradient = sum(multMatrixVector(matrix, xk), b);
        double[] pk = negate(gradient);
        long iterations = 0;
        while (iterations < Long.MAX_VALUE) {
            iterations++;
            double[] Apk = multMatrixVector(matrix, pk);
            double alphaK = Math.pow(length(gradient), 2) / scalarMult(Apk, pk);
            double[] gradientNext = sum(gradient, multiplyValue(alphaK, Apk));
            xk = sum(xk, multiplyValue(alphaK, pk));
            double betaK = Math.pow(length(gradientNext), 2) / Math.pow(length(gradient), 2);
            pk = sum(negate(gradientNext), multiplyValue(betaK, pk));
            if (length(gradientNext) < epsilon) {
                break;
            }
            pointsForOutput.add(gradientNext[0]);
            pointsForOutput.add(gradientNext[1]);
            gradient = gradientNext;
        }
        System.out.print(iterations + " ");
        //writeInFile("CGM.out", pointsForOutput);
        return xk;
    }


    @Override
    protected double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        throw new AssertionError("use another runImpl");
    }

}
