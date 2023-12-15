package ime.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.Test;

/**
 * JUnit test class for testing the {@link BufferedImageHandler} class.
 */
public class BufferedImageHandlerTest {

  /**
   * test to get image pixels.
   */
  @Test
  public void testGetImagePixels() {
    BufferedImageHandler handler = new BufferedImageHandler();

    // Create a sample BufferedImage
    int width = 3;
    int height = 2;
    BufferedImage sampleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    sampleImage.setRGB(0, 0, Color.RED.getRGB());
    sampleImage.setRGB(1, 0, Color.GREEN.getRGB());
    sampleImage.setRGB(2, 0, Color.BLUE.getRGB());

    float[][][] resultPixels = handler.getImagePixels(sampleImage);
    float[][][] expectedPixels = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    assertArrayEquals(expectedPixels, resultPixels);
  }

  /**
   * test to convert into image.
   */
  @Test
  public void testConvertIntoImage() {
    BufferedImageHandler handler = new BufferedImageHandler();

    // Create a sample 3D array of pixel values
    float[][][] pixelValues = {
        {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}},
        {{255, 255, 0}, {0, 255, 255}, {255, 0, 255}}
    };

    BufferedImage resultImage = handler.convertIntoImage(pixelValues);

    // Check the pixel values in the resulting BufferedImage
    assertEquals(Color.RED.getRGB(), resultImage.getRGB(0, 0));
    assertEquals(Color.GREEN.getRGB(), resultImage.getRGB(1, 0));
    assertEquals(Color.BLUE.getRGB(), resultImage.getRGB(2, 0));
  }
}