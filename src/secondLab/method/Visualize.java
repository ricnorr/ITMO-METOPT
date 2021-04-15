package secondLab.method;

import java.io.IOException;
import java.util.Arrays;


import static secondLab.method.Tester.*;

public class Visualize {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(new ConjugateGradientMethod().runImpl(matrix5, bVector5, new double[]{4, 5}, 0.0001)));
        System.out.println(Arrays.toString(new DescentGradientMethod().run(func1, derivative1, new double[]{5, 6}, 0.001)));
        try {
            Process process = new ProcessBuilder("python","./lab2-visualiser/main.py", func5String, "CGM.out", "20").start();
        } catch (IOException exception) {
          throw new IllegalStateException();
        }
    }

}
