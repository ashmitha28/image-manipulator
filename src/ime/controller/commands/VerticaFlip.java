package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that flips an
 * image vertically.
 */
public class VerticaFlip extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public VerticaFlip() {
    super(3, 1, 2, CommandEnum.verticalFlip);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return imageRepository::flipImageVertically;
  }
}
