package fourthLab.method;

import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;
import thirdLab.method.GaussMethod;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Markwardt extends AbstactNewtoneMethod {
    private double[][] H;
    public Markwardt(double[][] H) {
        this.H = H;
    }

    @Override
    public double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double gamma = 1000, beta = 0.5;
        double[] x = point;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double[] antiGradient = multVector(getGradient(derivative, x), -1);
            double[] s = new GaussMethod().solve(new StandardMatrix(sumMatrix(H, getGI(x.length, gamma))), antiGradient);
            double[] y = sum(x, s);
            if (function.apply(y) >= function.apply(x)) {
                gamma /= beta;
                continue;
            } else {
                x = y;
                gamma *= beta;
            }
            if (length(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }

    private double[][] getGI(int n, double gamma) {
        double[][] res = generateI(n);
        for (int i = 0; i < n; i++) {
            res[i][i] *= gamma;
        }
        return res;
    }
}
