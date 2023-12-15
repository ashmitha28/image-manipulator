package ime.controller;

import java.io.IOException;

/**
 * A mock implementation of the {@link FileHandlerProvider} interface used for testing purposes.
 * This class allows tracking method calls and simulating failures for providing file handlers.
 */
public class MockFileHandlerProvider implements FileHandlerProvider {

  private StringBuilder methodCallLogger;
  private Boolean fail;
  private final MockFileHandler mockFileHandler;

  /**
   * Creates a new instance of the MockFileHandlerProvider.
   *
   * @param mockFileHandler The mock file handler to be provided by this provider.
   */
  public MockFileHandlerProvider(MockFileHandler mockFileHandler) {
    this.methodCallLogger = new StringBuilder();
    this.fail = true;
    this.mockFileHandler = mockFileHandler;
  }

  /**
   * Provides a mock file handler for a given file type and logs the method call.
   *
   * @param fileType The type or identifier for the file handler.
   * @return A mock file handler associated with the specified file type.
   * @throws IOException If an error occurs during the file handler provider process or if the
   *                     MockFileHandlerProvider is set to fail.
   */
  @Override
  public FileHandler getFileHandler(String fileType) throws IOException {
    methodCallLogger.append("loadImage called " + fileType + " passed\n");
    if (fail) {
      throw new IOException("fileHandler provider failed");
    }

    return mockFileHandler;
  }

  /**
   * Get a log of method calls made to this MockFileHandlerProvider instance.
   *
   * @return A string containing the log of method calls.
   */
  public String getLogger() {
    return methodCallLogger.toString();
  }

  /**
   * Clear the log of method calls made to this MockFileHandlerProvider instance.
   */
  public void clearLogger() {
    methodCallLogger = new StringBuilder();
  }

  /**
   * Set the failure flag for the MockFileHandlerProvider.
   *
   * @param failFlag A boolean flag indicating whether the MockFileHandlerProvider should simulate
   *                 failures.
   */
  public void setFailureFlag(Boolean failFlag) {
    this.fail = failFlag;
  }
}
