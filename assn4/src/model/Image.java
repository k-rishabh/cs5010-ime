package model;

import java.io.File;
import java.io.IOException;

public abstract class Image implements ImageADT {
  protected PixelADT[][] pixels;

  protected void setPixel(int i, int j, PixelADT pixel) {
    this.pixels[i][j] = pixel;
  }

  @Override
  public void valueComponent(){
    PixelProcessor.apply(pixels, p -> p.showValue());
  }

  @Override
  public void intensityComponent(){
    PixelProcessor.apply(pixels, p -> p.showIntensity());
  }

  @Override
  public void lumaComponent(){
    PixelProcessor.apply(pixels, p -> p.showLuma());
  }

  @Override
  public void horizontalFlip() {

  }

  @Override
  public void verticalFlip() {

  }

  @Override
  public void brighten(int val) {
    PixelProcessor.apply(pixels, p -> p.brighten(val));
  }

  @Override
  public void blur(double[][] filter) {

  }

  @Override
  public void sharpen(double[][] filter) {

  }

  @Override
  public void sepia() {
    PixelProcessor.apply(pixels, p -> p.applySepia());
  }

  @Override
  abstract public void loadImage(File f) throws IOException;

  @Override
  abstract public void saveImage(String filename, File destination) throws IOException;

  @Override
  abstract public Image[] split();
}
