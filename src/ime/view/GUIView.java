package ime.view;

import ime.controller.Features;
import java.awt.Image;
import java.util.List;

/**
 * A GUIView allows users to perform operations on an image and provides support for two images to
 * be displayed (image and a histogram). It provides methods to get inputs from users, be it integer
 * values or confirmation status.
 */
public interface GUIView extends View {

  /**
   * This method is used to provide the implementation of the various features to the UI that can be
   * utilized as callbacks for its components.
   *
   * @param features the implementation of the {@link Features} class to be used
   */
  void setFeatures(Features features);

  /**
   * Method to set the image in the GUI.
   *
   * @param image the image data to be set
   */
  void setImage(Image image);

  /**
   * Method to set the relevant histogram in the GUI.
   *
   * @param image the histogram image data to be set
   */
  void setHistogram(Image image);

  /**
   * Enable the preview option for the operation.
   *
   * @param show true to enable, and false to disable
   */
  void enablePreview(boolean show);

  /**
   * Enable the apply option for the operation.
   *
   * @param show true to enable, and false to disable
   */
  void enableApply(boolean show);

  /**
   * Enable the toggle option for the previewed operation.
   *
   * @param show true to enable, and false to disable
   */
  void enableToggle(boolean show);

  /**
   * Prompt the user to input an integer value, providing the given message for context.
   *
   * @param message message to be displayed
   * @return user input
   * @throws IllegalStateException when user cancels operation
   */
  int getInput(String message) throws IllegalStateException;

  /**
   * Prompt the user for confirmation providing the given message for context.
   *
   * @param message message to be displayed.
   * @return true if user confirmed, else false
   */
  boolean getConfirmation(String message);

  /**
   * Prompt the user to choose image to laod.
   *
   * @param supportedFormats formats supported
   * @return filename chosen
   */
  String getFilePathToLoad(List<String> supportedFormats);

  /**
   * Prompt the user to choose filename to save.
   * @return filename chosen
   */
  String getFilePathToSave();

}
