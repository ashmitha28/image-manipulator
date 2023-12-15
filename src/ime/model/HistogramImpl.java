package ime.model;

import java.util.List;

/**
 * The HistogramImpl class implements the Histogram interface, providing methods to generate and
 * analyze histograms based on the pixel values of an Image. It calculates histograms for individual
 * color channels and provides methods to extract peak values and occurrence frequency.
 **/
public class HistogramImpl implements Histogram {

  private final int[][] hist;
  private final int channelCount;
  private final int width;
  private final ImageType imageType;

  /**
   * Constructs a HistogramImpl object based on the given Image, initializing the histogram data.
   *
   * @param image The Image from which to generate the histogram.
   */
  public HistogramImpl(Image image) {

    channelCount = image.getChannelCount();
    imageType = image.getImageType();
    width = 256;
    hist = new int[image.getChannelCount()][256];

    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {

        float[] pixel = image.getPixelValues(x, y);
        for (int i = 0; i < channelCount; i++) {
          hist[i][(int) (pixel[i])]++;
        }
      }
    }

  }

  /**
   * get channel count.
   *
   * @return integer count.
   */
  @Override
  public int getChannelCount() {
    return channelCount;
  }

  /**
   * getter for color channels.
   *
   * @return list of color channels.
   */
  @Override
  public List<ColorChannel> getColorChannels() {
    return imageType.colorChannels;
  }

  /**
   * y coordinate of peak.
   *
   * @param channelIndex The index of the color channel.
   * @param start        The start index of pixel values in consideration
   * @param end          The last index of pixel values in consideration
   * @return int.
   * @throws IllegalArgumentException invalid values.
   */
  @Override
  public int getPeakValue(int channelIndex, int start, int end) throws IllegalArgumentException {
    return getMaxFrequency(channelIndex, start, end);
  }

  /**
   * for the given channel the x coordinate of the peak.
   */

  @Override
  public int getMostFrequentValue(int channelIndex, int start, int end)
      throws IllegalArgumentException {
    int maxFrequency = getMaxFrequency(channelIndex, start, end);
    int pixelValue;
    for (pixelValue = start; pixelValue <= end; pixelValue++) {
      if (hist[channelIndex][pixelValue] == maxFrequency) {
        break;
      }
    }
    return pixelValue;
  }

  private int getMaxFrequency(int channelIndex, int start, int end) {
    validateChannelIndex(channelIndex);
    validateStartAndEnd(start, end);
    int maxFrequency = 0;
    for (int pixelValue = start; pixelValue <= end; pixelValue++) {
      if (hist[channelIndex][pixelValue] > maxFrequency) {
        maxFrequency = hist[channelIndex][pixelValue];
      }
    }
    return maxFrequency;
  }

  /**
   * checks if the passed channel index is valid.
   *
   * @param channelIndex channel index.
   */
  private void validateChannelIndex(int channelIndex) {
    if (channelIndex < 0 || channelIndex >= channelCount) {
      throw new IllegalArgumentException("Invalid channel index");
    }
  }

  private void validateStartAndEnd(int start, int end) {
    if (start < 0 || end > width || start > end) {
      throw new IllegalArgumentException("Invalid start / end value");
    }
  }
}
