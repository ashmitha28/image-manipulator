package ime.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The CommonFileHandler class is responsible for loading and saving RGB images in JPG and PNG
 * formats. It implements the FileHandler interface, This class uses a BufferedImageHandler to
 * handle the conversion between image data and BufferedImage objects.
 **/
public class CommonFileHandler implements FileHandler {

  private ImageHandler<BufferedImage> bufferedImageHandler;

  /**
   * constructor to initialise bufferedImageHandler class.
   */
  public CommonFileHandler() {
    bufferedImageHandler = new BufferedImageHandler();
  }

  /**
   * Saves a 3D array of floating-point image data to a file in JPG or PNG format.
   *
   * @param image    the image to be saved.
   * @param filename the filename (or path) that the image will be saved in.
   * @throws IOException If an error occurs while saving the image to the file.
   */
  @Override
  public void saveImage(float[][][] image, String filename) throws IOException {
    BufferedImage bufferedImage = bufferedImageHandler.convertIntoImage(image);
    ImageIO.write(bufferedImage, filename.split("\\.")[1], new File(filename));
  }

  /**
   * Loads an image from a file in JPG or PNG format and returns it as a 3D array of floating-point
   * image data.
   *
   * @param filename the filename to be loaded.
   * @return a 3d float array representing the pixel values of the image.
   * @throws IOException If an error occurs while reading the image from the file.
   */
  @Override
  public float[][][] loadImage(String filename) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new File(filename));
    return bufferedImageHandler.getImagePixels(bufferedImage);
  }
}
