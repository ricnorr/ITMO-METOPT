package secondLab.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DescentGradientMethod extends AbstractGradientMethod {

    @Override
    public double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        double[] nextPoint;
        long iterations = 0;
        double lambda = 8;
        List<Double> pointsForOuput = new ArrayList<>();
        pointsForOuput.add(point[0]);
        pointsForOuput.add(point[1]);
        do {
            iterations++;
            double[] gradient = findGradient(point);
            nextPoint = subtract(point, multiplyValue(lambda, gradient));
            // Отрицание стоит из-за приколов с NaN, который появлялся на функции a -> a[0] * Math.log(Math.tan(a[0] * a[0])) + Math.cos(Math.log(a[1]));
            // Map<Integer, Function<Double, Double>> derivative = Map.of(0, x -> 2 * x * x * (Math.tan(x * x) * Math.tan(x * x) + 1) / Math.tan(x * x) + Math.log(Math.tan(x * x)),1, x -> -Math.sin(Math.log(x)) / x);
            if (!(abs(nextPoint, point) >= epsilon)) {
                break;
            }
            double fx = function.apply(point);
            double fy = function.apply(nextPoint);
            while (fy >= fx) {
                lambda /= 2;
                nextPoint = subtract(point, multiplyValue(lambda, gradient));
                fy = function.apply(nextPoint);
                // Возможно говнокод, но в таком случае при равных значениях оно хотя бы раз да выполнится,
                // а дальше уже будем считать вычисления с поблажкой на погрешность,
                // чтобы не зациклилось на маленьких значениях лямбды
                if (fy - fx >= epsilon) {
                    break;
                }
            }
            pointsForOuput.add(-multiplyValue(lambda, gradient)[0]);
            pointsForOuput.add(-multiplyValue(lambda, gradient)[1]);
            point = nextPoint;
        } while (iterations < Long.MAX_VALUE);
        writeInFile("DGM.out", pointsForOuput);
        System.out.println("iterations : " + iterations);
        return point;
    }
}
