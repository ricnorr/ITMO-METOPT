package fourthLab.research;

import fourthLab.Gesse;
import fourthLab.method.BaseNewtonMethod;
import fourthLab.method.KvasiNewton;
import fourthLab.method.Markwardt;
import fourthLab.method.NewtoneMethod;
import thirdLab.matrix.Matrix;
import thirdLab.matrix.StandardMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {
    //copy-pasted from secondLab.Tester
    //need to be refactored
    private static double[] test(NewtoneMethod method, Function<double[], Double> function, BiFunction<Integer, double[], Double> derivative, double[] point) {
        return method.run(derivative, function, point);
    }

    //f(x) = 100(y − x^2 )^2 + (1 − x)^2
    public static Function<double[], Double> function1 = x -> Math.pow(100 * (x[1] - x[0]*x[0]), 2) + Math.pow(1 - x[0], 2);
    public static BiFunction<Integer, double[], Double> derivative1 = (a, b) -> switch (a) {
        case 0 -> 2 * (200 * Math.pow(b[0], 3) - 200 * b[0] * b[1] + b[0] - 1);
        case 1 -> 200 * (b[1] - Math.pow(b[0], 2));
        default -> throw new RuntimeException("No such coord in derivative");
    };

    public static Matrix gese1 = new StandardMatrix(new double[][]{
            {1, 2},
            {3, 4} });
    public static final Function<double[], Double> func2 = a -> a[0] * a[0] + 0.5 * a[1] * a[1] - a[0] * a[1];
    public static final Map<Integer, Function<double[], Double>> derivative2 = Map.of(0, x->2 * x[0] - x[1], 1, x->x[1] - x[0]);
    public static final String func2String = "x^2+0.5*y^2-x*y";
    public static final double[][] matrix2 = new double[][]{{2, -1},{-1, 1}};
    public static final double[] bVector2 = new double[]{0, 0};

    // Начальная точка 1 5
    public static final Function<double[], Double> func4 = a -> 64 * a[0] * a[0] + 126 * a[0] * a[1] + 64 * a[1] * a[1] + 10 * a[0] + 10 * a[1] - 187;
    public static final String func4String = "64*x*x + 126*x*y + 64*y*y + 10*x + 10*y - 187";
    public static final Map<Integer, Function<double[], Double>> derivative4 = Map.of(0, x -> 128 * x[0] + 126 * x[1] + 10, 1, x -> 126 * x[0] + 128 * x[1] + 10);
    public static final BiFunction<Integer, double[], Double> derivative4bf = (i, x) -> switch (i) {
        case 0 -> 128 * x[0] + 126 * x[1] + 10;
        case 1 -> 126 * x[0] + 128 * x[1] + 10;
        default -> throw new RuntimeException("No such coord in derivative");
    };
    public static final double[][] matrix4 = new double[][]{{128, 126},{126, 128}};
    public static final double[] bVector4 = new double[]{10, 10};
    public static final Function<double[], Double> gesseFunc41 = a -> 128d;
    public static final Function<double[], Double> gesseFunc42 = a -> 126d;
    public static final List<List<Function<double[], Double>>> gesseMatrix4 = List.of(
            List.of(gesseFunc41, gesseFunc42),
            List.of(gesseFunc42, gesseFunc41)
    );
    public static final Gesse gesse4 = new Gesse(gesseMatrix4);

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

    public static final Function<double[], Double> func7 = a -> 5 * a[0] * a[0] - 12 * a[0] * a[1] + 11 * a[1] * a[1] + 6 * a[0] - 13 * a[1] - 131;
    public static final String func7String = "-x2*sqrt(x1) + 2*x2^2 + x1 - 14*x2";
    public static final Map<Integer, Function<double[], Double>> derivative7 = Map.of(
            0, x -> 10 * x[0] - 12 * x[1] + 6,
            1, x -> -12 * x[0] + 22 * x[1] - 13);
    public static final Function<double[], Double> gesseFunc71 = a -> 128d;
    public static final Function<double[], Double> gesseFunc72 = a -> 126d;
    public static final List<List<Function<double[], Double>>> gesseMatrix7 = List.of(
            List.of(gesseFunc71, gesseFunc72),
            List.of(gesseFunc72, gesseFunc71)
    );
    public static final Gesse gesse7 = new Gesse(gesseMatrix7);

    public static final Function<double[], Double> func8 = a -> 5 * a[0] * a[0] - 12 * a[0] * a[1] + 11 * a[1] * a[1] + 6 * a[0] - 13 * a[1] - 131;
    public static final String func8String = "4 + (x1^2 + x2^2)^(1/3)";
    public static final BiFunction<Integer, double[], Double> derivative8 = (i, x) -> switch(i) {
        case 0 ->
            10 * x[0] - 12 * x[1] + 6;
        case 1 ->
            -12 * x[0] + 22 * x[1] - 13;
        default -> 0.0;
    };
    public static final Function<double[], Double> gesseFunc81 = a -> 128d;
    public static final Function<double[], Double> gesseFunc82 = a -> 126d;
    public static final List<List<Function<double[], Double>>> gesseMatrix8 = List.of(
            List.of(gesseFunc81, gesseFunc82),
            List.of(gesseFunc82, gesseFunc81)
    );
    public static final Gesse gesse8 = new Gesse(gesseMatrix8);


    private static void testFunction(Function<double[], Double> function, BiFunction<Integer, double[], Double> derivative, Gesse H, double[] point) {
        List<NewtoneMethod> methods = new ArrayList<>();
        methods.add(new KvasiNewton());
        methods.add(new BaseNewtonMethod(H));
        methods.add(new Markwardt(H));
        double[][] res = new double[methods.size()][];
        for (int i = 0; i < methods.size(); i++) {
            res[i] = test(methods.get(i), function, derivative, point);
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
        //testFunction(func4, derivative4bf, gesse4, new double[]{2, 3});
        testFunction(func8, derivative8, gesse8, new double[]{2, 3});
    }
}
