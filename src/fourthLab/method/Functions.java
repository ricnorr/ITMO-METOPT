package fourthLab.method;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Functions {
    //f(x) = 100(y − x^2 )^2 + (1 − x)^2
    public static Function<double[], Double> function1 = x -> Math.pow(100 * (x[1] - x[0]*x[0]), 2) + Math.pow(1 - x[0], 2);
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


    public static void main(String[] args) {
        double[] res = new KvasiNewton().runImpl(derivative1, function1, new double[]{0, 0});
        System.out.println(res[0]);
    }




}
