package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage implements Image {
  private int[][] red;
  private int[][] green;
  private int[][] blue;

  /**
   * Initializes the red, green, and blue pixels of the image with the given height and width.
   * Initially set to all 0, i.e, a black image.
   *
   * @param width the height of the image
   * @param height the width of the image
   */
  RGBImage(int height, int width) {
    red = new int[height][width];
    green = new int[height][width];
    blue = new int[height][width];
  }

  @Override
  public void loadImage(File f) throws IOException {
    BufferedImage img = read(f);

    //convert to rgb
  }

  @Override
  public void saveAsPPM(String filename, File destination) throws IOException {

  }

  @Override
  public void saveAsJPG(String filename, File destination) throws IOException {

  }

  @Override
  public void saveAsPNG(String filename, File destination) throws IOException {

  }


  @Override
  public int[][] getImageValue() {
    int[][] values = new int[red.length][red[0].length];

    for(int i = 0; i < red.length; i++) {
      for(int j = 0; j < red[0].length; j++) {
        values[i][j] = Math.max(red[i][j], green[i][j]);
        values[i][j] = Math.max(blue[i][j], values[i][j]);
      }
    }

    return values;
  }

  @Override
  public int getImageIntensity() {
    return 0;
  }

  @Override
  public int getImageLuma() {
    return 0;
  }
}
