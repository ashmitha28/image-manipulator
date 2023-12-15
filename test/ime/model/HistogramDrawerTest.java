package ime.model;

import org.junit.Before;
import org.junit.Test;

import ime.controller.ImageDrawerImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for the HistogramDrawerImpl class that does not mock the drawer object.
 */
public class HistogramDrawerTest {
  private Histogram histogram;
  private HistogramDrawerImpl histogramDrawer;

  public HistogramDrawerTest() {
    this.histogram = new HistogramImpl(new ImagePixelImpl(new float[][][]{{{0, 1, 2}, {0, 0, 2}}},
            ImageType.RGB));
  }

  @Before
  public void setUp() {
    histogramDrawer = new HistogramDrawerImpl(256, 256, new ImageDrawerImpl());
  }

  @Test
  public void testVisualizeHistogram() {
    float[] white = new float[]{255, 255, 255};
    float[] grey = new float[]{211, 211, 211};
    float[] red = new float[]{255, 0, 0};
    float[] green = new float[]{0, 255, 0};
    float[] blue = new float[]{0, 0, 255};
    float[][][] result = histogramDrawer.visualizeHistogram(histogram);

    assertEquals(256, result.length);
    assertEquals(256, result[0].length);
    assertEquals(3, result[0][0].length);
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        if ((i == 128 && j < 2) || (i >= 192 && j == 2)) {
          assertArrayEquals(green, result[i][j], 0);
        } else if ((j == 0 && i < 128)) {
          assertArrayEquals(red, result[i][j], 0);
        } else if ((j == 2 && i <= 128) || (i == 128 && j == 3)
                || (i > 128 && j == 1) || (i > 128 && j == 3)) {
          assertArrayEquals(blue, result[i][j], 0);
        } else if (i % 16 == 0 || j % 16 == 0) {
          assertArrayEquals(grey, result[i][j], 0);
        } else {
          assertArrayEquals(white, result[i][j], 0);
        }
      }
    }
  }

}