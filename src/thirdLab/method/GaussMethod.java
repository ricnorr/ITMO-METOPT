package thirdLab.method;

import thirdLab.matrix.Matrix;
import thirdLab.matrix.MatrixUtilities;
import thirdLab.exception.NoExactSolutionException;

import java.util.Arrays;

import static thirdLab.matrix.MatrixUtilities.*;


/**
 * сначала запускать directWalk, затем reverseWalk.
 * ReverseWalk бросит одно из исключений, если ответа нет / много решений
 */
public class GaussMethod implements MatrixMethod {
    public void directWalk(double[][] matrix, double[] vectB) {
        if (matrix[0].length > matrix.length + 1) {
            throw new NoExactSolutionException();
        }
        int currentRow = 0;
        int currentCol = 0;
        while (currentRow < matrix.length && currentCol < matrix[0].length) {
            int index = findMaxByAbsInColumn(matrix, currentRow, currentCol);
            if (MatrixUtilities.equals(matrix[index][currentCol], 0)) {
                currentCol++;
                continue;
            }
            swapLines(matrix, index, currentRow);
            swapElements(vectB, index, currentRow);
            for (int i = currentRow + 1; i < matrix.length; i++) {
                double coefficient = matrix[i][currentCol] / matrix[currentRow][currentCol];
                subtract(matrix[i], matrix[currentRow], coefficient);
                subtract(vectB, i, currentRow, coefficient);
            }
            currentRow++;
            currentCol++;
        }
    }


    public double[] reverseWalk(double[][] matrix, double[] vectB) {
        int currentRow = matrix.length - 1;
        /*while (currentRow > -1 && equals(matrix[currentRow][matrix[0].length - 1], 0)) {
            if (!equals(vectB[currentRow], 0)) {
                throw new NoSolutionException();
            }
            currentRow--;
        }
        if (currentRow == -1 || countNoZeroElements(matrix, currentRow) > 1) {
            throw new NoExactSolutionException();
        }*/
        double[] answer = new double[matrix[0].length];
        for (int i = answer.length - 1; i > -1; i--) {
            /*if (equals(matrix[i][i], 0)) {
                throw new NoExactSolutionException();
            } */
            double rightPart = vectB[i];
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

    @Override
    public double[] solve(Matrix matrix, double[] f) {
        double[][] m = matrix.getMatrix();
        directWalk(m, f);
        return reverseWalk(m, f);
    }

    public static void main(String[] args) {
        /* double[][] matrix = {{3, -2, -6}, {5, 1, 3}};
        double[][] matrixOneSolution = {{2, 1, 1, 2}, {1, -1, 0, -2}, {3, -1, 2, 2}}; // -1, 1, 3
        double[][] matrixNoSolutions = {{4, -3, 2, -1, 8}, {3, -2, 1, -3, 7}, {5, -3, 1, -8, 1}}; // no sol
        double[][] manySolutions = {{2, 3, -1, 1, 1}, {8, 12, -9, 8, 3}, {4, 6, 3, -2, 3}, {2, 3, 9, -7, 3}}; // many
        double[][] matrixOneSolution2 = {{2, -1, 5, 10}, {1, 1, -3, -2}, {2, 4, 1, 1}}; // 1.9999999999999996 -0.9999999999999998 1.0000000000000002
        double[][] matrixManySolutions = {{1, 1, -1, -2, 0}, {2, 1, -1, 1, -2}, {-1, 1, -1, 1, 4}};
        double[][] matrixNoSolutions2 = {{1, 1, -1, 0}, {2, -1, -1, -2}, {4, 1, -3, 5}}; // no sol
        double[][] noSolutions3 = {{0, 0, 1}, {0, 0, 2}};*/

        double[][] onesol = {{2, 1, 1}, {1, -1, 0}, {3, -1, 2}};
        double[] b = {2, -2, 2};


        double[][] matrixNoSolutions = {{4, -3, 2, -1}, {3, -2, 1, -3}, {5, -3, 1, -8}}; // no sol
        double[] ans = {8, 7, 1};


        double[][] matrixOneSolution2 = {{2, -1, 5}, {1, 1, -3}, {2, 4, 1}}; // 1.9999999999999996 -0.9999999999999998 1.0000000000000002
        double[] ansv = {10, -2, 1};


        new GaussMethod().directWalk(matrixOneSolution2, ansv);
        Arrays.stream(new GaussMethod().reverseWalk(matrixOneSolution2, ansv)).forEach(System.out::println);

    }
}
