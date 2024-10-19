package model;

import java.io.File;
import static javax.imageio.ImageIO.write;


public class JPGImage extends AbstractImage {
  JPGImage(int height, int width) {
    super(height, width);
  }

  @Override
  public void saveImage(String filename, File destination) {

  }
}
