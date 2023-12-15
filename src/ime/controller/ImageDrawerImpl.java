package ime.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class implements the ImageDrawer interface and provides methods for drawing on an image and
 * retrieving the image data. This implementation of the ImageDrawer class utilizes the
 * BufferedImage class and the methods provided by Graphics class for performing the drawing
 * functions on the image. The method setUpCanvas has to be invoked first to set up the canvas
 * before invoking other methods of the class.
 */
public class ImageDrawerImpl implements ImageDrawer {

  private final int channelCount = 3;
  private final BufferedImageHandler imageHandler;
  private BufferedImage image;
  private int height;
  private int width;

  private Graphics graphics;

  public ImageDrawerImpl() {
    imageHandler = new BufferedImageHandler();
  }

  /**
   * Gets the drawing content of the image as a 3D array of floating-point pixel values.
   *
   * @return A 3D array of floating-point pixel values representing the drawn content of image.
   */
  @Override
  public float[][][] getImageDrawing() {
    validateCanvas();
    return imageHandler.getImagePixels(image);
  }

  /**
   * Draws a line on the canvas between two specified points.
   *
   * @param x1 The x-coordinate of the starting point.
   * @param y1 The y-coordinate of the starting point.
   * @param x2 The x-coordinate of the ending point.
   * @param y2 The y-coordinate of the ending point.
   */
  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
    validateCanvas();
    if (isInvalidCoordinate(x1, y1) || isInvalidCoordinate(x2, y2)) {
      throw new IllegalArgumentException("Invalid co-ordinates provided");
    }
    graphics.drawLine(x1, y1, x2, y2);
  }

  private void validateCanvas() {
    if (image == null) {
      throw new IllegalArgumentException("Canvas not set up yet.");
    }
  }

  private boolean isInvalidCoordinate(int x, int y) {
    return x < 0 || x > width || y < 0 || y > height;
  }

  /**
   * Sets the drawing color based on a color palette.
   *
   * @param colorPalette An array of three values representing the Red, Green, and Blue color
   *                     components.
   * @throws IllegalArgumentException If the colorPalette does not contain three values.
   */
  @Override
  public void setColor(int[] colorPalette) {
    validateCanvas();
    if (colorPalette.length != channelCount) {
      throw new IllegalArgumentException("Color Palette should contain three values corresponding"
          + "to Red, Green, and Blue values.");
    }
    graphics.setColor(new Color(colorPalette[0], colorPalette[1], colorPalette[2]));
  }

  /**
   * Sets up a drawing canvas with the specified width and height. Color will be set to white by
   * default.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   */
  @Override
  public void setUpCanvas(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    graphics = image.getGraphics();
    this.width = width;
    this.height = height;
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
  }
}
