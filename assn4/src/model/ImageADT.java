package model;

import model.pixel.PixelADT;

public interface ImageADT {
  int getHeight();

  int getWidth();

  int getPackedPixel(int i, int j);

  Image deepCopy();

  void redComponent();

  void greenComponent();

  void blueComponent();

  void valueComponent();

  void intensityComponent();

  void lumaComponent();

  void horizontalFlip();

  void verticalFlip();

  void brighten(int val);

  Image[] split();

  void combine(Image img);

  void blur();

  void sharpen();

  void sepia();
}
