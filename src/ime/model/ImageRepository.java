package ime.model;

import ime.controller.ImageDrawer;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * An Image Repository is a class that manages multiple images and performs operations on them. It
 * is able to tag an image to a particular name and also save images that it has already tagged.
 */
public interface ImageRepository {

  /**
   * This method is used to load an image at a given file path and tag it the given name for the
   * image.
   *
   * @param image     the image represented in terms of its color channel values and the position of
   *                  said value
   * @param imageName the name for the image to be associated with
   * @throws IllegalArgumentException if the values of image does not have the size for width or
   *                                  color channel values.
   */
  void loadImage(float[][][] image, String imageName) throws IllegalArgumentException;

  /**
   * Retrieve the image that is tagged with the given image name.
   *
   * @param imageName The name that the image is tagged with
   * @return The image that has been tagged to the given name
   * @throws IllegalArgumentException if imageName has not been created/tagged yet.
   */
  float[][][] getImage(String imageName) throws IllegalArgumentException;


  /**
   * Split this image into its respective color channels and store the resulting images into the
   * destination image names.
   *
   * @throws IllegalArgumentException if srcImage has not been created/tagged yet.
   */
  void splitImageIntoColorChannels(String srcImage, List<String> destImageNames)
      throws IllegalArgumentException;

  /**
   * Combine the color channels images into o single image and save it into the destination image
   * name provided.
   *
   * @param images List of images to be combined.
   * @throws IllegalArgumentException if {@param images} has not been created/tagged yet.
   */
  void combineImages(List<String> images, String imageDestName)
      throws IllegalArgumentException;

  /**
   * Brighten this image by the given brightness constant and tag the new image as
   * {@param imageNameDest}.
   *
   * @param brightnessConstant the constant factor for the image to be brightened by
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void brightenImage(String imageNameSrc, String imageNameDest, float brightnessConstant)
      throws IllegalArgumentException;


  /**
   * Blur the source image using appropriate filter and save the blurred image into destination.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void blurImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Sharpen the source image using appropriate filter and save the sharpened image into the
   * destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void sharpenImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Horizontally flip the source image and save it into destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void flipImageHorizontally(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Vertically flip the source image and save it into destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void flipImageVertically(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Transform the source image into its intensity greyscale and save it into destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toIntensityGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Transform the source image into its luma greyscale and save it into the destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toLumaGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Transform the source image into its value greyscale and save it into the destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toValueGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Transform the source image into sepia and save it into the destination image.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toSepiaImage(String imageNameSrc, String imageNameDest )
      throws IllegalArgumentException;

  /**
   * Get the red component image of the source image and save it into destination.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toRedChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Get the green component image of the source image and save it into destination.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toGreenChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Get the blue component image of the source image and save it into destination.
   *
   * @param imageNameSrc  source image name
   * @param imageNameDest destination image name
   * @throws IllegalArgumentException if {@param imageNameSrc} has not been created/tagged yet.
   */
  void toBlueChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException;

  /**
   * Returns whether the given image name has been created/tagged in the repository yet.
   *
   * @param imageNameSrc image name to be searched for.
   * @return whether the image is present in the repository.
   */
  boolean isImagePresent(String imageNameSrc);

  /**
   * compresses the given image by the given amount of percentage.
   *
   * @param imageNameSrc    name of source file.
   * @param imageNameDest   name of destination file.
   * @param compressPercent amount to be compressed.
   * @throws IllegalArgumentException when the values passed are invalid.
   */
  void compress(String imageNameSrc, String imageNameDest, int compressPercent)
      throws IllegalArgumentException;

  /**
   * Generates a preview of an image transformation operation and saves the resulting image. The
   * method takes a source image, applies an operation to it, and stores the result with the
   * destination image name.
   *
   * @param imageNameSrc  The name of the source image for the transformation.
   * @param imageNameDest The name of the destination image where the preview will be saved.
   * @param operation     A BiConsumer that defines the image transformation operation to apply.
   * @param verticalSplit The percentage of vertical split for the source image (0-100).
   * @throws IllegalArgumentException If the verticalSplit value is outside the valid range or if
   *                                  the source image is not found.
   */

  void preview(String imageNameSrc, String imageNameDest, BiConsumer<String, String> operation,
      int verticalSplit);

  /**
   * Adjusts the levels of the source image and saves the result with the specified destination
   * image name. The levels are adjusted using the provided black point (b), mid-point (m), and
   * white point (w) values.
   *
   * @param imageNameSrc The name of the source image to adjust the levels.
   * @param destImage    The name of the destination image where the adjusted image will be saved.
   * @param b            The black point, the lower limit of the input pixel values to map to 0.
   *                     This value should be lesser than {@param m}.
   * @param m            The mid-point, the middle point of the input pixel values to map to a value
   *                     in the 0-255 range. This value should be lesser than {@param w}.
   * @param w            The white point, the upper limit of the input pixel values to map to 255.
   * @throws IllegalArgumentException If the source image is not found or if the b, m , w values are
   *                                  not in ascending order.
   */
  void levelsAdjust(String imageNameSrc, String destImage, int b, int m, int w);

  /**
   * Color corrects the image specified by the source image name and saves the corrected image with
   * the destination image name. The color correction process involves adjusting the brightness of
   * the image's color channels to match the average peak value across all channels. The corrected
   * image is saved with the destination image name.
   *
   * @param imageNameSrc  The name of the source image to be color-corrected.
   * @param imageNameDest The name of the destination image where the color-corrected image will be
   *                      saved.
   * @throws IllegalArgumentException If the source image is not found or if the image names are
   *                                  invalid.
   */
  void colorCorrect(String imageNameSrc, String imageNameDest);

  /**
   * Converts the specified source image into a histogram visualization and saves it with the
   * destination image name. The method uses the provided ImageDrawer to generate the histogram
   * visualization.
   *
   * @param imageNameSrc  The name of the source image to be converted into a histogram
   *                      visualization.
   * @param imageNameDest The name of the destination image where the histogram visualization will
   *                      be saved.
   * @param imageDrawer   The ImageDrawer used to create the histogram visualization.
   * @throws IllegalArgumentException If the source image is not found.
   */
  void toHistogram(String imageNameSrc, String imageNameDest, ImageDrawer imageDrawer);
}
