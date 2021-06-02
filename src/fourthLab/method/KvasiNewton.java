package fourthLab.method;

import firstLab.method.BrentMethod;
import firstLab.method.FibonacciMethod;
import firstLab.method.GoldenRatioMethod;
import fourthLab.derivative.Gradient;

import java.util.function.BiFunction;
import java.util.function.Function;

// 1 var
//метод Давидона-Флетчера-Пауэлла и метод Пауэлла


public class KvasiNewton extends AbstactNewtoneMethod {
    public double[] runFletcher(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[][] G_1 = generateI(point.length);
        double[] w_1 = getAntiGradient(derivative, point);
        double[] p_1 = getAntiGradient(derivative, point);
        double[] finalP_ = p_1;
        double alpha_1 = new FibonacciMethod(alph -> function.apply(sumVectors(point, multVector(finalP_, alph)))).run(0, 2, 0.0000000000000000000000000000001);
        double[] x_1 = sumVectors(point, multVector(p_1, alpha_1));
        double[] delta_x_1 = subtract(x_1, point);
        double mx = Double.MIN_VALUE;
        int iterations = 1;
        for (int i = 0; i < 150; i++) {
            double[] w_k = getAntiGradient(derivative, x_1);
            double[] delta_w_k = subtract(w_k, w_1);
            double[] v_k = multMatrix(G_1, delta_w_k);
            double[][] G_k = getNextGDavid(G_1, delta_x_1, delta_w_k, v_k); //
            double[] p_k = multMatrix(G_k, w_k);
            double[] finalX_1 = x_1;
            double alpha_k = new GoldenRatioMethod(alph -> function.apply(sumVectors(finalX_1, multVector(p_k, alph)))).run(-1, 1, 0.000000000000000000000000000001);
            double[] x_k = sumVectors(x_1, multVector(p_k, alpha_k));
            double[] delta_x_k = subtract(x_k, x_1);
            if (length(delta_x_k) < 0.0000001) {
                x_1 = x_k;
                break;
            }
            w_1 = w_k;
            x_1 = x_k;
            delta_x_1 = delta_x_k;
            G_1 = G_k;
            iterations++;
        }
        return x_1;
    }



    public double[] runPauell(BiFunction<Integer, double[], Double> derivative, Function<double[], Double> function, double[] point) {
        double[][] G_1 = generateI(point.length);
        double[] w_1 = getAntiGradient(derivative, point);
        double[] p_1 = getAntiGradient(derivative, point);
        double alpha_1 = new BrentMethod(alph -> function.apply(sumVectors(point, multVector(p_1, alph)))).run(-1, 1, 0.0000000000000000001);
        double[] x_1 = sumVectors(point, multVector(p_1, alpha_1));
        double[] delta_x_1 = subtract(x_1, point);
        int iterations = 1;
        for (int i = 0; i < 100; i++) {
            double[] w_k = getAntiGradient(derivative, x_1);
            double[] delta_w_k = subtract(w_k, w_1);
            double[] delta_x_1_wave = sumVectors(delta_x_1, multMatrix(G_1, delta_w_k));
            double[][] G_k = getNextGPauell(G_1, delta_x_1_wave, delta_w_k);
            double[] p_k = multMatrix(G_k, w_k);
            double[] finalX_1 = x_1;
            double alpha_k = new BrentMethod(alph -> function.apply(sumVectors(finalX_1, multVector(p_k, alph)))).run(-1, 1, 0.00000000001);
            double[] x_k = sumVectors(x_1, multVector(p_k, alpha_k));
            double[] delta_x_k = subtract(x_k, x_1);
            if (length(delta_x_k) < EPSILON) {
                x_1 = x_k;
                break;
            }
            w_1 = w_k;
            x_1 = x_k;
            delta_x_1 = delta_x_k;
            G_1 = G_k;
            iterations++;
        }
        return x_1;
    }

    private double[] getAntiGradient(BiFunction<Integer, double[], Double> derivative, double[] point) {
        double[] result = new double[point.length];
        for (int i = 0; i < point.length; i++) {
            result[i] = -derivative.apply(i, point);
        }
        return result;
    }




    public double[][] getNextGPauell(double[][] G, double[] delta_x_k, double[] delta_w_k) {
        return getNextGPauell(G, vectorToMatrix(delta_x_k), vectorToMatrix(delta_w_k));
    }

    public double[][] getNextGPauell(double[][] G, double[][] delta_x_k, double[][] delta_w_k) {
        return subtractMatrix(
                G,
                divideMatrix(
                        multMatrix(delta_x_k, transpose(delta_x_k)),
                        scalarMult(delta_w_k, delta_x_k)
                )
        );
    }


    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextGDavid(double[][] G, double[] delta_xk, double[] delta_wk, double[] v_k) {
        return getNextGDavid(G, vectorToMatrix(delta_xk), vectorToMatrix(delta_wk), vectorToMatrix(v_k));
    }

    /**
     * Returns next Hk in David-FLitcher-Pauell method
     */
    public double[][] getNextGDavid(double[][] Gk, double[][] delta_xk, double[][] delta_wk, double[][] v_k) {
        double[][] part1 = divideMatrix(
                multMatrix(delta_xk, transpose(delta_xk)),
                scalarMult(delta_wk, delta_xk));
        double[][] part2 = divideMatrix(
                multMatrix(v_k, transpose(v_k)),
                scalarMult(v_k, delta_wk));
        return subtractMatrix(
                subtractMatrix(Gk, part1),
                part2);
    }



    @Override
    protected double[] runImpl(Gradient gradient, Function<double[], Double> function, double[] point) {
        return new double[0];
    }
}

