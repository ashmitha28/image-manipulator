package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that brightens or
 * darkens an image depending on the given constant.
 */
public class Brighten extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public Brighten() {
    super(4, 2, 3, CommandEnum.brighten);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    try {
      float brightnessConstant = Float.parseFloat(tokens[1]);
      return (src, dest) -> imageRepository.brightenImage(src, dest, brightnessConstant);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "brightness command expects a number following the command");
    }
  }

  @Override
  protected String messageSenderHelper(String[] tokens) {
    return tokens[0] + " operation completed successfully for " + tokens[srcIndex]
        + " & put in " + tokens[destIndex] + " with constant value: " + tokens[1];
  }

}
