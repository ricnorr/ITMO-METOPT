package firstLab.method;

import java.util.function.Function;
public class BrentMethod extends AbstractMethod {
    private final ParabolaMethod parabolaMethod = new ParabolaMethod(function);
    public BrentMethod(Function<Double, Double> function) {
        super(function);
    }
    @Override
    protected double runImpl(double left, double right, double epsilon) {
        double x = left + (1 -  GOLDEN_RATIO) * (right - left);
        double w = left + (1 -  GOLDEN_RATIO) * (right - left);
        double v = left + (1 -  GOLDEN_RATIO) * (right - left);;
        double u;
        double prevDist;
        double curDist = right - left;
        double prevDistance = right - left;
        int index = 0;
        while (++index < MAX_ITERATIONS && (right - left) / 2 > epsilon) {
            u = left - 1;
            prevDist = curDist;
            prevDistance = right - left;
            if (x != w && w != v && v != x) {
                u = parabolaMethod.findParabolaMin(v, x, w);
            }
            if (!(left <= u && u <= right && Math.abs(u - x) < prevDist/2)) {
                if (x < (right - left) / 2) {
                    u = (1 - GOLDEN_RATIO) * x + GOLDEN_RATIO * right;
                    curDist = right - x;
                } else {
                    u = (1 - GOLDEN_RATIO) * x + GOLDEN_RATIO * left;
                    curDist = x - left;
                }
            }
            double fu = evaluateFx(u);
            double fx = evaluateFx(x);
            double fw = evaluateFx(w);
            double fv = evaluateFx(v);
            if (fu <= fx) {
                if (u >= x) {
                    left = x;
                } else {
                    right = x;
                }
                v = w; w = x; x = u;
            } else {
                if (u >= x) {
                    right = u;
                } else {
                    left = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                }
            }
            //printTableFormat(left, right, x, fx, v, fv, w, fw, (right - left) / prevDistance);
        }
        return (right + left) / 2;
    }
}
