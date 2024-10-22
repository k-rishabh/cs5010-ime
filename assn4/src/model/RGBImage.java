package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends Image {

  public RGBImage(int height, int width) {
    this.pixels = new PixelADT[height][width];
  }

  private RGBImage(PixelADT[][] pixels) {
    this.pixels = new PixelADT[pixels.length][pixels[0].length];

    for(int i=0; i<pixels.length; i++) {
      for(int j=0; j<pixels[0].length; j++) {
        this.pixels[i][j] = pixels[i][j];
      }
    }
  }


  @Override
  public Image[] split() {
    Image redTint = new RGBImage(pixels);
    Image greenTint = new RGBImage(pixels);
    Image blueTint = new RGBImage(pixels);

    PixelProcessor.apply(redTint.pixels, p -> p.applyRedTint());
    PixelProcessor.apply(greenTint.pixels, p -> p.applyGreenTint());
    PixelProcessor.apply(blueTint.pixels, p -> p.applyBlueTint());

    return new Image[] {redTint, blueTint, greenTint};
  }

}
