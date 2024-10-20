package model;

import java.io.File;
import java.io.IOException;

public abstract class AbstractImage implements Image {
  protected Pixel[][] pixels;

  @Override
  public Image horizontalFlip() {
    return null;
  }

  @Override
  public Image verticalFlip() {
    return null;
  }

  @Override
  public Image brighten(int val) {
    return null;
  }

  @Override
  public Image blur(float val) {
    return null;
  }

  @Override
  public Image sharpen(float val) {
    return null;
  }

  @Override
  public Image sepia() {
    return null;
  }

  @Override
  abstract public void loadImage(File f) throws IOException;

  @Override
  abstract public void saveImage(String filename, File destination) throws IOException;

  @Override
  abstract public Image valueComponent();

  @Override
  abstract public Image intensityComponent();

  @Override
  abstract public Image lumaComponent();

  @Override
  abstract public Image[] split();
}
