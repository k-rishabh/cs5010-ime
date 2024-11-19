package view;

import java.awt.image.BufferedImage;

import controller.ImageController;

public interface ImageView {
  void refreshScreen(BufferedImage imageName, BufferedImage histogram);

  void splitView(String imageName);

  void displayErrorMessage(String errorMessage);

  void addFeatures(ImageController features);
}
