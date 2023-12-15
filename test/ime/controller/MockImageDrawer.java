package ime.controller;

/**
 * mock image drawer for image drawer interface.
 */
public class MockImageDrawer implements ImageDrawer {

  private StringBuilder methodCallLogger;
  private Boolean fail;

  /**
   * instatiates objects.
   */
  public MockImageDrawer() {
    this.methodCallLogger = new StringBuilder();
    this.fail = false;
  }

  /**
   * get image drawing.
   *
   * @return float array.
   */
  @Override
  public float[][][] getImageDrawing() {
    methodCallLogger.append("get Image called\n");
    if (fail) {
      throw new IllegalArgumentException("image drawer failed");
    }
    return new float[256][256][3];
  }

  /**
   * draw line.
   *
   * @param x1 The x-coordinate of the starting point.
   * @param y1 The y-coordinate of the starting point.
   * @param x2 The x-coordinate of the ending point.
   * @param y2 The y-coordinate of the ending point.
   */
  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
    methodCallLogger.append("draw line called\n" + x1 + " " + y1 + " " + x2 + " " + y2);
    if (fail) {
      throw new IllegalArgumentException("image drawer failed");
    }
  }

  /**
   * sets color.
   *
   * @param colorPalette An array of three values representing the Red, Green, and Blue color
   *                     components.
   */
  @Override
  public void setColor(int[] colorPalette) {
    methodCallLogger.append("set color called\n");
    if (fail) {
      throw new IllegalArgumentException("image drawer failed");
    }
  }

  /**
   * set up canvas.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   */
  @Override
  public void setUpCanvas(int width, int height) {
    methodCallLogger.append("set up canvas called\n");
    if (fail) {
      throw new IllegalArgumentException("image drawer failed");
    }
  }

  /**
   * get logger method.
   *
   * @return string.
   */
  public String getLogger() {
    return methodCallLogger.toString();
  }

  /**
   * Clear the log of method calls made to this MockFileHandlerProvider instance.
   */
  public void clearLogger() {
    methodCallLogger = new StringBuilder();
  }

  /**
   * Set the failure flag for the MockFileHandlerProvider.
   *
   * @param failFlag A boolean flag indicating whether the MockFileHandlerProvider should simulate
   *                 failures.
   */
  public void setFailureFlag(Boolean failFlag) {
    this.fail = failFlag;
  }
}
