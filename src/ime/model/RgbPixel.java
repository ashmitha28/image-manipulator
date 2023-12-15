package ime.model;

/**
 * This class represents an RGB pixel and supported operations of it.
 */
public class RgbPixel implements Pixel {

  private static final int COLOR_CHANNEL_COUNT = 3;
  private float[] values;

  /**
   * Constructor that creates the RGB pixels using a float array.
   *
   * @param pixelValues the float array that contains the red, green, and blue values of the pixel.
   */
  public RgbPixel(float[] pixelValues) {
    if (pixelValues.length != COLOR_CHANNEL_COUNT) {
      throw new IllegalArgumentException("Number of values provided to the pixel is incorrect");
    }
    validatePixelValues(pixelValues);
    values = pixelValues;
  }

  public RgbPixel() {
    values = new float[COLOR_CHANNEL_COUNT];
  }

  private static void validateChannelValue(float pixelValue) {
    if (pixelValue < 0 || pixelValue > 255) {
      throw new IllegalArgumentException("All channels in the pixel should have a value "
          + "between 0 and 255");
    }
  }

  private void validatePixelValues(float[] pixelValues) {
    if (COLOR_CHANNEL_COUNT != pixelValues.length) {
      throw new IllegalArgumentException("Incorrect number of values passed to set pixel values");
    }
    for (int i = 0; i < COLOR_CHANNEL_COUNT; i++) {
      validateChannelValue(pixelValues[i]);
    }
  }

  @Override
  public int getColorChannelCount() {
    return COLOR_CHANNEL_COUNT;
  }

  @Override
  public float getValue() {
    float max = 0;
    for (int i = 0; i < values.length; i++) {
      if (values[i] == 255) {
        return 255;
      } else {
        if (values[i] > max) {
          max = values[i];
        }
      }
    }
    return max;
  }

  @Override
  public float getIntensity() {
    float sum = 0;
    for (int i = 0; i < getColorChannelCount(); i++) {
      sum += values[i];
    }
    return sum / getColorChannelCount();
  }

  @Override
  public float getLuma() {
    return (float) (0.2126 * values[0] + 0.7152 * values[1] + 0.0722 * values[2]);

  }

  @Override
  public Pixel transformPixel(float[][] transformCoefficients) throws IllegalArgumentException {
    if (transformCoefficients.length != COLOR_CHANNEL_COUNT
        || transformCoefficients[0].length != COLOR_CHANNEL_COUNT) {
      throw new IllegalArgumentException("Invalid transformation matrix provided");
    }
    float[] result = new float[COLOR_CHANNEL_COUNT];
    for (int i = 0; i < COLOR_CHANNEL_COUNT; i++) {
      float sum = 0;
      for (int j = 0; j < COLOR_CHANNEL_COUNT; j++) {
        sum += transformCoefficients[i][j] * values[j];
      }
      result[i] = Math.max(0, Math.min(255, sum));
    }
    return new RgbPixel(result);
  }

  @Override
  public float getChannelValue(int channel) {
    if (channel < 0 || channel > COLOR_CHANNEL_COUNT) {
      throw new IllegalArgumentException("Invalid color channel");
    }
    return values[channel];
  }

  @Override
  public float[] getChannelValues() {
    return values.clone();
  }

  @Override
  public void setColor(float[] colorChannelValues) throws IllegalArgumentException {
    validatePixelValues(colorChannelValues);
    values = colorChannelValues.clone();
  }

  @Override
  public void setColorChannel(int i, float value) {
    if (i < 0 || i >= COLOR_CHANNEL_COUNT) {
      throw new IllegalArgumentException("");
    }
    validateChannelValue(value);
    values[i] = value;
  }

  @Override
  public Pixel brighten(float brightnessConstant) {
    float[] result = new float[values.length];
    for (int i = 0; i < values.length; i++) {
      result[i] = Math.max(0, Math.min(255, values[i] + brightnessConstant));
    }
    return new RgbPixel(result);
  }
}
