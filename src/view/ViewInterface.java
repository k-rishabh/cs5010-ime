package view;

import controller.Features;

public interface ViewInterface {
  void refreshScreen(String imageName, String histogram);

  void splitView(String imageName);

  void displayErrorMessage(String errorMessage);

  void addFeatures(Features features);
}
