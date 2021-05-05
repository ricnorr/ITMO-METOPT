package thirdLab.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;


/**
 * Матрица должна быть симметричной по профилю
 */
public class ProfileMatrix implements Matrix {
    private double[] al;      // эл-ты нижнего треуг
    private double[] au;      // эл-ты верхнего треуг
    private double[] di;      // хранит диагонльные элементы
    private int[] ia;      //ia[i + 1] - ia[i] - кол-во внедиагоныльных эл-ов, на i-ой строчке
    private int n; //размерность
    private int inLu;

    public ProfileMatrix(double[][] matrix) {
        assert (matrix.length == matrix[0].length);
        n = matrix.length;
        di = new double[matrix.length];
        ia = new int[matrix.length + 2];
        ArrayList<Double> alList = new ArrayList<>();
        ArrayList<Double> auList = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            di[i] = matrix[i][i];
        }
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < matrix.length; i++) {
            // сколько эл-ов в профиле в i-ой строке
            ia[i + 1] = ia[i] + getRowProfileLen(matrix, i, alList, auList);
        }
        // заполнили индексы для строк
        al = new double[alList.size()];
        au = new double[auList.size()];
        for (int i = 0; i < alList.size(); i++) {
            al[i] = alList.get(i);
        }
        for (int i = 0; i < auList.size(); i++) {
            au[i] = auList.get(i);
        }
    }

    public double getElement(int a, int b) {
        if (a == b) {
            return di[a];
        }
        boolean flag = true;
        if (b > a) { // now minimum is a
            int temp = b;
            b = a;
            a = temp;
            flag = false;
        }
        int rowProfile = ia[a + 1] - ia[a];
        int indexWhereProfileBegins = a - rowProfile;
        if (b < indexWhereProfileBegins) {
            return 0;
        } else {
            if (flag) {
                return al[ia[a] + b - indexWhereProfileBegins];
            } else {
                return au[ia[a] + b - indexWhereProfileBegins];
            }
        }
    }

    private int getRowProfileLen(double[][] matrix, int row, List<Double> alList, List<Double> auList) {
        int index = 0;
        while (matrix[row][index] == 0 && index != row) {
            index++;
        }
        int count = 0;
        while (index != row) {
            alList.add(matrix[row][index]);
            auList.add(matrix[index][row]);
            count++;
            index++;
        }
        return count;
    }


    public static void main(String[] args) { // тесты для матрицы проходят, значит оно работает
        double[][][] matrixes = new double[100][][];
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int n = abs(random.nextInt()) % 100;
            matrixes[i] = new double[n][n];
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < j; k++) {
                    if ((j + k) % 5 == 1) {
                        matrixes[i][j][k] = 0;
                        matrixes[i][k][j] = 0;
                    } else {
                        matrixes[i][j][k] = max(0, random.nextDouble());
                        matrixes[i][k][j] = max(0, random.nextDouble());
                    }
                }
                matrixes[i][j][j] = random.nextDouble();
            }
            ProfileMatrix a = new ProfileMatrix(matrixes[i]);
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (matrixes[i][j][k] != a.getElement(j, k)) {
                        System.out.println("FAILED");
                    }
                }
            }
        }
    }


    @Override
    public int getColumnNumbers() {
        return n;
    }

    @Override
    public int getRowNumbers() {
        return n;
    }
}
