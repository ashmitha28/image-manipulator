package ime.model;

import ime.controller.ImageDrawer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * This implementation of {@link ImageRepository} stores multiple images as a map between the tagged
 * name of the image to its actual {@link Image} object.
 */
public class ImageRepositoryImpl implements ImageRepository {

  /**
   * map for storing the image with its name as the key.
   */
  private final Map<String, Image> imageMap;

  public ImageRepositoryImpl() {
    imageMap = new HashMap<>();
  }


  @Override
  public void loadImage(float[][][] imagePixels, String imageName) {
    Image image = new ImagePixelImpl(imagePixels, ImageType.RGB);
    imageMap.put(imageName, image);
  }

  @Override
  public float[][][] getImage(String imageName) {
    validateImagePresent(imageName);
    Image image = imageMap.get(imageName);
    int height = image.getHeight();
    int width = image.getWidth();
    int channelCount = image.getChannelCount();
    float[][][] result = new float[height][width][channelCount];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        float[] pixelValues = image.getPixelValues(i, j);
        System.arraycopy(pixelValues, 0, result[i][j], 0, channelCount);
      }
    }
    return result;
  }


  @Override
  public void splitImageIntoColorChannels(String srcImage, List<String> destImageNames)
      throws IllegalArgumentException {
    validateImagePresent(srcImage);
    List<Image> destImages = imageMap.get(srcImage).splitIntoColorChannels();
    for (int i = 0; i < destImages.size(); i++) {
      imageMap.put(destImageNames.get(i), destImages.get(i));
    }
  }

  @Override
  public void combineImages(List<String> srcImageNames, String imageDestName)
      throws IllegalArgumentException {
    validateImagesPresent(srcImageNames);
    List<Image> srcImageList = new ArrayList<>();
    Image firstSrcImage = imageMap.get(srcImageNames.get(0));
    for (int i = 1; i < srcImageNames.size(); i++) {
      srcImageList.add(imageMap.get(srcImageNames.get(i)));
    }
    Image newImage = firstSrcImage.combine(srcImageList);
    imageMap.put(imageDestName, newImage);
  }

  @Override
  public void brightenImage(String imageNameSrc, String imageNameDest, float brightnessConstant)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).brighten(brightnessConstant);
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void blurImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).blur();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void sharpenImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).sharpen();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void flipImageHorizontally(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).flipHorizontally();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void flipImageVertically(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).flipVertically();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toIntensityGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getIntensityImage();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toLumaGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getLumaImage();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toValueGreyScale(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getValueImage();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toSepiaImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getSepia();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toRedChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getRedComponent();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toGreenChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getGreenComponent();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void toBlueChannelImage(String imageNameSrc, String imageNameDest)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).getBlueComponent();
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public boolean isImagePresent(String imageName) {
    return imageMap.containsKey(imageName);
  }

  @Override
  public void compress(String imageNameSrc, String imageNameDest, int compressPercent)
      throws IllegalArgumentException {
    validateImagePresent(imageNameSrc);
    Image newImage = imageMap.get(imageNameSrc).compress(compressPercent);
    imageMap.put(imageNameDest, newImage);
  }

  @Override
  public void preview(String imageNameSrc, String imageNameDest,
      BiConsumer<String, String> operation, int verticalSplit) {
    if (verticalSplit < 0 || verticalSplit > 100) {
      throw new IllegalArgumentException("Invalid split position");
    }
    validateImagePresent(imageNameSrc);
    List<Image> images = imageMap.get(imageNameSrc).splitVertically(verticalSplit);
    //If the vertical split provides empty left part and whole image right part
    if (images.get(0) == null) {
      imageMap.put(imageNameDest, images.get(1));
      return;
    }
    addOperatedImagePart(imageNameDest, operation, images);
  }

  private void addOperatedImagePart(String imageNameDest, BiConsumer<String, String> operation,
      List<Image> images) {
    String tempKey = getTempKey();
    imageMap.put(tempKey, images.get(0));
    operation.accept(tempKey, tempKey);
    //If the vertical split provided empty right part and whole image left part(now operated)
    if (images.get(1) == null) {
      imageMap.put(imageNameDest, imageMap.get(tempKey));
    } else {
      imageMap.put(imageNameDest, imageMap.get(tempKey).append(images.get(1)));
    }
    imageMap.remove(tempKey);
  }

  /* This method returns a temporary key that does not already exist in the imageMap */
  private String getTempKey() {
    String tempKey = UUID.randomUUID().toString();
    // Check if the key exists in the HashMap
    while (isImagePresent(tempKey)) {
      tempKey = UUID.randomUUID().toString();
    }
    return tempKey;
  }

  @Override
  public void levelsAdjust(String imageNameSrc, String destImage, int b, int m, int w) {
    validateImagePresent(imageNameSrc);
    Image image = imageMap.get(imageNameSrc).levelAdjust(b, m, w);
    imageMap.put(destImage, image);
  }

  private int calculateAveragePeakValue(Image image) {
    int sumPeakValue = 0;
    HistogramImpl hist = new HistogramImpl(image);
    for (int channelIndex = 0; channelIndex < hist.getChannelCount(); channelIndex++) {
      sumPeakValue += hist.getMostFrequentValue(channelIndex, 10, 245);
    }
    return sumPeakValue / hist.getChannelCount();
  }


  @Override
  public void colorCorrect(String imageNameSrc, String imageNameDest) {
    validateImagePresent(imageNameSrc);
    Image image = imageMap.get(imageNameSrc);
    HistogramImpl hist = new HistogramImpl(image);

    int averagePeakValue = calculateAveragePeakValue(image);
    List<Image> limages = new ArrayList<>();

    // Calculate brightness adjustment for each channel
    float[] brightnessAdjustment = new float[image.getChannelCount()];
    for (int channelIndex = 0; channelIndex < image.getChannelCount(); channelIndex++) {
      int currentPeakValue = hist.getMostFrequentValue(channelIndex, 10, 245);
      int peakDifference = averagePeakValue - currentPeakValue;
      brightnessAdjustment[channelIndex] = peakDifference;
      limages.add(image.brighten(brightnessAdjustment[channelIndex]));
    }
    Image newimage = limages.get(0);
    limages.remove(0);
    imageMap.put(imageNameDest, newimage.combine(limages));
  }

  @Override
  public void toHistogram(String imageNameSrc, String imageNameDest, ImageDrawer imageDrawer) {
    validateImagePresent(imageNameSrc);
    Histogram histogram = new HistogramImpl(imageMap.get(imageNameSrc));
    float[][][] histogramImage = new HistogramDrawerImpl(256, 256, imageDrawer).visualizeHistogram(
        histogram);
    Image newImage = new ImagePixelImpl(histogramImage, ImageType.RGB);

    imageMap.put(imageNameDest, newImage);
  }

  private void validateImagePresent(String imageName) throws IllegalArgumentException {
    if (!this.isImagePresent(imageName)) {
      throw new IllegalArgumentException("image name invalid");
    }
  }

  private void validateImagesPresent(List<String> srcImageNames) throws IllegalArgumentException {
    srcImageNames.forEach(this::validateImagePresent);
  }
}
