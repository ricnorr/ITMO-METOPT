package thirdLab;

import thirdLab.matrix.Matrix;

import java.util.Arrays;


/**
 * сначала запускать directWalk, затем reverseWalk.
 * ReverseWalk бросит одно из исключений, если ответа нет / много решений
 */
public class GaussMethod {


    private static double EPSILON = 0.000001;

    public void directWalk(double[][] matrix) {
        if (matrix[0].length > matrix.length + 1) {
            throw new NoExactSolutionException();
        }
        int currentRow = 0;
        int currentCol = 0;
        while (currentRow < matrix.length && currentCol < matrix[0].length - 1) {
            int index = findMaxByAbsInColumn(matrix, currentRow, currentCol);
            if (equals(matrix[index][currentCol], 0)) {
                currentCol++;
                continue;
            }
            swapLines(matrix, index, currentRow);
            // теперь можно вычитать
            for (int i = currentRow + 1; i < matrix.length; i++) {
                subtract(matrix[i], matrix[currentRow], matrix[i][currentCol] / matrix[currentRow][currentCol]);
            }
            currentRow++;
            currentCol++;
        }
    }

    public double[] reverseWalk(double[][] matrix) {
        int currentRow = matrix.length - 1;
        while (currentRow > -1 && equals(matrix[currentRow][matrix[0].length - 2], 0)) {
            if (!equals(matrix[currentRow][matrix[0].length - 1], 0)) {
                throw new NoSolutionException();
            }
            currentRow--;
        }
        if (currentRow == -1 || countNoZeroElements(matrix, currentRow) > 1) {
            throw new NoExactSolutionException();
        }
        double[] answer = new double[matrix[0].length - 1];
        for (int i = answer.length - 1; i > -1; i--) {
            if (matrix[i][i] == 0) {
                throw new NoExactSolutionException();
            }
            double rightPart = matrix[i][matrix[0].length - 1];
            for (int j = i + 1; j < answer.length; j++) {
                rightPart -= matrix[i][j] * answer[j];
            }
            answer[i] = rightPart / matrix[i][i];
        }
        return answer;
    }

    private int findMaxByAbsInColumn(double[][] matrix, int row, int column) {
        double mx = Math.abs(matrix[row][column]);
        int index = row;
        for (int i = row; i < matrix.length; i++) {
            if (Math.abs(matrix[i][column]) > mx) {
                mx = Math.abs(matrix[i][column]);
                index = i;
            }
        }
        return index;
    }


    private int countNoZeroElements(double[][] matrix, int row) {
        int cnt = 0;
        for (int i = 0; i < matrix.length - 1; i++) {
            if (!equals(matrix[row][i], 0)) {
                cnt++;
            }
        }
        return cnt;
    }

    private boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }


    private void swapLines(double[][] matrix, int fromLine, int toLine) {
        double[] temp = new double[matrix[0].length];
        System.arraycopy(matrix[fromLine], 0, temp, 0, matrix[0].length);
        matrix[fromLine] = matrix[toLine];
        matrix[toLine] = temp;
    }

    private void subtract(double[] where, double[] what, double coefficient) {
        for (int i = 0; i < where.length; i++) {
            where[i] -= coefficient * what[i];
        }
    }

    public static void main(String[] args) {
        double[][] matrix = {{3, -2, -6}, {5, 1, 3}};
        double[][] matrixOneSolution = {{2, 1, 1, 2}, {1, -1, 0, -2}, {3, -1, 2, 2}}; // -1, 1, 3
        double[][] matrixNoSolutions = {{4, -3, 2, -1, 8}, {3, -2, 1, -3, 7}, {5, -3, 1, -8, 1}}; // no sol
        double[][] manySolutions = {{2, 3, -1, 1, 1}, {8, 12, -9, 8, 3}, {4, 6, 3, -2, 3}, {2, 3, 9, -7, 3}}; // many
        double[][] matrixOneSolution2 = {{2, -1, 5, 10}, {1, 1, -3, -2}, {2, 4, 1, 1}}; // 1.9999999999999996 -0.9999999999999998 1.0000000000000002
        double[][] matrixManySolutions = {{1, 1, -1, -2, 0}, {2, 1, -1, 1, -2}, {-1, 1, -1, 1, 4}};
        double[][] matrixNoSolutions2 = {{1, 1, -1, 0}, {2, -1, -1, -2}, {4, 1, -3, 5}}; // no sol
        double[][] noSolutions3 = {{0, 0, 1}, {0, 0, 2}};
        new GaussMethod().directWalk(noSolutions3);
        Arrays.stream(new GaussMethod().reverseWalk(noSolutions3)).forEach(System.out::println);

    }
}
