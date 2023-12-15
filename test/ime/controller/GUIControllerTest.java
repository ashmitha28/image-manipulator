package ime.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * test class for testing the gui controller.
 */
public class GUIControllerTest {

  private MockGUIView mockGUIView;
  private GUIController guiController;
  private MockImgRepo mockImgRepo;

  /**
   * to setup and initialise the mock gui object.
   */
  @Before
  public void setup() {
    mockGUIView = new MockGUIView();
    mockImgRepo = new MockImgRepo();
    guiController = new GUIController(mockImgRepo, mockGUIView, new FileHandlerProviderImpl());
  }

  /**
   * to test the execute method of the controller.
   */
  @Test
  public void testExecute() {
    guiController.execute();
    assertEquals("features has been set", mockGUIView.getLogger());
    assertEquals("", mockImgRepo.getLogger());
  }

}