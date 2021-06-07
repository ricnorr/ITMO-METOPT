package fourthLab.method;

import firstLab.method.GoldenRatioMethod;
import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.matrix.StandardMatrix;

import java.util.function.Function;

import static thirdLab.matrix.MatrixUtilities.len;

public class DirectionNewtonMethod extends AbstactNewtoneMethod{
    private final Hesse H;

    public DirectionNewtonMethod(Hesse H) {
        this.H = H;
    }

    @Override
    protected double[] runImpl(Gradient gradient, Function<double[], Double> function, double[] point) {
        double[] s, d, x = point.clone();
        System.out.println(x[0] + " " + x[1]);
        /*d = multVector(gradient.getGradient(x), -1);
        s = multVector(d, findArgMinGolden(x, d, function));
        x = sumVectors(x, s);*/
        int i = 0;
        System.out.println(x[0] + " " + x[1]);
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            i++;
            double[] grad = gradient.getGradient(x);
            double[] antiGradient = multVector(grad, -1);
            s = new thirdLab.method.GaussMethod().solve(new StandardMatrix(H.evaluate(x)), antiGradient);
            if (multVectors(s, grad) < 0) {
                d = s.clone();
            } else {
                d = antiGradient.clone();
            }
            s = multVector(d, findArgMinGolden(x, d, function));
            x = sumVectors(x, s);
            System.out.println(x[0] + " " + x[1]);
            if (len(s) <= EPSILON) {
                break;
            }
        }
        System.out.println(i);
        return x;
    }
}
