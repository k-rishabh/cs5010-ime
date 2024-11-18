package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageController;

public class ImageGUI extends JFrame implements ImageView {

private JMenuItem loadItem, saveItem, exitItem,undoItem;
  private JPanel mainPanel, imagePanel, leftPane, rightPane, operationPanel, histogramPanel;
  private JLabel imageLabel, histogramLabel;
  private JTextField brightnessValue, compressValue, blackLevelAdjustValue, midLevelAdjustValue, whiteLevelAdjustValue;
  private JButton brightenButton, compressButton, horizontalFlipButton, verticalFlipButton, sepiaButton, colorCorrectButton, levelAdjustButton, blurButton;
  private JComboBox<String> greyscaleTypes, componentTypes, filterTypes;
  private JButton greyscaleExecuteButton, componentExecuteButton, filterTypesButton;
  private JPanel splitPreviewPanel;
  private FileNameExtensionFilter filter;
  private JTextField splitPreviewPercentageValue;
  private JButton splitPreviewButton, applyFilterButton, cancelFilterButton;
  private final ViewAdapter model;
  private String selectedGreyscale, selectedComponent, selectedFilter;



  public ImageGUI(ViewAdapter model) {

    filter = new FileNameExtensionFilter("JPG, PNG, & PPM Images", "jpg", "png", "ppm");

    this.model = model;
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

    splitPreviewPanel = new JPanel(new GridBagLayout());
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

    applyFilterButton = new JButton("Apply");
    applyFilterButton.setToolTipText("Apply the current operation on entire image.");
    splitPreviewPanelConstraints.gridx = 2;
    splitPreviewPanel.add(applyFilterButton, splitPreviewPanelConstraints);

//    operationPanel.add(splitPreviewPanel);

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
    brightenButton.addActionListener(e -> showSplitPreviewDialog());

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
    sepiaButton.addActionListener(e -> showSplitPreviewDialog());
    basicOperationsPanel.add(sepiaButton, basicOperationsPanelConstraints);

    colorCorrectButton = new JButton("Color Correct");
    colorCorrectButton.setToolTipText("Apply Color correction");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 5;
    colorCorrectButton.addActionListener(e -> showSplitPreviewDialog());
    basicOperationsPanel.add(colorCorrectButton, basicOperationsPanelConstraints);

    operationPanel.add(basicOperationsPanel);

    JPanel colorTransformAndFilterPanel = new JPanel();
    colorTransformAndFilterPanel.setBorder(BorderFactory.createTitledBorder("Color Transform & Filters"));
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
    greyscaleExecuteButton.addActionListener(e -> showSplitPreviewDialog());

    colorTransformAndFilterControlsPanel.add(greyscaleExecuteButton, controlsPanelConstraints);



    blurButton = new JButton("Blur");
    blurButton.setToolTipText("Apply Blur Filter");
    controlsPanelConstraints.gridx = 0;
    controlsPanelConstraints.gridy = 1;
    blurButton.addActionListener(e -> showSplitPreviewDialog());
    colorTransformAndFilterControlsPanel.add(blurButton, controlsPanelConstraints);

    JButton sharpenButton = new JButton("Sharpen");
    sharpenButton.setToolTipText("Apply Sharpen Filter");
    basicOperationsPanelConstraints.gridx = 1;
    basicOperationsPanelConstraints.gridy = 1;
    sharpenButton.addActionListener(e -> showSplitPreviewDialog());
    colorTransformAndFilterControlsPanel.add(sharpenButton, basicOperationsPanelConstraints);

    colorTransformAndFilterPanel.add(colorTransformAndFilterControlsPanel);
    operationPanel.add(colorTransformAndFilterPanel);





    JPanel levelAdjustPanel = new JPanel(new GridBagLayout());
    levelAdjustPanel.setBorder(BorderFactory.createTitledBorder("Level Adjust Operation"));

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
    levelAdjustButton.addActionListener(e -> showSplitPreviewDialog());
    levelAdjustPanel.add(levelAdjustButton, levelAdjustPanelConstraints);

    operationPanel.add(levelAdjustPanel);



    leftPane.add(operationPanel);

    histogramLabel = new JLabel();
    histogramLabel.setIcon(new ImageIcon("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/res/ex-image-levels-adjust-high-histogram.jpg"));
    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setSize(400, 500);
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
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image preview"));
    imageLabel = new JLabel();
    imageLabel.setIcon(new ImageIcon("/Users/hariharasudan/CS5010/group/cs5010-group-assignments/res/ex-image-actual-levels-adjust.png"));
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

  private void showSplitPreviewDialog() {
    JDialog dialog = new JDialog(this, "Split Preview", true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(200, 100);
    dialog.setLocationRelativeTo(this);

    JPanel dialogPanel = new JPanel(new GridBagLayout());

    dialogPanel.add(splitPreviewPanel);

    dialog.add(dialogPanel);
    dialog.setVisible(true);
  }


  @Override
  public void refreshScreen(String imageName, String histogram) {
    Image BufferedImage = model.getBufferedImage(imageName);
    imageLabel.setIcon(new ImageIcon(BufferedImage));

    Image BufferedHistogramImage = model.getBufferedImage(histogram);
    histogramLabel.setIcon(new ImageIcon(BufferedHistogramImage));
  }

  @Override
  public void splitView(String imageName) {

  }

  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(mainPanel, errorMessage, "Error!!",
            JOptionPane.ERROR_MESSAGE);
  }

  public void addFeatures(ImageController features) {
    exitItem.addActionListener(evt -> features.exitProgram());
    loadItem.addActionListener(evt -> loadImage(features));
//    saveImageButton.addActionListener(evt -> saveImage(features));
    blurButton.addActionListener(evt -> blur(features));
    horizontalFlipButton.addActionListener(evt -> horizontalFlip(features));
    verticalFlipButton.addActionListener(evt -> verticalFlip(features));
    greyscaleExecuteButton.addActionListener(evt -> greyScale(features));

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
        imagePanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));
        brightenButton.setEnabled(true);
        brightnessValue.setEnabled(true);
        horizontalFlipButton.setEnabled(true);
        verticalFlipButton.setEnabled(true);
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
//
  private void horizontalFlip(Features features) {
    try {
      features.horizontalFlip();
    } catch (IOException | InputMismatchException e) {
      displayErrorMessage("Horizontal flip operation could not be performed");
    }
  }

  private void greyScale(Features features){
    try{
      switch(selectedGreyscale){
        case "Red Component":
          features.redComponent();
          break;
        case "Green Component":
          features.greenComponent();
          break;
        case "Blue Component":
          features.blueComponent();
          break;
        case "Luma":
          features.lumaGreyscale();
          break;
        case "Intensity":
          features.intensityGreyscale();
          break;
        case "Value":
          features.valueGreyscale();
          break;
      }
    } catch (IOException | InputMismatchException e){
      displayErrorMessage(selectedGreyscale + "operation could not be performed");
    }
  }

  private void blur(Features features){
    try{
      features.blur();
    } catch (IOException | InputMismatchException e){
      displayErrorMessage("Blur operation could not be performed");
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

      String value = brightnessValue.getText();
      features.brighten(Integer.parseInt(value));
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
