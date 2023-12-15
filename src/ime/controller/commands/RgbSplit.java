package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.model.ImageRepository;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * This class extends the AbstractCommand class and represents a specific command that splits an
 * image into its red, green, and blue color channels.
 */
public class RgbSplit extends AbstractCommand {

  /**
   * Constructor to initialize the fields.
   */
  public RgbSplit() {
    super(5, 1, 0, CommandEnum.rgb_split);
  }

  @Override
  protected BiConsumer<String, String> imageRepositoryMethodInvoker(String[] tokens,
      ImageRepository imageRepository) {
    List<String> colorChannelsImages = Arrays.asList(tokens).subList(2, tokens.length);
    return (src, dest) -> imageRepository.splitImageIntoColorChannels(src, colorChannelsImages);
  }

  @Override
  protected String messageSenderHelper(String[] tokens) {
    List<String> destImages = Arrays.asList(tokens[2], tokens[3], tokens[4]);
    return tokens[0] + " operation completed successfully for " + tokens[srcIndex]
        + " & put in " + destImages;
  }
}
