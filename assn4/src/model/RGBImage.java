package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage implements Image {
  private int[][] red;
  private int[][] green;
  private int[][] blue;
  private String imageName;

  /**
   * Initializes the red, green, and blue pixels of the image with the given height and width.
   * Initially set to all 0, i.e, a black image.
   *
   * @param width  the height of the image
   * @param height the width of the image
   */
  RGBImage(int height, int width) {
    red = new int[height][width];
    green = new int[height][width];
    blue = new int[height][width];
  }


  @Override
  public void loadImage(File f, String imageName) throws IOException {
    BufferedImage img = read(f);

    int width = img.getWidth();
    int height = img.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = img.getRGB(x, y);

        red[y][x] = (pixel >> 16) & 0xFF;
        green[y][x] = (pixel >> 8) & 0xFF;
        blue[y][x] = pixel & 0xFF;
      }
    }

    this.imageName = imageName;
  }

  @Override
  public void saveAsPPM(String filename, File destination) throws IOException {

  }

  @Override
  public void saveAsJPG(String filename, File destination) throws IOException {
    BufferedImage image = new BufferedImage(red.length, red[0].length, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        Color color = new Color(red[y][x], green[y][x], blue[y][x]);
        image.setRGB(x, y, color.getRGB());
      }
    }

    ImageIO.write(image, "jpg", destination);
  }

  @Override
  public void saveAsPNG(String filename, File destination) throws IOException {
    BufferedImage image = new BufferedImage(red.length, red[0].length, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        Color color = new Color(red[y][x], green[y][x], blue[y][x]);
        image.setRGB(x, y, color.getRGB());
      }
    }

    ImageIO.write(image, "png", destination);
  }


  @Override
  public Image getValueComponent() {
    int value;
    Image img = new RGBImage(red.length, red[0].length);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        value = Math.max(red[y][x], green[y][x]);
        value = Math.max(blue[y][x], value);
        img.setRed(y, x, value);
        img.setGreen(y, x, value);
        img.setBlue(y, x, value);
      }
    }

    return img;
  }

  @Override
  public Image brightenImage(int constant, String destinationImageName) {
    Image img = new RGBImage(red.length, red[0].length);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        img.setRed(y, x, Math.min(Math.max(red[y][x] + constant, 0), 255));
        img.setGreen(y, x, Math.min(Math.max(green[y][x] + constant, 0), 255));
        img.setBlue(y, x, Math.min(Math.max(blue[y][x] + constant, 0), 255));
      }
    }
    return img;
  }

  @Override
  public int[][] getRed() {
    return red;
  }

  @Override
  public void setRed(int height, int width, int value) {
    this.red[height][width] = value;
  }

  @Override
  public int[][] getGreen() {
    return green;
  }

  @Override
  public void setGreen(int height, int width, int value) {
    this.green[height][width] = value;
  }

  @Override
  public int[][] getBlue() {
    return blue;
  }

  @Override
  public void setBlue(int height, int width, int value) {
    this.blue[height][width] = value;
  }

  @Override
  public Image flipImage(String destinationImageName) {
    Image img = new RGBImage(red.length, red[0].length);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        img.setRed(x, y, red[y][x]);
        img.setGreen(x, y, red[y][x]);
        img.setBlue(x, y, red[y][x]);
      }
    }
    return img;
  }

  @Override
  public Image getImageIntensity() {
    Image img = new RGBImage(red.length, red[0].length);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        int intensity = (red[y][x] + green[y][x] + blue[y][x]) / 3;
        img.setRed(x, y, intensity);
        img.setGreen(x, y, intensity);
        img.setBlue(x, y, intensity);
      }
    }
    return img;
  }

  @Override
  public Image getImageLuma() {
    Image img = new RGBImage(red.length, red[0].length);
    for (int y = 0; y < red.length; y++) {
      for (int x = 0; x < red[0].length; x++) {
        int luma = 0.2126 * red[y][x] + 0.7152 * green[y][x] + 0.0722 * blue[y][x]; //Should make this double
        img.setRed(x, y, luma);
        img.setGreen(x, y, luma);
        img.setBlue(x, y, luma);
      }
    }
    return img;
  }
}
