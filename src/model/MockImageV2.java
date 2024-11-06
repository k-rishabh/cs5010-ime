package model;

public class MockImageV2 implements ImageV2 {
  @Override
  public ImageV2 compress(int ratio) {
    return null;
  }

  @Override
  public ImageV2 decompress() {
    return null;
  }

  @Override
  public void colorCorrect() {

  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {

  }

  @Override
  public ImageV2[] splitImage(int ratio) {
    return new ImageV2[0];
  }

  @Override
  public void mergeRight(ImageV2 img) {

  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getPackedPixel(int i, int j) {
    return 0;
  }

  @Override
  public ImageV1 deepCopy() {
    return null;
  }

  @Override
  public void redComponent() {

  }

  @Override
  public void greenComponent() {

  }

  @Override
  public void blueComponent() {

  }

  @Override
  public void valueComponent() {

  }

  @Override
  public void intensityComponent() {

  }

  @Override
  public void lumaComponent() {

  }

  @Override
  public void horizontalFlip() {

  }

  @Override
  public void verticalFlip() {

  }

  @Override
  public void brighten(int val) {

  }

  @Override
  public ImageV1[] splitComponents() {
    return new ImageV1[0];
  }

  @Override
  public void combineComponents(ImageV1 img) {

  }

  @Override
  public void blur() {

  }

  @Override
  public void sharpen() {

  }

  @Override
  public void sepia() {

  }
}
