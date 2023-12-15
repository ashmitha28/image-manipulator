package ime.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ime.controller.ImageDrawer;
import ime.controller.ImageDrawerImpl;
import java.util.Arrays;
import org.junit.Test;

/**
 * Class that tests for {@link ImageRepositoryImpl}.
 */
public class ImageRepositoryTest {

  private final float[][][] testPixels;


  /**
   * constructor to declare the mock classes.
   */
  public ImageRepositoryTest() {

    testPixels = new float[][][]{{{1, 1, 1}, {1, 1, 1,}}, {{2, 2, 2}, {2, 3, 4}}};
  }

  /**
   * test to check when load works.
   */
  @Test
  public void testLoadWorksWhenFileHandlerWorks() {

    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
  }


  /**
   * test save wghen valid arguments are passed.
   */
  @Test
  public void testSaveSuccessfullyWhenValidFileFormatAndFile() {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.getImage("ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to throw exception");
    }
  }

  /**
   * test load when filehandler completes load.
   */
  @Test
  public void testLoadSuccessWhenFileHandlerCompletesLoad() {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    assertTrue("Value needs to be present",
        imageRepository.isImagePresent("ImageName"));
  }

  /**
   * test all operations when image is not present.
   */
  @Test
  public void testOperationsForImageNotPresent() {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    ImageDrawer imageDrawer = new ImageDrawerImpl();

    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.getImage("ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.brightenImage("fileName", "ImageName", 5));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.combineImages(Arrays.asList("red", "green", "blue"), "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.blurImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.sharpenImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.flipImageHorizontally("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.flipImageVertically("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toIntensityGreyScale("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toLumaGreyScale("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toValueGreyScale("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toSepiaImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toRedChannelImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toGreenChannelImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toBlueChannelImage("fileName", "ImageName"));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.splitImageIntoColorChannels("fileName",
            Arrays.asList("red", "green", "blue")));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.compress("fileName", "ImageName", 20));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.preview("fileName", "ImageName", imageRepository::toValueGreyScale, 20));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.levelsAdjust("fileName", "ImageName", 20, 130, 220));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.toHistogram("fileName", "ImageName", imageDrawer));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.colorCorrect("fileName", "ImageName"));
  }

  /**
   * Test the 'brightenImage' method in the 'ImageRepository' implementation. Ensures that the
   * method loads an image, applies brightness adjustment, and checks if the brightened image
   * exists.
   *
   * @throws IllegalArgumentException If there's an issue during image loading.
   */
  @Test
  public void testbrighten() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.brightenImage("ImageName", "brightenedImage", 10);
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("brighten not found ",
        imageRepository.isImagePresent("brightenedImage"));
  }

  @Test
  public void testBlur() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.blurImage("ImageName", "blurImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("blurred image not found ",
        imageRepository.isImagePresent("blurImage"));
  }

  @Test
  public void testSharpen() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.sharpenImage("ImageName", "sharpenImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("sharpened image not found ",
        imageRepository.isImagePresent("sharpenImage"));
  }

  @Test
  public void testflipImageHorizontally() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.flipImageHorizontally("ImageName", "flippedImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("horizontally flipped image not found ",
        imageRepository.isImagePresent("flippedImage"));
  }

  @Test
  public void testflipImageVertically() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.flipImageVertically("ImageName", "flippedImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("vertically flipped image not found ",
        imageRepository.isImagePresent("flippedImage"));
  }

  @Test
  public void testIntensityGreyScale() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toIntensityGreyScale("ImageName", "intensityImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("intensity image not found ",
        imageRepository.isImagePresent("intensityImage"));
  }

  @Test
  public void testLumaGreyScale() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toLumaGreyScale("ImageName", "lumaImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("luma image not found ",
        imageRepository.isImagePresent("lumaImage"));
  }

  @Test
  public void testValueGreyScale() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toValueGreyScale("ImageName", "valueImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("valued image not found ",
        imageRepository.isImagePresent("valueImage"));
  }

  @Test
  public void testSepia() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toSepiaImage("ImageName", "sepiaImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("sepia image not found ",
        imageRepository.isImagePresent("sepiaImage"));
  }

  @Test
  public void testRedChannelImage() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toRedChannelImage("ImageName", "redImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("red image not found ",
        imageRepository.isImagePresent("redImage"));
  }

  @Test
  public void testGreenChannelImage() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.toGreenChannelImage("ImageName", "greenImage");
    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertTrue("green image not found ",
        imageRepository.isImagePresent("greenImage"));
  }

  @Test
  public void testBlueChannelImage() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.toBlueChannelImage("ImageName", "blueImage");

    assertTrue("blue image not found ",
        imageRepository.isImagePresent("blueImage"));
  }

  //test for image present after operation
  @Test
  public void testCompress() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.compress("ImageName", "flippedImage", 20);

    assertTrue("compressed image not found ",
        imageRepository.isImagePresent("flippedImage"));
  }

  @Test
  public void testPreview() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.preview("ImageName", "previewedImage",
        imageRepository::toValueGreyScale, 50);

    assertEquals(imageRepository.getImage("previewedImage"), testPixels);
    assertTrue("previewedImage image not found ",
        imageRepository.isImagePresent("previewedImage"));
  }

  @Test
  public void testPreviewForZeroSplit() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    try {
      imageRepository.preview("ImageName", "previewedImage",
          imageRepository::toValueGreyScale, 0);

    } catch (NullPointerException e) {
      fail("Not supposed to fail");
    }
    assertEquals(imageRepository.getImage("previewedImage"), testPixels);
    assertTrue("previewedImage image not found ",
        imageRepository.isImagePresent("previewedImage"));
  }

  @Test
  public void testPreviewForFullSplit() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    //  StringBuilder methodCallLogger = new StringBuilder("");

    float[][][] expected = new float[][][]{{{1, 1, 1}, {1, 1, 1,}}, {{2, 2, 2}, {4, 4, 4}}};

    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.preview("ImageName", "previewedImage",
        imageRepository::toValueGreyScale, 100);

    assertEquals(imageRepository.getImage("previewedImage"), expected);
    assertTrue("previewedImage image not found ",
        imageRepository.isImagePresent("previewedImage"));
  }

  @Test
  public void testPreviewInvalidSplit() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.preview("ImageName", "previewedImage",
            imageRepository::toValueGreyScale, -50));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.preview("ImageName", "previewedImage",
            imageRepository::toValueGreyScale, 150));

  }

  @Test
  public void testLevelsAdjust() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.levelsAdjust("ImageName", "AdjustedImage", 20, 150, 210);

    assertTrue("levels adjusted image not found ",
        imageRepository.isImagePresent("AdjustedImage"));
  }

  /**
   * TEST DOES NOT THROW ILLEGAL ARG EXCEPTION WHEN BMW IS NEGATIVE OR GREATER THAN 255.
   *
   * @throws IllegalArgumentException not there?
   */
  @Test
  public void testLevelsAdjustInvalidBMW() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }

    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.levelsAdjust("ImageName", "AdjustedImage", -20, 100, 200));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.levelsAdjust("ImageName", "AdjustedImage", 20, 200, 300));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.levelsAdjust("ImageName", "AdjustedImage", 20, 10, 200));
    assertThrows(IllegalArgumentException.class, () ->
        imageRepository.levelsAdjust("ImageName", "AdjustedImage", 220, 130, 20));
    assertTrue("levels adjusted image found when it should not be ",
        !imageRepository.isImagePresent("AdjustedImage"));
  }

  @Test
  public void testColorCorrect() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.colorCorrect("ImageName", "ColorCorrect");

    assertTrue("Color Corrected image not found ",
        imageRepository.isImagePresent("ColorCorrect"));
  }

  @Test
  public void testHistogram() throws IllegalArgumentException {
    ImageRepository imageRepository = new ImageRepositoryImpl();
    ImageDrawer imageDrawer = new ImageDrawerImpl();
    try {
      imageRepository.loadImage(testPixels, "ImageName");
    } catch (IllegalArgumentException e) {
      fail("Not supposed to fail");
    }
    imageRepository.toHistogram("ImageName", "histogram", imageDrawer);

    assertTrue("histogram image not found ",
        imageRepository.isImagePresent("histogram"));
  }

}
