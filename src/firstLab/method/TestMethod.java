package firstLab.method;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.abs;

public class TestMethod {
    public static void main(String[] args) {
        //x^{2\ }+4x\ +\ 3
        //Function<Double, Double> fx = (a) -> (a * a +Math.pow(Math.E, -0.35 * a));
        Function<Double, Double> fx = (a) -> (Math.pow(a, 4)  - 2 * Math.pow(a, 3) + Math.pow(a, 2) - 1/30);
        List<Method> list = new ArrayList<>();
        list.add(new DihotomiaMethod(fx));
        list.add(new FibonacciMethod(fx));
        list.add(new GoldenRatioMethod(fx));
        list.add(new BrentMethod(fx));
        list.add(new ParabolaMethod(fx));
        ArrayList<Double> list1 = new ArrayList<>();
        for (Method method : list) {
            //System.out.println(firstLab.method.getClass().getSimpleName() + " " + firstLab.method.run(-2, 3, 0.00001));
            System.out.println(method.getClass().getName() + " " + String.format("%f", method.run(-2, 2, 0.0001)));
        }
    }
}
