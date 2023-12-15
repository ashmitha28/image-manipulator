package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.controller.ImageDrawerImpl;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that generates a
 * histogram from an image.
 */
public class Histogram extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public Histogram() {
    super(3, CommandEnum.histogram);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return (srcImage, destImage) -> imageRepository.toHistogram(srcImage, destImage,
        new ImageDrawerImpl());
  }


}
