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
            double[] t = multiplyValue(alphaK, pk);
            double[] gradientNext = sum(gradient, multiplyValue(alphaK, Apk));
            xk = sum(xk, t);
            double betaK = Math.pow(length(gradientNext), 2) / Math.pow(length(gradient), 2);
            pk = sum(negate(gradientNext), multiplyValue(betaK, pk));
            pointsForOutput.add(t[0]);
            pointsForOutput.add(t[1]);
            if (length(gradientNext) < epsilon) {
                break;
            }
            gradient = gradientNext;
        }
        writeInFile("CGM.out", pointsForOutput);
        System.out.print(steps + "\n ");
        return xk;
    }




    // for point 3, matrix is diagonal, so matrix is presented as lambda function "matrix"
    public double[] runImpl(BiFunction<Integer, Integer, Double> matrix, double[] b, double[] xk, double epsilon) {
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
            gradient = gradientNext;
        }
        System.out.print(iterations + " ");
        return xk;
    }


    @Override
    protected double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        throw new AssertionError("use another runImpl");
    }

}
