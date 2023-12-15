package ime.controller;

/**
 * features interface facilitates various image processing operations. Acting as a bridge between
 * the user interface and underlying image processing commands, this class enables users to choose,
 * preview, and apply a range of manipulations to images, from basic adjustments to more advanced
 * transformations.
 **/
public interface Features {

  /**
   * to load image.
   */
  void loadImage();

  /**
   * method to save image.
   */
  void saveImage();

  /**
   * method for toggle button.
   */
  void toggle();

  /**
   * method if horizontal flip was chosen.
   */
  void chooseHorizontalFlip();

  /**
   * method if vertical flip was chosen.
   */
  void chooseVerticalFlip();

  /**
   * method if color correct  was chosen.
   */
  void chooseColorCorrect();

  /**
   * method if visualise red was chosen.
   */
  void chooseVisualizeRed();

  /**
   * method if visualise green was chosen.
   */
  void chooseVisualizeGreen();

  /**
   * method if visualise blue was chosen.
   */
  void chooseVisualizeBlue();

  /**
   * method if compression was chosen.
   */
  void chooseCompression();

  /**
   * method if sepia  was chosen.
   */
  void chooseSepia();

  /**
   * method if luma grey scale was chosen.
   */
  void chooseLumaGreyscale();

  /**
   * method if levels adjust  was chosen.
   */
  void chooseLevelsAdjust();

  /**
   * method if blur  was chosen.
   */
  void chooseBlur();

  /**
   * method if sharpen  was chosen.
   */
  void chooseSharpen();

  /**
   * method if apply the chosen operation was chosen.
   */
  void applyChosenOperation();

  /**
   * method if preview  was chosen.
   */
  void previewChosenOperation();
}
