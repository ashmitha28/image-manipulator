package ime.model;

/**
 * This class is a representation of a unit in an image. It is a representation of various color
 * channels combined.
 */
public interface Pixel {

  /**
   * Get the value of the pixel.
   *
   * @return the value of the pixel
   */
  float getValue();

  /**
   * Get the intensity of the pixel.
   *
   * @return the intensity of the pixel
   */
  float getIntensity();

  /**
   * Get the luma of the pixel.
   *
   * @return the luma of the pixel
   */
  float getLuma();

  /**
   * Perform color transformation on this pixel based on the given coefficients for the color
   * channels in the pixel.
   *
   * @param transformCoefficients the coefficients of color channels for the transformation
   * @throws IllegalArgumentException if the list size is not the same as the number of color
   *                                  channels in the pixel
   */
  Pixel transformPixel(float[][] transformCoefficients) throws IllegalArgumentException;

  /**
   * Get the value for a given channel in the pixel.
   *
   * @param channel the color channel
   * @return the value for the color channel in the pixel
   */
  float getChannelValue(int channel);

  /**
   * Set the values for the color channels of the pixels.
   *
   * @param colorChannelValues the list of values for each of the color channels
   * @throws IllegalArgumentException if the list size is not the same as the number of color
   *                                  channels in the pixel
   */
  void setColor(float[] colorChannelValues) throws IllegalArgumentException;

  /**
   * Set the value for a given channel in the pixel.
   *
   * @param channel the color channel
   * @param value   the value for the color channel to be set
   */
  void setColorChannel(int channel, float value);

  /**
   * Brighten the pixel by the given constant factor. Original pixel remains unchanged. To darken
   * the pixel, a negative constant can be passed.
   *
   * @param brightnessConstant the brightening factor
   * @return the brightened version of this pixel
   */
  Pixel brighten(float brightnessConstant);

  /**
   * Retrieves the number of color channels in the pixel.
   *
   * @return the number of color channels in the pixel
   */
  int getColorChannelCount();

  /**
   * Retrieves the values of each of the color channels stored in the pixel.
   *
   * @return the values of each color channels in the pixel
   */
  float[] getChannelValues();

}
