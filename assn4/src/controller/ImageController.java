package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Image;
import model.pixel.PixelADT;
import model.RGBImage;
import model.pixel.RGBPixel;

import static javax.imageio.ImageIO.read;

public class ImageController {
  private HashMap<String, Image> imageMap;

  public ImageController() {
    this.imageMap = new HashMap<>();
  }

  public void loadImage(String filePath, String imageName) throws IOException {
    File f = new File(filePath);
    BufferedImage img = read(f);

    int width = img.getWidth();
    int height = img.getHeight();

    PixelADT[][] pixels = new PixelADT[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = img.getRGB(x, y);
        pixels[y][x] = new RGBPixel(pixel);
      }
    }

    Image img1 = new RGBImage(pixels);
    this.imageMap.put(imageName,img1);
  }

  public void saveImage(Image imageToBeSaved,String destinationFilename) throws IOException {
    BufferedImage image = new BufferedImage(imageToBeSaved.getWidth(), imageToBeSaved.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < imageToBeSaved.getHeight(); y++) {
      for (int x = 0; x < imageToBeSaved.getWidth(); x++) {
        PixelADT rgb_pixel = imageToBeSaved.getPixel(y,x);
        int rgb_pixel_int = (rgb_pixel.getRed() <<16) | (rgb_pixel.getGreen() <<8) | (rgb_pixel.getBlue());
        image.setRGB(x, y, rgb_pixel_int);
      }
    }

    ImageIO.write(image, "png", new File(destinationFilename));
  }

  public void runScriptFile(String scriptFilePath) {
    try (Scanner scanner = new Scanner(new File(scriptFilePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        executeCommand(line);
      }
    } catch (IOException e) {
      System.out.println("Script file not found: " + scriptFilePath);
      e.printStackTrace();
    }
  }

  private void executeCommand(String command) throws IOException {
    String[] parts = command.split(" ");
    String action = parts[0];

    switch (action.toLowerCase()) {
      case "load":
        if (parts.length == 3) {
          loadImage(parts[1], parts[2]);
        } else {
          System.out.println("Invalid load command!");
        }
        break;
      case "save":
        if (parts.length == 3) {
          String imageName =  parts[2];
          Image imageToBeSaved = imageMap.get(imageName);
          saveImage(imageToBeSaved, parts[1]);
        } else {
          System.out.println("Invalid save command!");
        }
        break;
      case "brighten":
        if (parts.length == 4) {
          int value = Integer.parseInt(parts[1]);
          Image imageToBeBrightened = imageMap.get(parts[2]);
          Image brightenedImageCopy = imageToBeBrightened.deepCopy();

          brightenedImageCopy.brighten(value);
          imageMap.put(parts[3],brightenedImageCopy);
        } else {
          System.out.println("Invalid brighten command!");
        }
        break;
      case "vertical-flip":
        if (parts.length == 3) {
          Image imageToBeVerticallyFlipped = imageMap.get(parts[1]);
          Image verticallyFlippedImageCopy = imageToBeVerticallyFlipped.deepCopy();
          verticallyFlippedImageCopy.verticalFlip();
          imageMap.put(parts[2],verticallyFlippedImageCopy);
        } else {
          System.out.println("Invalid vertical-flip command!");
        }
        break;
      case "horizontal-flip":
        if (parts.length == 3) {
          Image imageToBeHorizontallyFlipped = imageMap.get(parts[1]);
          Image horizontallyFlippedImageCopy = imageToBeHorizontallyFlipped.deepCopy();
          horizontallyFlippedImageCopy.verticalFlip();
          imageMap.put(parts[2],horizontallyFlippedImageCopy);
        } else {
          System.out.println("Invalid horizontal-flip command!");
        }
        break;
      case "value-component":
        if (parts.length == 3) {
          Image imageValue = imageMap.get(parts[1]);
          Image imageValueCopy = imageValue.deepCopy();
          imageValueCopy.valueComponent();
          imageMap.put(parts[2],imageValueCopy);
        } else {
          System.out.println("Invalid value-component command!");
        }
        break;
      case "rgb-split":
        if (parts.length == 5) {
          Image imageSplit = imageMap.get(parts[1]);
          Image[] result = imageSplit.split();
          imageMap.put(parts[2],result[0]);
          imageMap.put(parts[3],result[1]);
          imageMap.put(parts[4],result[2]);
          System.out.println(imageMap.toString());
        } else {
          System.out.println("Invalid rgb-split command!");
        }
        break;
      case "blur":
        if (parts.length == 3) {
          Image imageBlur = imageMap.get(parts[1]);
          Image imageBlurCopy = imageBlur.deepCopy();

          imageBlurCopy.blur();
          imageMap.put(parts[2],imageBlurCopy);
        } else{
          System.out.println("Invalid blur command!");
        }
        break;
      case "sharpen":
        if(parts.length == 3){
          Image imageSharpen = imageMap.get(parts[1]);
          Image imageSharpenCopy = imageSharpen.deepCopy();
          imageSharpenCopy.sharpen();
          imageMap.put(parts[2],imageSharpenCopy);
        }
        else{
          System.out.println("Invalid sharpen command!");
        }
        break;
//      case "rgb-combine":
//        if (parts.length == 5) {
//          rgbCombine(parts[1], parts[2], parts[3], parts[4]);
//        } else {
//          System.out.println("Invalid rgb-combine command!");
//        }
//        break;
      default:
        System.out.println("Unknown command: " + command);
        break;
    }
  }

  public static void main(String[] args) throws IOException {
    ImageController imageController = new ImageController();
    imageController.runScriptFile("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/assn4/src/controller/scratch.txt");
  }

}
