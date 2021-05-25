package fourthLab.method;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Markwardt extends AbstactNewtoneMethod {


    @Override
    public double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double gamma, beta;
        double[] x = point;
        return new double[0];
    }
}
