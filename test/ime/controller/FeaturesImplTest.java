package ime.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * class to test the feature impl class.
 */
public class FeaturesImplTest {

  private FeaturesImpl features;
  private MockGUIView mockGUIView;
  private MockImgRepo mockImgRepo;

  /**
   * setup method.
   */
  @Before
  public void setup() {
    mockGUIView = new MockGUIView();
    mockImgRepo = new MockImgRepo();
    FileHandlerProvider fileHandlerProvider = new FileHandlerProviderImpl();
    GUIController guiController = new GUIController(mockImgRepo, mockGUIView, fileHandlerProvider);
    features = new FeaturesImpl(guiController);
    mockGUIView.setInputValueCounter(6);
  }

  /**
   * invoking toggle when image is not there.
   */
  @Test
  public void testToggle() {
    mockImgRepo.setFailureFlag(true);
    features.toggle();
    String expected = "message displayed" + " " + "Image Repository failed\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("getImage called and previewImage passed\n", mockImgRepo.getLogger());
  }


  /**
   * invoke load when repo fails.
   */
  @Test
  public void testLoad() {
    mockImgRepo.setFailureFlag(true);
    features.loadImage();
    assertEquals("loadImage called and guiImage passed\n", mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n" + "preview has been disabled\n" + "load success\n"
        + "message displayed Image Repository failed\n", mockGUIView.getLogger());
  }

  /**
   * test to check the behaviour when image is overwritten when user cancels.
   */
  @Test
  public void testOverwritingImageCancel() {
    mockGUIView.setFail(true);
    features.loadImage();
    features.chooseBlur();
    features.applyChosenOperation();
    features.loadImage();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "blurImage called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
            + "preview has been disabled\n"
            + "load success\n"
            + "image has been set\n" + "histogram has been set\n"
            + "Apply has been enabled\n"
            + "preview has been enabled\n"
            + "image has been set\n" + "histogram has been set\n"
            + "toggle has been disabled\n"
            + "Apply has been disabled\n" + "preview has been disabled\n"
            + "user canceled Current image is unsaved. Do you want to overwrite the image?\n",
        mockGUIView.getLogger());
  }

  /**
   * test to check the behaviour when image is overwritten and user confirms it.
   */
  @Test
  public void testOverwritingImageConfirm() {
    features.loadImage();
    features.chooseBlur();
    features.applyChosenOperation();
    features.loadImage();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "blurImage called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
            + "preview has been disabled\n"
            + "load success\n"
            + "image has been set\n"
            + "histogram has been set\n"
            + "Apply has been enabled\n"
            + "preview has been enabled\n"
            + "image has been set\n"
            + "histogram has been set\n"
            + "toggle has been disabled\n"
            + "Apply has been disabled\n"
            + "preview has been disabled\n"
            + "confirmation received Current image is unsaved."
            + " Do you want to overwrite the image?\n"
            + "load success\n"
            + "image has been set\n"
            + "histogram has been set\n",
        mockGUIView.getLogger());
  }

  /**
   * test to load and operate on an image without applying.
   */
  @Test
  public void testLoadImageNotApply() {
    features.loadImage();
    features.chooseBlur();
    features.previewChosenOperation();
    features.loadImage();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "preview called 0.0 and guiImage and previewImage passed\n"
            + "blurImage called guiImage and previewImage passed\n"
            + "getImage called and previewImage passed\n"
            + "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
        + "preview has been disabled\n" + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "Apply has been enabled\n"
        + "preview has been enabled\n"
        + "0 input is received Enter value between 0 - 100 for preview percentage\n"
        + "image has been set\n"
        + "toggle has been enabled\n"
        + "Apply has been disabled\n"
        + "preview has been disabled\n" + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n", mockGUIView.getLogger());
  }

  /**
   * test when multiple operations are selected and applied.
   */
  @Test
  public void testMultipleOperations() {
    features.loadImage();
    features.chooseBlur();
    features.chooseVisualizeRed();
    features.chooseSepia();
    features.applyChosenOperation();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "sepia called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
        + "preview has been disabled\n" + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "Apply has been enabled\n"
        + "preview has been enabled\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n"
        + "Apply has been enabled\n"
        + "preview has been enabled\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "toggle has been disabled\n", mockGUIView.getLogger());
  }

  /**
   * test when multiple operations are selected and an operation asks for user input.
   */
  @Test
  public void testMultipleOperationWithUserInput() {
    features.loadImage();
    features.chooseHorizontalFlip();
    features.chooseCompression();
    features.applyChosenOperation();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "compress called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
        + "preview has been disabled\n" + "load success\n"
        + "image has been set\n" + "histogram has been set\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n"
        + "0 input is received Enter value between 0 - 100 for compression factor\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "toggle has been disabled\n", mockGUIView.getLogger());
  }

  /**
   * test to check the whole flow and multiple loads are done.
   */
  @Test
  public void testWholeFlow() {
    mockGUIView.setFail(true);
    features.loadImage();
    features.chooseVisualizeBlue();
    features.applyChosenOperation();
    features.loadImage();
    mockGUIView.setFail(false);
    features.saveImage();
    features.loadImage();
    assertEquals(
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "blue channel called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n" + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
    assertEquals("Apply has been disabled\n"
        + "preview has been disabled\n" + "load success\n"
        + "image has been set\n" + "histogram has been set\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "toggle has been disabled\n"
        + "Apply has been disabled\n"
        + "preview has been disabled\n"
        + "user canceled Current image is unsaved. Do you want to overwrite the image?\n"
        + "Apply has been disabled\n"
        + "preview has been disabled\n"
        + "save success\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "Apply has been disabled\n"
        + "preview has been disabled\n"
        + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n", mockGUIView.getLogger());
  }

  /**
   * invoke save when model repo fails.
   */
  @Test
  public void testSave() {
    mockImgRepo.setFailureFlag(true);
    features.saveImage();
    assertEquals("getImage called and guiImage passed\n", mockImgRepo.getLogger());
  }

  /**
   * test choose horizontal flip when model fails.
   */
  @Test
  public void testChooseHorizontalFlip() {
    features.chooseHorizontalFlip();
    assertNoImageLoaded(false);
  }

  /**
   * test choose vertical flip when model fails.
   */
  @Test
  public void testChooseVerticalFlip() {
    features.chooseVerticalFlip();
    assertNoImageLoaded(false);
  }

  /**
   * test choose color correct when model fails.
   */
  @Test
  public void testChooseColorCorrect() {
    features.chooseColorCorrect();
    assertNoImageLoaded(true);
  }

  /**
   * test choose visualise red when model fails.
   */
  @Test
  public void testChooseVisualizeRed() {
    features.chooseVisualizeRed();
    assertNoImageLoaded(false);
  }

  /**
   * test choose visualise green when model fails.
   */
  @Test
  public void testChooseVisualizeGreen() {
    features.chooseVisualizeGreen();
    assertNoImageLoaded(false);
  }

  /**
   * test choose visualise blue when model fails.
   */
  @Test
  public void testChooseVisualizeBlue() {
    features.chooseVisualizeBlue();
    assertNoImageLoaded(false);
  }

  /**
   * test choose compress when model fails.
   */
  @Test
  public void testChooseCompression() {
    features.chooseCompression();
    String expected = "0 input is received Enter value between 0 - 100 for compression factor\n"
        + "Apply has been enabled\n" + "preview has been disabled\n";
    assertEquals(expected, mockGUIView.getLogger());
  }

  /**
   * test choose sepia when model fails.
   */
  @Test
  public void testchooseSepia() {
    features.chooseSepia();
    assertNoImageLoaded(true);
  }

  /**
   * test choose luma when model fails.
   */
  @Test
  public void testChooseLumaGreyscale() {
    features.chooseLumaGreyscale();
    assertNoImageLoaded(true);
  }

  /**
   * test choose levels adjust when model fails.
   */
  @Test
  public void testChooseLevelsAdjust() {
    features.chooseLevelsAdjust();
    String expected = "0 input is received Enter value between 0 - 253 for black point\n"
        + "1 input is received Enter value between 1 - 254 for mid point\n"
        + "2 input is received Enter value between 2 - 255 for white point\n"
        + "Apply has been enabled\n" + "preview has been enabled\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

  /**
   * test choose blur when model fails.
   */
  @Test
  public void testChooseBlur() {
    features.chooseBlur();
    String expected = "Apply has been enabled\n" + "preview has been enabled\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

  private void assertNoImageLoaded(boolean previewSupport) {
    mockImgRepo.setFailureFlag(true);
    String expected = "Apply has been enabled\npreview has been ";
    expected += previewSupport ? "enabled\n" : "disabled\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

  /**
   * test load when both view and model works.
   */
  @Test
  public void testLoadImage() {
    features.loadImage();
    String expected = "Apply has been disabled\n" + "preview has been disabled\n" + "load success\n"
        + "image has been set\n" + "histogram has been set\n";
    String expectedImgRepo =
        "loadImage called and guiImage passed\n" + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n" + "getImage called and hist passed\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals(expectedImgRepo, mockImgRepo.getLogger());
  }

  /**
   * test when invalid file was chosen.
   */
  @Test
  public void testInvalidFileChosen() {
    mockGUIView.setIOFail(true);
    features.loadImage();
    String expected =
        "Apply has been disabled\n" + "preview has been disabled\n" + "load unsuccessful\n"
            + "message displayed Invalid file format provided.\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

  /**
   * test invalid file to save.
   */
  @Test
  public void testInvalidFileSave() {
    mockGUIView.setIOFail(true);

    features.saveImage();
    String expected =
        "Apply has been disabled\n" + "preview has been disabled\n" + "save unsuccessful\n"
            + "message displayed Invalid file format provided.\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("getImage called and guiImage passed\n", mockImgRepo.getLogger());
  }

  /**
   * test when invalid image is toggled.
   */
  @Test
  public void testInvalidFileToggle() {
    mockGUIView.setFail(true);
    features.toggle();
    String expected = "image has been set\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("getImage called and previewImage passed\n", mockImgRepo.getLogger());
  }

  /**
   * test when preview is done when no operation is chosen.
   */
  @Test
  public void testPreviewWhenNoOperationIsChosen() {
    mockGUIView.setFail(true);
    features.loadImage();
    features.previewChosenOperation();
    String expectedView =
        "Apply has been disabled\n" + "preview has been disabled\n" + "load success\n"
            + "image has been set\n" + "histogram has been set\n"
            + "message displayed Operation not chosen\n";
    String expectedImgRepo =
        "loadImage called and guiImage passed\n" + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n" + "getImage called and hist passed\n";
    assertEquals(expectedView, mockGUIView.getLogger());
    assertEquals(expectedImgRepo, mockImgRepo.getLogger());
  }

  /**
   * test when apply is chosen when no image is loaded.
   */
  @Test
  public void testApplyIsChosenWhenImageNotLoaded() {
    mockGUIView.setFail(true);
    features.applyChosenOperation();
    String expected = "message displayed Operation not chosen\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

  /**
   * test save when both view and model works.
   */
  @Test
  public void testSaveImage() {
    features.saveImage();
    String expected = "Apply has been disabled\n" + "preview has been disabled\n" + "save success\n"
        + "image has been set\n" + "histogram has been set\n";
    assertEquals(expected, mockGUIView.getLogger());
  }

  /**
   * operation is not chosen and clicking apply.
   */
  @Test
  public void testApplyChosenValue() {
    features.applyChosenOperation();
    String expected = "message displayed Operation not chosen\n";
    assertEquals(expected, mockGUIView.getLogger());
  }


  private void assertApplyOperation(Runnable operation, boolean previewSupport, String name) {
    features.loadImage();
    operation.run();
    String expected = "Apply has been disabled\n" + "preview has been disabled\n"
        + "load success\nimage has been set\n" + "histogram has been set\n";
    if (name.equals("compress")) {
      expected += "0 input is received Enter value between 0 - 100 for compression factor\n";
    } else if (name.equals("levels adjust")) {
      expected += "0 input is received Enter value between 0 - 253 for black point\n"
          + "1 input is received Enter value between 1 - 254 for mid point\n"
          + "2 input is received Enter value between 2 - 255 for white point\n";
    }
    expected += "Apply has been enabled\n";
    features.applyChosenOperation();
    if (previewSupport) {
      expected += "preview has been enabled\n";
    } else {
      expected += "preview has been disabled\n";
    }
    expected += "image has been set\n" + "histogram has been set\n" + "toggle has been disabled\n";
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals(
        "loadImage called and guiImage passed\n" + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n" + "getImage called and hist passed\n" + name
            + " called guiImage and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n" + "getImage called and hist passed\n",
        mockImgRepo.getLogger());
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseApplyHFlip() {
    assertApplyOperation(() -> features.chooseHorizontalFlip(), false, "horizontal flip");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseApplyVFlip() {
    assertApplyOperation(() -> features.chooseVerticalFlip(), false, "vertical flip");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseApplyBlur() {
    assertApplyOperation(() -> features.chooseBlur(), true, "blurImage");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseApplySepia() {
    assertApplyOperation(() -> features.chooseSepia(), true, "sepia");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseVisualizeRedApply() {
    assertApplyOperation(() -> features.chooseVisualizeRed(), false, "red channel");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseVisualizeBlueApply() {
    assertApplyOperation(() -> features.chooseVisualizeBlue(), false, "blue channel");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseVisualizeGreenApply() {
    assertApplyOperation(() -> features.chooseVisualizeGreen(), false, "green channel");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseCompressionApply() {
    assertApplyOperation(() -> features.chooseCompression(), false, "compress");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseLumaGreyscaleApply() {
    assertApplyOperation(() -> features.chooseLumaGreyscale(), true, "luma gs");
  }

  /**
   * test when both model and view works.
   */
  @Test
  public void testChooseApplyLevelsAdjust() {
    assertApplyOperation(() -> features.chooseLevelsAdjust(), true, "levels adjust");
  }

  private void assertPreviewWorks(Runnable operation, String operationName,
      boolean previewSupport) {
    String unsupportedCommand = "preview has been disabled\n"
        + "0 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed This operation can not be previewed.\n";
    String supportedCommand = "preview has been enabled\n"
        + "0 input is received Enter value between 0 - 100 for preview percentage\n"
        + "image has been set\n" + "toggle has been enabled\n";
    String expected = "Apply has been disabled\n" + "preview has been disabled\n" + "load success\n"
        + "image has been set\n" + "histogram has been set\n" + "Apply has been enabled\n";

    expected += previewSupport ? supportedCommand : unsupportedCommand;
    assertPreviewWorks(operation, expected, operationName, previewSupport);
  }

  private void assertPreviewWorks(Runnable operation, String expectedString, String operationName,
      boolean previewSupport) {
    features.loadImage();
    operation.run();
    features.previewChosenOperation();
    assertEquals(expectedString, mockGUIView.getLogger());
    String expectedImgRepoLogger = previewSupport ? "loadImage called and guiImage passed\n"
        + "histogram called guiImage and hist passed\n"
        + "getImage called and guiImage passed\n"
        + "getImage called and hist passed\npreview called 0.0 "
        + "and guiImage and previewImage passed\n"
        + operationName + " called guiImage and previewImage passed\n"
        + "getImage called and previewImage passed\n"
        : "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n";
    assertEquals(expectedImgRepoLogger, mockImgRepo.getLogger());
  }

  /**
   * test blur operation.
   */
  @Test
  public void testPreviewBlur() {
    assertPreviewWorks(() -> features.chooseBlur(), "blurImage", true);
  }

  /**
   * test invalid input for horizontal flip.
   */
  @Test
  public void testInvalidInput() {
    mockGUIView.setInputValueCounter(0);
    features.loadImage();
    features.chooseHorizontalFlip();
    features.previewChosenOperation();
    assertEquals("Apply has been disabled\n"
        + "preview has been disabled\n"
        + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n"
        + "-1 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed Invalid value. Please try again\n"
        + "101 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed Invalid value. Please try again\n"
        + "0 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed This operation can not be previewed.\n", mockGUIView.getLogger());
  }

  /**
   * test invalid input for blur.
   */
  @Test
  public void testInvalidInputBlur() {
    mockGUIView.setInputValueCounter(0);
    features.chooseBlur();
    features.previewChosenOperation();
    assertEquals(
        "Apply has been enabled\n"
            + "preview has been enabled\n"
            + "-1 input is received Enter value between 0 - 100 for preview percentage\n"
            + "message displayed Invalid value. Please try again\n"
            + "101 input is received Enter value between 0 - 100 for preview percentage\n"
            + "message displayed Invalid value. Please try again\n"
            + "0 input is received Enter value between 0 - 100 for preview percentage\n"
            + "image has been set\n"
            + "toggle has been enabled\n", mockGUIView.getLogger());
  }

  /**
   * test invalid input for compression.
   */
  @Test
  public void testInvalidInputCompression() {
    mockGUIView.setInputValueCounter(0);
    features.chooseCompression();
    assertEquals("-1 input is received Enter value between 0 - 100 for compression factor\n"
        + "message displayed Invalid value. Please try again\n"
        + "101 input is received Enter value between 0 - 100 for compression factor\n"
        + "message displayed Invalid value. Please try again\n"
        + "0 input is received Enter value between 0 - 100 for compression factor\n"
        + "Apply has been enabled\n"
        + "preview has been disabled\n", mockGUIView.getLogger());
  }

  /**
   * test invalid input for levels adjust.
   */
  @Test
  public void testInvalidInputLevelsAdjust() {
    mockGUIView.setInputValueCounter(0);
    features.chooseLevelsAdjust();
    assertEquals("-1 input is received Enter value between 0 - 253 for black point\n"
        + "message displayed Invalid value. Please try again\n"
        + "101 input is received Enter value between 0 - 253 for black point\n"
        + "0 input is received Enter value between 102 - 254 for mid point\n"
        + "message displayed Invalid value. Please try again\n"
        + "100 input is received Enter value between 102 - 254 for mid point\n"
        + "message displayed Invalid value. Please try again\n"
        + "120 input is received Enter value between 102 - 254 for mid point\n"
        + "150 input is received Enter value between 121 - 255 for white point\n"
        + "Apply has been enabled\n"
        + "preview has been enabled\n", mockGUIView.getLogger());
  }

  /**
   * test preview operation when horizontally flipped.
   */
  @Test
  public void testPreviewHFlip() {

    assertPreviewWorks(() -> features.chooseHorizontalFlip(), "horizontal flip", false);
  }

  /**
   * test preview when vertically flipped.
   */
  @Test
  public void testPreviewVFlip() {

    assertPreviewWorks(() -> features.chooseVerticalFlip(), "vertical flip", false);
  }

  /**
   * test preview and visualise red.
   */
  @Test
  public void testPreviewVisualiseRed() {
    assertPreviewWorks(() -> features.chooseVisualizeRed(), "visualize red", false);
  }

  /**
   * test preview and visualise blue.
   */
  @Test
  public void testPreviewVisualiseBlue() {
    assertPreviewWorks(() -> features.chooseVisualizeBlue(), "blue channel", false);
  }

  /**
   * test preview and visualise green.
   */
  @Test
  public void testPreviewVisualiseGreen() {
    assertPreviewWorks(() -> features.chooseVisualizeGreen(), "green channel", false);
  }

  /**
   * test preview and sepia operation.
   */
  @Test
  public void testPreviewSepia() {
    assertPreviewWorks(() -> features.chooseSepia(), "sepia", true);
  }

  /**
   * test compression and preview.
   */
  @Test
  public void testPreviewCompression() {
    String expected = "Apply has been disabled\n" + "preview has been disabled\n" + "load success\n"
        + "image has been set\n" + "histogram has been set\n"
        + "0 input is received Enter value between 0 - 100 for compression factor\n"
        + "Apply has been enabled\n" + "preview has been disabled\n"
        + "1 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed This operation can not be previewed.\n";
    assertPreviewWorks(() -> features.chooseCompression(), expected, "compression", false);
  }

  /**
   * test preview and grey scale.
   */
  @Test
  public void testPreviewGreyScale() {
    assertPreviewWorks(() -> features.chooseLumaGreyscale(), "luma gs", true);
  }

  /**
   * test preview and levels adjust.
   */
  @Test
  public void testPreviewLevelsAdjust() {
    String expected = "Apply has been disabled\n"
        + "preview has been disabled\n"
        + "load success\n"
        + "image has been set\n"
        + "histogram has been set\n"
        + "0 input is received Enter value between 0 - 253 for black point\n"
        + "1 input is received Enter value between 1 - 254 for mid point\n"
        + "2 input is received Enter value between 2 - 255 for white point\n"
        + "Apply has been enabled\n"
        + "preview has been enabled\n"
        + "-1 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed Invalid value. Please try again\n"
        + "101 input is received Enter value between 0 - 100 for preview percentage\n"
        + "message displayed Invalid value. Please try again\n"
        + "0 input is received Enter value between 0 - 100 for preview percentage\n"
        + "image has been set\n"
        + "toggle has been enabled\n";
    String expectedImageRepoLogger =
        "loadImage called and guiImage passed\n"
            + "histogram called guiImage and hist passed\n"
            + "getImage called and guiImage passed\n"
            + "getImage called and hist passed\n"
            + "preview called 0.0 and guiImage and previewImage passed\n"
            + "levels adjust called guiImage and previewImage passed\n"
            + "getImage called and previewImage passed\n";
    features.loadImage();
    features.chooseLevelsAdjust();
    features.previewChosenOperation();
    assertEquals(expected, mockGUIView.getLogger());
    assertEquals(expectedImageRepoLogger, mockImgRepo.getLogger());
  }

}