package ime.controller;

/**
 * An Image Handler is used for converting an Image into a 2D array of its pixel values and vice
 * versa. A pixel value is represented as an array of each of its color channels.
 */
public interface ImageHandler<T> {

  /**
   * gets the image object and converts it into 3d float array.
   *
   * @param image object.
   * @return 3d float array of pixel values.
   */
  float[][][] getImagePixels(T image);

  /**
   * convert method to convert the 3d array back to image.
   *
   * @param pixelValues 3d float array.
   * @return image.
   */
  T convertIntoImage(float[][][] pixelValues);

}
