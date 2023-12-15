package ime.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of the FileHandlerProvider uses a static map to keep track of the specific
 * {@link FileHandler} implementations to be used for specific input string.
 */
public class FileHandlerProviderImpl implements FileHandlerProvider {

  private static final Map<FileFormatEnum, FileHandler> FILE_FORMAT_ENUM_MAP =
      generateFileHandlerClassMap();

  private static Map<FileFormatEnum, FileHandler> generateFileHandlerClassMap() {
    Map<FileFormatEnum, FileHandler> fileHandlers = new HashMap<>();
    fileHandlers.put(FileFormatEnum.ppm, new PpmFileHandler());
    fileHandlers.put(FileFormatEnum.jpg, new CommonFileHandler());
    fileHandlers.put(FileFormatEnum.png, new CommonFileHandler());
    return fileHandlers;
  }

  /**
   * Checks with the enum map of the class to map the input string to an implementation of
   * {@link FileHandlerProvider} class.
   *
   * @param fileName Input determining the type of FileHandler to be returned
   * @return object of {@link FileHandler}
   */
  public FileHandler getFileHandler(String fileName) {
    try {
      String[] filePathComponents = fileName.split("\\.");
      fileName = filePathComponents[filePathComponents.length - 1];
    } catch (Exception e) {
      System.out.println("Extension directly provided.");
    }
    FileFormatEnum type = validateFileType(fileName);
    return FILE_FORMAT_ENUM_MAP.get(type);
  }

  /**
   * checks if the filetype is valid or not and return the file format.
   *
   * @param fileType png/jpg/ppm.
   * @return the filetype.
   */

  private FileFormatEnum validateFileType(String fileType) {
    try {
      return FileFormatEnum.valueOf(fileType);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid file format provided.");
    }
  }
}
