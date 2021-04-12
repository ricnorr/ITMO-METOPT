package secondLab.method;

import java.util.Map;
import java.util.function.Function;

public interface GradientMethod {

    double[] run(Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative, double[] point, double epsilon);


}
