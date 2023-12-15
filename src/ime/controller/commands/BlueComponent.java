package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that extracts the
 * blue component from an image.
 */
public class BlueComponent extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public BlueComponent() {
    super(3, CommandEnum.blue_component);
  }

  @Override
  protected String extractTokensAndInvokeMethod(String[] tokens, ImageRepository imageRepository) {
    String imageName = tokens[1];
    String newImage = tokens[2];
    imageRepository.toBlueChannelImage(imageName, newImage);
    return messageSenderHelper(tokens);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return imageRepository::toIntensityGreyScale;
  }
}
