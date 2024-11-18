//package model;
//
//import java.io.IOException;
//
///**
// * A mock implementation of the model used for testing the controller.
// * It documents which functions are called in StringBuilder log.
// * It may also return a uniqueCode that it is initialized with, if return type is int.
// */
//public class MockImage implements ImageMap {
//  private StringBuilder log;
//
//  public MockImage(StringBuilder log) {
//    this.log = log;
//  }
//
//
//  @Override
//  public void load(String imageName, String filePath) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(filePath).append("\n");
//  }
//
//  @Override
//  public void save(String imageName, String filePath) throws IOException {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(filePath).append("\n");
//  }
//
//  @Override
//  public void sepia(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void redComponent(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void blueComponent(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void greenComponent(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void lumaGreyscale(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void valueGreyscale(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void intensityGreyscale(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void brighten(String imageName, String destImageName, int increment) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(destImageName).append("\n");
//  }
//
//  @Override
//  public void blur(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void sharpen(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void horizontalFlip(String imageName, String destImageName) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(destImageName).append("\n");
//  }
//
//  @Override
//  public void verticalFlip(String imageName, String destImageName) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(destImageName).append("\n");
//  }
//
//  @Override
//  public void rgbSplit(String imageName, String destRedImgName, String destGreenImageName, String destBlueImgName) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(destRedImgName).append(" ").append(destGreenImageName).append(" ").append(destBlueImgName).append("\n");
//  }
//
//  @Override
//  public void rgbCombine(String destImageName, String redImageName, String greenImageName, String blueImageName) {
//    log.append("Command Arguments: ").append(destImageName).append(" ").append(redImageName).append(" ").append(greenImageName).append(" ").append(blueImageName).append("\n");
//  }
//
//  @Override
//  public void colorCorrect(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//
//  @Override
//  public void compress(int percentage, String imageName, String destImageName) {
//    log.append("Command Arguments: ").append(percentage).append(" ").append(imageName).append(" ").append(destImageName).append("\n");
//  }
//
//  @Override
//  public void histogram(String imageName, String destImageName) {
//    log.append("Command Arguments: ").append(imageName).append(" ").append(destImageName).append("\n");
//  }
//
//  @Override
//  public void levelsAdjust(String[] args) {
//    log.append("Command Arguments: ").append(String.join(" ",args)).append("\n");
//  }
//}
