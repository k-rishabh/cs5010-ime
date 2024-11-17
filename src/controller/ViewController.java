package controller;

import java.io.IOException;
import java.util.InputMismatchException;

import controller.command.Blur;
import controller.command.ColorCorrect;
import controller.command.CombineRGB;
import controller.command.ComponentBlue;
import controller.command.ComponentGreen;
import controller.command.ComponentIntensity;
import controller.command.ComponentLuma;
import controller.command.ComponentRed;
import controller.command.ComponentValue;
import controller.command.Compress;
import controller.command.FlipHorizontal;
import controller.command.FlipVertical;
import controller.command.ImageCommand;
import controller.command.LevelsAdjust;
import controller.command.Load;
import controller.command.Save;
import controller.command.SplitRGB;
import model.ImageMapInterface;
import view.ViewInterface;

public class ViewController implements Features{

  private final ImageMapInterface model;
  private final String currentImage;
  private final String splitViewImage;
  private final String histogramImage;
  private final ViewInterface view;

  public ViewController(ImageMapInterface model, ViewInterface view) {
    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
    this.currentImage = "current-img";
    this.splitViewImage = "split-img";
    this.histogramImage = "histogram-img";

  }

  private void executeCommand(ImageCommand command) throws IOException {
    try {
      command.apply(model);
      refreshImage();
    } catch (IllegalArgumentException | InputMismatchException e) {
      view.displayErrorMessage(e.getMessage());
    }

  }

  @Override
  public void load(String filepath) throws IOException {
    String[] args = {filepath, currentImage};
    executeCommand(new Load(args));
  }

  @Override
  public void save(String filepath) throws IOException {
    String[] args = {filepath, currentImage};
    executeCommand(new Save(args));
  }

  @Override
  public void redComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentRed(args));
  }

  @Override
  public void greenComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentGreen(args));
  }

  @Override
  public void blueComponent() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentBlue(args));
  }

  @Override
  public void lumaGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentLuma(args));
  }

  @Override
  public void valueGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentValue(args));
  }

  @Override
  public void intensityGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ComponentIntensity(args));
  }

  @Override
  public void blur() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Blur(args));
  }

  @Override
  public void sharpen() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Blur(args));
  }

  @Override
  public void sepia() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Blur(args));
  }

  @Override
  public void brighten(int scale) throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Blur(args));
  }

  @Override
  public void verticalFlip() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new FlipVertical(args));
  }

  @Override
  public void horizontalFlip() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new FlipHorizontal(args));
  }

  @Override
  public void colorCorrect() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ColorCorrect(args));
  }

  @Override
  public void levelAdjust(int b, int m, int w) throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new LevelsAdjust(args));
  }

  @Override
  public void compress(double percentage) throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Compress(args));
  }

  @Override
  public void rgbSplit(String redFilePath, String greenFilePath, String blueFilePath) throws IOException {
    String redImage = currentImage + "-red";
    String greenImage = currentImage + "-green";
    String blueImage = currentImage + "-blue";

    String[] args = {currentImage, redImage, greenImage, blueImage};
    ImageCommand rgbSplit = new SplitRGB(args);
    rgbSplit.apply(model);

    String[] argsRed = {redFilePath, redImage};
    executeCommand(new Save(argsRed));

    String[] argsGreen = {greenFilePath, greenImage};
    executeCommand(new Save(argsGreen));

    String[] argsBlue = {greenFilePath, greenImage};
    executeCommand(new Save(argsBlue));
  }

  @Override
  public void rgbCombine(String redImageFile, String greenImageFile, String blueImageFile) throws IOException {
    String redImage = "red-" + currentImage;
    String greenImage = "green-" + currentImage;
    String blueImage = "blue-" + currentImage;

    String[] argsRed = {redImageFile, redImage};
    ImageCommand redLoad = new Load(argsRed);
    redLoad.apply(model);

    String[] argsGreen = {greenImageFile, greenImage};
    ImageCommand greenLoad = new Load(argsGreen);
    greenLoad.apply(model);

    String[] argsBlue = {blueImageFile, blueImage};
    ImageCommand blueLoad = new Load(argsBlue);
    blueLoad.apply(model);


    String[] argsCombine = {currentImage, redImage, greenImage, blueImage};
    executeCommand(new CombineRGB(argsCombine));
  }

  @Override
  public void split(String operationType, String[] args) throws IOException {
    
  }

  @Override
  public void refreshImage() throws IOException {
    view.refreshScreen(currentImage, histogramImage);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }
}
