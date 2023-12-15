package ime.controller.commands;

import java.io.IOException;

import ime.controller.CommandEnum;
import ime.controller.FileHandlerProvider;
import ime.model.ImageRepository;

/**
 * This class extends the AbstractCommand class and represents a specific command that saves an
 * image from the application to a file.
 */
public class Save extends AbstractCommand {

  private final FileHandlerProvider fileHandlerProvider;

  /**
   * Constructor to initialize the fields.
   */
  public Save(FileHandlerProvider fileHandlerProvider) {
    super(3, 1, 2, CommandEnum.save);
    this.fileHandlerProvider = fileHandlerProvider;
  }


  /* Validate token count, proceed to extract tokens and invoke appropriate method from
   ImageRepository (if command supports preview operation and the input tokens has valid params
    for the same, invoke the preview method of ImageRepository.*/
  @Override
  public String proceed(String[] tokens, ImageRepository imageRepository) {
    if (tokens.length < tokensRequired) {
      throw new IllegalArgumentException("Invalid number of tokens passed for the given command");
    }
    StringBuilder concatenatedString = new StringBuilder(tokens[1]);
    for (int i = 2; i < tokens.length - 1; i++) {
      concatenatedString.append(" " + tokens[i]);
    }
    String file = concatenatedString.toString();
    String imageName = tokens[tokens.length - 1];
    float[][][] image = imageRepository.getImage(imageName);
    try {
      fileHandlerProvider.getFileHandler(file).saveImage(image, file);
    } catch (IOException e) {
      return "Invalid file";
    }
    return messageSenderHelper(tokens);

  }

  ///Users/hari/Documents/Coursework/PDP/image-manipulator/./res/bird2.jpg
  @Override
  protected String messageSenderHelper(String[] tokens) {
    return "Saved successfully.";
  }
}
