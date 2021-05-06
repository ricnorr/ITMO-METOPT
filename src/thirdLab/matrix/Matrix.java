package thirdLab.matrix;

import thirdLab.LUMatrix;

public interface Matrix {
    double getElement(int a, int b);
    int getColumnNumbers();
    int getRowNumbers();
    LUMatrix LUDecomposition();
}
