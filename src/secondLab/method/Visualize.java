package secondLab.method;

import java.io.IOException;
import java.util.Arrays;


import static secondLab.method.Tester.*;

public class Visualize {
    public static void main(String[] args) {

        System.out.println(Arrays.toString(new DescentGradientMethod().run(func1, derivative1, new double[]{5, 6}, 0.001)));
        try {
            Process process = new ProcessBuilder("python3","./lab2-visualiser/main.py", func1String, "DGM.out", "12").start();
        } catch (IOException exception) {
          throw new IllegalStateException();
        }
    }

}
