package ime.controller;

import java.io.IOException;

/**
 * FileHandlerProvider provides a factory method to get different implementations of
 * {@link FileHandler} depending on the input passed.
 */
public interface FileHandlerProvider {

  /**
   * This is a factory method to return an implementation of PhysicsBall depending on the input
   * provided.
   *
   * @param fileType Input determining the type of file handler to be returned
   * @return instantiated FileHandler object
   */
  FileHandler getFileHandler(String fileType) throws IOException;
}
