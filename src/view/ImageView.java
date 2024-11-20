package view;

import java.awt.image.BufferedImage;

import controller.ImageController;

public interface ImageView {
  void displayMessage(String errorMessage, String title, int type);

  boolean displayConfirmation(String confirmationMessage, String title);

  void listen(ImageController controller);

  void refreshScreen(BufferedImage imageName, BufferedImage histogram);

  boolean splitViewWindow(BufferedImage splitImage);
}
