package ime.model;

import ime.controller.ImageDrawer;

/**
 * The HistogramDrawerImpl class implements the HistogramDrawer interface, providing methods to
 * visualize histograms as graphical representations using an associated ImageDrawer. It visualizes
 * histograms on a canvas image.
 **/
public class HistogramDrawerImpl implements HistogramDrawer {

  private final int height;
  private final int width;
  private final ImageDrawer imageDrawer;

  /**
   * Initializes a new instance of the HistogramDrawerImpl class with the specified width, height,
   * and an associated ImageDrawer.
   *
   * @param width       The width of the canvas for histogram visualization.
   * @param height      The height of the canvas for histogram visualization.
   * @param imageDrawer An ImageDrawer used for drawing the histogram.
   */
  public HistogramDrawerImpl(int width, int height, ImageDrawer imageDrawer) {
    this.width = width;
    this.height = height;
    this.imageDrawer = imageDrawer;
  }

  /**
   * Retrieves the maximum count value from a histogram, considering all color channels.
   *
   * @param histogram The histogram from which to find the maximum count.
   * @return The maximum count value among all color channels in the histogram.
   */
  private int getMaxCount(Histogram histogram) {
    int max = 0;
    for (int i = 0; i < histogram.getChannelCount(); i++) {
      if (histogram.getPeakValue(i, 0, 255) > max) {
        max = histogram.getPeakValue(i, 0, 255);
      }
    }
    return max;
  }

  /**
   * Sets up the canvas for drawing the histogram grid lines.
   *
   * @param imageDrawer The ImageDrawer to use for drawing the grid lines.
   */
  private void setUpHistogram(ImageDrawer imageDrawer) {
    imageDrawer.setColor(new int[]{211, 211, 211}); //Set the grid line color as Grey
    int columnLineAdder = width / 16;
    int rowLineAdder = height / 16;
    for (int i = 0; i < width; i += columnLineAdder) {
      imageDrawer.drawLine(i, 0, i, height);
    }
    for (int i = 0; i < height; i += rowLineAdder) {
      imageDrawer.drawLine(0, i, width, i);
    }
  }

  /**
   * Visualizes a histogram as a graphical representation. the method computes the normalised value
   * the start and the end of the pairs of pixels and draws the lines according to the values. The
   * drawing is made considering the y-axis grows downwards.
   *
   * @param histogram The histogram to visualize.
   * @return A 3D array of floating-point pixel values representing the visualized histogram.
   */
  @Override
  public float[][][] visualizeHistogram(Histogram histogram) {
    imageDrawer.setUpCanvas(256, 256);

    setUpHistogram(imageDrawer);
    int maxCount = getMaxCount(histogram);
    for (int i = 0; i < histogram.getChannelCount(); i++) {
      imageDrawer.setColor(histogram.getColorChannels().get(i).rgb);
      for (int j = 0; j < width - 1; j++) {
        int normalizedValueStart
            = maxCount == 0 ? 0 : height * histogram.getPeakValue(i, j, j) / maxCount;
        int normalizedValueEnd
            = maxCount == 0 ? 0 : height * histogram.getPeakValue(i, j + 1, j + 1) / maxCount;
        imageDrawer.drawLine(j, height - normalizedValueStart, j + 1, height - normalizedValueEnd);
      }
    }
    return imageDrawer.getImageDrawing();
  }
}
