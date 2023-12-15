package ime.controller.commands;

import ime.model.ImageRepository;

/**
 * This interface represents a command that is supported by the application and provides a method
 * that will be implemented by each implementation or supported command in the application.
 */
public interface Command {

  /**
   * This method takes in the list of tokens as input for the running of the command and performs
   * the appropriate action.
   *
   * @param tokens          The arguments for the operation
   * @param imageRepository The imageRepository to be used for the operation
   * @return status of the operation
   */
  String proceed(String[] tokens, ImageRepository imageRepository);

  String constructCommand(String[] tokens);

  String constructPreviewCommand(String[] tokens, int previewPercent);
}
