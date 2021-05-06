package thirdLab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Point2 {
    public static void main(String[] args) {
        String dir = "matr/point2";
        try {
            if (Path.of(dir).getParent() != null) {
                Files.createDirectories(Path.of(dir));
            }
            for (int i = 0; i < 10; i++) {
                MatrixUtilities.genWriteAkSoLE(dir + File.separator + "test" + i, i);
            }
            for (int j = 0; j < 10; j++) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
