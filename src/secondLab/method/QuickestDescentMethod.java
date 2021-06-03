package secondLab.method;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;



public class QuickestDescentMethod extends AbstractGradientMethod {

    @Override
    public double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        double[] nextPoint = point;
        long iterations = 0;
        do {
            iterations++;
            point = nextPoint;
            double[] gradient = findGradient(point);
            double lambda = findArgMinGolden(point, gradient, function);
            nextPoint = subtract(point, multiplyValue(lambda, gradient));
        } while (iterations < 4096 * 256 && abs(nextPoint, point) >= epsilon);
        System.out.print(iterations + " ");
        return point;
    }
}
