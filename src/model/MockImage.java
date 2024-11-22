package model;

import java.io.IOException;

import controller.filter.Filter;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * A mock implementation of the model used for testing the controller.
 * It documents which functions are called in StringBuilder log.
 * It may also return a uniqueCode that it is initialized with, if return type is int.
 */
public class MockImage implements ImageModel {
  private final Appendable out;

  /**
   * Constructor function for the mock model. Takes in an appendable object to which it will add
   * the log output for performing the operations.
   *
   * @param out the appendable log
   */
  public MockImage(Appendable out) {
    this.out = out;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public Pixel getPixel(int i, int j) {
    return new RGBPixel(0, 0, 0);
  }

  @Override
  public ImageModel deepCopy() {
    return this;
  }

  @Override
  public void applyColorFilter(Filter filter) {
    try {
      out.append(String.format("In applyColorFilter with filter %s.\n",
              filter.getClass().getName()));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void applyImageFilter(Filter filter) {
    try {
      out.append(String.format("In applyImageFilter with filter %s.\n",
              filter.getClass().getName()));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void valueComponent() {
    try {
      out.append("In valueComponent.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void brighten(int val) {
    try {
      out.append(String.format("In brighten with value %d.\n", val));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void horizontalFlip() {
    try {
      out.append("In horizontalFlip.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void verticalFlip() {
    try {
      out.append("In verticalFlip.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public ImageModel[] splitComponents() {
    try {
      out.append("In splitComponents.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
    return new MockImage[]{this, this, this};
  }

  @Override
  public void combineComponents(ImageModel img) {
    try {
      out.append("In combineComponents.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void compress(int ratio) {
    try {
      out.append(String.format("In compress with ratio %d.\n", ratio));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void colorCorrect() {
    try {
      out.append("In colorCorrect.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public ImageModel histogram() {
    try {
      out.append("In histogram.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
    return null;
  }

  @Override
  public void levelsAdjust(int black, int mid, int white) {
    try {
      out.append(String.format("In levelsAdjust with black %d, mid %d, white %d.\n",
              black, mid, white));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public ImageModel[] splitImage(int ratio) {
    try {
      out.append(String.format("In splitImage with ratio %d.\n", ratio));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
    return new MockImage[]{this, this};
  }

  @Override
  public void mergeSplits(ImageModel img) {
    try {
      out.append("In mergeSplits.\n");
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public void downscale(int newHeight, int newWidth) {
    try {
      out.append(String.format("In downscale with height %d and width %d.\n", newHeight, newWidth));
    } catch (IOException e) {
      System.out.println("Unable to append to out!\n");
    }
  }

  @Override
  public ImageModel applyMasking(ImageModel maskImage, ImageModel operatedImage) {
    return null;
  }
}
