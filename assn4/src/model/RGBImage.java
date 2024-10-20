package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends AbstractImage {

  @Override
  public void loadImage(File f) throws IOException {

  }

  @Override
  public void saveImage(String filename, File destination) throws IOException {

  }

  @Override
  public Image valueComponent() {
    return null;
  }

  @Override
  public Image intensityComponent() {
    return null;
  }

  @Override
  public Image lumaComponent() {
    return null;
  }

  @Override
  public Image[] split() {
    return new Image[0];
  }
}
