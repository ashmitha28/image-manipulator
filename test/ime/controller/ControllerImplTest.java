package ime.controller;

import static org.junit.Assert.assertEquals;

import ime.view.View;
import ime.view.ViewImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains the unit tests for the controller {@link ControllerImpl}.
 */
public class ControllerImplTest {

  private final MockImgRepo mockImgRepo;
  private final MockFileHandler mockFileHandler;
  private final MockFileHandlerProvider mockFileHandlerProvider;
  private final MockHistogram mockHistogram;
  private final MockImageDrawer mockImageDrawer;
  private OutputStream outputStream;

  private View view;

  /**
   * Constructor initializing the Mock objects that will be used for testing of controller class.
   */
  public ControllerImplTest() {
    mockImgRepo = new MockImgRepo();
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(new ByteArrayOutputStream());
    view = new ViewImpl(printStream);
    mockFileHandler = new MockFileHandler();
    mockFileHandlerProvider = new MockFileHandlerProvider(mockFileHandler);
    mockHistogram = new MockHistogram();
    mockImageDrawer = new MockImageDrawer();
  }

  /**
   * This method is used to clear all the loggers and to set the default behaviours for the mock
   * classes.
   */
  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    view = new ViewImpl(new PrintStream(outputStream));
    mockImgRepo.setFailureFlag(true);
    mockFileHandlerProvider.setFailureFlag(false);
    mockImageDrawer.setFailureFlag(false);
    mockHistogram.setFailureFlag(false);
    mockHistogram.clearLogger();
    mockImgRepo.clearLogger();
    mockImgRepo.clearLogger();
  }

  /**
   * test for invalid tokens.
   */
  @Test
  public void testInvalidToken() {

    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "load\nsave\nsplit\nhorizontal-flip\nvertical-flip\nbrighten"
            + "\ncompress\nlevels-adjust\nhistogram\ncolor-correct\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n" + "Command not found\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run: \n"
        + "Invalid number of tokens passed for the given command\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test to run invalid script.
   */
  @Test
  public void testRunInvalidScript() {

    ImageProcessingController controller = new ControllerImpl(
        new Scanner("run testscript.txt\nexit"), view, mockImgRepo, mockFileHandlerProvider, true);
    controller.execute();
    assertEquals("", mockImgRepo.getLogger());
    String expectedView = "Please enter the command to run: \n" + "Invalid script location/file.\n"
        + "Please enter the command to run:";
    assertEquals(expectedView, outputStream.toString().trim());
  }

  /**
   * test to run valid script.
   */
  @Test
  public void testRunValidScript() {
    mockFileHandler.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("run test/resources/testscript.txt\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, false);
    controller.execute();

    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.LOAD, "test"),
        mockImgRepo.getLogger());

    assertEquals("loadImage called test/resources/testImage.ppm passed\n",
        mockFileHandler.getLogger());

    String expectedView = "Image Repository failed\n" + "Exiting with no more commands\n"
        + "Script file execution complete.";
    assertEquals(expectedView, outputStream.toString().trim());
  }

  /**
   * test to check invalid image operations.
   */
  @Test
  public void testInvalidImage() {
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "blur invalidImageName destImage" + "\nsharpen invalidImageName destImage"
            + "\nhorizontal-flip invalidImageName destImage"
            + "\nvertical-flip invalidImageName destImage"
            + "\nintensity-component invalidImageName destImage"
            + "\nvalue-component invalidImageName destImage"
            + "\nluma-component invalidImageName destImage"
            + "\nred-component invalidImageName destImage"
            + "\ngreen-component invalidImageName destImage"
            + "\nblue-component invalidImageName destImage" + "\nsepia invalidImageName destImage"
            + "\ncompress 10 invalidImageName destImage"
            + "\nlevels-adjust 10 20 200 invalidImageName destImage"
            + "\ncolor-correct invalidImageName destImage"
            + "\nhistogram invalidImageName destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, false);
    controller.execute();
    assertEquals(
        mockImgRepo.getLoggerMessageForOperation(MockImgRepo.BLUR, "invalidImageName", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SHARPEN, "invalidImageName",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.H_FLIP,
            "invalidImageName", "destImage") + mockImgRepo.getLoggerMessageForOperation(
            MockImgRepo.V_FLIP, "invalidImageName", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.INTENSITY, "invalidImageName",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.VALUE,
            "invalidImageName", "destImage") + mockImgRepo.getLoggerMessageForOperation(
            MockImgRepo.LUMA, "invalidImageName", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.RED_COMP, "invalidImageName",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.GREEN_COMP,
            "invalidImageName", "destImage") + mockImgRepo.getLoggerMessageForOperation(
            MockImgRepo.BLUE_COMP, "invalidImageName", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SEPIA, "invalidImageName",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COMPRESS,
            "invalidImageName", "destImage") + mockImgRepo.getLoggerMessageForOperation(
            MockImgRepo.LEVELS_ADJUST, "invalidImageName", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COLOR_CORRECT,
            "invalidImageName", "destImage") + mockImgRepo.getLoggerMessageForOperation(
            MockImgRepo.HISTOGRAM, "invalidImageName", "destImage"), mockImgRepo.getLogger());

    assertEquals("Source Name invalid\n" + "Source Name invalid\n" + "Source Name invalid\n"
        + "Source Name invalid\n" + "Source Name invalid\n" + "Source Name invalid\n"
        + "Source Name invalid\n" + "Source Name invalid\n" + "Source Name invalid\n"
        + "Source Name invalid\n" + "Source Name invalid\n" + MockImgRepo.COMPRESS + " failed\n"
        + MockImgRepo.LEVELS_ADJUST + " failed\n" + MockImgRepo.COLOR_CORRECT + " failed\n"
        + MockImgRepo.HISTOGRAM + " failed", outputStream.toString().trim());
  }

  /**
   * test to save invalid image.
   */
  @Test
  public void testInvalidImageForSave() {

    ImageProcessingController controller = new ControllerImpl(
        new Scanner("save test/resources/testImage.ppm destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.GET_IMAGE, "destImage"),
        mockImgRepo.getLogger());
    assertEquals(mockFileHandler.getLogger(), "");
    assertEquals(mockFileHandlerProvider.getLogger(), "");
    assertEquals("Please enter the command to run: \n" + "Image Repository failed\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test invalid image for split.
   */
  @Test
  public void testInvalidImageForSplit() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("rgb-split invalid red green blue" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SPLIT_IMAGE, "invalid",
        "[red, green, blue]"), mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n" + "Image Repository failed\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test for invalid image combine.
   */
  @Test
  public void testInvalidImageForCombine() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("rgb-combine invalid red green blue" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals(
        mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COMBINE_IMAGE, "[red, green, blue]",
            "invalid"), mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n" + "Image Repository failed\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test to brighten invalid image.
   */
  @Test
  public void testInvalidImageForBrighten() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("brighten 122 invalid destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals(
        mockImgRepo.getLoggerMessageForOperation(MockImgRepo.BRIGHTEN_IMAGE, "invalid", "destImage",
            122), mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n" + "Source Name invalid\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }


  /**
   * string in place of float.
   */
  @Test
  public void testInvalidFloatForCompress() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("compress number invalid destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals(
        "Please enter the command to run: \n" + "number expected following compress command\n"
            + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * string in place of float.
   */
  @Test
  public void testInvalidTokensForLevelsAdjust() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("levels-adjust 5 invalid destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, false);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Invalid number of tokens passed for the given command",
        outputStream.toString().trim());
  }

  @Test
  public void testNoNumbersForLevelsAdjust() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("levels-adjust num1 num2 num3 invalid destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n"
        + "3 numbers required following the levels-adjust command\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  @Test
  public void testSplitFunctionality() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "levels-adjust 1 2 3 src dest split 10\n" + "sepia src dest split 0"
            + "\nblur src dest split 20" + "\nsharpen src dest split 20" + "\nexit"), view,
        mockImgRepo, mockFileHandlerProvider, false);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 10)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.LEVELS_ADJUST, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 0)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SEPIA, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 20)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.BLUR, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 20)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SHARPEN, "src", "dest"),
        mockImgRepo.getLogger());
    assertEquals(
        "Successfully Previewed\n" + "Successfully Previewed\n" + "Successfully Previewed\n"
            + "Successfully Previewed", outputStream.toString().trim());
  }

  @Test
  public void testSplitFunctionalityInvalidCount() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "color-correct src dest split" + "\nluma-component src dest split 10 10" + "\nexit"), view,
        mockImgRepo, mockFileHandlerProvider, false);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Invalid number of tokens passed for the given command\n"
        + "Invalid number of tokens passed for the given command", outputStream.toString().trim());
  }

  @Test
  public void testSplitFunctionalityForUnsupportedCommand() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "red-component src dest split" + "\nbrighten 2 src dest split 10 10" + "\nexit"), view,
        mockImgRepo, mockFileHandlerProvider, false);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Invalid number of tokens passed for the given command\n"
        + "Invalid number of tokens passed for the given command", outputStream.toString().trim());
  }

  @Test
  public void testSplitFunctionality2() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "color-correct src dest split 10\n" + "\nluma-component src dest split 10"
            + "\nvalue-component src dest split 10" + "\nintensity-component src dest split 10"
            + "\nexit"), view, mockImgRepo, mockFileHandlerProvider, false);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 10)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COLOR_CORRECT, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 10)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.LUMA, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 10)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.VALUE, "src", "dest")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.PREVIEW, "src", "dest", 10)
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.INTENSITY, "src", "dest"),
        mockImgRepo.getLogger());
    assertEquals(
        "Successfully Previewed\n" + "Successfully Previewed\n" + "Successfully Previewed\n"
            + "Successfully Previewed", outputStream.toString().trim());
  }

  /**
   * test when string is given in place of float.
   */

  @Test
  public void testInvalidFloatForBrighten() {
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("brighten number invalidName.ppm destImage " + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    mockImgRepo.getLogger();
    assertEquals("", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n"
        + "brightness command expects a number following the command\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test to split combine valid image.
   */
  @Test
  public void testValidImageSplitCombine() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(
        new Scanner("rgb-split valid red green blue" + "\nrgb-combine valid red green blue\nexit"),
        view, mockImgRepo, mockFileHandlerProvider, true);
    controller.execute();
    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SPLIT_IMAGE, "valid",
        "[red, green, blue]") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COMBINE_IMAGE,
        "[red, green, blue]", "valid"), mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n"
        + "rgb-split operation completed successfully for valid & put in "
        + "[red, green, blue]\nPlease enter the command to run: \n"
        + "rgb-combine operation completed successfully for [red, green, blue] & put in "
        + "valid\nPlease enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test on valid image.
   */
  @Test
  public void testValidImageOperations() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "brighten 122 valid destImage" + "\nblur valid destImage" + "\ncompress 22 valid destImage"
            + "\nlevels-adjust 10 130 200 valid destImage" + "\nhistogram valid destImage"
            + "\ncolor-correct valid destImage" + "\nsharpen valid destImage" + "\nexit"), view,
        mockImgRepo, mockFileHandlerProvider, true);
    controller.execute();
    assertEquals(
        mockImgRepo.getLoggerMessageForOperation(MockImgRepo.BRIGHTEN_IMAGE, "valid", "destImage",
            122) + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.BLUR, "valid", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COMPRESS, "valid", "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.LEVELS_ADJUST, "valid",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.HISTOGRAM, "valid",
            "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.COLOR_CORRECT,
            "valid", "destImage") + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.SHARPEN,
            "valid", "destImage"), mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n"
        + "brighten operation completed successfully for valid & put in destImage "
        + "with constant value: 122\n" + "Please enter the command to run: \n"
        + "blur operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run: \n"
        + "compress operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run: \n"
        + "levels-adjust operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run: \n"
        + "histogram operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run: \n"
        + "color-correct operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run: \n"
        + "sharpen operation completed successfully for valid & put in destImage\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test grey scale on valid image.
   */
  @Test
  public void testValidImageGreyscales() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "\nintensity-component invalidName.ppm destImage"
            + "\nvalue-component invalidName.ppm destImage"
            + "\nluma-component invalidName.ppm destImage\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    assertEquals("intensity gs called invalidName.ppm and destImage passed\n"
        + "value gs called invalidName.ppm and destImage passed\n"
        + "luma gs called invalidName.ppm and destImage passed\n", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n" + "Please enter the command to run: \n"
        + "intensity-component operation completed successfully for invalidName.ppm "
        + "& put in destImage\nPlease enter the command to run: \n"
        + "value-component operation completed successfully for invalidName.ppm"
        + " & put in destImage\nPlease enter the command to run: \n"
        + "luma-component operation completed successfully for invalidName.ppm "
        + "& put in destImage\nPlease enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test sepia on valid image.
   */
  @Test
  public void testValidImageFlipsSepia() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "\nhorizontal-flip invalidName.ppm destImage" + "\nvertical-flip invalidName.ppm destImage"
            + "\nsepia invalidName.ppm destImage" + "\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    assertEquals("horizontal flip called invalidName.ppm and destImage passed\n"
        + "vertical flip called invalidName.ppm and destImage passed\n"
        + "sepia called invalidName.ppm and destImage passed\n", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \nPlease enter the command to run: \n"
        + "horizontal-flip operation completed successfully for invalidName.ppm "
        + "& put in destImage\nPlease enter the command to run: \n"
        + "vertical-flip operation completed successfully for invalidName.ppm "
        + "& put in destImage\nPlease enter the command to run: \n"
        + "sepia operation completed successfully for invalidName.ppm & put in destImage\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test image components on valid image.
   */
  @Test
  public void testValidImageComponents() {
    mockImgRepo.setFailureFlag(false);
    ImageProcessingController controller = new ControllerImpl(new Scanner(
        "\nred-component invalidName destImage" + "\ngreen-component invalidName destImage"
            + "\nblue-component invalidName destImage\nexit"), view, mockImgRepo,
        mockFileHandlerProvider, true);
    controller.execute();
    assertEquals("red channel called invalidName and destImage passed\n"
        + "green channel called invalidName and destImage passed\n"
        + "blue channel called invalidName and destImage passed\n", mockImgRepo.getLogger());
    assertEquals("Please enter the command to run: \n" + "Please enter the command to run: \n"
        + "red-component operation completed successfully for invalidName "
        + "& put in destImage\nPlease enter the command to run: \n"
        + "green-component operation completed successfully for invalidName "
        + "& put in destImage\nPlease enter the command to run: \n"
        + "blue-component operation completed successfully for invalidName "
        + "& put in destImage\nPlease enter the command to run:", outputStream.toString().trim());
  }

  /**
   * test load and save on valid image.
   *
   * @throws IOException whe file not found.
   */
  @Test
  public void testValidImageLoadSave() throws IOException {
    mockImgRepo.setFailureFlag(false);
    mockFileHandler.setFailureFlag(false);
    mockFileHandlerProvider.setFailureFlag(false);
    String commandList = "load test/resources/testImage.ppm destImage"
        + "\nsave test/resources/testImage.ppm destImage\nexit";
    String expectedFileHandlerLog = "loadImage called test/resources/testImage.ppm passed\n"
        + "saveImage called test/resources/testImage.ppm passed\n";
    ImageProcessingController controller = new ControllerImpl(new Scanner(commandList), view,
        mockImgRepo, mockFileHandlerProvider, true);

    controller.execute();

    assertEquals(mockImgRepo.getLoggerMessageForOperation(MockImgRepo.LOAD, "destImage")
            + mockImgRepo.getLoggerMessageForOperation(MockImgRepo.GET_IMAGE, "destImage"),
        mockImgRepo.getLogger());

    assertEquals(expectedFileHandlerLog, mockFileHandler.getLogger());

    assertEquals(mockImgRepo.getLastLoadedFloat(),
        mockFileHandler.loadImage("test/resources/testImage.ppm"));

    assertEquals("Please enter the command to run: \n" + "Loaded successfully.\n"
        + "Please enter the command to run: \n" + "Saved successfully.\n"
        + "Please enter the command to run:", outputStream.toString().trim());
  }

}