package ime.model;

/**
 * A Histogram drawer is used to provide a visualization for a given histogram.
 */
public interface HistogramDrawer {

  /**
   * Visualizes a histogram as a graphical representation.
   *
   * @param histogram The histogram to visualize.
   * @return A 3D array of floating-point pixel values representing the visualized histogram.
   */
  float[][][] visualizeHistogram(Histogram histogram);
}
