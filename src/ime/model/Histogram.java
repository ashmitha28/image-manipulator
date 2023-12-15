package ime.model;

import java.util.List;

/**
 * interface histogram contains methods to creat a histogram image for a given image.
 */
public interface Histogram {


  /**
   * Retrieves the number of color channels in the image.
   *
   * @return The number of color channels present in the image.
   */
  int getChannelCount();

  /**
   * Retrieves the list of color channels associated with the image type used in the histogram.
   *
   * @return A list of ColorChannelEnum representing the color channels in the image type.
   */
  List<ColorChannel> getColorChannels();

  /**
   * Finds the maximum frequency of individual pixel values for the specified channel index and for
   * the given start and end pixel value. If the start and end values are equal, it will return the
   * frequency of the given pixel value in the given channel index.
   *
   * @param channelIndex The index of the color channel.
   * @param start        The start index of pixel values in consideration
   * @param end          The last index of pixel values in consideration
   * @return The peak value (y-coordinate) of the histogram for the specified channel.
   * @throws IllegalArgumentException if the channelIndex, start or end is invalid. It will also get
   *                                  thrown when start > end
   */
  int getPeakValue(int channelIndex, int start, int end) throws IllegalArgumentException;

  /**
   * Finds the most frequent value (x-coordinate of the peak) for the specified channel index. When
   * there are multiple values with same frequency, returns the first.
   *
   * @param channelIndex The index of the color channel.
   * @param start        The start index of pixel values in consideration
   * @param end          The last index of pixel values in consideration
   * @return The most frequent value (x-coordinate of the peak) for the specified channel.
   * @throws IllegalArgumentException if the channelIndex, start or end is invalid. It will also get
   *                                  thrown when start > end
   */
  int getMostFrequentValue(int channelIndex, int start, int end) throws IllegalArgumentException;

}
