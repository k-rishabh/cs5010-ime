package model;

import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends AbstractImage {

  public RGBImage(int height, int width) {
    pixels = new Pixel[height][width];
  }

  @Override
  public void loadImage(File f) throws IOException {

  }

  @Override
  public void saveImage(String filename, File destination) throws IOException {

  }

  @Override
  public Image valueComponent() {
    int value;
    Image img = new RGBImage(pixels.length,pixels.length);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        value = pixels[y][x].getMaxComponent();
        Pixel pixel = new RGBPixel(value, value, value);
        img.setPixel(pixel,y,x);
      }
    }

    return img;
  }

  @Override
  public Image intensityComponent() {
    int value;
    Image img = new RGBImage(pixels.length,pixels.length);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        value = pixels[y][x].getAvgComponent();
        Pixel pixel = new RGBPixel(value, value, value);
        img.setPixel(pixel,y,x);
      }
    }

    return img;
  }

  @Override
  public Image lumaComponent() {
    int value;
    Image img = new RGBImage(pixels.length,pixels.length);
    for (int y = 0; y < pixels.length; y++) {
      for (int x = 0; x < pixels[0].length; x++) {
        value = pixels[y][x].getAvgComponent();
        Pixel pixel = new RGBPixel((int) 0.2126*value, (int) 0.7152*value, (int) 0.0722*value);
        img.setPixel(pixel,y,x);
      }
    }

    return img;
  }

  @Override
  public Image[] split() {
    return new Image[0];
  }

  @Override
  public void setPixel(Pixel p,int y, int x) {
    this.pixels[y][x] = p;
  }
}
