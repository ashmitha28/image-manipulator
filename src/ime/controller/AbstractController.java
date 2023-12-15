package ime.controller;

import ime.controller.commands.BlueComponent;
import ime.controller.commands.Blur;
import ime.controller.commands.Brighten;
import ime.controller.commands.ColorCorrect;
import ime.controller.commands.Combine;
import ime.controller.commands.Command;
import ime.controller.commands.Compress;
import ime.controller.commands.GreenComponent;
import ime.controller.commands.Histogram;
import ime.controller.commands.HorizontalFlip;
import ime.controller.commands.IntensityGreyscale;
import ime.controller.commands.LevelsAdjust;
import ime.controller.commands.Load;
import ime.controller.commands.LumaGreyscale;
import ime.controller.commands.RedComponent;
import ime.controller.commands.RgbSplit;
import ime.controller.commands.Run;
import ime.controller.commands.Save;
import ime.controller.commands.Sepia;
import ime.controller.commands.Sharpen;
import ime.controller.commands.ValueGreyscale;
import ime.controller.commands.VerticaFlip;
import ime.model.ImageRepository;
import ime.view.View;
import java.util.HashMap;
import java.util.Map;

/**
 * The AbstractController class is an abstract implementation of the ImageProcessingController
 * interface, providing a foundation for image processing controllers. It defines a set of known
 * commands and their corresponding command objects to perform various image processing operations.
 **/
public abstract class AbstractController implements ImageProcessingController {

  private final FileHandlerProvider fileHandlerProvider;
  protected final ImageRepository imgRepo;
  protected Map<CommandEnum, Command> knownCommands;
  protected View view;

  /**
   * constructor for the controller that initialises the objects.
   *
   * @param fileHandlerProvider file handler provider object.
   * @param imageRepository     image repository object.
   * @param view                view object.
   */
  public AbstractController(FileHandlerProvider fileHandlerProvider,
      ImageRepository imageRepository, View view) {
    this.fileHandlerProvider = fileHandlerProvider;
    this.imgRepo = imageRepository;
    this.view = view;
    initializeKnownCommands();
  }

  private static CommandEnum getCommandEnum(String commandStr) throws IllegalArgumentException {
    for (CommandEnum cmd : CommandEnum.values()) {
      if (cmd.getRepresentation().equals(commandStr)) {
        return cmd;
      }
    }
    throw new IllegalArgumentException("Command not found"); // Command not found
  }

  /**
   * Initializes the mapping of command enums to their corresponding command objects.
   */
  private void initializeKnownCommands() {
    knownCommands = new HashMap<>();
    knownCommands.put(CommandEnum.blur, new Blur());
    knownCommands.put(CommandEnum.sharpen, new Sharpen());
    knownCommands.put(CommandEnum.brighten, new Brighten());
    knownCommands.put(CommandEnum.load, new Load(fileHandlerProvider));
    knownCommands.put(CommandEnum.save, new Save(fileHandlerProvider));
    knownCommands.put(CommandEnum.horizontalFlip, new HorizontalFlip());
    knownCommands.put(CommandEnum.verticalFlip, new VerticaFlip());
    knownCommands.put(CommandEnum.rgb_combine, new Combine());
    knownCommands.put(CommandEnum.rgb_split, new RgbSplit());
    knownCommands.put(CommandEnum.value_component, new ValueGreyscale());
    knownCommands.put(CommandEnum.luma_component, new LumaGreyscale());
    knownCommands.put(CommandEnum.intensity_component, new IntensityGreyscale());
    knownCommands.put(CommandEnum.sepia, new Sepia());
    knownCommands.put(CommandEnum.red_component, new RedComponent());
    knownCommands.put(CommandEnum.green_component, new GreenComponent());
    knownCommands.put(CommandEnum.blue_component, new BlueComponent());
    knownCommands.put(CommandEnum.run, new Run(view, fileHandlerProvider));
    knownCommands.put(CommandEnum.compress, new Compress());
    knownCommands.put(CommandEnum.histogram, new Histogram());
    knownCommands.put(CommandEnum.color_correct, new ColorCorrect());
    knownCommands.put(CommandEnum.levels_adjust, new LevelsAdjust());
  }

  /**
   * the execute command method takes in the commands as tokens and checks if it's a valid command
   * from the enum.
   *
   * @param commandTokens string commands passed.
   * @return boolean value of true or false to check if the command is valid or not.
   */
  protected boolean executeCommand(String commandTokens) {
    commandTokens = commandTokens.trim();
    if (commandTokens.startsWith("#") || commandTokens.isEmpty()) {
      return emptyCommandStatus();
    }

    String[] tokens = commandTokens.split(" ");
    try {
      CommandEnum commandKeyword = getCommandEnum(tokens[0]);
      if (commandKeyword == CommandEnum.exit) {
        return true;
      }

      Command commandObject = knownCommands.get(commandKeyword);
      runCommandObject(commandObject, tokens);
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
      return returnValueError();
    }
    return returnValueNoError();
  }

  /**
   * Handles the case where the command execution results in an error. This method has to be
   * overridden by the Controller Implementations if the default behaviour is to be overridden.
   *
   * @return Returns false by default.
   */
  protected boolean returnValueError() {
    return false;
  }

  /**
   * Handles the case where the command execution is successful. This method has to be
   * overridden by the Controller Implementations if the default behaviour is to be overridden.
   *
   * @return False to indicate a successful execution.
   */
  protected boolean returnValueNoError() {
    return false;
  }

  /**
   * Handles the case where the command tokens are empty.
   *
   * @return Returns false by default
   */
  private static boolean emptyCommandStatus() {
    return false;
  }

  /**
   * Runs the provided command object with the given tokens and updates the view with the result.
   *
   * @param command The command object to be executed.
   * @param tokens  The tokens representing the command arguments.
   */
  protected void runCommandObject(Command command, String[] tokens) {
    String returnedMessage = command.proceed(tokens, imgRepo);
    view.displayMessage(returnedMessage);
  }
}

