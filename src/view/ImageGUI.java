package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.ImageController;

import static java.lang.Integer.parseInt;

public class ImageGUI extends JFrame implements ImageView {

private JMenuItem loadItem, saveItem, exitItem,undoItem;
  private JPanel mainPanel, imagePanel, leftPane, rightPane, operationPanel, histogramPanel;
  private JLabel imageLabel, histogramLabel;
  private JTextField brightnessValue, compressValue, blackLevelAdjustValue, midLevelAdjustValue, whiteLevelAdjustValue, heightValue, widthValue;
  private JButton brightenButton, compressButton, horizontalFlipButton, verticalFlipButton, sepiaButton, colorCorrectButton, levelAdjustButton, blurButton;
  private JComboBox<String> greyscaleTypes;
  private JButton greyscaleExecuteButton, downscaleButton;
  JButton sharpenButton;
  JRadioButton toggleButton;
  private JPanel splitPreviewPanel;
  private FileNameExtensionFilter filter;
  private JTextField splitPreviewPercentageValue;
  private JButton applyFilterButton;
  private String selectedGreyscale, selectedComponent, selectedFilter;



  public ImageGUI() {

    filter = new FileNameExtensionFilter("JPG, PNG, & PPM Images", "jpg", "png", "ppm");
//    this.model = model;
    // Set up menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    loadItem = new JMenuItem("Open...");
    saveItem = new JMenuItem("Save As");
    undoItem = new JMenuItem("Undo");
    exitItem = new JMenuItem("Exit");

    fileMenu.add(loadItem);
    fileMenu.add(saveItem);
    fileMenu.add(undoItem);
    fileMenu.add(exitItem);
    setJMenuBar(menuBar);

    mainPanel = new JPanel(new GridBagLayout());

    JPanel mainScreen = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(5, 2, 5, 2);
    c.gridx = 0;
    c.gridy = 1;
    c.weighty = 0.95;
    mainPanel.add(mainScreen, c);

    leftPane = new JPanel();
    leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
    rightPane = new JPanel(new GridBagLayout());


    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));



    JPanel basicOperationsPanel = new JPanel(new GridBagLayout());
    basicOperationsPanel.setBorder(BorderFactory.createTitledBorder("Basic Operations"));
    GridBagConstraints basicOperationsPanelConstraints = new GridBagConstraints();
    basicOperationsPanelConstraints.anchor = GridBagConstraints.CENTER;
    basicOperationsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    basicOperationsPanelConstraints.weightx = 1;
    basicOperationsPanelConstraints.weighty = 1;
    basicOperationsPanelConstraints.insets = new Insets(3, 3, 3, 3);

    brightnessValue = new JTextField("0", 3);
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 1;
    basicOperationsPanel.add(brightnessValue, basicOperationsPanelConstraints);

    brightenButton = new JButton("Execute Brightness");
    brightenButton.setToolTipText("Apply brightness");
    basicOperationsPanelConstraints.gridx = 1;

    basicOperationsPanel.add(brightenButton, basicOperationsPanelConstraints);

    compressValue = new JTextField("0", 3);
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 2;
    basicOperationsPanel.add(compressValue, basicOperationsPanelConstraints);

    compressButton = new JButton("Compress");
    compressButton.setToolTipText("Compress image");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanel.add(compressButton, basicOperationsPanelConstraints);


    horizontalFlipButton = new JButton("Horizontal Flip");
    horizontalFlipButton.setToolTipText("Apply horizontal flip");
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 4;
    basicOperationsPanel.add(horizontalFlipButton, basicOperationsPanelConstraints);

    verticalFlipButton = new JButton("Vertical Flip");
    verticalFlipButton.setToolTipText("Apply vertical flip");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 4;
    basicOperationsPanel.add(verticalFlipButton, basicOperationsPanelConstraints);

    sepiaButton = new JButton("Sepia");
    sepiaButton.setToolTipText("Apply Sepia Filter");
    basicOperationsPanelConstraints.gridx = 0;
    basicOperationsPanelConstraints.gridy = 5;
    basicOperationsPanel.add(sepiaButton, basicOperationsPanelConstraints);

    colorCorrectButton = new JButton("Color Correct");
    colorCorrectButton.setToolTipText("Apply Color correction");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 5;
    basicOperationsPanel.add(colorCorrectButton, basicOperationsPanelConstraints);

    operationPanel.add(basicOperationsPanel);

    JPanel colorTransformAndFilterPanel = new JPanel();
    colorTransformAndFilterPanel.setBorder(BorderFactory.createTitledBorder("Greyscale & Filters"));
    JPanel colorTransformAndFilterControlsPanel = new JPanel(new GridBagLayout());

    GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
    controlsPanelConstraints.anchor = GridBagConstraints.CENTER;
    controlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    controlsPanelConstraints.weightx = 1;
    controlsPanelConstraints.weighty = 1;
    controlsPanelConstraints.insets = new Insets(3, 3, 3, 3);


    greyscaleTypes = new JComboBox<>(new String[]{"Select Greyscale", "Luma", "Intensity", "Value","Red Component", "Blue Component", "Green Component"});
    greyscaleTypes.setToolTipText("Select Greyscale");
    greyscaleTypes.addActionListener(e -> this.selectedGreyscale = (String) greyscaleTypes.getSelectedItem());
    controlsPanelConstraints.gridx = 0;
    colorTransformAndFilterControlsPanel.add(greyscaleTypes, controlsPanelConstraints);

    greyscaleTypes.addActionListener(e -> this.selectedGreyscale =
            (String) greyscaleTypes.getSelectedItem());

    greyscaleExecuteButton = new JButton("Execute");
    greyscaleExecuteButton.setToolTipText("Execute the selected greyscale type operation");
    controlsPanelConstraints.gridx = 1;

    colorTransformAndFilterControlsPanel.add(greyscaleExecuteButton, controlsPanelConstraints);



    blurButton = new JButton("Blur");
    blurButton.setToolTipText("Apply Blur Filter");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 1;
    colorTransformAndFilterControlsPanel.add(blurButton, controlsPanelConstraints);

    sharpenButton = new JButton("Sharpen");
    sharpenButton.setToolTipText("Apply Sharpen Filter");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 1;
    colorTransformAndFilterControlsPanel.add(sharpenButton, basicOperationsPanelConstraints);

    colorTransformAndFilterPanel.add(colorTransformAndFilterControlsPanel);
    operationPanel.add(colorTransformAndFilterPanel);





    JPanel levelAdjustPanel = new JPanel(new GridBagLayout());
    levelAdjustPanel.setBorder(BorderFactory.createTitledBorder("Level Adjustment"));

    GridBagConstraints levelAdjustPanelConstraints = new GridBagConstraints();
    levelAdjustPanelConstraints.anchor = GridBagConstraints.CENTER;
    levelAdjustPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    levelAdjustPanelConstraints.weightx = 1;
    levelAdjustPanelConstraints.weighty = 1;
    levelAdjustPanelConstraints.insets = new Insets(3, 3, 3, 3);

    blackLevelAdjustValue = new JTextField("Enter black", 3);
    levelAdjustPanelConstraints.gridx = 1;
    levelAdjustPanelConstraints.gridy = 0;
    levelAdjustPanel.add(blackLevelAdjustValue, levelAdjustPanelConstraints);

    midLevelAdjustValue = new JTextField("Enter mid", 3);
    levelAdjustPanelConstraints.gridx = 2;
    levelAdjustPanel.add(midLevelAdjustValue, levelAdjustPanelConstraints);

    whiteLevelAdjustValue = new JTextField("Enter white", 3);
    levelAdjustPanelConstraints.gridx = 3;
    levelAdjustPanel.add(whiteLevelAdjustValue, levelAdjustPanelConstraints);

    levelAdjustButton = new JButton("Level Adjust");
    levelAdjustButton.setToolTipText("Apply level adjust");
    levelAdjustPanelConstraints.gridx = 4;
    levelAdjustPanel.add(levelAdjustButton, levelAdjustPanelConstraints);

    operationPanel.add(levelAdjustPanel);

    JPanel downscalePanel = new JPanel(new GridBagLayout());
    downscalePanel.setBorder(BorderFactory.createTitledBorder("Downscaling"));

    GridBagConstraints downscalePanelConstraints = new GridBagConstraints();
    downscalePanelConstraints.anchor = GridBagConstraints.CENTER;
    downscalePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    downscalePanelConstraints.weightx = 1;
    downscalePanelConstraints.weighty = 1;
    downscalePanelConstraints.insets = new Insets(3, 3, 3, 3);

    heightValue = new JTextField("Enter Height", 3);
    downscalePanelConstraints.gridx = 1;
    downscalePanelConstraints.gridy = 0;
    downscalePanel.add(heightValue, downscalePanelConstraints);

    widthValue = new JTextField("Enter Width", 3);
    downscalePanelConstraints.gridx = 2;
    downscalePanel.add(widthValue, downscalePanelConstraints);

    downscaleButton = new JButton("Downscale");
    downscaleButton.setToolTipText("Apply Downscaling");
    downscalePanelConstraints.gridx = 3;
    downscalePanel.add(downscaleButton, downscalePanelConstraints);

    operationPanel.add(downscalePanel);

    splitPreviewPanel = new JPanel(new GridBagLayout());
    splitPreviewPanel.setBorder(BorderFactory.createTitledBorder("Split Preview"));
    GridBagConstraints splitPreviewPanelConstraints = new GridBagConstraints();
    splitPreviewPanelConstraints.anchor = GridBagConstraints.CENTER;
    splitPreviewPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    splitPreviewPanelConstraints.weightx = 1;
    splitPreviewPanelConstraints.weighty = 1;
    splitPreviewPanelConstraints.insets = new Insets(2, 2, 2, 2);





    splitPreviewPercentageValue = new JTextField("0", 3);
    splitPreviewPanelConstraints.gridx = 0;
    splitPreviewPanelConstraints.gridy = 0;
    splitPreviewPanel.add(splitPreviewPercentageValue, splitPreviewPanelConstraints);

    toggleButton = new JRadioButton();
    toggleButton.setText("Split Preview");
    splitPreviewPanelConstraints.gridx = 3;
    splitPreviewPanel.add(toggleButton, splitPreviewPanelConstraints);

    operationPanel.add(splitPreviewPanel);

    leftPane.add(operationPanel);

    histogramLabel = new JLabel();
    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setSize(300, 300);
    histogramPanel.setPreferredSize(new Dimension(350, 350));
    histogramPanel.add(histogramLabel);
    leftPane.add(histogramPanel);

    GridBagConstraints leftConstraints = new GridBagConstraints();
    leftConstraints.gridx = 0;
    leftConstraints.gridy = 0;
    leftConstraints.weightx = 0.1;
    leftConstraints.weighty = 1;
    leftConstraints.fill = GridBagConstraints.BOTH;
    mainScreen.add(leftPane, leftConstraints);

    rightPane.setLayout(new BorderLayout());
    imagePanel = new JPanel();
    imagePanel.setSize(960, 720);
    imagePanel.setPreferredSize(new Dimension(350, 350));
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);
    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    rightPane.add(scroller, BorderLayout.CENTER);

    GridBagConstraints rightConstraints = new GridBagConstraints();
    rightConstraints.gridx = 1;
    rightConstraints.gridy = 0;

    rightConstraints.weightx =0.9;
    rightConstraints.weighty = 1;
    rightConstraints.fill = GridBagConstraints.BOTH;
    mainScreen.add(rightPane, rightConstraints);

    add(mainPanel);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 500));
    pack();
    setVisible(true);


  }

//  private void showSplitPreviewDialog() {
//    JDialog dialog = new JDialog(this, "Split Preview", true);
//    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//    dialog.setSize(200, 100);
//    dialog.setLocationRelativeTo(this);
//
//    JPanel dialogPanel = new JPanel(new GridBagLayout());
//
//    dialogPanel.add(splitPreviewPanel);
//
//    dialog.add(dialogPanel);
//    dialog.setVisible(true);
//  }


  @Override
  public void refreshScreen(BufferedImage currentImage, BufferedImage histogram) {
    imageLabel.setIcon(new ImageIcon(currentImage));
    histogramLabel.setIcon(new ImageIcon(histogram));
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
  public void addFeatures(ImageController features) {


    exitItem.addActionListener(e -> System.exit(0));
    loadItem.addActionListener(e -> loadImage(features));
    saveItem.addActionListener(evt -> saveImage(features));

    blurButton.addActionListener(e -> blur(features));
    sharpenButton.addActionListener(e -> sharpen(features));
    horizontalFlipButton.addActionListener(e -> horizontalFlip(features));
    verticalFlipButton.addActionListener(e -> verticalFlip(features));
    greyscaleExecuteButton.addActionListener(e -> greyScale(features));
    sepiaButton.addActionListener(e -> sepia(features));
    brightenButton.addActionListener(e -> brighten(features));
    colorCorrectButton.addActionListener(e -> colorCorrect(features));
    levelAdjustButton.addActionListener(e -> levelAdjust(features));
    downscaleButton.addActionListener(e -> downscale(features));
    compressButton.addActionListener(e -> compress(features));

  }

  private void loadImage(ImageController features) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(filter);
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
        brightnessValue.setEnabled(true);
        horizontalFlipButton.setEnabled(true);
        verticalFlipButton.setEnabled(true);
        displayCompletionMessage(mainPanel, "Successfully loaded the image.");
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
        features.applyImageTransform(tokens,0);
        displayCompletionMessage(mainPanel, "Successfully saved the image.");
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
//
  private void horizontalFlip(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try {
      tokens.add("horizontal-flip");
      features.applyImageTransform(tokens,0);
    } catch (InputMismatchException e) {
      displayErrorMessage("Horizontal flip operation could not be performed");
    }
  }


  private void sepia(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try {
      tokens.add("sepia");
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e) {
      displayErrorMessage("Sepia operation could not be performed");
    }
  }

  private void greyScale(ImageController features){
    List<String> tokens = new ArrayList<>();
    int ratio = splitRatio();
    try{
      switch(selectedGreyscale){
        case "Red Component":
          tokens.add("red-component");
          features.applyImageTransform(tokens,ratio);
          break;
        case "Green Component":
          tokens.add("green-component");
          features.applyImageTransform(tokens,ratio);
          break;
        case "Blue Component":
          tokens.add("blue-component");
          features.applyImageTransform(tokens,ratio);
          break;
        case "Luma":
          tokens.add("luma-component");
          features.applyImageTransform(tokens,ratio);
          break;
        case "Intensity":
          tokens.add("intensity-component");
          features.applyImageTransform(tokens,ratio);
          break;
        case "Value":
          tokens.add("value-component");
          features.applyImageTransform(tokens,ratio);
          break;
      }
    } catch (InputMismatchException e){
      displayErrorMessage(selectedGreyscale + "operation could not be performed");
    }
  }

  private void blur(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("blur");
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e){
      displayErrorMessage("Blur operation could not be performed");
    }
  }

  private void sharpen(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("sharpen");
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e){
      displayErrorMessage("Sharpen operation could not be performed");
    }
  }


  private void colorCorrect(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("color-correct");
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e){
      displayErrorMessage("Color Correct operation could not be performed");
    }
  }

  private void levelAdjust(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("levels-adjust");
      tokens.add(blackLevelAdjustValue.getText());
      tokens.add(midLevelAdjustValue.getText());
      tokens.add(whiteLevelAdjustValue.getText());
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e){
      displayErrorMessage("Levels Adjust operation could not be performed");
    }
  }

  private void downscale(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("downscale");
      tokens.add(heightValue.getText());
      tokens.add(widthValue.getText());
      features.applyImageTransform(tokens,splitRatio());
    } catch (InputMismatchException e){
      displayErrorMessage("Downscale operation could not be performed");
    }
  }


  private void compress(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try{
      tokens.add("compress");
      tokens.add(compressValue.getText());
      features.applyImageTransform(tokens,0);
    } catch (InputMismatchException e){
      displayErrorMessage("Compress operation could not be performed");
    }
  }


  private void verticalFlip(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try {
      tokens.add("vertical-flip");
      features.applyImageTransform(tokens,0);
    } catch (InputMismatchException e) {
      displayErrorMessage("Vertical flip operation could not be performed");
    }
  }

  private void brighten(ImageController features) {
    List<String> tokens = new ArrayList<>();
    try {
      tokens.add("brighten");
      tokens.add(brightnessValue.getText());
      features.applyImageTransform(tokens,0);
      brightnessValue.setText("");
    } catch (InputMismatchException e) {
      displayErrorMessage("Brighten operation could not be performed");
      brightnessValue.setText("");
    } catch (NumberFormatException e) {
      displayErrorMessage("Provided brighten value is invalid. Please enter a valid number.");
      brightnessValue.setText("");
    }
  }

  private int splitRatio(){
    if(toggleButton.isSelected()){
      return parseInt(splitPreviewPercentageValue.getText());
    }
    return 0;
  }
}
