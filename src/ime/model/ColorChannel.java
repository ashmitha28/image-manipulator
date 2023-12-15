package ime.model;

/**
 * enum class to establish the RED, GREEN, BLUE channels of the image. Finalised the channels
 * sizes.
 */
public enum ColorChannel {
  RED(new int[]{255, 0, 0}),
  GREEN(new int[]{0, 255, 0}),
  BLUE(new int[]{0, 0, 255});

  public final int[] rgb;

  ColorChannel(int[] rgbValues) {
    this.rgb = rgbValues;
  }

}