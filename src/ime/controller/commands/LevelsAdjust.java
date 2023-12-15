package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that adjusts the
 * levels of an image.
 */
public class LevelsAdjust extends AbstractCommand {

  public LevelsAdjust() {
    super(6, 4, 5, true, CommandEnum.levels_adjust);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository)
      throws IllegalArgumentException {
    try {
      int b = Integer.parseInt(tokens[1]);
      int m = Integer.parseInt(tokens[2]);
      int w = Integer.parseInt(tokens[3]);
      return (src, dest) -> imageRepository.levelsAdjust(src, dest, b, m, w);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("3 numbers required following the levels-adjust command");
    }
  }

}
