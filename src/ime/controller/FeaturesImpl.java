package ime.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements the {@link Features} interface and implements the callback methods for the
 * {@link ime.view.GUIView}. The creation of this class requires an object of the concrete class
 * {@link GUIController} because this implementation of features can be
 * considered an extension of the gui controller. This class was introduced only to prevent the
 * view from being able to access the controller interface's public methods.
 **/
public class FeaturesImpl implements Features {

  private static final String activeImage = "guiImage";
  private static final String histogram = "hist";
  private static final String preview = "previewImage";

  private boolean unsavedImagePrompt;
  private List<String> tokens;

  private CommandEnum chosenCommand;
  private GUIController controller;

  private boolean isPreview;

  /**
   * constructor that instantiates the objects.
   *
   * @param controller gui controller.
   */
  public FeaturesImpl(GUIController controller) {
    this.controller = controller;
    isPreview = false;
    unsavedImagePrompt = false;
  }

  private boolean invokeCommand(CommandEnum commandEnum, String[] tokens) {
    String command = controller.knownCommands.get(commandEnum).constructCommand(tokens);
    String histogramCommand = controller.knownCommands.get(CommandEnum.histogram)
        .constructCommand(new String[]{activeImage, histogram});
    boolean commandSuccess = controller.executeCommand(command);
    if (commandSuccess) {
      controller.executeCommand(histogramCommand);
      controller.updateImage(activeImage);
      controller.updateHistogram(histogram);
      isPreview = false;
    }
    return commandSuccess;
  }

  @Override
  public void loadImage() {
    controller.setupOperation(false, false);
    if (unsavedImagePrompt) {
      if (!controller.getConfirmation(
          "Current image is unsaved. Do you want to overwrite the image?")) {
        return;
      }
    }
    String filePath = controller.openFileAction();
    if (filePath != null) {
      boolean loadSuccess = invokeCommand(CommandEnum.load, new String[]{filePath, activeImage});
      if (loadSuccess) {
        unsavedImagePrompt = false;
      }
    }
  }

  @Override
  public void saveImage() {
    controller.setupOperation(false, false);
    String fileName = controller.saveFileAction();
    if (fileName != null) {
      boolean saveSuccess = invokeCommand(CommandEnum.save, new String[]{fileName, activeImage});
      if (saveSuccess) {
        unsavedImagePrompt = false;
      }
    }
  }

  @Override
  public void toggle() {
    if (isPreview) {
      controller.updateImage(activeImage);
    } else {
      controller.updateImage(preview);
    }
    isPreview = !isPreview;
  }

  @Override
  public void chooseHorizontalFlip() {
    setCommandTokens(CommandEnum.horizontalFlip);
    controller.setupOperation(true, false);
  }

  @Override
  public void chooseVerticalFlip() {
    setCommandTokens(CommandEnum.verticalFlip);
    controller.setupOperation(true, false);
  }

  @Override
  public void chooseColorCorrect() {
    setCommandTokens(CommandEnum.color_correct);
    controller.setupOperation(true, true);
  }

  @Override
  public void chooseVisualizeRed() {
    setCommandTokens(CommandEnum.red_component);
    controller.setupOperation(true, false);
  }

  @Override
  public void chooseVisualizeGreen() {
    setCommandTokens(CommandEnum.green_component);
    controller.setupOperation(true, false);
  }

  @Override
  public void chooseVisualizeBlue() {
    setCommandTokens(CommandEnum.blue_component);
    controller.setupOperation(true, false);
  }

  @Override
  public void chooseCompression() {
    try {
      int compressPercent = getValueWithConstraint("compression factor", 0, 100);
      setCommandTokens(CommandEnum.compress,
          Arrays.asList(String.valueOf(compressPercent), activeImage));
      controller.setupOperation(true, false);
    } catch (IllegalStateException e) {
      controller.setupOperation(false, false);
    }
  }

  @Override
  public void chooseSepia() {
    setCommandTokens(CommandEnum.sepia);
    controller.setupOperation(true, true);
  }

  @Override
  public void chooseLumaGreyscale() {
    setCommandTokens(CommandEnum.luma_component);
    controller.setupOperation(true, true);
  }

  @Override
  public void chooseLevelsAdjust() {
    int b;
    int m;
    int w;
    try {
      b = getValueWithConstraint("black point", 0, 253);
      m = getValueWithConstraint("mid point", b + 1, 254);
      w = getValueWithConstraint("white point", m + 1, 255);
      setCommandTokens(CommandEnum.levels_adjust,
          Arrays.asList(String.valueOf(b), String.valueOf(m), String.valueOf(w), activeImage));
      controller.setupOperation(true, true);
    } catch (IllegalStateException e) {
      controller.setupOperation(false, false);
    }
  }

  /* This method prompts for input and retries until it gets an input that satisfies the min and max
     values. The method throws IllegalStateException if the user cancels the operation */
  private int getValueWithConstraint(String message, int min, int max) {
    int val = controller.getInput("Enter value between " + min + " - " + max + " for " + message);
    while (val < min || val > max) {
      controller.sendDisplayMessage("Invalid value. Please try again");
      val = controller.getInput("Enter value between " + min + " - " + max + " for " + message);
    }
    return val;
  }

  @Override
  public void chooseBlur() {
    setCommandTokens(CommandEnum.blur);
    controller.setupOperation(true, true);
  }

  @Override
  public void chooseSharpen() {
    setCommandTokens(CommandEnum.sharpen);
    controller.setupOperation(true, true);
  }

  private void setCommandTokens(CommandEnum commandEnum, List<String> tokens) {
    chosenCommand = commandEnum;
    this.tokens = tokens;
  }

  private void setCommandTokens(CommandEnum commandEnum) {
    chosenCommand = commandEnum;
    tokens = Arrays.asList(activeImage);
  }

  @Override
  public void applyChosenOperation() {
    if (chosenCommand == null) {
      controller.sendDisplayMessage("Operation not chosen");
      return;
    }
    List<String> commandTokens = new ArrayList<>(tokens);
    commandTokens.add(activeImage);
    boolean applySuccess = invokeCommand(chosenCommand, commandTokens.toArray(new String[0]));
    if (applySuccess) {
      controller.setToggle(false);
      unsavedImagePrompt = true;
    }
  }

  @Override
  public void previewChosenOperation() {
    if (chosenCommand == null) {
      controller.sendDisplayMessage("Operation not chosen");
      return;
    }
    List<String> commandTokens = new ArrayList<>(tokens);
    commandTokens.add(preview);
    previewOperation(commandTokens);

  }

  private void previewOperation(List<String> commandTokens) {
    try {
      int previewPercent = getValueWithConstraint("preview percentage", 0, 100);
      String command = controller.knownCommands.get(chosenCommand)
          .constructPreviewCommand(commandTokens.toArray(new String[0]), previewPercent);
      controller.executeCommand(command);
      controller.updateImage(preview);
      isPreview = true;
      controller.setToggle(true);
    } catch (IllegalStateException e) {
      //preview failed since user cancelled operation. Do nothing here.
    } catch (IllegalArgumentException e) {
      controller.sendDisplayMessage(e.getMessage()); //When operation can't be previewed
    }
  }

}

