package fourthLab.hesse;

import java.util.List;
import java.util.function.Function;

public class MHesse extends Hesse {
    public MHesse(List<List<Function<double[], Double>>> matrix) {
        super(matrix);
    }
    @Override
    public double[][] evaluate(double[] v) {
        final int n = v.length;
        double[][] res = new double[n][n];
        res[0][0] = evalEl(0, 0, new double[0]);
        res[0][1] = evalEl(0, 1, new double[]{v[0], v[1]});
        res[0][2] = evalEl(0, 2, new double[]{v[0], v[1], v[2]});

        for (int i = 1; i < n - 1; i++) {
            for (int j = i - 1; j <= i; j++) {
                res[i][j] = evalEl(1, i - j, new double[]{v[i - 1], v[i], v[i + 1]});
            }
        }
        final int n1 = n - 1;
        res[n1][n1] = evalEl(2, 0, new double[]{v[n1 - 2], v[n1 - 1], v[n1]});
        res[n1][n1 - 1] = evalEl(1, 1, new double[]{v[n1 - 2], v[n1 - 1], v[n1]});
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                res[i][j] = res[j][i];
            }
        }
        return res;
    }
}
