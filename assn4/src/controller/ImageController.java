//package controller;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Scanner;
//
//import model.Image;
//import model.ImageADT;
//import model.RGBImage;
//
//public class ImageController {
//  private HashMap<String, Image> imageMap;
//
//  public ImageController() {
//    this.imageMap = new HashMap<>();
//    Image img1 = new RGBImage(2,3);
//  }
//
//  public void runScriptFile(String scriptFilePath) {
//    try (Scanner scanner = new Scanner(new File(scriptFilePath))) {
//      while (scanner.hasNextLine()) {
//        String line = scanner.nextLine().trim();
//        executeCommand(line);
//      }
//    } catch (FileNotFoundException e) {
//      System.out.println("Script file not found: " + scriptFilePath);
//      e.printStackTrace();
//    }
//  }
//
//  private void executeCommand(String command) {
//    String[] parts = command.split(" ");
//    String action = parts[0];
//
//    switch (action.toLowerCase()) {
//      case "load":
//        if (parts.length == 3) {
//          loadImage(parts[1], parts[2]);
//        } else {
//          System.out.println("Invalid load command!");
//        }
//        break;
//      case "save":
//        if (parts.length == 3) {
//          saveImage(parts[1], parts[2]);
//        } else {
//          System.out.println("Invalid save command!");
//        }
//        break;
//      case "brighten":
//        if (parts.length == 4) {
//          int value = Integer.parseInt(parts[1]);
//          brighten(value, parts[2], parts[3]);
//        } else {
//          System.out.println("Invalid brighten command!");
//        }
//        break;
//      case "vertical-flip":
//        if (parts.length == 3) {
//          flipVertically(parts[1], parts[2]);
//        } else {
//          System.out.println("Invalid vertical-flip command!");
//        }
//        break;
//      case "horizontal-flip":
//        if (parts.length == 3) {
//          flipHorizontally(parts[1], parts[2]);
//        } else {
//          System.out.println("Invalid horizontal-flip command!");
//        }
//        break;
//      case "value-component":
//        if (parts.length == 3) {
//          valueComponent(parts[1], parts[2]);
//        } else {
//          System.out.println("Invalid value-component command!");
//        }
//        break;
//      case "rgb-split":
//        if (parts.length == 5) {
//          rgbSplit(parts[1], parts[2], parts[3], parts[4]);
//        } else {
//          System.out.println("Invalid rgb-split command!");
//        }
//        break;
//      case "rgb-combine":
//        if (parts.length == 5) {
//          rgbCombine(parts[1], parts[2], parts[3], parts[4]);
//        } else {
//          System.out.println("Invalid rgb-combine command!");
//        }
//        break;
//      default:
//        System.out.println("Unknown command: " + command);
//        break;
//    }
//  }
//
//
//}
