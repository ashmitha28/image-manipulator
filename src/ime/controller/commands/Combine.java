package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that combines
 * images using their respective color channels.
 */
public class Combine extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public Combine() {
    super(5, 0, 1, CommandEnum.rgb_combine);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    List<String> colorChannelsImages = Arrays.asList(tokens).subList(2, tokens.length);
    return (src, dest) -> imageRepository.combineImages(colorChannelsImages, dest);
  }

  @Override
  protected String messageSenderHelper(String[] tokens) {
    List<String> srcImages = Arrays.asList(tokens[2], tokens[3], tokens[4]);
    return tokens[0] + " operation completed successfully for " + srcImages
        + " & put in " + tokens[1];
  }
}
