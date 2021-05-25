package fourthLab.method;

import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

import java.util.function.BiFunction;
import java.util.function.Function;

public class BaseNewtonMethod extends AbstactNewtoneMethod{
    private Matrix H;

    public BaseNewtonMethod(double[][] H) {
        this.H = new StandardMatrix(H);
    }

    @Override
    protected double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[] s, x = point;
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            double[] gradient = getGradient(derivative, x);
            s = new thirdLab.method.GaussMethod().solve(H, multVector(gradient, -1));
            // Сделать сумму векторов отдельно
            for (int i = 0; i < x.length; i++) {
                x[i] += s[i];
            }
            if (thirdLab.matrix.MatrixUtilities.len(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }
}
