package fourthLab.method;

import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.matrix.StandardMatrix;
import thirdLab.method.GaussMethod;

import java.util.function.Function;

public class Markwardt extends AbstactNewtoneMethod {
    private static final boolean isHolecky = true;
    private final Hesse H;

    public Markwardt(Hesse H) {
        this.H = H;
    }

    @Override
    public double[] runImpl(Gradient gr, Function<double[], Double> function, double[] point) {
        double tau = 1000, beta = 0.5;
        if (isHolecky) {
            tau = 0.0;
        }
        double[] x = point.clone();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double[] antiGradient = gr.getAntiGradient(x);
            double[][] Hi = H.evaluate(x);
            double[][] m = sumMatrix(Hi, getTI(x.length, tau));
            if (isHolecky) {
                int f = 0;
                while (!cholesky(m) && f < 100) {
                    f++;
                    tau = Math.max(tau * 2, 1);
                    m = sumMatrix(Hi, getTI(x.length, tau));
                }
                if (f == 100) {
                    System.out.println("f = 100");
                }
            }
            double[] s = new GaussMethod().solve(new StandardMatrix(m), antiGradient);
            double[] y = sumVectors(x, s);
            if (function.apply(y) > function.apply(x)) {
                tau /= beta;
                continue;
            } else {
                x = y.clone();
                tau *= beta;
            }
            if (length(s) <= EPSILON) {
                break;
            }
            if (i == MAX_ITERATIONS) {
                System.out.println("Markwardt maxiterations");
            }
        }
        return x;
    }

    private boolean cholesky(double[][] a) {
        final int m = a.length;
        double[][] l = new double[m][m];
        for(int i = 0; i< m;i++){
            for(int k = 0; k < (i+1); k++){
                double sum = 0;
                for(int j = 0; j < k; j++){
                    sum += l[i][j] * l[k][j];
                }
                l[i][k] = (i == k) ? Math.sqrt(a[i][i] - sum) : // Source of NaN at 32,32
                        (1.0 / l[k][k] * (a[i][k] - sum));
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= i; j++) {
                if (Double.isNaN(l[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private double[][] getTI(int n, double tau) {
        double[][] res = generateI(n);
        for (int i = 0; i < n; i++) {
            res[i][i] *= tau;
        }
        return res;
    }
}
