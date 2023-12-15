package ime.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ime.model.ImageConstants.BLUR_FILTER;
import static ime.model.ImageConstants.SEPIA_TRANSFORMER;
import static ime.model.ImageConstants.SHARPEN_FILTER;

/**
 * This implementation of {@link ImagePixelImpl} stores width x height number of pixels and has an
 * associated image type to it which can be any one of the types listed in {@link ImageType}.
 */
public class ImagePixelImpl implements Image {

  private final int width;
  private final int height;

  private final ImageType imageType;

  private final Pixel[][] pixels;

  /**
   * This constructor initializes the {@link ImagePixelImpl} using a 2D pixel array.
   *
   * @param pixelValues the 2D pixel array that makes up the image
   * @param imageType   the type of this image
   */
  public ImagePixelImpl(Pixel[][] pixelValues, ImageType imageType) {
    if (!(pixelValues.length > 0 && pixelValues[0].length > 0)) {
      throw new IllegalArgumentException("Image should contain at least one pixel");
    }
    this.imageType = imageType;
    height = pixelValues.length;
    width = pixelValues[0].length;
    pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        setPixelValue(this.pixels, i, j, pixelValues[i][j].getChannelValues());
      }
    }
  }

  /**
   * This constructor initializes the {@link ImagePixelImpl} using a 2D array of float[] that will
   * be used to generate the 2D array of pixels that make up this image.
   *
   * @param pixelValues the 2D float[] array that makes up the image
   * @param imageType   the type of this image
   */
  public ImagePixelImpl(float[][][] pixelValues, ImageType imageType) {
    if (!(pixelValues.length > 0 && pixelValues[0].length > 0)) {
      throw new IllegalArgumentException("Image should contain at least one pixel");
    }
    this.imageType = imageType;
    width = pixelValues[0].length;
    height = pixelValues.length;
    pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        setPixelValue(this.pixels, i, j, pixelValues[i][j]);
      }
    }
  }

  /**
   * getter for width.
   *
   * @return integer width.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * getter height.
   *
   * @return integer height.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * split into color channels.
   *
   * @return list of image channels.
   */
  @Override
  public List<Image> splitIntoColorChannels() {
    List<Image> result = new ArrayList<>();
    int channelCount = this.pixels[0][0].getColorChannelCount();

    for (int i = 0; i < channelCount; i++) {
      result.add(toChannel(i));
    }
    return result;
  }

  @Override
  public Image combine(List<Image> images) {
    if (images.size() != this.getChannelCount() - 1) {
      throw new IllegalArgumentException("Invalid number of images");
    }

    Pixel[][] resultPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float[] pixelValues = new float[this.getChannelCount()];
        pixelValues[0] = this.pixels[i][j].getChannelValues()[0];
        for (int k = 1; k < this.getChannelCount(); k++) {
          pixelValues[k] = images.get(k - 1).getPixelValues(i, j)[k];
        }
        setPixelValue(resultPixels, i, j, pixelValues);
      }
    }

    return new ImagePixelImpl(resultPixels, imageType);
  }

  @Override
  public Image brighten(float brightnessConstant) {
    Pixel[][] resultPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = pixels[i][j].brighten(brightnessConstant);
      }
    }
    return new ImagePixelImpl(resultPixels, imageType);
  }

  @Override
  public Image blur() {
    return applyFilter(BLUR_FILTER);
  }

  @Override
  public Image sharpen() {
    return applyFilter(SHARPEN_FILTER);
  }

  @Override
  public Image flipHorizontally() {
    Pixel[][] resultPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        setPixelValue(resultPixels, i, j, pixels[i][width - j - 1].getChannelValues());
      }
    }
    return new ImagePixelImpl(resultPixels, imageType);
  }

  @Override
  public Image flipVertically() {
    Pixel[][] resultPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        setPixelValue(resultPixels, i, j, pixels[height - i - 1][j].getChannelValues());
      }
    }
    return new ImagePixelImpl(resultPixels, imageType);
  }

  @Override
  public float[] getPixelValues(int row, int col) {
    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IllegalArgumentException("Pixel location invalid");
    }
    return pixels[row][col].getChannelValues();
  }


  @Override
  public Image getIntensityImage() {

    return getGreyscaleImage(Pixel::getIntensity);
  }

  @Override
  public Image getLumaImage() {
    return getGreyscaleImage(Pixel::getLuma);
  }

  @Override
  public Image getValueImage() {
    return getGreyscaleImage(Pixel::getValue);
  }

  private Image getGreyscaleImage(Function<Pixel, Float> component) {
    Pixel[][] resultPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float characteristic = component.apply(pixels[i][j]);
        float[] greyscale = new float[pixels[i][j].getColorChannelCount()];
        Arrays.fill(greyscale, characteristic);
        setPixelValue(resultPixels, i, j, greyscale);
      }
    }
    return new ImagePixelImpl(resultPixels, imageType);
  }

  @Override
  public Image getSepia() {
    return performColorTransformation(SEPIA_TRANSFORMER);
  }

  @Override
  public int getChannelCount() {
    return pixels[0][0].getColorChannelCount();
  }

  @Override
  public Image getRedComponent() {
    int index = getColorChannelIndex(ColorChannel.RED);
    return toChannel(index);
  }

  @Override
  public Image getGreenComponent() {
    int index = getColorChannelIndex(ColorChannel.GREEN);
    return toChannel(index);
  }

  @Override
  public Image getBlueComponent() {
    int index = getColorChannelIndex(ColorChannel.BLUE);
    return toChannel(index);
  }

  @Override
  public Image compress(int compressPercent) {

    if (compressPercent < 0 || compressPercent > 100) {
      throw new IllegalArgumentException("Compress percentage invalid");
    }
    float[][][] resultPixels = getPaddedPixels();
    haarTransform(resultPixels);
    applyThreshold(compressPercent, resultPixels);
    invHaar(resultPixels);
    return new ImagePixelImpl(removePad(resultPixels), imageType);
  }


  @Override
  public List<Image> splitVertically(int splitPercent) {
    int splitPosition = Math.round(splitPercent * width / 100f);
    if (splitPosition <= 0) {
      return Arrays.asList(null, new ImagePixelImpl(pixels, imageType));
    } else if (splitPosition >= width) {
      return Arrays.asList(new ImagePixelImpl(pixels, imageType), null);
    }
    Pixel[][] leftImagePixels = new Pixel[height][splitPosition];
    Pixel[][] rightImagePixels = new Pixel[height][width - splitPosition];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j >= splitPosition) {
          setPixelValue(rightImagePixels, i, j - splitPosition, pixels[i][j].getChannelValues());
        } else {
          setPixelValue(leftImagePixels, i, j, pixels[i][j].getChannelValues());
        }
      }
    }
    Image leftImage = new ImagePixelImpl(leftImagePixels, imageType);
    Image rightImage = new ImagePixelImpl(rightImagePixels, imageType);

    return Arrays.asList(leftImage, rightImage);
  }

  @Override
  public Image append(Image image) {
    if (image.getHeight() != height) {
      throw new IllegalArgumentException("The given image cannot be appended to this image");
    }
    Pixel[][] newImagePixels = new Pixel[height][width + image.getWidth()];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width + image.getWidth(); j++) {
        if (j < width) {
          setPixelValue(newImagePixels, i, j, pixels[i][j].getChannelValues());
        } else {
          setPixelValue(newImagePixels, i, j, image.getPixelValues(i, j - width));
        }
      }
    }
    return new ImagePixelImpl(newImagePixels, imageType);
  }

  @Override
  public ImageType getImageType() {
    return imageType;
  }

  @Override
  public Image levelAdjust(int b, int m, int w) throws IllegalArgumentException {
    if (b > m || m > w || b < 0 || w > 255) {
      throw new IllegalArgumentException("invalid b/m/w values");
    }
    float[] coefficients = compute(b, m, w);
    float[][][] resultPixels = new float[height][width][getChannelCount()];
    adjustLevelsUsingCoefficients(b, w, resultPixels, coefficients);
    return new ImagePixelImpl(resultPixels, imageType);
  }

  private void adjustLevelsUsingCoefficients(int b, int w, float[][][] resultPixels,
                                             float[] coefficients) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float[] x = getPixelValues(i, j);
        for (int k = 0; k < getChannelCount(); k++) {
          if (x[k] <= b) {
            resultPixels[i][j][k] = 0;
          } else if (x[k] >= w) {
            resultPixels[i][j][k] = 255;
          } else {
            resultPixels[i][j][k] =
                    coefficients[0] * x[k] * x[k] + coefficients[1] * x[k] + coefficients[2];
          }
        }
      }
    }
  }

  private float[] compute(int b, int m, int w) {
    float[] coefficients = new float[3];
    float equationA = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    float equationA_a = -b * (128 - 255) + 128 * w - 255 * m;
    float equationA_b = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    float equationA_c = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);
    coefficients[0] = equationA_a / equationA;
    coefficients[1] = equationA_b / equationA;
    coefficients[2] = equationA_c / equationA;

    return coefficients;
  }

  private float[][][] removePad(float[][][] pixels) {
    float[][][] result = new float[height][width][getChannelCount()];
    for (int c = 0; c < getChannelCount(); c++) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          result[i][j][c] = Math.max(0, Math.min(255, pixels[i][j][c]));
        }
      }
    }
    return result;
  }

  private void applyThreshold(float compressPercent, float[][][] transformed) {
    // think abt cases
    if (compressPercent == 0) {
      return;
    }

    compressPercent = compressPercent / 100;
    SortedSet<Float> uniqueElementSet = getUniqueElements(transformed);
    List<Float> sortedElementList = uniqueElementSet.stream().collect(Collectors.toList());
    int num = (int) (compressPercent * sortedElementList.size());
    float threshold = sortedElementList.get(num - 1);
    for (int i = 0; i < getChannelCount(); i++) {
      for (int m = 0; m < transformed.length; m++) {
        for (int n = 0; n < transformed[0].length; n++) {
          if ((Math.abs(transformed[m][n][i])) <= threshold) {
            transformed[m][n][i] = 0;
          }
        }
      }
    }
  }

  private SortedSet<Float> getUniqueElements(float[][][] transformedImage) {
    SortedSet<Float> arr = new TreeSet<>();
    for (int i = 0; i < getChannelCount(); i++) {
      for (int m = 0; m < transformedImage.length; m++) {
        for (int n = 0; n < transformedImage.length; n++) {
          arr.add(Math.abs(transformedImage[m][n][i]));
        }
      }
    }
    return arr;
  }


  private void haarTransform(float[][][] pixelsToBeTransformed) {
    for (int a = 0; a < getChannelCount(); a++) {
      int c = pixelsToBeTransformed.length; // Find the maximum dimension
      while (c > 1) {
        applyRowTransformation(pixelsToBeTransformed, c, a, this::transform);
        applyColumnTransformation(pixelsToBeTransformed, c, a, this::transform);
        c = c / 2;
      }
    }
  }

  private List<Float> extractCol(float[][][] pixelsToBeTransformed, int j, int c, int a) {
    List<Float> result = new ArrayList<>();
    for (int i = 0; i < c; i++) {
      result.add(pixelsToBeTransformed[i][j][a]);
    }
    return result;
  }

  private List<Float> extractRow(float[][] pixels, int c, int a) {
    List<Float> result = new ArrayList<>();
    for (int i = 0; i < c; i++) {
      result.add(pixels[i][a]);
    }
    return result;
  }

  private void invHaar(float[][][] pixelsTransformed) {

    for (int a = 0; a < getChannelCount(); a++) {
      int c = 2;
      while (c <= pixelsTransformed.length) {
        applyColumnTransformation(pixelsTransformed, c, a, this::invTransform);
        applyRowTransformation(pixelsTransformed, c, a, this::invTransform);
        c = c * 2;
      }
    }
  }

  private void applyRowTransformation(float[][][] pixelsTransformed, int c, int a,
                                      Function<List<Float>, List<Float>> transformFunction) {
    for (int i = 0; i < c; i++) {
      List<Float> rowValues = extractRow(pixelsTransformed[i], c, a);
      List<Float> transformed = transformFunction.apply(rowValues);
      for (int j = 0; j < c; j++) {
        pixelsTransformed[i][j][a] = transformed.get(j);
      }
    }
  }

  private void applyColumnTransformation(float[][][] pixelsTransformed, int c, int a,
                                         Function<List<Float>, List<Float>> transformFunction) {
    for (int j = 0; j < c; j++) {
      List<Float> colValues = extractCol(pixelsTransformed, j, c, a);
      List<Float> transformed = transformFunction.apply(colValues);
      for (int i = 0; i < c; i++) {
        pixelsTransformed[i][j][a] = transformed.get(i);
      }
    }
  }

  private float[][][] getPaddedPixels() {
    int n = Math.max(height, width);
    int paddedSize = 1;
    while (paddedSize < n) {
      paddedSize *= 2;
    }

    float[][][] result = new float[paddedSize][paddedSize][getChannelCount()];
    for (int i = 0; i < paddedSize; i++) {
      for (int j = 0; j < paddedSize; j++) {
        if ((i >= height) || (j >= width)) {
          result[i][j] = new float[getChannelCount()];
        } else {
          result[i][j] = pixels[i][j].getChannelValues();
        }
      }
    }
    return result;
  }

  private List<Float> transform(List<Float> arr) {
    int n = arr.size();
    List<Float> result = new ArrayList<>(Collections.nCopies(n, 0f));

    for (int i = 0; i < n / 2; i = i + 1) {
      int sumIndex = i * 2;
      float a = arr.get(sumIndex);
      float b = arr.get(sumIndex + 1);
      // Compute the sum and difference coefficients
      result.set(i, (float) ((a + b) / Math.sqrt(2)));
      result.set(n / 2 + i, (float) ((a - b) / Math.sqrt(2)));
    }

    return result;
  }


  private List<Float> invTransform(List<Float> arr) {
    int n = arr.size() / 2;
    double[] avg = new double[n];
    double[] diff = new double[n];

    int j = n;
    for (int i = 0; i < n; i++, j++) {
      float a = arr.get(i);
      float b = arr.get(j);
      double av = (a + b) / Math.sqrt(2);
      double de = (a - b) / Math.sqrt(2);

      avg[i] = av;
      diff[i] = de;
    }

    List<Float> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      result.add((float) avg[i]);
      result.add((float) diff[i]);
    }

    return result;
  }

  private int getColorChannelIndex(ColorChannel colorChannel) {
    int index = imageType.colorChannels.indexOf(colorChannel);
    if (index < 0) {
      throw new IllegalArgumentException("red component can not be obtained for the given image");
    }
    return index;
  }

  /**
   * Given a matrix of coefficients for the color channels, return an image that is the color
   * transformed version of this image.
   *
   * @param transformCoefficients the matrix containing the coefficients for the color channels
   * @return the Image after color transformation
   */
  private Image performColorTransformation(float[][] transformCoefficients) {
    Pixel[][] resultPixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = pixels[i][j].transformPixel(transformCoefficients);
      }
    }
    return new ImagePixelImpl(resultPixels, imageType);
  }

  /**
   * Given a filter, apply it to the image and return the result which is a new image.
   *
   * @param filter the filter to be applied.
   * @return the image result after performing the filter on the original image.
   */
  private Image applyFilter(float[][] filter) {
    Pixel[][] resultPixel = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float[] filterValues = new float[this.getChannelCount()];
        for (int k = 0; k < this.getChannelCount(); k++) {
          float sum = getConvolutionProduct(i, j, filter, k);

          filterValues[k] = Math.max(0, Math.min(255, sum));
        }
        setPixelValue(resultPixel, i, j, filterValues);
      }
    }
    return new ImagePixelImpl(resultPixel, imageType);
  }

  /**
   * For a given coordinate (i,j), find the result of convolution between this image and the given
   * filter centred at (i,j).
   */
  private float getConvolutionProduct(int i, int j, float[][] filter, int channel) {
    int filterHeight = filter.length;
    int filterWidth = filter[0].length;

    int leftOffset = Math.max(0, j - filterWidth / 2) - (j - filterWidth / 2);
    int rightOffset = (j + filterWidth / 2) - Math.min(width - 1, j + filterWidth / 2);
    int topOffset = Math.max(0, i - filterHeight / 2) - (i - filterHeight / 2);
    int bottomOffset = (i + filterHeight / 2) - Math.min(height - 1, i + filterHeight / 2);

    float sum = 0;

    for (int m = topOffset; m < filterHeight - bottomOffset; m++) {
      for (int n = leftOffset; n < filterWidth - rightOffset; n++) {
        sum += filter[m][n] * pixels[i - (filterHeight / 2) + m][j - (filterWidth / 2) + n]
                .getChannelValues()[channel];
      }
    }

    return sum;
  }

  /**
   * For a given channel index of pixels, return a new image that contains this image's values for
   * that channel and 0 as values for every other channel.
   */
  private Image toChannel(int channel) {

    int channelCount = this.pixels[0][0].getColorChannelCount();
    if (channel >= channelCount || channel < 0) {
      throw new IllegalArgumentException("Invalid channel provided");
    }
    Pixel[][] resultPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        resultPixels[i][j] = imageType.generatePixel();
        resultPixels[i][j].setColorChannel(channel, this.pixels[i][j].getChannelValue(channel));
      }
    }

    return new ImagePixelImpl(resultPixels, imageType);
  }

  /**
   * This method is used to generate a pixel for the given image type and assign the values given to
   * the coordinates of the resulting Image.
   **/
  private void setPixelValue(Pixel[][] resultPixels, int i, int j, float[] channelValues) {
    resultPixels[i][j] = imageType.generatePixel();
    resultPixels[i][j].setColor(channelValues);
  }

}
