package ime.controller.commands;

import ime.controller.CommandEnum;
import ime.controller.ControllerImpl;
import ime.controller.FileHandlerProvider;
import ime.model.ImageRepository;
import ime.view.View;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class extends the AbstractCommand class and represents a specific command that runs a script
 * file.
 */
public class Run extends AbstractCommand {

  private final View view;
  private final FileHandlerProvider fileHandlerProvider;

  /**
   * Constructor to initialize the fields that also takes in the view which will be used to create a
   * new controller object.
   */
  public Run(View view, FileHandlerProvider fileHandlerProvider) {
    super(2, CommandEnum.run);
    this.view = view;
    this.fileHandlerProvider = fileHandlerProvider;
  }

  @Override
  protected String extractTokensAndInvokeMethod(String[] tokens, ImageRepository imageRepository) {
    try {
      File reader = new File(tokens[1]);
      new ControllerImpl(new Scanner(reader), view, imageRepository, fileHandlerProvider,
          false).execute();
      return "Script file execution complete.";
    } catch (IOException e) {
      return "Invalid script location/file.";
    }
  }

}
