package ime.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * This file handler supports loading and saving of RGB files in PPM formats.
 */
public class PpmFileHandler implements FileHandler {

  @Override
  public float[][][] loadImage(String filename) throws FileNotFoundException {

    Scanner sc = this.getScanner(filename);
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
    float[][][] pixelValues = new float[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelValues[i][j][0] = sc.nextInt();
        pixelValues[i][j][1] = sc.nextInt();
        pixelValues[i][j][2] = sc.nextInt();
      }
    }
    return pixelValues;
  }

  @Override
  public void saveImage(float[][][] image, String filename) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append("P3").append(System.lineSeparator());
    builder.append(image[0].length).append(" ").append(image.length).append(System.lineSeparator());
    builder.append(findMaxValue(image)).append(System.lineSeparator());
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        float[] channelValues = image[i][j];
        for (int k = 0; k < image[i][j].length; k++) {
          builder.append((int) channelValues[k]).append(" ");
        }
        builder.append(" ");
      }
      builder.append(System.lineSeparator());
    }
    FileOutputStream fos = new FileOutputStream(filename);
    fos.write(builder.toString().getBytes());
    fos.close();

  }

  private int findMaxValue(float[][][] image) {
    int max = 0;
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {

        int red = (int) image[i][j][0];
        int green = (int) image[i][j][1];
        int blue = (int) image[i][j][2];
        int pixelValue = Math.max(Math.max(red, green), blue);
        if (pixelValue == 255) {
          return pixelValue;
        } else if (pixelValue > max) {
          max = pixelValue;
        }
      }
    }
    return max;
  }

  private Scanner getScanner(String filename) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filename));
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
    return sc;
  }
}
