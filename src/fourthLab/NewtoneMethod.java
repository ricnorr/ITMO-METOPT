package fourthLab;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface NewtoneMethod {
    double[] run(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point);
}
