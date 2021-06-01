package fourthLab.method;

import fourthLab.Gesse;
import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

import java.util.function.BiFunction;
import java.util.function.Function;

import static thirdLab.matrix.MatrixUtilities.*;


public class BaseNewtonMethod extends AbstactNewtoneMethod{
    private final Gesse H;

    public BaseNewtonMethod(Gesse H) {
        this.H = H;
    }

    @Override
    protected double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[] s, x = point.clone();
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            double[] gradient = getGradient(derivative, x);
            // s задает направление спуска (наверное)
            s = new thirdLab.method.GaussMethod().solve(new StandardMatrix(H.evaluate(x)), multVector(gradient, -1));
            // Сделать сумму векторов отдельно
            for (int i = 0; i < x.length; i++) {
                x[i] += s[i];
            }
            if (len(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }
}
