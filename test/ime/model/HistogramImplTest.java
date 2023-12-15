package ime.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * class to test the histogramImpl class.
 */
public class HistogramImplTest {

  private Image image;
  private Histogram histogram;

  /**
   * set up method to set before tests.
   */
  @Before
  public void setUp() {
    float[][][] testPixels = new float[][][]{{{55, 5, 5}, {73, 3, 83}},
        {{122, 242, 2}, {55, 4, 4}}};
    image = new ImagePixelImpl(testPixels, ImageType.RGB);
    histogram = new HistogramImpl(image);
  }

  /**
   * Test getChannelCount method.
   */
  @Test
  public void testGetChannelCount() {

    int expectedChannelCount = 3;
    int actualChannelCount = histogram.getChannelCount();
    assertEquals(expectedChannelCount, actualChannelCount);
  }

  /**
   * Test getColorChannels method.
   */
  @Test
  public void testGetColorChannels() {

    List<ColorChannel> expectedChannels = Arrays.asList(ColorChannel.RED,
        ColorChannel.GREEN, ColorChannel.BLUE);
    List<ColorChannel> actualChannels = histogram.getColorChannels();
    assertEquals(expectedChannels, actualChannels);
  }

  /**
   * Test getPeakValue method when there is peak.
   */
  @Test
  public void testGetPeakValue() {
    int channelIndex = 0;

    assertEquals(2, histogram.getPeakValue(channelIndex, 10, 245));
    assertEquals(2, histogram.getPeakValue(channelIndex, 0, 255));
    assertEquals(1, histogram.getPeakValue(2, 0, 255));
    assertEquals(0, histogram.getPeakValue(0, 30, 30));
    assertEquals(0, histogram.getPeakValue(1, 10, 240));
  }

  /**
   * Test getPeakValue with an invalid channel index.
   */
  @Test
  public void testGetPeakValueInvalidIndex() {

    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(-10, 11, 23));
    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(5, 11, 23));
  }


  /**
   * Test getPeakValue with an invalid channel index.
   */
  @Test
  public void testGetPeakValueInvalidStart() {

    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(1, -1, 23));
    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(1, 275, 23));
  }

  /**
   * Test getPeakValue with an invalid channel index.
   */
  @Test
  public void testGetPeakValueInvalidEnd() {

    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(1, 1, -23));
    assertThrows(IllegalArgumentException.class,
        () -> histogram.getPeakValue(1, 25, 283));
  }

  /**
   * Test when providing valid inputs.
   */
  @Test
  public void testGetMostFrequentValueValid() {
    // Test when providing valid inputs
    int channelIndex = 0;
    int start = 10;
    int end = 245;
    int actual = 55;

    int mostFrequentValue = histogram.getMostFrequentValue(channelIndex, start, end);
    assertEquals(actual, mostFrequentValue);
  }

  /**
   * test when image is black (no peaks).
   */
  @Test
  public void testGetMostFrequentValueBlackImage() {
    float[][][] testPixels = new float[][][]{{{0, 0, 0}, {0, 0, 0}}, {{0, 0, 0}, {0, 0, 0}}};
    image = new ImagePixelImpl(testPixels, ImageType.RGB);
    histogram = new HistogramImpl(image);

    int channelIndex = 0;
    int start = 10;
    int end = 245;

    int mostFrequentValue = histogram.getMostFrequentValue(channelIndex, start, end);
    assertEquals(start, mostFrequentValue);
    assertEquals(4, histogram.getPeakValue(0, 0, 0));
    assertEquals(4, histogram.getPeakValue(0, 0, 255));
  }

  /**
   * test for invalid channel index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetMostFrequentValueInvalidChannelIndex() {
    // Test when providing an invalid channel index
    int invalidChannelIndex = -1;
    int start = 10;
    int end = 245;

    histogram.getMostFrequentValue(invalidChannelIndex, start, end);
  }

  /**
   * test for invalid start.
   */
  @Test
  public void testGetMostFrequentValueInvalidStartEnd() {

    assertThrows(IllegalArgumentException.class,
        () -> histogram.getMostFrequentValue(0, -10, 235));
    assertThrows(IllegalArgumentException.class,
        () -> histogram.getMostFrequentValue(0, 40, 20));
    assertThrows(IllegalArgumentException.class,
        () -> histogram.getMostFrequentValue(0, 40, 300));
  }

}