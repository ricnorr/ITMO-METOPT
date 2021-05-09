package thirdLab.matrix;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public double[][] getMatrix() {
        double[][] result = new double[getRowNumbers()][getColumnNumbers()];
        for (int i = 0; i < getRowNumbers(); i++) {
            for (int j = 0; j < getColumnNumbers(); j++) {
                result[i][j] = getElement(i, j);
            }
        }
        return result;
    }
}
