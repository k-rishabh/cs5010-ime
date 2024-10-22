package model;

import java.io.File;
import java.io.IOException;

public interface ImageADT {

  void valueComponent();

  void intensityComponent();

  void lumaComponent();

  void horizontalFlip();

  void verticalFlip();

  void brighten(int val);

  Image[] split();

  void blur(double[][] filter);

  void sharpen(double[][] filter);

  void sepia();

  int getHeight();

  int getWidth();

  PixelADT getPixel(int i, int j);

  void setPixel(int i, int j, PixelADT pixel);
}
