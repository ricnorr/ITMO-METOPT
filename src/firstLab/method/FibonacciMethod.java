package firstLab.method;

import java.util.function.Function;

import static java.lang.Math.sqrt;

public class FibonacciMethod extends AbstractMethod {

    public FibonacciMethod(Function<Double, Double> function) {
        super(function);
    }

    //Approximately nth fibonacci
    private double getFibonacci(long index) {
        assert (index >= 0);
        if (index <= 1) {
            return (index == 0 ? index : 1);
        }
        return (Math.pow((1 + sqrt(5)) / 2, index) - Math.pow((1 - sqrt(5)) / 2, index)) / sqrt(5);
    }

    //Count steps for fibonacci firstLab.method, based on lectures
    private long countSteps(double left, double right, double epsilon) {
        assert (left <= right);
        assert (epsilon > 0);
        long index = 2;
        double distance = right - left;
        while (distance / epsilon >= getFibonacci(index) && index < Long.MAX_VALUE) {
            index++;
        }
        return (index - 2);
    }

    protected double runImpl(double left, double right, double epsilon) {
        final double startingDistance = right - left;
        long n = countSteps(left, right, epsilon);
        long i;
        double x1 = left + getFibonacci(n - 1 + 1) / getFibonacci(n + 2) * startingDistance;
        double x2 = left + getFibonacci(n - 1 + 2) / getFibonacci(n + 2) * startingDistance;
        double fx1 = evaluateFx(x1);
        double fx2 = evaluateFx(x2);
        for (i = 1; i <= n && left <= right; i++) {
            if (fx1 > fx2) {
                left = x1;
                x1 = x2;
                fx1 = fx2;
                x2 = left + getFibonacci(n - i + 2) / getFibonacci(n + 2) * startingDistance;
                fx2 = evaluateFx(x2);
            } else {
                right = x2;
                x2 = x1;
                fx2 = fx1;
                x1 = left + getFibonacci(n - i + 1) / getFibonacci(n + 2) * startingDistance;
                fx1 = evaluateFx(x1);
            }
        }
        return (right + left) / 2;
    }
}
