package fourthLab.method;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Functions {
    //f(x) = 100(y − x^2 )^2 + (1 − x)^2
    public static Function<double[], Double> function1 = x -> 100 * Math.pow((x[1] - x[0] * x[0]), 2) + Math.pow(1 - x[0], 2);
    public static BiFunction<Integer, double[], Double> derivative1 = (a, b) -> {
        switch (a) {
            case 0:
                return 2 * (200 * Math.pow(b[0], 3) - 200 * b[0] * b[1] + b[0] - 1);
            case 1:
                return 200 * (b[1] - Math.pow(b[0], 2));
            default:
                throw new RuntimeException("No such coord in derivative");
        }
    };

    //(x*x + y - 11)^2 +(x + y * y - 7)^2
    public static Function<double[], Double> function2 = x -> Math.pow(x[0] * x[0] + x[1] - 11, 2) + Math.pow(x[0] + x[1] * x[1] - 7, 2);
    public static BiFunction<Integer, double[], Double> derivative2 = (a, b) -> {
        switch (a) {
            case 0:
                return 2 * (-7 + b[0] + b[1] * b[1] + 2 * b[0] * (-11 + b[0] * b[0] + b[1]));
            case 1:
                return 2 * (b[0] * b[0] + 2 * b[1] * (b[0] + b[1] * b[1] - 7) + b[1] - 11);
            default:
                throw new RuntimeException("No such coord in derivative");
        }
    };


    //(a + 10*b)^2 + 5 * (c - d)^2 + (b - 2c)^4 + 10 * (a - d)^4
    // (0, 0,0, 0)
    public static Function<double[], Double> function3 = x -> Math.pow(x[0] + 10 * x[1], 2) + 5 * Math.pow(x[2] - x[3], 2) + Math.pow(x[1] - 2 * x[2], 4) + 10 * Math.pow(x[0] - x[3], 4);
    public static BiFunction<Integer, double[], Double> derivative3 = (a, b) -> {
        switch (a) {
            case 0:
                return 2 * (20 * Math.pow(b[0] - b[3], 3) + b[0] + 10 * b[1]);
            case 1:
                return 4 * (5 * (b[0] + 10 * b[1]) + Math.pow(b[1] - 2 * b[2], 3));
            case 2:
                return 10 * (b[2] - b[3]) - 8 * Math.pow(b[1] - 2 * b[2], 3);
            case 3:
                return 10 * (-4 * Math.pow(b[0] - b[3], 3) - b[2] + b[3]);
            default:
                throw new RuntimeException("КУДА ЛЕЗЕШЬ");
        }
    };

    // 100 - 2 / (1 + ((x - 1)/2)^2 + ((y - 2)/3)^2) - 1 / (1 + ((x - 2)/2)^2 + ((y - 1)/3)^2)
    // (1.27, 1.72)
    public static Function<double[], Double> function4 = (a) -> 100 -
            (2 / (1 + Math.pow((a[0] - 1) / 2, 2) + Math.pow((a[1] - 1) / 3, 2))) - (1 / (1 + Math.pow((a[0] - 2) / 2, 2) + Math.pow((a[1] - 1) / 3, 2)));
    public static BiFunction<Integer, double[], Double> derivative4 = (a, b) -> {
        switch (a) {
            case 0:
                return (b[0] - 2) / (2 * Math.pow(1.0 / 4 * Math.pow(b[0] - 2, 2) + 1.0 / 9 * Math.pow(b[1] - 1, 2) + 1, 2)) +
                        (b[0] - 1) / Math.pow(1.0 / 4 * Math.pow(b[0] - 1, 2) + 1.0 / 9 * Math.pow(b[1] - 2, 2) + 1, 2);
            case 1:
                return (4 * (b[1] - 2)) / (9 * Math.pow(1.0 / 4 * Math.pow(b[0] - 1, 2) + 1.0 / 9 * Math.pow(b[1] - 2, 2) + 1, 2)) +
                        2 * (b[1] - 1) / (9 *
                                Math.pow(
                                        1.0 / 4 * Math.pow(b[0] - 2, 2) + 1.0 / 9 * Math.pow(b[1] - 1, 2) + 1, 2));
            default:
                throw new RuntimeException("ТЫ КУДА ЗААЛЕЗ ДУРЕНЬ");
        }
    };

    static List<Function<double[], Double>> bifunc = List.of(function1, function2, function4);
    static List<BiFunction<Integer, double[], Double>> biderivatives = List.of(derivative1, derivative2, derivative4);


    static List<Function<double[], Double>> fourfunc = List.of(function3);
    static List<BiFunction<Integer, double[], Double>> fourderivatives = List.of(derivative3);

    public static void main(String[] args) {
        // а ещё у квазиньютоновских сверхлинейная скорость сходимости, да да!
        //Arrays.stream(new KvasiNewton().runImpl(derivative2, function2, new double[]{30, 30})).forEach(System.out::println);
        for (int i = 0; i < fourfunc.size(); i++) {
            if (i != 0) {
                continue;
            }
            double[] point = new double[]{-20, -20, -20, -20};
            System.out.println("Function " + i);
            for (int j = 0; j < 5; j++) {
                double[] temp_point = new double[]{point[0] + 5 * j, point[1] + 5 * j, point[2] + 5 * j, point[3] + 5 * j};
                System.out.println(temp_point[0]);
                Arrays.stream(new KvasiNewton().runPauellPrintPoints(fourderivatives.get(i), fourfunc.get(i), point));
                }
            }
        }


    }

