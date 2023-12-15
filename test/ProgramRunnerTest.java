import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runners.model.TestTimedOutException;

/**
 * Test for the ProgramRunner class.
 */
public class ProgramRunnerTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  @Rule
  public Timeout globalTimeout = Timeout.seconds(2); // 3 seconds max per method tested
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Setup method to initialize the objects.
   */
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void cleanUp() {
    System.setOut(originalOut);
  }

  @Test
  public void testMainWithTextArg() {
    //Since the controller waits for input, the program never ends.
    thrown.expect(TestTimedOutException.class);
    String[] args = {"-text"};
    ProgramRunner.main(args);
  }

  @Test(expected = Test.None.class)
  public void testMainWithNoArg() {
    String[] args = {};
    ProgramRunner.main(args);
  }

  @Test
  public void testMainWithValidArgs() {
    String[] args = {"-file", "test/resources/emptyscript.txt"};
    ProgramRunner.main(args);

    String expectedOutput = "Exiting with no more commands\n";
    assertTrue(outContent.toString().equals(expectedOutput));
  }

  @Test
  public void testMainWithInvalidArgs() {
    String[] args = {"invalidScript.txt"};
    ProgramRunner.main(args);

    String expectedOutput = "Invalid arguments provided to the Program Runner. Either pass "
        + "no arguments or provide '-file filename' or '-text\n";
    assertTrue(outContent.toString().contains(expectedOutput));
  }

  @Test
  public void testMainWithInvalidFile() {
    String[] args = {"-file", "invalidScript.txt"};
    ProgramRunner.main(args);

    String expectedOutput = "Invalid file provided. Exiting.\n";
    assertTrue(outContent.toString().contains(expectedOutput));
  }

}