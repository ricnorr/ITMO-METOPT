package fourthLab.method;

import fourthLab.derivative.Gradient;
import fourthLab.hesse.Hesse;
import thirdLab.exception.NoSolutionException;
import thirdLab.matrix.StandardMatrix;

import java.util.function.Function;

public class MarkwardtCholecky extends Markwardt {
    public MarkwardtCholecky(Hesse H) {
        super(H);
    }

    @Override
    public double[] runImpl(Gradient gr, Function<double[], Double> function, double[] point) {
        double tau0 = 0.0, beta = 0.5, tau = tau0;
        int chol = 0;
        boolean step2 = true;
        double[] x = point.clone();
        double[][] Hi = new double[0][0], m;
        double[] antiGradient = new double[0];
        System.out.println(this.getClass().getSimpleName());
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (step2) {
                antiGradient = gr.getAntiGradient(x);
                Hi = H.evaluate(x);
            }
            //step 2.5
            m = sumMatrix(Hi, getTI(x.length, tau));
            int f = 0;
            while (!cholesky(m) && f < 100) {
                f++;
                tau = Math.max(tau * 2, 1);
                m = sumMatrix(Hi, getTI(x.length, tau));
            }
            chol += f;
            System.out.println(tau + " " + chol);
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
                tau *= beta;
            }
            if (length(s) <= EPSILON) {
                break;
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
                l[i][k] = (i == k) ? Math.sqrt(a[i][i] - sum) :
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
