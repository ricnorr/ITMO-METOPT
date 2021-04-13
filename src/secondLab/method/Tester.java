package secondLab.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Tester {
    private static final double EPSILON = 0.0000001;
    private static double[] test(GradientMethod method, Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative) {
        return method.run(function, derivative, new double[]{1, 1}, EPSILON);
    }

    public static final Function<double[], Double> func1 = a -> 1.5 * (a[0] - a[1]) * (a[0] - a[1]) + 1.0 / 3 * (a[0] + a[1]) * (a[0] + a[1]);
    public static final Map<Integer, Function<double[], Double>> derivative1 = Map.of(0, x -> 1.0 / 3 * (11 * x[0] - 7 * x[1]), 1, x -> 1.0 / 3 * (11 * x[1] - 7 * x[0]));
    public static final String func1String = "1.5*(x-y)^2+0.33*(x + y)^2";
    public static final double[][] matrix1 = new double[][]{{11.0/3, -7.0/3},{-7.0/3, 11.0/3}};
    public static final double[] bVector1 = new double[]{0, 0};

    public static final Function<double[], Double> func2 = a -> a[0] * a[0] + 0.5 * a[1] * a[1] - a[0] * a[1];
    public static final Map<Integer, Function<double[], Double>> derivative2 = Map.of(0, x->2 * x[0] - x[1], 1, x->x[1] - x[0]);
    public static final String func2String = "x^2+0.5*y^2-x*y";
    public static final double[][] matrix2 = new double[][]{{2, -1},{-1, 1}};
    public static final double[] bVector2 = new double[]{0, 0};

    public static final Function<double[], Double> func4 = a -> 64 * a[0] * a[0] + 126 * a[0] * a[1] + 64 * a[1] * a[1] - 10 * a[0] + 30 * a[1] + 13;
    public static final String func4String = "64*x*x + 126*x*y + 64*y*y - 10*x + 30*y + 13";
    public static final Map<Integer, Function<double[], Double>> derivative4 = Map.of(0, x -> 128 * x[0] + 126 * x[1] - 10, 1, x -> 126 * x[0] + 128 * x[1] + 30);
    public static final double[][] matrix4 = new double[][]{{128, 126},{126, 128}};
    public static final double[] bVector4 = new double[]{-10, 30};



    public static final Function<double[], Double> func3 = a -> 1 - Math.exp(-(10 * a[0] * a[0] + a[1] * a[1]));
    public static final Map<Integer, Function<double[], Double>> derivative3 = Map.of(
            0, x -> 20 * x[0] * Math.exp(-10 * x[0] * x[0] - x[1] * x[1]),
            1, x -> 2 * x[1] * Math.exp(-10 * x[0] * x[0] - x[1] * x[1]));
    public static final String func3String = "1-2.7^(-1*(10*x^2+y^2))";

    private static void testFunction(Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative) {
        List<GradientMethod> methods = new ArrayList<>();
        methods.add(new DescentGradientMethod());
        methods.add(new QuickestDescentMethod());
        //матрица - коэффициенты производных при x1, x2
        methods.add(new ConjugateGradientMethod());
        double[][] res = new double[methods.size()][];
        for (int i = 0; i < res.length; i++) {
            res[i] = test(methods.get(i), function, derivative);
        }
        for (int i = 0; i < res.length; i++) {
            System.out.print(methods.get(i).getClass().getSimpleName() + ": ");
            for (double y : res[i]) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        /*Function<double[], Double> func = a -> 10 * a[0] * a[0] + a[1] * a[1];
        Map<Integer, Function<double[], Double>> derivative = Map.of(0, x -> 20 * x[0],1, x -> 2 * x[1]);
        testFunction(func, derivative);
        testFunction(func1, derivative1);
        testFunction(func3, derivative3);*/
        double[] res = new ConjugateGradientMethod().runImpl(matrix1, bVector1, new double[]{10, 12}, 0.0001);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}
