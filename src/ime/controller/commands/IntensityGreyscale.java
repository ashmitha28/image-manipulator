package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that converts an
 * image to its intensity greyscale.
 */
public class IntensityGreyscale extends AbstractCommand {

  public IntensityGreyscale() {
    super(3, 1, 2, true, CommandEnum.intensity_component);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    return imageRepository::toIntensityGreyScale;
  }
}
