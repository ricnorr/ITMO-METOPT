package fourthLab.method;

import firstLab.method.GoldenRatioMethod;
import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.matrix.StandardMatrix;

import java.util.function.Function;

import static thirdLab.matrix.MatrixUtilities.len;

public class OneDimMinNewtonMethod extends AbstactNewtoneMethod {
    private final Hesse H;

    public OneDimMinNewtonMethod(Hesse H) {
        this.H = H;
    }

    @Override
    protected double[] runImpl(Gradient gr, Function<double[], Double> function, double[] point) {
        double[] s, d, x = point.clone();
        int i = 0;
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            i++;
            double[] gradient = gr.getGradient(x);
            d = new thirdLab.method.GaussMethod().solve(new StandardMatrix(H.evaluate(x)), multVector(gradient, -1));
            s = multVector(d, findArgMinGolden(x, d, function));
            x = sumVectors(x, s);
            if (len(s) <= EPSILON) {
                break;
            }
        }
        System.out.println(i);
        return x;
    }
}
