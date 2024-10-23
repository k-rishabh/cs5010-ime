package model.pixel;

public interface PixelADT {
  void brighten(int val);

  int[] applyFilter(double val);

  void applySepia();

  void applyRedTint();

  void applyGreenTint();

  void applyBlueTint();

  void showValue();

  void showIntensity();

  void showLuma();

  PixelADT deepCopy();

  int getRed();

  int getGreen();

  int getBlue();
}
