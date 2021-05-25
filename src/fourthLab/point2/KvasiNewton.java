package fourthLab.point2;

import firstLab.method.BrentMethod;
import firstLab.method.GoldenRatioMethod;
import fourthLab.AbstactNewtoneMethod;
import fourthLab.NewtoneMethod;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import static fourthLab.AbstactNewtoneMethod.*;
import static java.lang.Math.sqrt;

// 1 var
//метод Давидона-Флетчера-Пауэлла и метод Пауэлла




public class KvasiNewton extends AbstactNewtoneMethod {

    @Override
    public double[] runImpl(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[][] Hk = generateI(point.length);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double[] way = multMatrix(Hk, point);
            double[] finalPoint = point;
            double alpha = new GoldenRatioMethod(x -> function.apply(subtract(finalPoint, multVector(way, x)))).run(-100, 100, EPSILON);
            double[] newPoint = subtract(point, multVector(way, alpha));
            if (length(subtract(newPoint, point)) < EPSILON) {
                break;
            }
            Hk = getNextH(Hk, subtract(newPoint, point), subtract(getGradient(derivative, newPoint), getGradient(derivative, point)));
            point = newPoint;
        }
        return point;
    }

    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextH(double[][] Hk, double[] sk, double[] yk) {
        return getNextH(Hk, vectorToMatrix(sk), vectorToMatrix(yk));
    }
    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextH(double[][] Hk, double[][] sk, double[][] yk) {
        return sumMatrix(
                subtractMatrix(Hk, divideMatrix(multMatrix(multMatrix(multMatrix(Hk, yk), transpose(yk)), Hk), scalarMult(multMatrix(Hk, yk), yk))),
                divideMatrix(multMatrix(sk, transpose(sk)), scalarMult(yk, sk)));
    }
}

