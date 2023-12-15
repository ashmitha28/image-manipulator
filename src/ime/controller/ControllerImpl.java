package ime.controller;

import java.util.NoSuchElementException;
import java.util.Scanner;

import ime.model.ImageRepository;
import ime.view.View;

/**
 * The `controller` class implements the ImageProcessingController interface and provides
 * functionality to execute image processing commands specified in a script file.
 */

public class ControllerImpl extends AbstractController {

  private final boolean userPrompt;
  private final Scanner in;

  /**
   * Constructs a new controller instance that does not require user prompt with the given image
   * files map .
   */
  public ControllerImpl(Scanner in, View view, ImageRepository imgRepo,
                        FileHandlerProvider fileHandlerProvider, Boolean userPrompt) {
    super(fileHandlerProvider, imgRepo, view);
    this.in = in;
    this.userPrompt = userPrompt;
  }

  /**
   * The execute method allows the controller to execute the command that is read from the script
   * file or from the cli.
   */
  @Override
  public void execute() {
    boolean endFlag = false;
    try {
      while (!endFlag) {
        if (userPrompt) {
          view.displayMessage("Please enter the command to run: ");
        }
        String command = in.nextLine();
        endFlag = executeCommand(command);
      }
    } catch (NoSuchElementException e) {
      view.displayMessage("Exiting with no more commands");
    }

  }

}


