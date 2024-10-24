package controller;

import java.util.HashMap;

import model.Image;
import util.ImageUtil;

public class ImageHandler {
  static protected HashMap<String, Image> images;

  public ImageHandler() {
    images = new HashMap<>();
  }

  public void loadImage(String filePath, String imageName) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      images.put(imageName, ImageUtil.loadImageRaw(filePath));
    }
  }

  public void saveImage(String filePath, String imageName) {
    Image img = images.get(imageName);

    if (img == null) {
      System.out.println("Image " + imageName + " not found!");
      return;
    }

    String extension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();

    if (extension.equals(".jpg") || extension.equals(".png")) {
      ImageUtil.saveImageRaster(filePath, img);
    } else if (extension.equalsIgnoreCase(".ppm")) {
      ImageUtil.saveImageRaw(filePath, img);
    }
  }

  public int redComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.redComponent());
  }

  public int greenComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.greenComponent());
  }

  public int blueComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.blueComponent());
  }

  public int valueComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.valueComponent());
  }

  public int intensityComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.intensityComponent());
  }

  public int lumaComponent(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.lumaComponent());
  }

  public int horizontalFlip(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.horizontalFlip());
  }

  public int verticalFlip(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.verticalFlip());
  }

  public int brighten(int val, String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.brighten(val);
    images.put(destName, destImg);

    if (images.containsKey(destName)) {
      return 0;
    } else {
      return 1;
    }
  }

  public int rgbSplit(String srcName, String redName, String greenName, String blueName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return 1;
    }

    Image destImg = images.get(srcName).deepCopy();
    Image[] split = destImg.split();

    images.put(redName, split[0]);
    images.put(greenName, split[1]);
    images.put(blueName, split[2]);

    if(images.containsKey(redName)
            && images.containsKey(greenName)
            && images.containsKey(blueName)) {
      return 0;
    } else {
      return 1;
    }
  }

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

    Image destImg = images.get(redName).deepCopy();
    destImg.combine(images.get(greenName));
    destImg.combine(images.get(blueName));

    images.put(destName, destImg);
    if (images.get(destName) != null) {
      return 0;
    } else {
      return 1;
    }
  }

  public int blur(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.blur());
  }

  public int sharpen(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.sharpen());
  }

  public int sepia(String srcName, String destName) {
    return ImageTransformer.apply(srcName, destName, img -> img.sepia());
  }

  public Image getImage(String s) {
    if(!images.containsKey(s)) {
        System.out.println("Image " + s + " not found!");
    }
    return images.get(s);
  }
}
