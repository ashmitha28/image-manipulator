package ime.controller;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for testing the {@link PpmFileHandler} class.
 */
public class PpmFileHandlerTest {

  private FileHandler fileHandler;

  /**
   * Set up the test environment by initializing the {@link PpmFileHandler}.
   */
  @Before
  public void setUp() {
    fileHandler = new PpmFileHandler();
  }

  /**
   * Test case to verify that the {@code loadImage} method throws an {@link IOException} when an
   * image file does not exist.
   *
   * @throws IOException If the test does not throw an exception as expected.
   */
  @Test
  public void testLoadImage() {

    assertThrows(IOException.class, () -> fileHandler.loadImage("invalidImage.ppm"));

  }

  /**
   * Test case to verify loading an image in PPM format and comparing the pixel values with the
   * expected values.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test
  public void testLoadImagePng() throws IOException {
    float[][][] expected = {
        {{0, 50, 0}, {0, 150, 0}},
        {{0, 25, 0}, {0, 75, 0}}
    };
    float[][][] pixels = fileHandler.loadImage("test/resources/testImage.ppm");
    assertTrue(Arrays.deepEquals(expected, pixels));
  }

  /**
   * Test case to verify saving an image in PPM format and checking if the file is created
   * successfully.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test
  public void testSaveImage() throws IOException {
    float[][][] pixels = new float[][][]{
        {{0, 50, 0}, {0, 150, 0}},
        {{0, 25, 0}, {0, 75, 0}}
    };
    fileHandler.saveImage(pixels, "test/resources/testImage.ppm");
    float[][][] loaded = fileHandler.loadImage("test/resources/testImage.ppm");
    assertArrayEquals(pixels, loaded);
  }

  /**
   * Test case to verify that the {@code saveImage} method throws an {@link IOException} when the
   * specified directory is invalid.
   *
   * @throws IOException If the test does not throw an exception as expected.
   */
  @Test
  public void testSaveImageInvalidDirectory() {
    float[][][] image = new float[][][]{
        {{0, 50, 0}, {0, 150, 0}},
        {{0, 25, 0}, {0, 75, 0}}
    };
    assertThrows(IOException.class, () -> fileHandler.saveImage(image, "test/invalid/test.ppm"));
  }
}
