package ime.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

/**
 * class to test the RgbPixel class.
 */
public class RgbPixelTest {

  private RgbPixel pixel;

  /**
   * setup for rgb matrix.
   */
  @Before
  public void setUp() {
    float[] initialValues = {100.0f, 150.0f, 200.0f};
    pixel = new RgbPixel(initialValues);
  }

  @Test
  public void testConstructorThrowsException() {
    float[] initialValues = {100.0f, 150.0f};
    assertThrows(IllegalArgumentException.class, () -> new RgbPixel(initialValues));
    float[] initialValues2 = {100.0f, 150.0f, 300f};
    assertThrows(IllegalArgumentException.class, () -> new RgbPixel(initialValues2));
    float[] initialValues3 = {-100.0f, 150.0f, 200f};
    assertThrows(IllegalArgumentException.class, () -> new RgbPixel(initialValues3));
  }

  /**
   * test to check getters. getChannelValues method check.
   */
  @Test
  public void testConstructorSettingAndGetValues() {
    float[] initialValues = {20f, 10.0f, 20.0f};
    pixel = new RgbPixel(initialValues);
    assertArrayEquals(initialValues, pixel.getChannelValues(), 0.001f);
    assertEquals(3, pixel.getColorChannelCount());
    assertEquals(20, pixel.getChannelValue(0), 0.01);
    assertEquals(10, pixel.getChannelValue(1), 0.01);
    assertEquals(20, pixel.getChannelValue(2), 0.01);
  }

  /**
   * test to check getters. getValue method check.
   */
  @Test
  public void testGetValue() {
    float maxValue = pixel.getValue();
    assertEquals(200.0f, maxValue, 0.001f);
  }

  /**
   * test to get intensity.
   */
  @Test
  public void testGetIntensity() {
    float intensity = pixel.getIntensity();
    assertEquals(150.0f, intensity, 0.001f);
  }

  /**
   * test to get luma.
   */
  @Test
  public void testGetLuma() {
    float luma = pixel.getLuma();
    assertEquals(142.98f, luma, 0.001f);
  }

  /**
   * test to transform pixel.
   */
  @Test
  public void testTransformPixel() {
    float[][] transformCoefficients = {
        {0.5f, 0.0f, 0.0f},
        {0.0f, 0.5f, 0.0f},
        {0.0f, 0.0f, 0.5f}
    };

    Pixel transformedPixel = pixel.transformPixel(transformCoefficients);
    float[] transformedValues = transformedPixel.getChannelValues();

    assertEquals(50.0f, transformedValues[0], 0.001f);
    assertEquals(75.0f, transformedValues[1], 0.001f);
    assertEquals(100.0f, transformedValues[2], 0.001f);
  }

  /**
   * test to transform pixel (failure case).
   */
  @Test
  public void testTransformPixelFailWhenTransformerIncompatible() {
    float[][] transformCoefficients = {
        {0.5f, 0.0f},
        {0.0f, 0.5f},
        {0.0f, 0.0f}
    };

    assertThrows(IllegalArgumentException.class, () -> pixel.transformPixel(transformCoefficients));
  }

  /**
   * test to get channel values.
   */
  @Test
  public void testGetChannelValue() {
    float channelValue = pixel.getChannelValue(1);
    assertEquals(150.0f, channelValue, 0.001f);
  }

  /**
   * test to set color.
   */
  @Test
  public void testSetColor() {
    float[] newValues = {50.0f, 100.0f, 150.0f};
    pixel.setColor(newValues);

    float[] updatedValues = pixel.getChannelValues();
    assertArrayEquals(newValues, updatedValues, 0.001f);
  }

  /**
   * test to set color(failure case).
   */
  @Test
  public void testSetColorThrowsExceptionOnInvalidInput() {
    float[] newValues = {100.0f, 150.0f};
    assertThrows(IllegalArgumentException.class, () -> pixel.setColor(newValues));
    float[] newValues2 = {100.0f, 150.0f, -100f};
    assertThrows(IllegalArgumentException.class, () -> pixel.setColor(newValues2));
    float[] newValues3 = {100.0f, 150.0f, 300f};
    assertThrows(IllegalArgumentException.class, () -> pixel.setColor(newValues3));
  }

  /**
   * test to set color channels.
   */
  @Test
  public void testSetColorChannel() {
    float newValue = 75.0f;
    pixel.setColorChannel(1, newValue);

    float updatedValue = pixel.getChannelValue(1);
    assertEquals(newValue, updatedValue, 0.001f);
  }

  /**
   * test to set color channel's failure cases.
   */
  @Test
  public void testSetColorChannelFailure() {
    assertThrows(IllegalArgumentException.class, () -> pixel.setColorChannel(1, -75));
    assertThrows(IllegalArgumentException.class, () -> pixel.setColorChannel(1, 300));
    assertThrows(IllegalArgumentException.class, () -> pixel.setColorChannel(4, 75));
    assertThrows(IllegalArgumentException.class, () -> pixel.setColorChannel(-1, 75));
  }

  /**
   * test to check brighten method.
   */
  @Test
  public void testBrighten() {
    float brightnessConstant = 30.0f;
    Pixel brightenedPixel = pixel.brighten(brightnessConstant);

    assertArrayEquals(new float[]{130, 180, 230}, brightenedPixel.getChannelValues(), 0.01f);
    brightenedPixel = brightenedPixel.brighten(50);
    assertArrayEquals(new float[]{180, 230, 255}, brightenedPixel.getChannelValues(), 0.01f);
    brightenedPixel = brightenedPixel.brighten(200);
    assertArrayEquals(new float[]{255, 255, 255}, brightenedPixel.getChannelValues(), 0.01f);
    brightenedPixel = brightenedPixel.brighten(10);
    assertArrayEquals(new float[]{255, 255, 255}, brightenedPixel.getChannelValues(), 0.01f);
  }

  /**
   * test to check brighten method with negative input.
   */
  @Test
  public void testDarken() {
    float brightnessConstant = -30.0f;
    Pixel darkenedPixel = pixel.brighten(brightnessConstant);

    assertArrayEquals(new float[]{70, 120, 170}, darkenedPixel.getChannelValues(), 0.01f);
    darkenedPixel = darkenedPixel.brighten(-100);
    assertArrayEquals(new float[]{0, 20, 70}, darkenedPixel.getChannelValues(), 0.01f);
    darkenedPixel = darkenedPixel.brighten(-100);
    assertArrayEquals(new float[]{0, 0, 0}, darkenedPixel.getChannelValues(), 0.01f);
    darkenedPixel = darkenedPixel.brighten(-10);
    assertArrayEquals(new float[]{0, 0, 0}, darkenedPixel.getChannelValues(), 0.01f);
  }
}