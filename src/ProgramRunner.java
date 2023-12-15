import ime.controller.ControllerImpl;
import ime.controller.FileHandlerProvider;
import ime.controller.FileHandlerProviderImpl;
import ime.controller.GUIController;
import ime.controller.ImageProcessingController;
import ime.model.ImageRepository;
import ime.model.ImageRepositoryImpl;
import ime.view.JFrameView;
import ime.view.View;
import ime.view.ViewImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class contains the main method to be run to start the application.
 */
public class ProgramRunner {

  /**
   * This main method requires no arguments to be run and passes the program execution control to
   * the controller after providing it the Model and View classes for its instantiation.
   */
  public static void main(String[] args) {
    if (!isValidInput(args)) {
      return;
    }
    FileHandlerProvider fileHandlerProvider = new FileHandlerProviderImpl();
    if (args.length == 0) {
      JFrameView frame = new JFrameView();
      ImageRepository imageRepository = new ImageRepositoryImpl();
      new GUIController(imageRepository, frame, fileHandlerProvider).execute();
    } else {
      View view = new ViewImpl(System.out);
      ImageRepository imageRepository = new ImageRepositoryImpl();
      runController(args, view, imageRepository, fileHandlerProvider);
    }

  }

  private static void runController(String[] args, View view, ImageRepository imageRepository,
      FileHandlerProvider fileHandlerProvider) {
    try {
      Scanner controllerInput = getControllerInput(args);
      boolean userPrompt = args.length != 2; //no user prompt if file provided as input
      ImageProcessingController controller = new ControllerImpl(controllerInput, view,
          imageRepository, fileHandlerProvider, userPrompt);
      controller.execute();
    } catch (FileNotFoundException e) {
      System.out.println("Invalid file provided. Exiting.");
    }
  }

  private static boolean isValidInput(String[] args) {
    if ((args.length == 2 && args[0].equals("-file") || (args.length == 0)
        || (args.length == 1 && args[0].equals("-text")))) {
      return true;
    } else {
      System.out.println("Invalid arguments provided to the Program Runner. Either pass "
          + "no arguments or provide '-file filename' or '-text");
      return false;
    }
  }

  private static Scanner getControllerInput(String[] args) throws FileNotFoundException {
    Scanner controllerInput;
    if (args.length == 1) {
      controllerInput = new Scanner(System.in);
    } else {
      controllerInput = new Scanner(new File(args[1]));
    }
    return controllerInput;
  }
}
