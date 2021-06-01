package fourthLab.method;

import fourthLab.Gesse;
import thirdLab.matrix.StandardMatrix;
import thirdLab.method.GaussMethod;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Markwardt extends AbstactNewtoneMethod {
    private Gesse H;
    public Markwardt(Gesse H) {
        this.H = H;
    }
    @Override
    public double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double tau = 1000, beta = 0.5;
        double[] x = point.clone();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double[] antiGradient = multVector(getGradient(derivative, x), -1);
            double[] s = new GaussMethod().solve(new StandardMatrix(sumMatrix(H.evaluate(x), getTI(x.length, tau))), antiGradient);
            double[] y = sumVectors(x, s);
            if (function.apply(y) > function.apply(x)) {
                tau /= beta;
                continue;
            } else {
                x = y;
                tau *= beta;
            }
            if (length(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }

    private double[][] getTI(int n, double tau) {
        double[][] res = generateI(n);
        for (int i = 0; i < n; i++) {
            res[i][i] *= tau;
        }
        return res;
    }
}
