package thirdLab.matrix;

/**
 * Сделано на всякий случай для совместимости с LU
 */
public class StandardMatrix extends AbstractMatrix {
    private double[][] matrix;
    private int n;

    public StandardMatrix(double[][] matrix) {
        this.matrix = matrix;
        n = matrix.length;
    }

    @Override
    public double getElement(int a, int b) {
        return matrix[a][b];
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
