package model;

import controller.filter.Filter;

public class MockImageV2 implements ImageV2 {
  private StringBuilder log;
  private int uniqueCode;

  public MockImageV2(StringBuilder log, int uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public ImageV2 compress(int ratio) {
    log.append(String.format("Inside compress with ratio of: %d.\n", ratio));
    return null;
  }

  @Override
  public void colorCorrect() {
    log.append("Inside color correct.");
  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {
    log.append(String.format("Inside levelsAdjust with black = %d, mid = %d, white = %d.\n",
            black, mid, white));
  }

  @Override
  public ImageV2[] splitImage(int ratio) {
    log.append(String.format("Inside splitImage with ratio of: %d.\n", ratio));
    return null;
  }

  @Override
  public void mergeRight(ImageV2 img) {
    log.append(String.format("Inside of mergeRight with img height = %d and width = %d.\n",
            img.getHeight(), img.getWidth()));
  }

  @Override
  public int getHeight() {
    log.append("Inside getHeight. \n");
    return uniqueCode;
  }

  @Override
  public int getWidth() {
    log.append("Inside getWidth. \n");
    return uniqueCode;
  }

  @Override
  public int getPackedPixel(int i, int j) {
    log.append(String.format("Inside getPackedPixel with i = %d, j = %d. \n", i, j));
    return uniqueCode;
  }

  @Override
  public ImageV1 deepCopy() {
    log.append("Inside deepCopy. \n");
    return null;
  }

  @Override
  public void applyColorFilter(Filter filter) {

  }

  @Override
  public void applyImageFilter(Filter filter) {

  }

  @Override
  public void valueComponent() {
    log.append("Inside valueComponent. \n");
  }

  @Override
  public void brighten(int val) {
    log.append(String.format("Inside brighten with val = %d. \n", val));
  }

  @Override
  public void horizontalFlip() {
    log.append("Inside horizontalFlip. \n");
  }

  @Override
  public void verticalFlip() {
    log.append("Inside verticalFlip. \n");
  }

  @Override
  public ImageV1[] splitComponents() {
    log.append("Inside splitComponents. \n");
    return null;
  }

  @Override
  public void combineComponents(ImageV1 img) {
    log.append(String.format("Inside combineComponents with img height = %d and width = %d. \n",
            img.getHeight(), img.getWidth()));
  }
}
