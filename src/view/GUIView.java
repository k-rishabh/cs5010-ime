package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageController;

public class GUIView extends JFrame implements ImageView {

  // panels
  private final JPanel mainScreen;
  private final JPanel parentPanel;
  private final JPanel leftPanel;
  private final JPanel rightPanel;
  private final JPanel colorPanel;
  private final JPanel imageTFPanel;
  private final JPanel histogramPanel;
  private final JPanel imagePanel;
  private final JPanel splitPreviewPanel;

  // file menu
  private final JMenuItem loadItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JMenuItem undoItem;
  private FileNameExtensionFilter fileFilter;

  // split view transformations
  private final ButtonGroup splitView;
  private final JRadioButton previewMode;
  private final JRadioButton standardMode;
  private final JSlider splitViewSlider;

  private final JComboBox<String> greyscaleTypes;
  private final JButton grayscaleButton;
  private String selectedGrayscale;

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

  // histogram
  private final JLabel imageLabel;

  // image
  private final JLabel histogramLabel;

  // slider values
  private int brightnessValue;
  private int blackLevelValue;
  private int midLevelValue;
  private int whiteLevelValue;
  private int compressValue;
  private int splitValue;


  public GUIView() {
    super("Graphical Image Manipulation and Enhancement");

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
    GUIHelper helper = new GUIHelper(parentPanel, null);
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

    helper.createLabel("Split Preview Ratio:", 0, 0);
    splitViewSlider = helper.createSlider(0, 100, 50, 20, 10, 1, 0);
    splitViewSlider.addChangeListener(e -> {
      splitValue = splitViewSlider.getValue();
      splitViewSlider.setToolTipText(splitValue + "%");
    });

    previewMode = helper.createRadioButton("Preview Mode", false, 2, 0);
    standardMode = helper.createRadioButton("Standard Mode", true, 3, 0);
    splitView = new ButtonGroup();
    splitView.add(previewMode);
    splitView.add(standardMode);


    greyscaleTypes = new JComboBox<>(new String[]{"Red Component", "Blue Component",
            "Green Component", "Luma Component",
            "Intensity Component", "Value Component"});
    greyscaleTypes.setToolTipText("Select the grayscale filter to be applied.");
    greyscaleTypes.setSelectedItem(null);
    colorConstraints.gridx = 0;
    colorConstraints.gridy = 1;
    greyscaleTypes.addActionListener(e -> this.selectedGrayscale =
            (String) greyscaleTypes.getSelectedItem());
    colorPanel.add(greyscaleTypes, colorConstraints);

    grayscaleButton = helper.createButton("Apply",
            "Applies the selected grayscale filter on the image", 1, 1);

    blurButton = helper.createButton("Blur",
            "Blurs the image", 0, 2);

    sharpenButton = helper.createButton("Sharpen",
            "Sharpens the image", 1, 2);

    sepiaButton = helper.createButton("Sepia",
            "Applies a sepia color filter on the image", 2, 2);

    colorCorrectButton = helper.createButton("Color Correct",
            "Corrects the colors of the image by aligning the peaks", 3, 2);

    blackLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 0, 3);
    blackLevelSlider.addChangeListener(e -> {
      blackLevelValue = blackLevelSlider.getValue();
      blackLevelSlider.setToolTipText(String.valueOf(blackLevelValue));
    });

    midLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 1, 3);
    midLevelSlider.addChangeListener(e -> {
      midLevelValue = midLevelSlider.getValue();
      midLevelSlider.setToolTipText(String.valueOf(midLevelValue));
    });

    whiteLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 2, 3);
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

    brightnessValueSlider = helper.createSlider(-255, 255, 0, 100, 50, 0, 0);
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

    compressValueSlider = helper.createSlider(0, 100, 0, 20, 10, 0, 2);
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

    GridBagConstraints leftConstraints = helper.createConstraints(-1, GridBagConstraints.BOTH,
            0, 0, 0.1, 1, null);
    parentPanel.add(leftPanel, leftConstraints);

    // image
    rightPanel.setLayout(new BorderLayout());
    imagePanel = new JPanel();
    imagePanel.setSize(700, 720);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    rightPanel.add(scroller, BorderLayout.CENTER);

    GridBagConstraints rightConstraints = helper.createConstraints(-1, GridBagConstraints.BOTH,
            1, 0, 0.9, 1, null);
    parentPanel.add(rightPanel, rightConstraints);

    add(mainScreen);

    splitPreviewPanel = new JPanel(new GridBagLayout());
    splitPreviewPanel.setSize(1280, 720);

    GridBagConstraints splitPreviewPanelConstraints = helper.createConstraints(
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 1, 1,
            0, 0, new Insets(3, 3, 3, 3));

    helper.changePanel(splitPreviewPanel, splitPreviewPanelConstraints);
    splitPreviewLabel = helper.createLabel("Preview: ", 0, 0);

    applyFilterButton = helper.createButton("Apply",
            "Applies the previewed operation on entire image", 0, 2);
    splitPreviewPanel.add(applyFilterButton, splitPreviewPanelConstraints);

    cancelFilterButton = helper.createButton("Cancel",
            "Cancel the current operation", 2, 2);
    splitPreviewPanel.add(cancelFilterButton, splitPreviewPanelConstraints);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
  }

  private void setAllInputs(boolean set) {
    this.grayscaleButton.setEnabled(set);
    this.blurButton.setEnabled(set);
    this.sharpenButton.setEnabled(set);
    this.sepiaButton.setEnabled(set);
    this.colorCorrectButton.setEnabled(set);
    this.levelsAdjustButton.setEnabled(set);
    this.brightenButton.setEnabled(set);
    this.horizontalFlipButton.setEnabled(set);
    this.verticalFlipButton.setEnabled(set);
    this.compressButton.setEnabled(set);
    this.downscaleButton.setEnabled(set);

    // TODO: check
    this.leftPanel.setEnabled(set);
  }

  @Override
  public void displayMessage(String informMessage, String title, int type) {
    JOptionPane.showMessageDialog(mainScreen, informMessage, title, type);
  }

  @Override
  public boolean displayConfirmation(String confirmationMessage, String title) {
    return false;
  }

  @Override
  public void listen(ImageController controller) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println("Exception while setting theme!\n" + e);
    }

    setVisible(true);

    loadItem.addActionListener(e -> loadImage(controller));
    saveItem.addActionListener(e -> saveImage(controller));
    exitItem.addActionListener(e -> System.exit(0));

    grayscaleButton.addActionListener(e -> splitView(controller, "grayscale"));
    blurButton.addActionListener(e -> splitView(controller, "blur"));
    sharpenButton.addActionListener(e -> splitView(controller, "sharpen"));
    sepiaButton.addActionListener(e -> splitView(controller, "sepia"));
    colorCorrectButton.addActionListener(e -> splitView(controller, "color-correct"));
    levelsAdjustButton.addActionListener(e -> splitView(controller, "levels-adjust"));

    brightenButton.addActionListener(e -> nonSplitView(controller, "brighten"));
    horizontalFlipButton.addActionListener(e -> nonSplitView(controller, "horizontal-flip"));
    verticalFlipButton.addActionListener(e -> nonSplitView(controller, "vertical-flip"));
    compressButton.addActionListener(e -> nonSplitView(controller, "compress"));
    downscaleButton.addActionListener(e -> nonSplitView(controller, "downscale"));

    undoItem.addActionListener(e -> nonSplitView(controller, "undo"));
  }

  @Override
  public void refreshScreen(BufferedImage currentImage, BufferedImage histogram) {
    imageLabel.setIcon(new ImageIcon(currentImage));
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  private void loadImage(ImageController controller) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(fileFilter);
    int retValue = fChooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      List<String> tokens = new ArrayList<>();
      tokens.add("load");
      tokens.add(f.getPath());
      controller.applyImageTransform(tokens, 0);
      imagePanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));

      displayMessage("Successfully loaded the image!", "Success",
              JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void saveImage(ImageController controller) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      List<String> tokens = new ArrayList<>();
      tokens.add("save");
      tokens.add(f.getPath());
      controller.applyImageTransform(tokens, 0);
      displayMessage("Successfully saved the image!", "Success",
              JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void splitView(ImageController controller, String command) {
    List<String> tokens = new ArrayList<>();

    if (command.equals("grayscale")) {
      if (selectedGrayscale == null) {
        return;
      }

      switch (selectedGrayscale) {
        case "Red Component":
          command = "red-component";
          break;
        case "Green Component":
          command = "green-component";
          break;
        case "Blue Component":
          command = "blue-component";
          break;
        case "Luma Component":
          command = "luma-component";
          break;
        case "Intensity Component":
          command = "intensity-component";
          break;
        case "Value Component":
          command = "value-component";
          break;
      }
    }

    tokens.add(command);
    int ratio = splitRatio();

    if (command.equals("levels-adjust")) {
      tokens.add(String.valueOf(blackLevelValue));
      tokens.add(String.valueOf(midLevelValue));
      tokens.add(String.valueOf(whiteLevelValue));
    }

    controller.applyImageTransform(tokens, ratio);
  }

  private void nonSplitView(ImageController controller, String command) {
    List<String> tokens = new ArrayList<>();
    tokens.add(command);

    switch (command) {
      case "compress":
        tokens.add(String.valueOf(compressValue));
        controller.applyImageTransform(tokens, 0);
        break;
      case "downscale":
        tokens.add(heightValue.getText());
        tokens.add(widthValue.getText());
        controller.applyImageTransform(tokens, 0);
        break;
      case "brighten":
        System.out.println(brightnessValue);
        tokens.add(String.valueOf(brightnessValue));
        controller.applyImageTransform(tokens, 0);
        break;
      default:
        // horizontal and vertical flip
        controller.applyImageTransform(tokens, 0);
        break;
    }
  }

  @Override
  public boolean splitViewWindow(BufferedImage splitImage) {
    splitPreviewLabel.setIcon(new ImageIcon(splitImage));
    AtomicBoolean ret = new AtomicBoolean(false);
    JDialog dialog = new JDialog(this, "Split Preview", true);
    JPanel dialogPanel = new JPanel(new GridBagLayout());

    applyFilterButton.addActionListener(e -> {
      dialog.setEnabled(false);
      dialog.setVisible(false);
      ret.set(true);
    });

    cancelFilterButton.addActionListener(e -> {
      dialog.setEnabled(false);
      dialog.setVisible(false);
      ret.set(false);
    });

    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(1280, 720);
    dialog.setLocationRelativeTo(this);

    dialogPanel.add(splitPreviewPanel);
    dialog.add(dialogPanel);
    dialog.setVisible(true);

    return ret.get();
  }

  private int splitRatio() {
    if (previewMode.isSelected()) {
      return splitValue;
    }
    return 0;
  }
}
