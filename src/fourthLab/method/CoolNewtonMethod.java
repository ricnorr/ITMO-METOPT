package fourthLab.method;

import firstLab.method.GoldenRatioMethod;
import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.matrix.StandardMatrix;

import java.util.function.Function;

import static thirdLab.matrix.MatrixUtilities.len;

public class CoolNewtonMethod extends AbstactNewtoneMethod{
    private final Hesse H;

    public CoolNewtonMethod(Hesse H) {
        this.H = H;
    }

    @Override
    protected double[] runImpl(Gradient gradient, Function<double[], Double> function, double[] point) {
        double[] s, d, x = point.clone();
        d = multVector(gradient.getGradient(x), -1);
        s = multVector(d, findArgMinGolden(x, d, function));
        x = sumVectors(x, s);
        int i = 0;
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            i++;
            double[] grad = gradient.getGradient(x);
            double[] antiGradient = multVector(grad, -1);
            s = new thirdLab.method.GaussMethod().solve(new StandardMatrix(H.evaluate(x)), antiGradient);
            if (multVectors(s, grad) - EPSILON < 0) {
                d = s.clone();
            } else {
                d = antiGradient.clone();
            }
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
