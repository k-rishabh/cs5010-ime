package model;

import model.pixel.PixelADT;
import model.pixel.PixelProcessor;

/**
 * Abstract class that represents an image in red, blue, and green pixels.
 * Contains implementation for
 */
public class RGBImage extends Image {

  public RGBImage(int height, int width) {
    this.pixels = new PixelADT[height][width];
  }

  @Override
  public Image[] split() {
    Image redTint = this.deepCopy();
    Image greenTint = this.deepCopy();
    Image blueTint = this.deepCopy();

    PixelProcessor.apply(redTint.pixels, p -> p.applyRedTint());
    PixelProcessor.apply(greenTint.pixels, p -> p.applyGreenTint());
    PixelProcessor.apply(blueTint.pixels, p -> p.applyBlueTint());

    return new Image[] {redTint, greenTint, blueTint};
  }

  @Override
  public Image deepCopy() {
    int height = this.pixels.length;
    int width = this.pixels[0].length;

    Image copyImage = new RGBImage(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        copyImage.pixels[i][j] = pixels[i][j].deepCopy();
      }
    }

    return copyImage;
  }

  @Override
  public Image combine() {
    return null;
  }

}
