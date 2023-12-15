import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import ime.model.Image;
import ime.model.ImagePixelImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method
 * as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename) throws IOException {
    Scanner sc;
    Image test;
    try {
      test = new ImagePixelImpl(filename);
      test = test.brighten(50);
      test = test.sharpen();
      test.saveImage("./src/blur.ppm");
      test = test.sharpen();
      test = test.sharpen();
      test = test.sharpen();
      test = test.sharpen();
      test.saveImage("./src/blurred.ppm");
      test = test.flipHorizontally();
      test = test.flipVertically();
      test = test.darken(100);
      List<Image> test2 = test.splitIntoColorChannels();
      test2.get(0).saveImage("./src/outred.ppm");
      test2.get(1).saveImage("./src/outgreen.ppm");
      test2.get(2).saveImage("./src/outblue.ppm");
      test = test.getValueImage();
      test.saveImage("./src/out.ppm");
      test = test2.get(0).combine(Arrays.asList(test2.get(1), test2.get(2)));
      test.saveImage("./src/out2.ppm");
      test = test.getLumaImage();
      test.saveImage("./src/luma.ppm");
      test = test.getIntensityImage();
      test.saveImage("./src/intensity.ppm");
      test = test.getSepia();
      test.saveImage("./src/sepia.ppm");

    } catch (FileNotFoundException e) {
      System.out.println("File missing. Cannot perform the operation");
    }

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }

  //demo main
  public static void main(String[] args) throws IOException {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "images/koala.ppm";
    }

    ImageUtil.readPPM(filename);
  }
}

