package secondLab.method;

import java.util.function.Function;

public class DescentGradientMethod extends AbstractGradientMethod {

    @Override
    public double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        double[] nextPoint;
        long iterations = 0;
        double lambda = 8;
        do {
            iterations++;
            double[] gradient = findGradient(point);
            nextPoint = subtract(point, multiplyValue(lambda, gradient));
            if (abs(nextPoint, point) <= epsilon) {
                break;
            }
            double fx = function.apply(point);
            double fy = function.apply(nextPoint);
            while (fy >= fx) {
                lambda /= 2;
                nextPoint = subtract(point, multiplyValue(lambda, gradient));
                fy = function.apply(nextPoint);
                if (fy - fx <= epsilon) {
                    break;
                }
            }
            point = nextPoint;
        } while (iterations < Long.MAX_VALUE);
        System.out.print(iterations + " ");
        return point;
    }
}
