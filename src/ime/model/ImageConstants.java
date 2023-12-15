package ime.model;

/**
 * This class contains the constants required for operations on different types of images.
 */
public final class ImageConstants {

  /**
   * float matrix for sepia.
   */
  public static final float[][] SEPIA_TRANSFORMER =
      {{0.393f, 0.769f, 0.189f}, {0.349f, 0.686f, 0.168f}, {0.272f, 0.534f, 0.131f}};
  /**
   * float matrix for blur.
   */
  public static final float[][] BLUR_FILTER =
      {{1 / 16f, 1 / 8f, 1 / 16f}, {1 / 8f, 1 / 4f, 1 / 8f}, {1 / 16f, 1 / 8f, 1 / 16f}};
  /**
   * float matrix for sharpen.
   */
  public static final float[][] SHARPEN_FILTER =
      {{-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f},
          {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
          {-1 / 8f, 1 / 4f, 1, 1 / 4f, -1 / 8f},
          {-1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f},
          {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}
      };

  private ImageConstants() {
  }
}
