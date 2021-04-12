package secondLab.method;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;



public class QuickestDescentMethod extends AbstractGradientMethod {

    @Override
    public double[] runImpl(Function<double[], Double> function, double[] point, double epsilon) {
        double[] nextPoint = point;
        long iterations = 0;
        ArrayList<Double> listForOutput =  new ArrayList<>();
        listForOutput.add(point[0]);
        listForOutput.add(point[1]);
        do {
            iterations++;
            point = nextPoint;
            double[] gradient = findGradient(point);
            double lambda = findArgMinGolden(point, gradient, function);
            nextPoint = subtract(point, multiplyValue(lambda, gradient));
            listForOutput.add(-multiplyValue(lambda, gradient)[0]); // visualization
            listForOutput.add(-multiplyValue(lambda, gradient)[1]); // visualization
        } while (iterations < Long.MAX_VALUE && abs(nextPoint, point) >= epsilon);
        writeInFile("QDM.out", listForOutput);
        System.out.print(iterations + " ");
        return point;
    }
}
