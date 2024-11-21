package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import controller.Features;

public class MockGUI implements ImageView {
  Scanner sc;
  Readable in;
  Appendable out;

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

  }

  @Override
  public void listen(Features controller) {
    try {
      out.append("Listening for input from user...\n");
    } catch (IOException e) {
      System.out.println("ERROR: Unable to append to out!\n");
    }

    while (sc.hasNextLine()) {
      List<String> tokens = new ArrayList<>(Arrays.asList(sc.nextLine().split(" ")));

      int n = tokens.size();
      int ratio = 0;
      if (n > 2 && tokens.get(n - 2).equals("split")) {
        ratio = Integer.parseInt(tokens.get(n - 1));
        tokens.remove(n - 1);
        tokens.remove(n - 1);
      }

//      controller.applyImageTransform(tokens, ratio);
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
