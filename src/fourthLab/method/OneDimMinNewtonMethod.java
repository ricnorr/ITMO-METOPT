package fourthLab.method;

import firstLab.method.GoldenRatioMethod;
import fourthLab.Gesse;
import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

import java.util.function.BiFunction;
import java.util.function.Function;

import static thirdLab.matrix.MatrixUtilities.len;

public class OneDimMinNewtonMethod extends AbstactNewtoneMethod {
    private final Gesse H;

    public OneDimMinNewtonMethod(Gesse H) {
        this.H = H;
    }

    @Override
    protected double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[] s, d, x = point;
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            double[] gradient = getGradient(derivative, x);
            // s задает направление поиска, которое может не быть спуском
            d = new thirdLab.method.GaussMethod().solve(new StandardMatrix(H.evaluate(x)), multVector(gradient, -1));
            s = multVector(d, findArgMinGolden(x, d, function));
            // Сделать сумму векторов отдельно
            x = sumVectors(x, s);
            if (len(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }

    private double findArgMinGolden(double[] point, double[] d, Function<double[], Double> function) {
        return new GoldenRatioMethod(x -> function.apply(sumVectors(point, multVector(d, x))))
                .run(-100, 100, 0.00001);
    }
}
