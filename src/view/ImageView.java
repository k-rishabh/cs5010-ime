package view;

import java.awt.image.BufferedImage;

import controller.Features;

public interface ImageView {
  void displayMessage(String message, String title, int type);

  boolean displayConfirmation(String message, String title);

  void setAllInputs(boolean set);

  void listen(Features controller);

  void refreshScreen(BufferedImage imageName, BufferedImage histogram);

  boolean splitViewWindow(BufferedImage splitImage);
}
