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
  private int uniqueCode;

  public MockImage(StringBuilder log, int uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void compress(int ratio) {
    log.append(String.format("Inside compress with ratio of: %d.\n", ratio));
  }

  @Override
  public void colorCorrect() {
    log.append("Inside color correct.");
  }

  @Override
  public ImageModel histogram() {
return null;
  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {
    log.append(String.format("Inside levelsAdjust with black = %d, mid = %d, white = %d.\n",
            black, mid, white));
  }

  @Override
  public ImageModel[] splitImage(int ratio) {
    log.append(String.format("Inside splitImage with ratio of: %d.\n", ratio));
    return null;
  }

  @Override
  public void mergeSplits(ImageModel img) {
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
  public int getRed(int i, int j) {
    return 0;
  }

  @Override
  public int getGreen(int i, int j) {
    return 0;
  }

  @Override
  public int getBlue(int i, int j) {
    return 0;
  }

  @Override
  public int getPackedPixel(int i, int j) {
    log.append(String.format("Inside getPackedPixel with i = %d, j = %d. \n", i, j));
    return uniqueCode;
  }

  @Override
  public Pixel getPixel(int i, int j) {
    return null;
  }

  @Override
  public ImageModel deepCopy() {
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
  public ImageModel[] splitComponents() {
    log.append("Inside splitComponents. \n");
    return null;
  }

  @Override
  public void combineComponents(ImageModel img) {
    log.append(String.format("Inside combineComponents with img height = %d and width = %d. \n",
            img.getHeight(), img.getWidth()));
  }
}
