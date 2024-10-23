package controller;

import java.sql.SQLOutput;
import java.util.HashMap;

import model.Image;
import util.ImageUtil;

public class ImageHandler {
  private HashMap<String, Image> images;

  public ImageHandler() {
    images = new HashMap<>();
  }

  public void loadImage(String filePath, String imageName) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      this.images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      this.images.put(imageName, ImageUtil.loadImageRaw(filePath));
    }
  }

  public void saveImage(String filePath, String imageName) {
    Image img = images.get(imageName);

    if (img == null) {
      System.out.println("Image " + imageName + " not found!");
      return;
    }

    String extension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();

    if (extension.equals("jpg") || extension.equals("png")) {
      ImageUtil.saveImageRaster(filePath, img);
    } else if (extension.equalsIgnoreCase("ppm")) {
      ImageUtil.saveImageRaw(filePath, img);
    }
  }

  public void redComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.redComponent();
    this.images.put(destName, destImg);
  }

  public void blueComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.blueComponent();
    this.images.put(destName, destImg);
  }

  public void greenComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.greenComponent();
    this.images.put(destName, destImg);
  }

  public void valueComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.valueComponent();
    this.images.put(destName, destImg);
  }

  public void intensityComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.intensityComponent();
    this.images.put(destName, destImg);
  }

  public void lumaComponent(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.lumaComponent();
    this.images.put(destName, destImg);
  }

  public void horizontalFlip(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.horizontalFlip();
    this.images.put(destName, destImg);
  }

  public void verticalFlip(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.verticalFlip();
    this.images.put(destName, destImg);
  }

  public void brighten(int val, String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.brighten(val);
    this.images.put(destName, destImg);
  }

  public void rgbSplit(String srcName, String redName, String blueName, String greenName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    Image[] split = destImg.split();

    this.images.put(redName, split[0]);
    this.images.put(blueName, split[1]);
    this.images.put(greenName, split[2]);
  }

  public void rgbCombine(String destName, String redName, String blueName, String greenName) {
    if (images.get(redName) == null) {
      System.out.println("Image " + redName + " not found!");
      return;
    } else if (images.get(blueName) == null) {
      System.out.println("Image " + blueName + " not found!");
      return;
    } else if (images.get(greenName) == null) {
      System.out.println("Image " + greenName + " not found!");
      return;
    }

    Image destImg = images.get(redName).deepCopy();
    destImg.combine(images.get(blueName));
    destImg.combine(images.get(greenName));

    this.images.put(destName, destImg);
  }

  public void blur(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.blur();
    this.images.put(destName, destImg);
  }

  public void sharpen(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.sharpen();
    this.images.put(destName, destImg);
  }

  public void sepia(String srcName, String destName) {
    if (images.get(srcName) == null) {
      System.out.println("Image " + srcName + " not found!");
      return;
    }

    Image destImg = images.get(srcName).deepCopy();
    destImg.sepia();
    this.images.put(destName, destImg);
  }

}
