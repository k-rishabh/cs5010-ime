package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import controller.Features;

/**
 * Mock class that imitates the GUI view. It is used for testing the GUI (view) controller.
 * It stores the functions called in a log format using "out" and reads user input from "in".
 */
public class MockGUI implements ImageView {
  Scanner sc;
  Readable in;
  Appendable out;

  /**
   * Constructor function that initializes the Readable and Appendable objects of the class.
   * The scanner is used to read the user input from Readable.
   * The Appendable is used to log the working of the program
   *
   * @param in the readable to read user input from
   * @param out the appendable to log functionality
   */
  public MockGUI(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
    sc = new Scanner(in);
  }

  @Override
  public void displayMessage(String message, String title, int type) {
    try {
      out.append(String.format("Displaying type %d message: %s\n", type, message));
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }
  }

  @Override
  public boolean displayConfirmation(String message, String title) {
    try {
      out.append(String.format("Asking for confirmation with message: %s\n", message));
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }
    return true;
  }

  @Override
  public void setAllInputs(boolean set) {
    try {
      out.append(String.format("Setting all inputs to %s.\n", set));
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }
  }

  @Override
  public void listen(Features controller) {
    try {
      out.append("Listening for input from user...\n");
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }

    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      String[] tokens = line.split(" ");

      // calls respective callback functions
      switch (tokens[0]) {
        case "blur":
          controller.applyBlur(Integer.parseInt(tokens[1]));
          break;
        case "sharpen":
          controller.applySharpen(Integer.parseInt(tokens[1]));
          break;
        case "sepia":
          controller.applySepia(Integer.parseInt(tokens[1]));
          break;
        case "color-correct":
          controller.applyColorCorrect(Integer.parseInt(tokens[1]));
          break;
        case "levels-adjust":
          controller.applyLevelsAdjust(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
                  Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
          break;
        case "brighten":
          controller.applyBrighten(Integer.parseInt(tokens[1]));
          break;
        case "horizontal-flip":
          controller.applyHorizontalFlip();
          break;
        case "vertical-flip":
          controller.applyVerticalFlip();
          break;
        case "compress":
          controller.applyCompress(Integer.parseInt(tokens[1]));
          break;
        case "downscale":
          controller.applyDownscale(tokens[1], tokens[2]);
          break;
        case "load":
          controller.load(new File(tokens[1]));
          break;
        default:
          controller.applyGrayscale(tokens[0], Integer.parseInt(tokens[1]));
          break;
      }
    }
  }

  @Override
  public void refreshScreen(BufferedImage imageName, BufferedImage histogram) {
    try {
      out.append("Refreshing screen with new image and histogram.\n");
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }
  }

  @Override
  public boolean splitViewWindow(BufferedImage splitImage) {
    try {
      out.append("Confirming split view operation in a new window.\n");
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }

    return false;
  }
}
