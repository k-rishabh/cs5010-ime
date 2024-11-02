package controller.command;

import controller.ImageCommand;
import model.Image;

public class FlipHorizontal implements ImageCommand {

  @Override
  public int apply(Image img) {
    int h = img.getHeight();
    int w = img.getWidth();

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w / 2; j++) {
        int p1 = img.getPackedPixel(i, j);
        int p2 = img.getPackedPixel(i, w - j);

        img.setPixel(i, j, p2);
        img.setPixel(i, w - j, p1);
      }
    }

    return 0;
  }
}
