package model;

import model.pixel.PixelADT;

public interface ImageADT {

  void valueComponent();

  void intensityComponent();

  void lumaComponent();

  void horizontalFlip();

  void verticalFlip();

  void brighten(int val);

  Image[] split();

  void blur();

  void sharpen();

  void sepia();

  int getHeight();

  int getWidth();

  PixelADT getPixel(int i, int j);
//
//  void setPixel(int i, int j, PixelADT pixel);

  Image deepCopy();

  Image combine();
}
