package ime.model;

import static org.junit.Assert.assertEquals;

import ime.controller.MockHistogram;
import ime.controller.MockImageDrawer;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for the HistogramDrawerImpl class.
 */
public class HistogramDrawerImplTest {

  private MockImageDrawer mockImageDrawer;
  private MockHistogram mockHistogram;
  private HistogramDrawerImpl histogramDrawer;

  /**
   * Constructor for the test class, initializes the MockImageDrawer and MockHistogram.
   */
  public HistogramDrawerImplTest() {
    this.mockImageDrawer = new MockImageDrawer();
    this.mockHistogram = new MockHistogram();
  }

  /**
   * Setup method called before each test, creating a new instance of HistogramDrawerImpl with
   * specified dimensions and a mock image drawer. Clears loggers in MockHistogram and
   * MockImageDrawer.
   */
  @Before
  public void setUp() {

    histogramDrawer = new HistogramDrawerImpl(256, 256, mockImageDrawer);
    mockHistogram.clearLogger();
    mockImageDrawer.clearLogger();
  }

  /**
   * Test method for the visualizeHistogram function with a valid histogram. Verifies the dimensions
   * of the resulting array.
   */
  @Test
  public void testVisualizeHistogram() {

    mockImageDrawer.setFailureFlag(false);
    mockHistogram.setFailureFlag(false);
    float[][][] result = histogramDrawer.visualizeHistogram(mockHistogram);
    assertEquals(256, result.length);
    assertEquals(256, result[0].length);
    assertEquals(3, result[0][0].length);
  }

  /**
   * Test method for the visualizeHistogram function with an invalid histogram. Expects an
   * IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVisualizeHistogramInvalidHistogram() {

    mockImageDrawer.setFailureFlag(false);
    mockHistogram.setFailureFlag(true);
    float[][][] result = histogramDrawer.visualizeHistogram(mockHistogram);
    assertEquals(256, result.length);
    assertEquals(256, result[0].length);
    assertEquals(3, result[0][0].length);
  }

  /**
   * Test method for the visualizeHistogram function with an invalid image drawer. Expects an
   * IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVisualizeHistogramInvalidimageDrawer() {
    mockImageDrawer.setFailureFlag(true);
    mockHistogram.setFailureFlag(false);
    float[][][] result = histogramDrawer.visualizeHistogram(mockHistogram);
    assertEquals(256, result.length);
    assertEquals(256, result[0].length);
    assertEquals(3, result[0][0].length);
  }

}