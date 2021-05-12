package thirdLab.research;

import thirdLab.exception.NoExactSolutionException;
import thirdLab.exception.NoSolutionException;
import thirdLab.matrix.MatrixUtilities;
import thirdLab.matrix.ProfileMatrix;
import thirdLab.matrix.SparseMatrix;
import thirdLab.method.ConjugateMethod;
import thirdLab.method.GaussMethod;
import thirdLab.method.LUMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Method;

public class Point23 {
    final static int TESTCOUNT = 20;

    private static void test(String dir, Method method) {
        try {
            if (Path.of(dir).getParent() != null) {
                Files.createDirectories(Path.of(dir));
            }
            for (int i = 0; i < TESTCOUNT; i++) {
                try {
                    String curTest = dir + File.separator + "test_" + i + ".txt";
                    method.invoke(null, curTest, i);
                    ProfileMatrix m = new ProfileMatrix(curTest);
                    try (BufferedReader reader = Files.newBufferedReader(Path.of(curTest))) {
                        for (int j = 0; j < 4; j++) {
                            reader.readLine();
                        }
                        double[] f = MatrixUtilities.readDoubleVector(reader);
                        double[] ans =  MatrixUtilities.readDoubleVector(reader);
                        //printRes(m.getColumnNumbers(), i, ans, new LUMethod().solve(new ProfileMatrix(m.getMatrix()), f));
                        //printRes(m.getColumnNumbers(), i, ans, new GaussMethod().solve(new ProfileMatrix(m.getMatrix()), f));
                         printRes(m.getColumnNumbers(), i, ans, new ConjugateMethod().solve(new SparseMatrix(m.getMatrix()), f, 0.0000000001));
                    }
                } catch (NoSolutionException | NoExactSolutionException e) {
                    System.err.println(e.getClass().getSimpleName());
                }
            }
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Method findMethodByName(String name) {
        Method[] methods = MatrixUtilities.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("размерность, k, |x* - xk|, |x* - xk|/|x*|");
        test("matr/point2", findMethodByName("genWriteAkSoLE"));
        test("matr/point3", findMethodByName("genWriteGilbertSoLE"));
    }

    private static void printRes(int n, int k, double[] x, double[] xk) {
        double t = MatrixUtilities.dist(x, xk);
        double xm = MatrixUtilities.dist(x, new double[x.length]);
        if (xm != 0) {
            xm = t / xm;
        }
        System.out.println(n + "         " + k + "     " + t + "      " + xm);
    }
}