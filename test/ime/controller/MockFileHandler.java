package ime.controller;

import java.io.IOException;

/**
 * A mock implementation of the {@link FileHandler} interface used for testing purposes. This class
 * allows tracking method calls and simulating failures for loading and saving images.
 */
public class MockFileHandler implements FileHandler {

  private StringBuilder methodCallLogger;
  private Boolean fail;

  /**
   * Creates a new instance of the MockFileHandler.
   */
  public MockFileHandler() {
    this.methodCallLogger = new StringBuilder();
    this.fail = true;
  }

  /**
   * Loads image pixel data from a file and logs the method call.
   *
   * @param filename The path to the image file to load.
   * @return A 3D array representing the pixel values of the loaded image.
   * @throws IOException If an error occurs during the loading process or if the MockFileHandler is
   *                     set to fail.
   */
  @Override
  public float[][][] loadImage(String filename) throws IOException {
    float[][][] testPixelValues = {
        {{100, 50, 75}, {200, 150, 175}},
        {{50, 25, 37.5f}, {100, 75, 87.5f}}
    };
    methodCallLogger.append("loadImage called " + filename + " passed\n");
    if (fail) {
      throw new IOException("file handler failed");
    }
    return testPixelValues;
  }

  /**
   * Saves an image to a file and logs the method call.
   *
   * @param image    The image to be saved.
   * @param filename The path to the file where the image should be saved.
   * @throws IOException If an error occurs during the saving process or if the MockFileHandler is
   *                     set to fail.
   */
  @Override
  public void saveImage(float[][][] image, String filename) throws IOException {
    methodCallLogger.append("saveImage called " + filename + " passed\n");
    if (fail) {
      throw new IOException("file handler failed");
    }
  }

  /**
   * Get a log of method calls made to this MockFileHandler instance.
   *
   * @return A string containing the log of method calls.
   */
  public String getLogger() {
    return methodCallLogger.toString();
  }

  /**
   * Clear the log of method calls made to this MockFileHandler instance.
   */
  public void clearLogger() {
    methodCallLogger = new StringBuilder();
  }

  /**
   * Set the failure flag for the MockFileHandler.
   *
   * @param failFlag A boolean flag indicating whether the MockFileHandler should simulate
   *                 failures.
   */
  public void setFailureFlag(Boolean failFlag) {
    this.fail = failFlag;
  }
}
