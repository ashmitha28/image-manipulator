package ime.controller;

/**
 * enum command to save the commands possible from the script.
 */
public enum CommandEnum {
  load("load"),
  save("save"),
  brighten("brighten"),
  blur("blur"),
  sharpen("sharpen"),
  sepia("sepia"),
  horizontalFlip("horizontal-flip"),
  verticalFlip("vertical-flip"),
  rgb_split("rgb-split"),
  value_component("value-component"),
  luma_component("luma-component"),
  intensity_component("intensity-component"),
  red_component("red-component"),
  green_component("green-component"),
  blue_component("blue-component"),
  rgb_combine("rgb-combine"),
  run("run"),
  compress("compress"),
  levels_adjust("levels-adjust"),

  histogram("histogram"),
  color_correct("color-correct"),
  exit("exit");

  private final String command;

  /**
   * constructor that initialises string s which is the user command input.
   *
   * @param command is the value of command to be executed.
   */
  CommandEnum(String command) {
    this.command = command;
  }

  /**
   * getter method to return the command.
   *
   * @return the mapped command for the user input.
   */
  public String getRepresentation() {
    return command;
  }

}
