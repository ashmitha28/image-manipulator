package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that flips an
 * image horizontally.
 */
public class HorizontalFlip extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public HorizontalFlip() {
    super(3, CommandEnum.horizontalFlip);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return imageRepository::flipImageHorizontally;
  }
}
