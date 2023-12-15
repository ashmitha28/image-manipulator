package ime.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

/**
 * test for image drawer impl.
 */
public class ImageDrawerImplTest {

  private ImageDrawerImpl imageDrawer;

  /**
   * setup before tests.
   */
  @Before
  public void setUp() {
    imageDrawer = new ImageDrawerImpl();
    imageDrawer.setUpCanvas(100, 100);
  }

  /**
   * test to setup canvas which throws exception.
   */
  @Test
  public void testMethodInvocationBeforeSetupCanvasThrowsException() {
    imageDrawer = new ImageDrawerImpl();
    assertThrows(IllegalArgumentException.class,
        () -> imageDrawer.setColor(new int[]{121, 231, 111}));
    assertThrows(IllegalArgumentException.class,
        () -> imageDrawer.drawLine(0, 1, 2, 3));
    assertThrows(IllegalArgumentException.class,
        () -> imageDrawer.getImageDrawing());
  }

  /**
   * test to get image drawing.
   */
  @Test
  public void testGetImageDrawing() {
    float[][][] imageDrawing = imageDrawer.getImageDrawing();
    assertNotNull(imageDrawing);
    assertEquals(100, imageDrawing.length);
    assertEquals(100, imageDrawing[0].length);
    assertEquals(3, imageDrawing[0][0].length);
  }

  /**
   * test to draw line.
   */
  @Test
  public void testDrawLine() {
    int x1 = 10;
    int y1 = 10;
    int x2 = 90;
    int y2 = 90;
    float[] color = new float[]{255, 255, 255};
    imageDrawer.drawLine(x1, y1, x2, y2);

    float[][][] imageDrawing = imageDrawer.getImageDrawing();
    assertArrayEquals(color, imageDrawing[x1][y1], 0.0f);
    assertArrayEquals(color, imageDrawing[20][20], 0.0f); //point in betweem
    assertArrayEquals(color, imageDrawing[x2][y2], 0.0f);
  }

  /**
   * test to draw line with invalid coordinates (X).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDrawLineWithInvalidCoordinates() {
    int x1 = -2;
    int y1 = 10;
    int x2 = 110;
    int y2 = 90;

    imageDrawer.drawLine(x1, y1, x2, y2);
  }

  /**
   * x>width.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDrawLineWithOutOfBoundCoordinate() {
    int x1 = 267;
    int y1 = 10;
    int x2 = 110;
    int y2 = 90;
    imageDrawer.drawLine(x1, y1, x2, y2);
  }

  /**
   * y>height.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDrawLineWithOutOfBoundCoordinateY() {
    int x1 = 267;
    int y1 = 400;
    int x2 = 110;
    int y2 = 90;
    imageDrawer.drawLine(x1, y1, x2, y2);
  }

  /**
   * color pallet for green and blue should be 0.
   */
  @Test
  public void testSetColor() {
    int[] colorPalette = {255, 0, 0}; // Red
    float[] expectedColor = {255, 0, 0}; // Red
    float[] defaultColor = {255, 255, 255}; // Red
    imageDrawer.setColor(colorPalette);
    imageDrawer.drawLine(10, 15, 10, 15); //draw on a point
    float[][][] imageDrawing = imageDrawer.getImageDrawing();

    assertArrayEquals(expectedColor, imageDrawing[15][10], 0);
    assertArrayEquals(defaultColor, imageDrawing[0][10], 0); //default color on other points
  }

  /**
   * test for invalid pallete.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetColorWithInvalidPalette() {
    int[] invalidPalette = {255, 0}; // Invalid palette
    imageDrawer.setColor(invalidPalette);
  }

  /**
   * test to set up canvas.
   */
  @Test
  public void testSetUpCanvas() {
    int height = 150;
    int width = 200;

    imageDrawer.setUpCanvas(width, height);

    float[][][] imageDrawing = imageDrawer.getImageDrawing();
    assertNotNull(imageDrawing);
    assertEquals(height, imageDrawing.length);
    assertEquals(width, imageDrawing[0].length);
    assertEquals(3, imageDrawing[0][0].length);
  }

  /**
   * test to set canvas with invalid dimensions.
   */
  @Test
  public void testSetUpCanvasInvalidDimensions() {

    assertThrows(IllegalArgumentException.class, () -> imageDrawer.setUpCanvas(-280, 150));
    assertThrows(IllegalArgumentException.class, () -> imageDrawer.setUpCanvas(250, -150));

  }

}