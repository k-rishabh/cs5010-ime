package model;

import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends Image {

  public RGBImage(int height, int width) {
    pixels = new PixelADT[height][width];
  }

  @Override
  public void loadImage(File f) throws IOException {

  }

  @Override
  public void saveImage(String filename, File destination) throws IOException {

  }

  @Override
  public Image[] split() {
    Image[] result = new RGBImage[3];

    PixelProcessor.apply(result[0].pixels, p -> p.applyRedTint());
    PixelProcessor.apply(result[1].pixels, p -> p.applyGreenTint());
    PixelProcessor.apply(result[2].pixels, p -> p.applyBlueTint());

    return result;
  }
}
