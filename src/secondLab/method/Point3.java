package secondLab.method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.min;

/**
 * For point3 in report
 */
public class Point3 {
    public static void main(String[] args) {

        for (int x : List.of(10, 100, 1000, 10000, 100000)) { // demensions
            for (int y : List.of(1, 250, 500, 750, 1000, 1250, 1500, 1750, 2000)) { // condition number
                //Map of derivative
                Map<Integer, Function<double[], Double>> derivative = new HashMap<>();
                //Starting point will be always (3, 3, ,3 ....)
                double[] point = new double[x];
                for (int i = 0; i < x - 1; i++) {
                    int finalI = i;
                    int mn = min(y, i + 1);
                    derivative.put(i, arg -> mn * arg[finalI] * 2);
                    point[i] = 3;
                }
                // For conjugate method, imitate matrix.
                // Matrix format will depend on condition number ("число обусловленности")
                // For example 3 x 3 matrix, with cond. number 5, will be:
                /// 1 0 0
                /// 0 2 0
                /// 0 0 5
                /// Not hard to notice that, condition number is indeed 5
                BiFunction<Integer, Integer, Double> biFunction = (a, b) -> {
                    if (!a.equals(b)) {
                        return 0.0;
                    }
                    int min = min(y - 1, a);
                    if (b == x - 1) {
                        return 2 * (double) y;
                    } else {
                        return (double) (2 * (min + 1));
                    }
                };
                derivative.put(x - 1, arg -> arg[x - 1] * y * 2);
                point[x - 1] = 3;
                double[] ans = new DescentGradientMethod().run(a -> {
                    double result = 0;
                    for (int i = 0; i < a.length - 1; i++) {
                        result += min(i + 1, y) * a[i] * a[i];
                    }
                    result += a[a.length - 1] * a[a.length - 1] * y;
                    return result;
                }, derivative, point, 0.0001);
                System.out.print("& ");
                ans = new ConjugateGradientMethod().runImpl(biFunction, new double[point.length], point, 0.0001);
            }
            System.out.println();
        }
    }
}

/*

 */