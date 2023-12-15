package ime.view;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

/**
 * test for the view class.
 */
public class ViewImplTest {

  private ViewImpl view;
  private ByteArrayOutputStream outputStream;

  /**
   * setup method to set the output stream.
   */
  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    view = new ViewImpl(printStream);
  }

  /**
   * test to check if the display method works.
   */
  @Test
  public void testDisplayMessage() {
    String message = "Hello, World!";
    view.displayMessage(message);
    String printedOutput = outputStream.toString().trim();

    assertEquals(message, printedOutput);
  }

}