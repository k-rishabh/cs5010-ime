package model.pixel;

public interface PixelADT {

  int getPacked();

  PixelADT deepCopy();

  void showRed();

  void showGreen();

  void showBlue();

  void showValue();

  void showIntensity();

  void showLuma();

  void brighten(int val);

  void applyRedTint();

  void applyGreenTint();

  void applyBlueTint();

  void addComponent(PixelADT pixel);

  int[] applyFilter(double val);

  void applySepia();

}
