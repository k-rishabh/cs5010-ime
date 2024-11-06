package controller;

import java.util.HashMap;
import java.util.Map;

import model.ImageV1;
import model.ImageV2;
import util.Histogram;
import util.ImageTransformer;
import util.ImageUtil;

/**
 * Provides a medium between the model and the controller.
 * Class that is responsible for maintaining a list of active images.
 * Also performs transformations/operations on those images.
 */
public class ImageHandler {
  static protected Map<String, ImageV1> images;

  /**
   * Default constructor function that initializes an empty list of images.
   */
  public ImageHandler() {
    images = new HashMap<>();
  }

  /**
   * Function that is responsible for loading all types of images into the hashmap.
   *
   * @param filePath  the file path of the image to be loaded
   * @param imageName the variable name of the image in the hashmap
   */
  public void loadImage(String filePath, String imageName) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      images.put(imageName, ImageUtil.loadImageRaw(filePath));
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
    }
  }

  /**
   * Function that is responsible for saving all types of images to the file system.
   *
   * @param filePath  the file path where the image must be saved
   * @param imageName the variable name of the image in the hashmap
   */
  public void saveImage(String filePath, String imageName) {
    ImageV1 img = images.get(imageName);

    if (img == null) {
      System.out.println("Image " + imageName + " not found!");
      return;
    }

    String extension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();

    if (extension.equals(".jpg") || extension.equals(".png")) {
      ImageUtil.saveImageRaster(filePath, img);
    } else if (extension.equalsIgnoreCase(".ppm")) {
      ImageUtil.saveImageRaw(filePath, img);
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
    }
  }

  /**
   * Function that applies the red-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int redComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.redComponent());
  }

  /**
   * Function that applies the green-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int greenComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.greenComponent());
  }

  /**
   * Function that applies the blue-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int blueComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.blueComponent());
  }

  /**
   * Function that applies the value-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int valueComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.valueComponent());
  }

  /**
   * Function that applies the intensity-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int intensityComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.intensityComponent());
  }

  /**
   * Function that applies the luma-component transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int lumaComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.lumaComponent());
  }

  /**
   * Function that applies the horizontal-flip transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int horizontalFlip(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.horizontalFlip());
  }

  /**
   * Function that applies the vertical-flip transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int verticalFlip(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.verticalFlip());
  }

  /**
   * Function that applies the brighten/darken transformation on a given image.
   * Stores the result as a new image.
   *
   * @param val      the amount to be brightened/darkened by
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int brighten(int val, String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.brighten(val));
  }

  /**
   * Function that applies the rgb-split transformation on a given image.
   * Stores the red, green, and blue components as new images in the hashmap.
   *
   * @param srcName   the variable name of the image to be split
   * @param redName   the name with which the red image must be saved
   * @param greenName the name with which the green image must be saved
   * @param blueName  the name with which the blue image must be saved
   * @return 0 if success, 1 if failure
   */
  public int rgbSplit(String srcName, String redName, String greenName, String blueName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    ImageV1 destImg = images.get(srcName).deepCopy();
    ImageV1[] split = destImg.splitComponents();

    images.put(redName, split[0]);
    images.put(greenName, split[1]);
    images.put(blueName, split[2]);

    if (images.containsKey(redName)
            && images.containsKey(greenName)
            && images.containsKey(blueName)) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Function that applies the rgb-combine transformation on a given image.
   * Stores the combination as a new image in the hashmap.
   *
   * @param destName  the name with which the combined image must be stored
   * @param redName   the variable name of the red image
   * @param greenName the variable name of the red image
   * @param blueName  the variable name of the red image
   * @return 0 if success, 1 if failure
   */
  public int rgbCombine(String destName, String redName, String blueName, String greenName) {
    if (images.get(redName) == null) {
      System.out.println("Image " + redName + " not found!");
      return 1;
    } else if (images.get(greenName) == null) {
      System.out.println("Image " + greenName + " not found!");
      return 1;
    } else if (images.get(blueName) == null) {
      System.out.println("Image " + blueName + " not found!");
      return 1;
    }

    ImageV1 destImg = images.get(redName).deepCopy();
    destImg.combineComponents(images.get(greenName));
    destImg.combineComponents(images.get(blueName));

    images.put(destName, destImg);
    if (images.get(destName) != null) {
      return 0;
    } else {
      return 1;
    }
  }

  public int colorCorrectness(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
    }

    ImageV1 destImg = images.get(srcName).deepCopy();
    destImg.colorCorrect();
    images.put(destName, destImg);
    if (images.get(destName) != null) {
      return 0;
    } else {
      return 1;
    }
  }

  public int levelAdjust(String srcName, String destName, int black, int mid, int white) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
    }

    ImageV1 destImg = images.get(srcName).deepCopy();
    destImg.levelsAdjust(black, mid, white);
    images.put(destName, destImg);
    if (images.get(destName) != null) {
      return 0;
    } else {
      return 1;
    }
  }

  public int histogram(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
    }
    ImageV2 destImg = images.get(srcName).deepCopy();
    Histogram hist = new Histogram();
    ImageV2 histImg = hist.createHistogram(destImg);
    images.put(destName, histImg);
    if (images.get(destName) != null) {
      return 0;
    } else {
      return 1;
    }

  }



  /**
   * Function that applies the blur transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int blur(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.blur());
  }

  /**
   * Function that applies the sharpen transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int sharpen(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.sharpen());
  }

  /**
   * Function that applies the sepia transformation on a given image.
   * Stores the result as a new image.
   *
   * @param srcName  the image on which the transformation must be applied
   * @param destName the variable name of the resulting image after applying transformation
   * @return 0 if success, 1 if failure
   */
  public int sepia(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.sepia());
  }

  /**
   * Returns an image that exists in the hash map.
   *
   * @param imgName the variable name of the image in the hashmap
   * @return the Image with the given variable name
   */
  public ImageV1 getImage(String imgName) {
    if (!images.containsKey(imgName)) {
      System.out.println("Image " + imgName + " not found!");
      return null;
    }
    return images.get(imgName);
  }
}

