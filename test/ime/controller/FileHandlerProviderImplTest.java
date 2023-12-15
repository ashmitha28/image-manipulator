package ime.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains the unit tests for {@link FileHandlerProviderImpl}.
 */
public class FileHandlerProviderImplTest {

  private FileHandlerProvider fileHandlerProvider;

  /**
   * setup.
   */
  @Before
  public void setUp() {
    fileHandlerProvider = new FileHandlerProviderImpl();
  }


  /**
   * Test case to verify that the {@code getFileHandler} method returns a {@link PpmFileHandler} for
   * a .ppm file.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test
  public void testGetFileHandlerForPPM() throws IOException {
    FileHandler fileHandler = fileHandlerProvider.getFileHandler("testImage.ppm");
    assertTrue(fileHandler instanceof PpmFileHandler);
  }

  /**
   * Test case to verify that the {@code getFileHandler} method returns a {@link CommonFileHandler}
   * for a .jpg file.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test
  public void testGetFileHandlerForJPG() throws IOException {
    FileHandler fileHandler = fileHandlerProvider.getFileHandler("testImage.jpg");
    assertTrue(fileHandler instanceof CommonFileHandler);
  }

  /**
   * Test case to verify that the {@code getFileHandler} method returns a {@link CommonFileHandler}
   * for a .png file.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test
  public void testGetFileHandlerForPNG() throws IOException {
    FileHandler fileHandler = fileHandlerProvider.getFileHandler("testImage.png");
    assertTrue(fileHandler instanceof CommonFileHandler);
  }

  /**
   * Test case to verify that the {@code getFileHandler} method throws an
   * {@link IllegalArgumentException} for an invalid file type.
   *
   * @throws IOException If an IO exception occurs during the test.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileType() throws IOException {
    fileHandlerProvider.getFileHandler("image.txt");
  }

  /**
   * Test case to verify that the {@code getFileHandler} method throws an
   * {@link IllegalArgumentException} for a file with no extension.
   *
   * @throws IllegalArgumentException If the test does not throw an exception as expected.
   */
  @Test
  public void testFileWithNoExtension() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () ->
        fileHandlerProvider.getFileHandler("image"));
  }

}