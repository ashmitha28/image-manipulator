package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that extracts the
 * red component from an image.
 */
public class RedComponent extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public RedComponent() {
    super(3, 1, 2, CommandEnum.red_component);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return imageRepository::toRedChannelImage;
  }
}
