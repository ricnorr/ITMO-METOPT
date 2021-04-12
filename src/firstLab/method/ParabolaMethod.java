package firstLab.method;

import java.util.function.Function;

public class ParabolaMethod extends AbstractMethod {

    protected ParabolaMethod(Function<Double, Double> function) {
        super(function);
    }


    private double findPoint(double compareValue, double border, double shift) {
        while (evaluateFx(border + shift) < compareValue) {
            shift /= 2;
        }
        return border + shift;
    }

    public double findParabolaMin(double x1, double x2, double x3) {
        double functionX1 = evaluateFx(x1);
        double functionX2 = evaluateFx(x2);
        double functionX3 = evaluateFx(x3);
        double parabolaCoefficient1 = (functionX2 - functionX1) / (x2 - x1);
        double parabolaCoefficient2 = 1 / (x3 - x2) * ((functionX3 - functionX1)/(x3 - x1) - (functionX2 - functionX1)/(x2 - x1));
        return  (x1 + x2 - parabolaCoefficient1 / parabolaCoefficient2) / 2;
    }

    @Override
    protected double runImpl(double left, double right, double epsilon) {
        double x1, x2, x3;
        x1 = left + (right - left) / 4;
        x2 = left + 2 * (right - left) / 4;
        x3 = left + 3 * (right - left) / 4;
        double functionX1 = evaluateFx(x1);
        double functionX2 = evaluateFx(x2);
        double functionX3 = evaluateFx(x3);
        if (functionX1 > functionX2 && functionX2 > functionX3) {
            x3 = findPoint(functionX2, right, -(right - left) / 4);
        }
        if (functionX1 < functionX2 && functionX2 < functionX3) {
            x1 = findPoint(functionX2, left, (right - left) / 4);
        }
        double previosX4 = Double.MAX_VALUE;
        long steps = 0;
        double prevDist = 0;
        prevDist = x3 - x1;
        while (true) {
            steps++;
            functionX2 = evaluateFx(x2);
            double x4 =  findParabolaMin(x1, x2, x3);
            //printTableFormat(x1, evaluateFx(x1), x2, evaluateFx(x2), x3, evaluateFx(x3), x4, evaluateFx(x4), (x3 - x1) / prevDist);
            double functionX4 = evaluateFx(x4);
            if (Math.abs(x4 - previosX4) <= epsilon) {
                // terminated;
                System.out.println(x4);
                return x4;
            }
            prevDist = x3 - x1;
            if (x1 < x4 && x4 < x2) {
                if (functionX4 >= functionX2) {
                    x1 = x4;
                } else {
                    x3 = x2;
                    x2 = x4;
                }
             } else if (x2 < x4 && x4 < x3) {
                    if (functionX4 >= functionX2) {
                        x3 = x4;
                    } else {
                        x3 = x2;
                    }
            } else {
                // x4 >= x3, terminated
                return (x1 + x3) / 2;
            }
            previosX4 = x4;
        }
    }
}
