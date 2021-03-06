package thirdLab.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static thirdLab.matrix.MatrixUtilities.vectorToString;


/**
 * Матрица должна быть симметричной по профилю
 */
public class ProfileMatrix extends AbstractMatrix {
    private double[] al;      // эл-ты нижнего треуг
    private double[] au;      // эл-ты верхнего треуг
    private double[] di;      // хранит диагонльные элементы
    private int[] ia;      //ia[i + 1] - ia[i] - кол-во внедиагоныльных эл-ов, на i-ой строчке
    private int n; //размерность


    public ProfileMatrix(String filename) {
        Path path = Path.of(filename);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            al = Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToDouble(Double::valueOf).toArray();
            au = Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToDouble(Double::valueOf).toArray();
            di = Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToDouble(Double::valueOf).toArray();
            ia = Arrays.stream(reader.readLine().split("\\s+")).filter(x -> !x.isEmpty()).mapToInt(Integer::valueOf).toArray();
            n = di.length;
        } catch (IOException e) {
            System.err.println("IO failed");
        } catch (NumberFormatException debug) {
            System.err.println("Debug");
        }
    }

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

    public ProfileMatrix(double[] al, double[] au, double[] di, int[] ia) {
        this.al = al;
        this.au = au;
        this.di = di;
        this.ia = ia;
        this.n = di.length;
    }


    public void writeInFile(String filename) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename).toAbsolutePath())) {
            writer.write(vectorToString(al));
            writer.write(vectorToString(au));
            writer.write(vectorToString(di));
            writer.write(Arrays.stream(ia).mapToObj(Integer::toString).collect(Collectors.joining(" ", "", "\n")));
        } catch (IOException e) {
            e.printStackTrace();
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


    @Override
    public int getColumnNumbers() {
        return n;
    }

    @Override
    public int getRowNumbers() {
        return n;
    }

    /**
     * Заменяет элемент только если он был в профиле
     * Необходимо для LU разложения
     */
    @Override
    public void replace(int i, int j, double x) {
        if (i == j) {
            di[i] = x;
            return;
        }
        boolean flag = true;
        if (j > i) { // now minimum is a
            int temp = j;
            j = i;
            i = temp;
            flag = false;
        }
        int rowProfile = ia[i + 1] - ia[i];
        int indexWhereProfileBegins = i - rowProfile;
        if (j >= indexWhereProfileBegins) {
            if (flag) {
                al[ia[i] + j - indexWhereProfileBegins] = x;
            } else {
                au[ia[i] + j - indexWhereProfileBegins] = x;
            }
        }
    }


    public static void main(String[] args) { // тесты для матрицы проходят, значит оно работает
        double[][][] matrixes = new double[100][][];
        for (int i = 0; i < 100; i++) {
            matrixes[i] = MatrixUtilities.generateMatrix();
            int n = matrixes[i].length;
            ProfileMatrix a = new ProfileMatrix(matrixes[i]);
            a.writeInFile("matr/test.txt");
            LUMatrix b = MatrixUtilities.LUDecomposition(new ProfileMatrix("matr/test.txt"));
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (matrixes[i][j][k] != a.getElement(j, k)) {
                        System.out.println("FAILED");
                    }
                }
            }
        }
    }


}
