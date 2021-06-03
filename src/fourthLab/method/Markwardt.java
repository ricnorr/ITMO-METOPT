package fourthLab.method;

import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.exception.NoSolutionException;
import thirdLab.matrix.StandardMatrix;
import thirdLab.method.GaussMethod;
import thirdLab.method.MatrixMethod;

import java.util.function.Function;

public class Markwardt extends AbstactNewtoneMethod {
    protected final Hesse H;
    protected static final MatrixMethod method = new GaussMethod();

    public Markwardt(Hesse H) {
        this.H = H;
    }
    @Override
    public double[] runImpl(Gradient gr, Function<double[], Double> function, double[] point) {
        double tau0 = 1000, beta = 0.5, tau = tau0;
        boolean step2 = true;
        double[] x = point.clone();
        double[][] Hi = new double[0][0], m;
        double[] antiGradient = new double[0];
        System.out.println(this.getClass().getSimpleName());
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (step2) {
                antiGradient = gr.getAntiGradient(x);
                Hi = H.evaluate(x);
                tau = tau0;
            }
            System.out.println(tau);
            //step 3
            m = sumMatrix(Hi, getTI(x.length, tau));
            double[] s = method.solve(new StandardMatrix(m), antiGradient);
            if (s.length == 0) {
                throw new NoSolutionException();
            }
            //step 4
            double[] y = sumVectors(x, s);
            //step 5
            if (function.apply(y) > function.apply(x)) {
                tau /= beta;
                step2 = false;
                //to step 3
                continue;
            } else {
                step2 = true;
                x = y.clone();
                tau0 *= beta;
            }
            if (length(s) <= EPSILON) {
                break;
            }
        }
        return x;
    }

    protected double[][] getTI(int n, double tau) {
        double[][] res = generateI(n);
        for (int i = 0; i < n; i++) {
            res[i][i] *= tau;
        }
        return res;
    }
}
