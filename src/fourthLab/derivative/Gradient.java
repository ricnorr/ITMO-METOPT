package fourthLab.derivative;

import java.util.function.BiFunction;

public interface Gradient {
    double[] getGradient(double[] point);
    double[] getAntiGradient(double[] point);
}
