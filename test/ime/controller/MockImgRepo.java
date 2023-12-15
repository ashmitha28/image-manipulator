package ime.controller;

import java.util.List;
import java.util.function.BiConsumer;

import ime.model.ImageRepository;

/**
 * A mock implementation of the {@link ImageRepository} interface used for testing purposes. This
 * class allows tracking method calls and simulating failures for various image processing
 * operations.
 */
public class MockImgRepo implements ImageRepository {

  public static final String LOAD = "loadImage";
  public static final String GET_IMAGE = "getImage";
  public static final String SPLIT_IMAGE = "splitImage";
  public static final String COMBINE_IMAGE = "combine images";
  public static final String BRIGHTEN_IMAGE = "brightenImage";
  public static final String H_FLIP = "horizontal flip";
  public static final String V_FLIP = "vertical flip";
  public static final String SEPIA = "sepia";
  public static final String LUMA = "luma gs";
  public static final String VALUE = "value gs";
  public static final String INTENSITY = "intensity gs";
  public static final String BLUR = "blurImage";
  public static final String RED_COMP = "red channel";
  public static final String BLUE_COMP = "blue channel";
  public static final String GREEN_COMP = "green channel";
  public static final String SHARPEN = "sharpenImage";
  public static final String IMAGE_PRESENT = "imagePresent";
  public static final String COMPRESS = "compress";
  public static final String LEVELS_ADJUST = "levels adjust";
  public static final String COLOR_CORRECT = "color correct";
  public static final String HISTOGRAM = "histogram";

  public static final String PREVIEW = "preview";

  private StringBuilder methodCallLogger;
  private Boolean fail;

  private float[][][] lastLoadedFloat;

  /**
   * Creates a new instance of the MockImgRepo.
   */
  public MockImgRepo() {
    methodCallLogger = new StringBuilder();
    fail = false;
    lastLoadedFloat = new float[][][]{{{0,0,0}}};
  }

  /**
   * Loads a given image into the repository with the given name.
   *
   * @param image     The image to be loaded into the repository.
   * @param imageName The name to associate with the loaded image.
   */
  @Override
  public void loadImage(float[][][] image, String imageName) {
    methodCallLogger.append(getLoggerMessageForOperation(LOAD, imageName));
    if (fail) {
      throw new IllegalArgumentException("Image Repository failed");
    }
    lastLoadedFloat = image;
  }

  /**
   * Retrieves the image tagged by the given name.
   *
   * @param imageName The name of the image to be retrieved.
   */

  @Override
  public float[][][] getImage(String imageName)
          throws IllegalArgumentException {
    methodCallLogger.append(getLoggerMessageForOperation(GET_IMAGE, imageName));
    if (fail) {
      throw new IllegalArgumentException("Image Repository failed");
    }
    return lastLoadedFloat;
  }

  /**
   * Splits the source image into multiple color channels and associates them with the specified
   * destination image names.
   *
   * @param srcImage       The name of the source image to be split.
   * @param destImageNames A list of names for the destination images.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image is invalid.
   */
  @Override
  public void splitImageIntoColorChannels(String srcImage, List<String> destImageNames) {
    methodCallLogger.append(
            getLoggerMessageForOperation(SPLIT_IMAGE, srcImage, destImageNames.toString()));
    if (fail) {
      throw new IllegalArgumentException("Image Repository failed");
    }
  }

  /**
   * Combines a list of source images into a new image and associates it with the specified
   * destination image name.
   *
   * @param images        A list of image names to be combined.
   * @param imageDestName The name to associate with the combined image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image names are invalid.
   */
  @Override
  public void combineImages(List<String> images, String imageDestName) {
    methodCallLogger.append(
            getLoggerMessageForOperation(COMBINE_IMAGE, images.toString(), imageDestName));
    if (fail) {
      throw new IllegalArgumentException("Image Repository failed");
    }
  }

  /**
   * Brightens the source image by applying a specified brightness constant and associates the
   * result with the destination image name.
   *
   * @param imageNameSrc       The name of the source image to be brightened.
   * @param imageNameDest      The name to associate with the brightened image.
   * @param brightnessConstant The constant to adjust image brightness.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void brightenImage(String imageNameSrc, String imageNameDest, float brightnessConstant) {
    methodCallLogger.append(
            getLoggerMessageForOperation(BRIGHTEN_IMAGE, imageNameSrc, imageNameDest,
                    brightnessConstant));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Blurs the source image and associates the result with the destination image name.
   *
   * @param imageNameSrc  The name of the source image to be blurred.
   * @param imageNameDest The name to associate with the blurred image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void blurImage(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(getLoggerMessageForOperation(BLUR, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Sharpens the source image and associates the result with the destination image name.
   *
   * @param imageNameSrc  The name of the source image to be sharpened.
   * @param imageNameDest The name to associate with the sharpened image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void sharpenImage(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(SHARPEN, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Flips the source image horizontally and associates the result with the destination image name.
   *
   * @param imageNameSrc  The name of the source image to be flipped horizontally.
   * @param imageNameDest The name to associate with the flipped image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void flipImageHorizontally(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(H_FLIP, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Flips the source image vertically and associates the result with the destination image name.
   *
   * @param imageNameSrc  The name of the source image to be flipped vertically.
   * @param imageNameDest The name to associate with the flipped image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void flipImageVertically(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(V_FLIP, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Converts the source image to intensity grayscale and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image to be converted to intensity grayscale.
   * @param imageNameDest The name to associate with the intensity grayscale image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toIntensityGreyScale(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(INTENSITY, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Converts the source image to luma grayscale and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image to be converted to luma grayscale.
   * @param imageNameDest The name to associate with the luma grayscale image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toLumaGreyScale(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(LUMA, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Converts the source image to value grayscale and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image to be converted to value grayscale.
   * @param imageNameDest The name to associate with the value grayscale image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toValueGreyScale(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(VALUE, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Applies a sepia filter to the source image and associates the result with the destination image
   * name.
   *
   * @param imageNameSrc  The name of the source image to be converted to sepia.
   * @param imageNameDest The name to associate with the sepia image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toSepiaImage(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(SEPIA, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Extracts the red channel of the source image and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image from which to extract the red channel.
   * @param imageNameDest The name to associate with the red channel image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toRedChannelImage(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(RED_COMP, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Extracts the green channel of the source image and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image from which to extract the green channel.
   * @param imageNameDest The name to associate with the green channel image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toGreenChannelImage(String imageNameSrc, String imageNameDest)
          throws IllegalArgumentException {
    methodCallLogger.append(
            getLoggerMessageForOperation(GREEN_COMP, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Extracts the blue channel of the source image and associates the result with the destination
   * image name.
   *
   * @param imageNameSrc  The name of the source image from which to extract the blue channel.
   * @param imageNameDest The name to associate with the blue channel image.
   * @throws IllegalArgumentException If the operation fails due to an issue specified by the
   *                                  MockImgRepo or if the source image name is invalid.
   */
  @Override
  public void toBlueChannelImage(String imageNameSrc, String imageNameDest)
          throws IllegalArgumentException {

    methodCallLogger.append(
            getLoggerMessageForOperation(BLUE_COMP, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("Source Name invalid");
    }
  }

  /**
   * Checks whether an image with the given name is present in the repository.
   *
   * @param imageName The name of the image to check for in the repository.
   * @return true if the image is present; false otherwise.
   */
  @Override
  public boolean isImagePresent(String imageName) {
    methodCallLogger.append(
            getLoggerMessageForOperation(IMAGE_PRESENT, imageName));
    return fail;
  }

  @Override
  public void compress(String imageNameSrc, String imageNameDest, int compressPercent)
          throws IllegalArgumentException {
    methodCallLogger.append(
            getLoggerMessageForOperation(COMPRESS, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException("compress failed");
    }
  }

  @Override
  public void preview(String imageNameSrc, String imageNameDest,
                      BiConsumer<String, String> operation, int verticalSplit) {
    methodCallLogger.append(
            getLoggerMessageForOperation(PREVIEW, imageNameSrc, imageNameDest, verticalSplit));
    operation.accept(imageNameSrc, imageNameDest);
    if (fail) {
      throw new IllegalArgumentException("preview failed");
    }
  }

  @Override
  public void levelsAdjust(String imageNameSrc, String destImage, int b, int m, int w) {
    methodCallLogger.append(
            getLoggerMessageForOperation(LEVELS_ADJUST, imageNameSrc, destImage));
    if (fail) {
      throw new IllegalArgumentException(LEVELS_ADJUST + " failed");
    }
  }

  @Override
  public void colorCorrect(String imageNameSrc, String imageNameDest) {
    methodCallLogger.append(
            getLoggerMessageForOperation(COLOR_CORRECT, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException(COLOR_CORRECT + " failed");
    }
  }

  @Override
  public void toHistogram(String imageNameSrc, String imageNameDest, ImageDrawer imageDrawer) {
    methodCallLogger.append(
            getLoggerMessageForOperation(HISTOGRAM, imageNameSrc, imageNameDest));
    if (fail) {
      throw new IllegalArgumentException(HISTOGRAM + " failed");
    }
  }

  /**
   * Get a log of method calls made to this MockImgRepo instance.
   *
   * @return A string containing the log of method calls.
   */
  public String getLogger() {
    return methodCallLogger.toString();
  }

  /**
   * Clear the log of method calls made to this MockImgRepo instance.
   */
  public void clearLogger() {
    methodCallLogger = new StringBuilder();
  }

  /**
   * Set the failure flag for the MockImgRepo, allowing or disallowing simulated failures.
   *
   * @param failFlag A boolean flag indicating whether the MockImgRepo should simulate failures.
   */
  public void setFailureFlag(Boolean failFlag) {
    this.fail = failFlag;
  }

  public String getLoggerMessageForOperation(String operation, String param1, String param2) {
    return operation + " called " + param1 + " and " + param2 + " passed\n";
  }

  public String getLoggerMessageForOperation(String operation, String param1, String param2,
                                             float param3) {
    return operation + " called " + param3 + " and " + param1 + " and " + param2 + " passed\n";
  }

  public String getLoggerMessageForOperation(String operation, String param1) {
    return operation + " called and " + param1 + " passed\n";
  }

  public float[][][] getLastLoadedFloat() {
    return lastLoadedFloat;
  }
}
