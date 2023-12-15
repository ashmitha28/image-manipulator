package ime.model;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * This enum list the different types of images and provides some functions pertaining to each of
 * the specific image types.
 */
public enum ImageType {
  RGB(RgbPixel::new, Collections.unmodifiableList(
      Arrays.asList(ColorChannel.RED, ColorChannel.GREEN, ColorChannel.BLUE)));

  /**
   * The color channels present in the image type.
   */
  public final List<ColorChannel> colorChannels;


  private final Supplier<Pixel> pixelSupplier;

  ImageType(Supplier<Pixel> pixelSupplier, List<ColorChannel> colorChannels) {
    this.pixelSupplier = pixelSupplier;
    this.colorChannels = colorChannels;
  }

  /**
   * Generate a new pixel for an image of this image type.
   *
   * @return a new pixel for the image type
   */
  public Pixel generatePixel() {
    return pixelSupplier.get();
  }
}
