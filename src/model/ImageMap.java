package model;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;

import controller.filter.BlurFilter;
import controller.filter.CompBlueFilter;
import controller.filter.CompGreenFilter;
import controller.filter.CompRedFilter;
import controller.filter.IntensityFilter;
import controller.filter.LumaFilter;
import controller.filter.SepiaFilter;
import controller.filter.SharpenFilter;
import util.ImageTransformer;
import util.ImageUtil;

import static java.lang.Integer.parseInt;

public class ImageMap implements ImageMapInterface {

  private final HashMap<String, ImageModel> images;

  public ImageMap() {
    this.images = new HashMap<>();
  }

  @Override
  public void load(String imageName, String filePath) {
    String extension = filePath.substring(filePath.lastIndexOf('.'));

    if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".png")) {
      images.put(imageName, ImageUtil.loadImageRaster(filePath));
    } else if (extension.equalsIgnoreCase(".ppm")) {
      images.put(imageName, ImageUtil.loadImageRaw(filePath));
    } else {
      System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
    }

  }

  @Override
  public void save(String imageName,String filePath) throws IOException {
    ImageModel img = images.get(imageName);

    if (img == null) {
      System.out.println("Image " + imageName + " not found!");
    }
    else {
      String extension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();

      if (extension.equals(".jpg") || extension.equals(".png")) {
        ImageUtil.saveImageRaster(filePath, img);
      } else if (extension.equalsIgnoreCase(".ppm")) {
        ImageUtil.saveImageRaw(filePath, img);
      } else {
        System.out.printf("Error: Did not recognize file extension: %s!\n", extension);
      }
    }
  }

  @Override
  public void sepia(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new SepiaFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new SepiaFilter()));
    }
  }

  @Override
  public void redComponent(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new CompRedFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new CompRedFilter()));
    }
  }

  @Override
  public void blueComponent(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new CompBlueFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new CompBlueFilter()));
    }
  }

  @Override
  public void greenComponent(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new CompGreenFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new CompGreenFilter()));
    }
  }

  @Override
  public void lumaGreyscale(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new LumaFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new LumaFilter()));
    }
  }

  @Override
  public void valueGreyscale(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.valueComponent(), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.valueComponent());
    }
  }

  @Override
  public void intensityGreyscale(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyColorFilter(new IntensityFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyColorFilter(new IntensityFilter()));
    }
  }

  @Override
  public void brighten(String imageName, String destImageName, int increment) {
    ImageTransformer.apply(images, imageName,
            destImageName, img -> img.brighten(increment));
  }

  @Override
  public void blur(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyImageFilter(new BlurFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.applyImageFilter(new BlurFilter()));
    }
  }

  @Override
  public void sharpen(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.applyImageFilter(new SharpenFilter()), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0],
              args[1], img -> img.applyImageFilter(new SharpenFilter()));
    }

  }

  @Override
  public void horizontalFlip(String ImageName, String destImgName) {
    ImageTransformer.apply(images, ImageName, destImgName, img -> img.horizontalFlip());
  }

  @Override
  public void verticalFlip(String ImageName, String destImgName) {
    ImageTransformer.apply(images, ImageName, destImgName, img -> img.verticalFlip());
  }

  @Override
  public void rgbSplit(String ImageName, String destRedImgName, String destGreenImageName, String destBlueImgName) {
    ImageModel rgbSplit = this.images.get(ImageName).deepCopy();
    ImageModel[] splitComponents = rgbSplit.splitComponents();
    this.images.put(destRedImgName, splitComponents[0]);
    this.images.put(destGreenImageName, splitComponents[1]);
    this.images.put(destBlueImgName, splitComponents[2]);
  }

  @Override
  public void rgbCombine(String destImgName,String redImgName, String greenImageName, String blueImgName) {
    ImageModel destImg = this.images.get(redImgName).deepCopy();
    destImg.combineComponents(this.images.get(greenImageName));
    destImg.combineComponents(this.images.get(blueImgName));
    this.images.put(destImgName, destImg);
  }

  @Override
  public void colorCorrect(String[] args) {
    if (args.length > 2) {
      int splitPercentage = parseInt(args[3]);
      ImageTransformer.applySplit(images, args[0],
              args[1], img -> img.colorCorrect(), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[0], args[1], img -> img.colorCorrect());
    }
  }

  @Override
  public void compress(int percentage, String ImageName, String destImgName) {
    ImageTransformer.apply(images, ImageName, destImgName, img -> img.compress(percentage));
  }

  @Override
  public void histogram(String ImageName, String destImgName) {
    ImageTransformer.apply(images, ImageName, destImgName, img -> img.histogram());
  }

  public void downscale(String ImageName, String destImgName) {

  }

  @Override
  public void levelsAdjust(String[] args) {
    if (args.length > 5) {
      int splitPercentage = parseInt(args[6]);
      ImageTransformer.applySplit(images, args[3],
              args[4], img -> img.levelsAdjust(parseInt(args[0]), parseInt(args[1]), parseInt(args[2])), splitPercentage);
    }
    else{
      ImageTransformer.apply(images, args[3], args[4], img -> img.levelsAdjust(parseInt(args[0]), parseInt(args[1]), parseInt(args[2])));
    }

  }
}
