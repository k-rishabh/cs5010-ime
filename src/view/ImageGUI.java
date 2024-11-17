package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

public class ImageGUI extends JFrame implements ViewInterface{

  private final JPanel imgPanel;
  private final JButton brightenButton;
  private final JButton verticalFlipButton;
  private final JButton horizontalFlipButton;
  private final JTextArea brightnessValue;
  private final JButton saveImageButton;
  private final FileNameExtensionFilter filter;
  private final JButton exitButton;
  private final JButton loadButton;
  private final JPanel mainPanel;

  public ImageGUI() {
    filter = new FileNameExtensionFilter("JPG, PNG & PPM Images", "jpg", "png","ppm");

    mainPanel = new JPanel(new GridBagLayout());


  }

  @Override
  public void refreshScreen(String imageName, String histogram) {
    
  }

  @Override
  public void splitView(String imageName) {

  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(mainPanel, errorMessage, "Error!!",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addFeatures(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());

    loadButton.addActionListener(evt -> loadImage(features));

    saveImageButton.addActionListener(evt -> saveImage(features));

    horizontalFlipButton.addActionListener(evt -> horizontalFlip(features));

    verticalFlipButton.addActionListener(evt -> verticalFlip(features));

    brightenButton.addActionListener(e -> brighten(features));
  }

  private void loadImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      try {
        features.load(f.getPath());
        imgPanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));

        brightenButton.setEnabled(true);
        brightnessValue.setEnabled(true);
        horizontalFlipButton.setEnabled(true);
        verticalFlipButton.setEnabled(true);

        saveImageButton.setEnabled(true);
        displayCompletionMessage(mainPanel, "Successfully loaded the image.");
      } catch (IOException | InputMismatchException e) {
        displayErrorMessage("Please provide a valid file and try again");
      }
    }
  }

  private void saveImage(Features features) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      try {
        String path = f.getAbsolutePath();
        features.save(path);
        displayCompletionMessage(mainPanel, "Successfully saved the image.");
      } catch (IOException | InputMismatchException e) {
        displayErrorMessage("Could not save the image. Please provide a valid path and try "
                + "again");
      }
    }
  }

  private void displayCompletionMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Operation Performed",
            JOptionPane.INFORMATION_MESSAGE);
  }

  private void horizontalFlip(Features features) {
    try {
      features.horizontalFlip();
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Horizontal flip operation could not be performed");
    }
  }

  private void verticalFlip(Features features) {
    try {
      features.verticalFlip();
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Vertical flip operation could not be performed");
    }
  }

  private void brighten(Features features) {
    try {
      int value = Integer.parseInt(brightnessValue.getText());
      features.brighten(value);
      brightnessValue.setText("");
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Brighten operation could not be performed");
      brightnessValue.setText("");
    } catch (NumberFormatException e) {
      displayErrorMessage("Provided brighten value is invalid. Please enter a valid number.");
      brightnessValue.setText("");
    }
  }
}
