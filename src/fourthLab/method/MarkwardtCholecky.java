package fourthLab.method;

import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.exception.NoExactSolutionException;
import thirdLab.exception.NoSolutionException;
import thirdLab.matrix.StandardMatrix;
import thirdLab.method.MatrixMethod;

import java.util.function.Function;

public class MarkwardtCholecky extends Markwardt {
    public MarkwardtCholecky(Hesse H) {
        super(H);
    }

    @Override
    public double[] runImpl(Gradient gr, Function<double[], Double> function, double[] point) {
        double tau0 = 0.01, beta = 0.5, tau = tau0;
        boolean step2 = true;
        double[] x = point.clone();
        double[][] Hi = new double[0][0], m;
        double[] antiGradient = new double[0];
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (step2) {
                antiGradient = gr.getAntiGradient(x);
                Hi = H.evaluate(x);
                tau = tau0;
                //step2 = false;
            }
            //step 3
            m = sumMatrix(Hi, getTI(x.length, tau));
            int f = 0;
            while (!cholesky(m) && f < 100) {
                f++;
                tau = Math.max(tau * 2, 1);
                m = sumMatrix(Hi, getTI(x.length, tau));
            }
            double[] s = new double[0];
            for (MatrixMethod method : methods) {
                try {
                    s = method.solve(new StandardMatrix(m), antiGradient);
                    break;
                } catch (NoExactSolutionException ignored) {}
            }
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
}
