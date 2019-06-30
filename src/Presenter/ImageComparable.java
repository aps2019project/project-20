package Presenter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.stream.IntStream;

public interface ImageComparable {
    default double computeSnapshotSimilarity(final Image image1, final Image image2) {
        final int width = (int) image1.getWidth();
        final int height = (int) image1.getHeight();
        final PixelReader reader1 = image1.getPixelReader();
        final PixelReader reader2 = image2.getPixelReader();

        final double nbNonSimilarPixels = IntStream.range(0, width).parallel().
                mapToLong(i -> IntStream.range(0, height).parallel().filter(j -> reader1.getArgb(i, j) != reader2.getArgb(i, j)).count()).sum();

        return 100d - nbNonSimilarPixels / (width * height) * 100d;
    }
}
