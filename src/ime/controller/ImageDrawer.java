package ime.controller;

/**
 * This interface is designed to provide methods for drawing on an image and retrieving the image
 * data.
 */
public interface ImageDrawer {

  /**
   * gets the drawing content and returns the float 3d image.
   *
   * @return returns the image canvas.
   */
  float[][][] getImageDrawing();

  /**
   * Draws a line on the canvas between two specified points.
   *
   * @param x1 The x-coordinate of the starting point.
   * @param y1 The y-coordinate of the starting point.
   * @param x2 The x-coordinate of the ending point.
   * @param y2 The y-coordinate of the ending point.
   */
  void drawLine(int x1, int y1, int x2, int y2);

  /**
   * Sets the drawing color based on a color palette.
   *
   * @param colorPalette An array of three values representing the Red, Green, and Blue color
   *                     components.
   */
  void setColor(int[] colorPalette);

  /**
   * Sets up a drawing canvas with the specified width and height.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   */
  void setUpCanvas(int width, int height);
}
