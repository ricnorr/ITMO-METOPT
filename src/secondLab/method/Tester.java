package secondLab.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Tester {
    private static final double EPSILON = 0.00000001;
    private static double[] test(GradientMethod method, Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative, double[] point) {
        return method.run(function, derivative, point, EPSILON);
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

    // Начальная точка 1 5
    public static final Function<double[], Double> func4 = a -> 64 * a[0] * a[0] + 126 * a[0] * a[1] + 64 * a[1] * a[1] + 10 * a[0] + 10 * a[1] - 187;
    public static final String func4String = "64*x*x + 126*x*y + 64*y*y + 10*x + 10*y - 187";
    public static final Map<Integer, Function<double[], Double>> derivative4 = Map.of(0, x -> 128 * x[0] + 126 * x[1] + 10, 1, x -> 126 * x[0] + 128 * x[1] + 10);
    public static final double[][] matrix4 = new double[][]{{128, 126},{126, 128}};
    public static final double[] bVector4 = new double[]{10, 10};

    // Начальная точка4 5
    public static final Function<double[], Double> func5 = a -> 13 * a[0] * a[0] - 37 * a[0] * a[1] + 70 * a[1] * a[1] + 13 * a[1] - 56;
    public static final String func5String = "13*x*x - 37*x*y + 70*y*y + 13*y - 56";
    public static final Map<Integer, Function<double[], Double>> derivative5 = Map.of(0, x -> 26 * x[0] - 37 * x[1], 1, x -> -37 * x[0] + 140 * x[1] + 13);
    public static final double[][] matrix5 = new double[][]{{26, -37},{-37, 140}};
    public static final double[] bVector5 = new double[]{0, 13};

    // Начальная точка -1 2
    public static final Function<double[], Double> func6 = a -> 5 * a[0] * a[0] - 12 * a[0] * a[1] + 11 * a[1] * a[1] + 6 * a[0] - 13 * a[1] - 131;
    public static final String func6String = "5*x*x - 12*x*y + 11*y*y + 6*x - 13*y - 131";
    public static final Map<Integer, Function<double[], Double>> derivative6 = Map.of(0, x -> 10 * x[0] - 12 * x[1] + 6, 1, x -> -12 * x[0] + 22 * x[1] - 13);
    public static final double[][] matrix6 = new double[][]{{10, -12},{-12, 22}};
    public static final double[] bVector6 = new double[]{6, -13};



    public static final Function<double[], Double> func3 = a -> 1 - Math.exp(-(10 * a[0] * a[0] + a[1] * a[1]));
    public static final Map<Integer, Function<double[], Double>> derivative3 = Map.of(
            0, x -> 20 * x[0] * Math.exp(-10 * x[0] * x[0] - x[1] * x[1]),
            1, x -> 2 * x[1] * Math.exp(-10 * x[0] * x[0] - x[1] * x[1]));
    public static final String func3String = "1-2.7^(-1*(10*x^2+y^2))";

    private static void testFunction(Function<double[], Double> function, Map<Integer, Function<double[], Double>> derivative, double[][] matrix, double[] bVector, double[] point) {
        List<GradientMethod> methods = new ArrayList<>();
        methods.add(new DescentGradientMethod());
        methods.add(new QuickestDescentMethod());
        double[][] res = new double[methods.size() + 1][];
        for (int i = 0; i < methods.size(); i++) {
            res[i] = test(methods.get(i), function, derivative, point);
        }
        res[2] = new ConjugateGradientMethod().runImpl(matrix, bVector, point, EPSILON);
        for (int i = 0; i < res.length - 1; i++) {
            System.out.print(methods.get(i).getClass().getSimpleName() + ": ");
            for (double y : res[i]) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
        System.out.println("Conjugate gradient");
        for (double y : res[2]) {
            System.out.print(y + " ");
        }
    }
    public static void main(String[] args) {
        /*Function<double[], Double> func = a -> 10 * a[0] * a[0] + a[1] * a[1];
        Map<Integer, Function<double[], Double>> derivative = Map.of(0, x -> 20 * x[0],1, x -> 2 * x[1]);
        testFunction(func, derivative);
        testFunction(func1, derivative1);
        testFunction(func3, derivative3);
        //double[] res = new ConjugateGradientMethod().runImpl(matrix1, bVector1, new double[]{10, 12}, 0.0001);
        double[] res = new QuickestDescentMethod().run(func4, derivative4, new double[]{3,3},0.0001);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
        testFunction(func4, derivative4, matrix4, bVector4, new double[]{1, 5}); */
    }
}
