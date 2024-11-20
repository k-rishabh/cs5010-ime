package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageController;

import static java.lang.Integer.parseInt;

public class ImageGUI extends JFrame implements ImageView {

  private GUIHelper helper;

  private final JMenuItem loadItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JMenuItem undoItem;

  private final JPanel mainScreen;
  private final JPanel parentPanel;
  private final JPanel leftPanel;
  private final JPanel rightPanel;
  private final JPanel colorPanel;
  private final JPanel imageTFPanel;
  //  private final JPanel levelsAdjustPanel;
//  private final JPanel downscalePanel;
  private final JPanel histogramPanel;
  private final JPanel imagePanel;
  private final JPanel splitPreviewPanel;

  // file menu
  private FileNameExtensionFilter fileFilter;

  // split view transformations
  private final ButtonGroup splitView;
  private final JRadioButton previewMode;
  private final JRadioButton standardMode;
  private final JSlider splitViewSlider;

  private final JComboBox<String> greyscaleTypes;
  private final JButton greyscaleButton;
  private String selectedGreyscale;

  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton sepiaButton;
  private final JButton colorCorrectButton;
  private final JButton levelsAdjustButton;
  private final JSlider blackLevelSlider;
  private final JSlider midLevelSlider;
  private final JSlider whiteLevelSlider;

  // non-split view transformations
  private final JSlider brightnessValueSlider;
  private final JButton brightenButton;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JSlider compressValueSlider;
  private final JButton compressButton;
  private final JTextField heightValue;
  private final JTextField widthValue;
  private final JButton downscaleButton;

  private final JLabel splitPreviewLabel;
  private final JButton cancelFilterButton;
  private final JButton applyFilterButton;
  private final AtomicBoolean splitOperation =new AtomicBoolean(false);


  // histogram
  private final JLabel imageLabel;

  // image
  private final JLabel histogramLabel;

  // slider values
  private int brightnessValue;
  private int blacklevelValue;
  private int midLevelValue;
  private int whiteLevelValue;
  private int compressValue;
  private int splitValue;


  public ImageGUI() {
    super("Image Processing Application");
    // set up file menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    fileFilter = new FileNameExtensionFilter(".png, .jpg, .ppm",
            "jpg", "png", "ppm");

    loadItem = new JMenuItem("Load Image");
    saveItem = new JMenuItem("Save As");
    undoItem = new JMenuItem("Undo");
    exitItem = new JMenuItem("Exit");

    fileMenu.add(loadItem);
    fileMenu.add(saveItem);
    fileMenu.add(undoItem);
    fileMenu.add(exitItem);
    setJMenuBar(menuBar);

    // main screen
    mainScreen = new JPanel(new GridBagLayout());

    parentPanel = new JPanel(new GridBagLayout());
    helper = new GUIHelper(parentPanel, null);
    GridBagConstraints c = helper.createConstraints(-1, GridBagConstraints.BOTH,
            0, 1, 1, 0.95, new Insets(1, 1, 1, 1));

    mainScreen.add(parentPanel, c);

    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//    leftPanel.setPreferredSize(new Dimension(500, 600));
    rightPanel = new JPanel(new GridBagLayout());

    // color transformations
    colorPanel = new JPanel(new GridBagLayout());
    colorPanel.setBorder(BorderFactory.createTitledBorder("Color Operations"));
    GridBagConstraints colorConstraints = helper.createConstraints(
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 1, 1, 0, 0,
            new Insets(3, 3, 3, 3));

    helper.changePanel(colorPanel, colorConstraints);

    JLabel splitLabel = new JLabel("Split Preview Ratio:");
    colorConstraints.gridx = 0;
    colorConstraints.gridy = 0;
    colorPanel.add(splitLabel, colorConstraints);
    splitViewSlider = helper.createSlider(0, 100, 50, 1, 0);
    splitViewSlider.addChangeListener(e -> {
      splitValue = splitViewSlider.getValue();
      splitViewSlider.setToolTipText(splitValue + "%");
    });
    previewMode = helper.createRadioButton("Preview Mode", false, 2, 0);
    standardMode = helper.createRadioButton("Standard Mode", true, 3, 0);
    splitView = new ButtonGroup();
    splitView.add(previewMode);
    splitView.add(standardMode);


    greyscaleTypes = new JComboBox<>(new String[]{"Luma", "Intensity", "Value", "Red Component",
            "Blue Component", "Green Component"});
    greyscaleTypes.setToolTipText("Select the grayscale filter to be applied.");
    greyscaleTypes.setSelectedItem(null);
    colorConstraints.gridx = 0;
    colorConstraints.gridy = 1;
    greyscaleTypes.addActionListener(e -> this.selectedGreyscale =
            (String) greyscaleTypes.getSelectedItem());
    colorPanel.add(greyscaleTypes, colorConstraints);

    greyscaleButton = helper.createButton("Apply",
            "Applies the selected grayscale filter on the image", 1, 1);

    blurButton = helper.createButton("Blur",
            "Blurs the image", 0, 2);

    sharpenButton = helper.createButton("Sharpen",
            "Sharpens the image", 1, 2);

    sepiaButton = helper.createButton("Sepia",
            "Applies a sepia color filter on the image", 2, 2);

    colorCorrectButton = helper.createButton("Color Correct",
            "Corrects the colors of the image by aligning the peaks", 3, 2);

    blackLevelSlider = helper.createSlider(0, 255, 0, 0, 3);
    blackLevelSlider.addChangeListener(e -> {
      blacklevelValue = blackLevelSlider.getValue();
      blackLevelSlider.setToolTipText(String.valueOf(blacklevelValue));
    });

    midLevelSlider = helper.createSlider(0, 255, 0, 1, 3);
    midLevelSlider.addChangeListener(e -> {
      midLevelValue = midLevelSlider.getValue();
      midLevelSlider.setToolTipText(String.valueOf(midLevelValue));
    });

    whiteLevelSlider = helper.createSlider(0, 255, 0, 2, 3);
    whiteLevelSlider.addChangeListener(e -> {
      whiteLevelValue = whiteLevelSlider.getValue();
      whiteLevelSlider.setToolTipText(String.valueOf(whiteLevelValue));
    });

    levelsAdjustButton = helper.createButton("Levels Adjust",
            "Adjusts the black, mid, and white components of the image", 3, 3);

    leftPanel.add(colorPanel);

    // non-split view operations
    imageTFPanel = new JPanel(new GridBagLayout());
    imageTFPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    GridBagConstraints imageTFconstraints = helper.createConstraints(GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, 1, 1, 0, 0,
            new Insets(1, 1, 1, 1));

    helper.changePanel(imageTFPanel, imageTFconstraints);

    brightnessValueSlider = helper.createSlider(-255, 255, 0, 0, 0);
    brightnessValueSlider.addChangeListener(e -> {
      brightnessValue = brightnessValueSlider.getValue();
      brightnessValueSlider.setToolTipText(String.valueOf(brightnessValue));
    });

    brightenButton = helper.createButton("Brighten",
            "Brightens the image by the given amount", 1, 0);

    horizontalFlipButton = helper.createButton("Flip Horizontally",
            "Flips the image horizontally", 0, 1);

    verticalFlipButton = helper.createButton("Flip Vertically",
            "Flips the image vertically", 1, 1);

    compressValueSlider = helper.createSlider(0, 100, 0, 0, 2);
    compressValueSlider.addChangeListener(e -> {
      compressValue = compressValueSlider.getValue();
      compressValueSlider.setToolTipText(String.valueOf(compressValue));
    });

    compressButton = helper.createButton("Compress",
            "Compresses the image by the given amount", 1, 2);

    heightValue = helper.createTextField("Enter Height", 3, 0, 3);
    widthValue = helper.createTextField("Enter Width", 3, 1, 3);
    downscaleButton = helper.createButton("Downscale",
            "Downscales the image by the given height and width", 2, 3);

    leftPanel.add(imageTFPanel);

    // histogram
    histogramLabel = new JLabel();
    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setSize(300, 300);
    histogramPanel.setPreferredSize(new Dimension(350, 350));
    histogramPanel.add(histogramLabel);
    leftPanel.add(histogramPanel);

    GridBagConstraints leftConstraints = new GridBagConstraints();
    leftConstraints.gridx = 0;
    leftConstraints.gridy = 0;
    leftConstraints.weightx = 0.1;
    leftConstraints.weighty = 1;
    leftConstraints.fill = GridBagConstraints.BOTH;
    parentPanel.add(leftPanel, leftConstraints);

    // image
    rightPanel.setLayout(new BorderLayout());
    imagePanel = new JPanel();
    imagePanel.setSize(700, 720);
//    imagePanel.setPreferredSize(new Dimension(700, 720));
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);
    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    rightPanel.add(scroller, BorderLayout.CENTER);

    GridBagConstraints rightConstraints = new GridBagConstraints();
    rightConstraints.gridx = 1;
    rightConstraints.gridy = 0;

    rightConstraints.weightx = 0.9;
    rightConstraints.weighty = 1;
    rightConstraints.fill = GridBagConstraints.BOTH;
    parentPanel.add(rightPanel, rightConstraints);

    add(mainScreen);

    splitPreviewPanel = new JPanel(new GridBagLayout());
    splitPreviewPanel.setBorder(BorderFactory.createTitledBorder("Split preview"));
    GridBagConstraints splitPreviewPanelConstraints = new GridBagConstraints();
    splitPreviewPanelConstraints.anchor = GridBagConstraints.CENTER;
    splitPreviewPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    splitPreviewPanelConstraints.weightx = 1;
    splitPreviewPanelConstraints.weighty = 1;
    splitPreviewPanelConstraints.insets = new Insets(3, 3, 3, 3);
    splitPreviewPanel.setSize(960, 720);

    splitPreviewLabel = new JLabel();
    splitPreviewPanel.add(splitPreviewLabel);

    applyFilterButton = new JButton("Apply");
    applyFilterButton.setToolTipText("Apply the current operation on entire image.");
    splitPreviewPanelConstraints.gridx = 2;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(applyFilterButton, splitPreviewPanelConstraints);

    cancelFilterButton = new JButton("Cancel");
    cancelFilterButton.setToolTipText("Cancel the current operation.");
    splitPreviewPanelConstraints.gridx = 3;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(cancelFilterButton, splitPreviewPanelConstraints);




    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
    setVisible(true);


  }

  @Override
  public void refreshScreen(BufferedImage currentImage, BufferedImage histogram) {
    imageLabel.setIcon(new ImageIcon(currentImage));
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  private void nonSplitView(ImageController features, String command) {
    List<String> tokens = new ArrayList<>();
    try {
      switch (command) {
        case "Horizontal Flip":
          tokens.add("horizontal-flip");
          features.applyImageTransform(tokens, 0);
          break;
        case "Vertical Flip":
          tokens.add("vertical-flip");
          features.applyImageTransform(tokens, 0);
          break;
        case "Compress":
          tokens.add("compress");
          tokens.add(String.valueOf(compressValue));
          features.applyImageTransform(tokens, 0);
          break;
        case "Downscale":
          tokens.add("downscale");
          tokens.add(heightValue.getText());
          tokens.add(widthValue.getText());
          features.applyImageTransform(tokens, 0);
          break;
        case "Brighten":
          tokens.add("brighten");
          System.out.println(brightnessValue);
          tokens.add(String.valueOf(brightnessValue));
          features.applyImageTransform(tokens, 0);
          break;
      }
    } catch (InputMismatchException e) {
      displayErrorMessage("Downscale operation could not be performed");
    }
  }

  private void splitView(ImageController features, String command) {
    if (command == "Greyscale") {
      command = selectedGreyscale;
    }
    List<String> tokens = new ArrayList<>();
    int ratio = splitRatio();
    try {
      switch (command) {
        case "Red Component":
          tokens.add("red-component");
          features.applyImageTransform(tokens, ratio);
//          if(ratio>0) {
//            this.showSplitPreviewDialog(features,List.of(new String[]{"red-component"}));
//          }
          break;
        case "Green Component":
          tokens.add("green-component");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Blue Component":
          tokens.add("blue-component");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Luma":
          tokens.add("luma-component");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Intensity":
          tokens.add("intensity-component");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Value":
          tokens.add("value-component");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Sepia":
          tokens.add("sepia");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Color Correct":
          tokens.add("color-correct");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Levels Adjust":
          tokens.add("levels-adjust");
          tokens.add(String.valueOf(blacklevelValue));
          tokens.add(String.valueOf(midLevelValue));
          tokens.add(String.valueOf(whiteLevelValue));
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Blur":
          tokens.add("blur");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;
        case "Sharpen":
          tokens.add("sharpen");
          features.applyImageTransform(tokens, ratio);
//          this.showSplitPreviewDialog();
          break;

      }
    } catch (InputMismatchException e) {
      displayErrorMessage(command + "operation could not be performed");
    }
  }

  private void undoImage(ImageController features) {
    try {
      List<String> tokens = new ArrayList<>();
      tokens.add("undo");
      features.applyImageTransform(tokens, 0);
    }
    catch (InputMismatchException e) {
      displayErrorMessage("Undo operation could not be performed");
    }
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(mainScreen, errorMessage, "Error!!",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addFeatures(ImageController features) {
    exitItem.addActionListener(e -> System.exit(0));
    loadItem.addActionListener(e -> loadImage(features));
    saveItem.addActionListener(e -> saveImage(features));
    undoItem.addActionListener(e -> undoImage(features));

    brightenButton.addActionListener(e -> nonSplitView(features, "Brighten"));
    compressButton.addActionListener(e -> nonSplitView(features, "Compress"));
    downscaleButton.addActionListener(e -> nonSplitView(features, "Downscale"));
    horizontalFlipButton.addActionListener(e -> nonSplitView(features, "Horizontal Flip"));
    verticalFlipButton.addActionListener(e -> nonSplitView(features, "Vertical Flip"));

    blurButton.addActionListener(e -> splitView(features, "Blur"));
    sharpenButton.addActionListener(e -> splitView(features, "Sharpen"));
    greyscaleButton.addActionListener(e -> splitView(features, "Greyscale"));
    sepiaButton.addActionListener(e -> splitView(features, "Sepia"));
    colorCorrectButton.addActionListener(e -> splitView(features, "ColorCorrect"));
    levelsAdjustButton.addActionListener(e -> splitView(features, "Levels Adjust"));

  }

  @Override
  public void splitView(BufferedImage splitImage) {
    splitPreviewLabel.setIcon(new ImageIcon(splitImage));
  }

  private void loadImage(ImageController features) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(fileFilter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      try {
        List<String> tokens = new ArrayList<>();
        tokens.add("load");
        tokens.add(f.getPath());
        features.applyImageTransform(tokens, 0);
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));
        brightenButton.setEnabled(true);
        brightnessValueSlider.setEnabled(true);
        horizontalFlipButton.setEnabled(true);
        verticalFlipButton.setEnabled(true);
        displayCompletionMessage(mainScreen, "Successfully loaded the image.");
      } catch (InputMismatchException e) {
        displayErrorMessage("Please provide a valid file and try again");
      }
    }
  }

  private void saveImage(ImageController features) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      try {
        List<String> tokens = new ArrayList<>();
        tokens.add("save");
        tokens.add(f.getPath());
        features.applyImageTransform(tokens, 0);
        displayCompletionMessage(mainScreen, "Successfully saved the image.");
      } catch (InputMismatchException e) {
        displayErrorMessage("Could not save the image. Please provide a valid path and try "
                + "again");
      }
    }
  }

  private void displayCompletionMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Operation Performed",
            JOptionPane.INFORMATION_MESSAGE);
  }

  private int splitRatio() {
    if (previewMode.isSelected()) {
      return splitValue;
    }
    return 0;
  }

    private void showSplitPreviewDialog(ImageController features,List<String> tokens) {
    JDialog dialog = new JDialog(this, "Split Preview", true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(200, 100);
    dialog.setLocationRelativeTo(this);
    JPanel dialogPanel = new JPanel(new GridBagLayout());
    dialogPanel.add(splitPreviewPanel);
    dialog.add(dialogPanel);
    dialog.setVisible(true);
    applyFilterButton.addActionListener(e -> {
        features.applyImageTransform(List.of(new String[]{"undo"}), 0);
      System.out.println(tokens);
        features.applyImageTransform(tokens, 0);
        dialog.setEnabled(false);
      });
      cancelFilterButton.addActionListener(e -> {
        features.applyImageTransform(List.of(new String[]{"undo"}), 0);
        dialog.setEnabled(false);
      });

  }
}
