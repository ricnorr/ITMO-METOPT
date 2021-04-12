package firstLab.method;

import java.util.function.Function;

public class DihotomiaMethod extends AbstractMethod {
   // private final Function<Double, Double> function;

    public DihotomiaMethod(Function<Double, Double> function) {
        super(function);
    }

    @Override
    protected double runImpl(double left, double right, double epsilon) {
        long i;
        double prevlen;
        for (i = 0; i < MAX_ITERATIONS && (right - left) / 2 > epsilon; i++) {
            prevlen = right - left;
            double x1 = (left + right - epsilon) / 2, x2 = (left + right + epsilon) / 2;
            if (evaluateFx(x1) <= evaluateFx(x2)) {
                right = x2;
            } else {
                left = x1;
            }
            //printTableFormat(left, right, x1, evaluateFx(x1), x2, evaluateFx(x2), (right - left) / prevlen);
        }
        return (left + right) / 2;
    }
}
