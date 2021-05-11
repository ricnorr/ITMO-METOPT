package thirdLab.matrix;

public interface Matrix {
    double getElement(int a, int b);

    int getColumnNumbers();

    int getRowNumbers();

    double[][] getMatrix();

    void replace(int i, int j, double x);
}
