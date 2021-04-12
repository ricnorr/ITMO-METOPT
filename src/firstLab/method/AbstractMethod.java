package firstLab.method;

import java.util.function.Function;

import static java.lang.Math.sqrt;

public abstract class AbstractMethod implements Method {
    protected final Function<Double, Double> function;
    protected final static double GOLDEN_RATIO = (sqrt(5) - 1) / 2;
    protected final int MAX_ITERATIONS = 20000;
    protected AbstractMethod(Function<Double, Double> function) {
        this.function = function;
    }
    protected double evaluateFx(double x) {
        return function.apply(x);
    }

    //Pre: left <= right && epsilon > 0
    //Post: x, f(x) = min of function
    @Override
    public double run(double left, double right, double epsilon) {
        assert (left <= right);
        assert (epsilon > 0);
        return runImpl(left, right, epsilon);
    }


    protected void printTableFormat(double... doubles) {
        /*
        StringBuilder stringBuilder = new StringBuilder();
        for (double value : doubles) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" & ");
            }
            stringBuilder.append(String.format("%.7f", value));
        }
        System.out.print(stringBuilder);
        System.out.print("\\\\ \\hline");
        System.out.print(System.lineSeparator()); */
    }

    protected abstract double runImpl(double left, double right, double epsilon);
}
