package fourthLab;

import java.util.List;
import java.util.function.Function;

public class Gesse {
    private List<List<Function<double[], Double>>> matrix;
    public Gesse(List<List<Function<double[], Double>>> matrix) {
        this.matrix = matrix;
    }
    private double evalEl(int i, int j, double[] v) {
        return matrix.get(i).get(j).apply(v);
    }
    public double[][] evaluate(double[] v) {
        final int n = v.length;
        double[][] res = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = evalEl(i, j, v);
            }
        }
        return res;
    }
}
