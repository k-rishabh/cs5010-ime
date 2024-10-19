package model;

import java.io.File;
import static javax.imageio.ImageIO.write;

public class PPMImage extends AbstractImage {
  PPMImage(int height, int width) {
    super(height, width);
  }

  @Override
  public void saveImage(String filename, File destination) {

  }
}
