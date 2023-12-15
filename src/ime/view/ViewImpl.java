package ime.view;

import java.io.PrintStream;

/**
 * This implementation of the {@link View} is used to display messages to the user.
 */
public class ViewImpl implements View {

  private final PrintStream out;

  /**
   * Constructor for initializing the object.
   */
  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void displayMessage(String message) {
    out.println(message);
  }
}
