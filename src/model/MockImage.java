package model;

import controller.filter.Filter;
import model.pixel.Pixel;

/**
 * A mock implementation of the model used for testing the controller.
 * It documents which functions are called in StringBuilder log.
 * It may also return a uniqueCode that it is initialized with, if return type is int.
 */
public class MockImage implements ImageModel {
  private StringBuilder log;

  public MockImage(StringBuilder log) {
    this.log = log;
  }

  @Override
  public int getHeight() {
    return 1;
  }

  @Override
  public int getWidth() {
    return 1;
  }

  @Override
  public Pixel getPixel(int i, int j) {
    return null;
  }

  @Override
  public ImageModel deepCopy() {
    return this;
  }

  @Override
  public void applyColorFilter(Filter filter) {
    log.append(String.format("In applyColorFilter with filter %s.\n", filter.getClass().getName()));
  }

  @Override
  public void applyImageFilter(Filter filter) {
    log.append(String.format("In applyImageFilter with filter %s.\n", filter.getClass().getName()));
  }

  @Override
  public void valueComponent() {
    log.append("In valueComponent.\n");
  }

  @Override
  public void brighten(int val) {
    log.append(String.format("In brighten with value %d.\n", val));
  }

  @Override
  public void horizontalFlip() {
    log.append("In horizontalFlip.\n");
  }

  @Override
  public void verticalFlip() {
    log.append("In verticalFlip.\n");
  }

  @Override
  public ImageModel[] splitComponents() {
    log.append("In splitComponents.\n");
    return null;
  }

  @Override
  public void combineComponents(ImageModel img) {
    log.append("In combineComponents.\n");
  }

  @Override
  public void compress(int ratio) {
    log.append(String.format("In compress with ratio %d.\n", ratio));
  }

  @Override
  public void colorCorrect() {
    log.append("In colorCorrect.\n");
  }

  @Override
  public ImageModel histogram() {
    log.append("In histogram.\n");
    return null;
  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {
    log.append(String.format("In levelsAdjust with black %d, mid %d, white %d.\n",
            black, mid, white));
  }

  @Override
  public ImageModel[] splitImage(int ratio) {
    log.append(String.format("In splitImage with ratio %d.\n", ratio));
    return null;
  }

  @Override
  public void mergeSplits(ImageModel img) {
    log.append("In mergeSplits.\n");
  }

  @Override
  public void downscale(int newHeight, int newWidth) {
    log.append(String.format("In downscale with height %d and width %d.\n", newHeight, newWidth));
  }

  @Override
  public ImageModel applyMasking(ImageModel maskImage, ImageModel operatedImage) {
    return null;
  }
}
