package secondLab.method;

import firstLab.method.BrentMethod;
import firstLab.method.DihotomiaMethod;
import firstLab.method.GoldenRatioMethod;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.sqrt;

public abstract class AbstractGradientMethod implements GradientMethod {

    protected Map<Integer, Function<double[], Double>> derivative;

    protected double[] subtract(double[] firstVector, double[] secondVector) {
        assert firstVector.length == secondVector.length;
        double[] result = new double[firstVector.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = firstVector[i] - secondVector[i];
        }
        return result;
    }

    public double[] run(Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative, double[] point, double epsilon) {
        this.derivative = derivative;
        return runImpl(function, point, epsilon);
    }

    public double[] multMatrixVector(BiFunction<Integer, Integer, Double> matrix, double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += matrix.apply(i, j) * vector[j];
            }
        }
        return result;
    }


    protected abstract double[] runImpl(Function<double[], Double> function, double[] point, double epsilon);

    protected double[] sum(double[] firstVector, double[] secondVector) {
        return subtract(firstVector, negate(secondVector));
    }

    protected double[] negate(double[] vector) {
        return Arrays.stream(vector).map(x -> -x).toArray();
    }

    protected double[] multiplyValue(final double value, double[] vector) {
        return Arrays.stream(vector).map(x -> x * value).toArray();
    }

    protected double[] findGradient(double[] point) {
        double[] result = new double[point.length];
        for (int i = 0; i < point.length; i++) {
            result[i] = derivative.get(i).apply(point);
        }
        return result;
    }

    protected double[] findAntiGradient(double[] point) {
        return negate(findGradient(point));
    }

    protected double findArgMinGolden(double[] point, double[] gradient, Function<double[], Double> function) {
        return new GoldenRatioMethod(x -> function.apply(subtract(point, multiplyValue(x, findGradient(point)))))
                .run(0, 20, 0.00001);
    }

    protected double findArgMinDihotomia(double[] point, double[] gradient, Function<double[], Double> function) {
        return new DihotomiaMethod(x -> function.apply(subtract(point, multiplyValue(x, findGradient(point)))))
                .run(0, 20, 0.00001);
    }

    protected double findArgMinDihotomiaConjugate(double[] point, double[] v, Function<double[], Double> function) {
        return new DihotomiaMethod(x -> function.apply(sum(point, multiplyValue(x,v))))
                .run(-20, 20, 0.001);
    }

    protected double findArgMinParabola(double[] point, double[] gradient, Function<double[], Double> function) {
        return new DihotomiaMethod(x -> function.apply(subtract(point, multiplyValue(x, findGradient(point)))))
                .run(0, 20, 0.00001);
    }

    protected double findArgMinBrent(double[] point, double[] gradient, Function<double[], Double> function) {
        return new BrentMethod(x -> function.apply(subtract(point, multiplyValue(x, findGradient(point)))))
                .run(0, 20, 0.00001);
    }

    /**
     * Makes file for vizualization
     * @param fileName where to write file
     * @param list x,y, dx1,dy1, dx2, dy2, dx3, dy3, ....
     */
    protected void writeInFile(String fileName, List<Double> list) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName))) {
            double xPoint = list.get(0);
            double yPoint = list.get(1);
            for (int i = 2; i < list.size(); i++) {
                writer.write(xPoint + " " + yPoint + " " + list.get(i) + " " + list.get(i + 1) + System.lineSeparator());
                xPoint += list.get(i);
                yPoint += list.get(i + 1);
                i++;
            }
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    protected double abs(double[] firstPoint, double[] secondPoint) {
        double result = 0;
        for (int i = 0; i < firstPoint.length; i++) {
            result += Math.pow(firstPoint[i] - secondPoint[i], 2);
        }
        return sqrt(result);
    }

    protected double length(double[] vector) {
        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            result += vector[i] * vector[i];
        }
        return sqrt(result);
    }

    protected double scalarMult(double[] firstVector, double[] secondVector) {
        double result = 0;
        for (int i= 0;i < firstVector.length; i++) {
            result += firstVector[i] * secondVector[i];
        }
        return result;
    }


}
