package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * The view interface that defines functionality for displaying messages, refreshing the screen,
 * or listening for user input.
 */
public interface ImageView {
  /**
   * Displays an information or error message to the user.
   *
   * @param message the message to be displayed
   * @param title the title of the pop-up window
   * @param type the type of message, in the form of an enum
   */
  void displayMessage(String message, String title, int type);

  /**
   * Asks the user for confirmation on a particular task.
   *
   * @param message the confirmation message that the user must accept or reject
   * @param title the title of the pop-up window
   * @return true if yes, else false
   */
  boolean displayConfirmation(String message, String title);

  /**
   * Sets all inputs in the GUI to enabled or disabled.
   *
   * @param set the mode to be set to (true is enabled, false is disabled)
   */
  void setAllInputs(boolean set);

  /**
   * The parent callback function that waits and listens for user input and acts accordingly by
   * calling the respective controller/features function.
   *
   * @param controller an object of features where a method will be called based on user input
   */
  void listen(Features controller);

  /**
   * Refreshes the GUI screen with a new image and its respective histogram.
   *
   * @param imageName the new image to be displayed
   * @param histogram the new histogram to be displayed
   */
  void refreshScreen(BufferedImage imageName, BufferedImage histogram);

  /**
   * If on preview mode, this method is called which prompts the user with a pop-up window with a
   * preview of the operation. If the user accepts, it applies a transformation to the entire image.
   * Else, it cancels the operation.
   *
   * @param splitImage the previewed image
   * @return true if the user accepts, false if they cancel
   */
  boolean splitViewWindow(BufferedImage splitImage);
}
