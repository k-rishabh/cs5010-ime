package view;

import controller.ImageController;

public interface ImageView {
  void refreshScreen(String imageName, String histogram);

  void splitView(String imageName);

  void displayErrorMessage(String errorMessage);

  void addFeatures(ImageController features);
}
