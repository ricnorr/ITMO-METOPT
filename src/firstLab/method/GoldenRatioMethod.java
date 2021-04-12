package firstLab.method;

import java.util.function.Function;

public class GoldenRatioMethod extends AbstractMethod {

    public GoldenRatioMethod(Function<Double, Double> function) {
        super(function);
    }

    protected double runImpl(double left, double right, double epsilon) {
        double lambda = left + (1 - GOLDEN_RATIO) * (right - left), mu = left + GOLDEN_RATIO * (right - left);
        double funcLambda = evaluateFx(lambda), funcMu = evaluateFx(mu);
        double lastLeft = left;
        double lastRight = right;
        long steps = 0;
        while (right - left >= epsilon && steps < MAX_ITERATIONS) {
            //printTableFormat(left, right, lambda, funcLambda, mu, funcMu, (right - left) / (lastRight - lastLeft));
            steps++;
            lastLeft = left;
            lastRight = right;
            if (funcLambda > funcMu) {
                left = lambda;
                lambda = mu;
                funcLambda = funcMu;
                mu = left + GOLDEN_RATIO * (right - left);
                funcMu = evaluateFx(mu);
            } else {
                right = mu;
                mu = lambda;
                funcMu = funcLambda;
                lambda = left + (1 - GOLDEN_RATIO) * (right - left);
                funcLambda = evaluateFx(lambda);
            }
        }
        //printTableFormat(left, right, lambda, funcLambda, mu, funcMu, (right - left) / (lastRight - lastLeft));
        return (right + left) / 2;
    }

}