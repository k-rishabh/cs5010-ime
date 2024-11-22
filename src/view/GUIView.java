package view;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * Class that represents the GUI view of the application. It accepts user input through
 * mouse and keyboard, using the java swing library. Defines functionality for all operations
 * that the model and controller can perform, with the help of buttons, labels, sliders, and other
 * swing objects.
 */
public class GUIView extends JFrame implements ImageView {

  // panels
  private final JPanel mainScreen;
  private final JPanel splitPreviewPanel;

  // file menu
  private final JMenuItem loadItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JMenuItem undoItem;
  private final FileNameExtensionFilter fileFilter;

  private final JRadioButton previewMode;
  private final JRadioButton standardMode;
  private final JSlider splitViewSlider;

  private final JComboBox<String> greyscaleTypes;
  private final JButton grayscaleButton;
  private final JLabel flipsLabel;
  private final JLabel brightenLabel;
  private final JLabel compressLabel;
  private final JLabel heightLabel;
  private final JLabel widthLabel;
  private final JLabel splitRatioLabel;
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

  // slider labels
  private final JLabel blackLevelLabel;
  private final JLabel midLevelLabel;
  private final JLabel whiteLevelLabel;


  /**
   * Constructor function that initializes the main JPanel which is visible to the user. It defines
   * the location and behavior of all visual artifacts that are present in the application, using
   * Java Swing objects. Uses the helper class for ease of creation of objects.
   */
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

    JPanel parentPanel = new JPanel(new GridBagLayout());
    GUIHelper helper = new GUIHelper(parentPanel, null);
    GridBagConstraints c = helper.createConstraints(-1, GridBagConstraints.BOTH,
            0, 1, 1, 0.95, new Insets(0, 0, 0, 0));

    mainScreen.add(parentPanel, c);

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

    // color transformations
    JPanel colorPanel = new JPanel(new GridBagLayout());
    colorPanel.setBorder(BorderFactory.createTitledBorder("Color Operations"));
    GridBagConstraints colorConstraints = helper.createConstraints(
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 1, 1, 0, 0,
            new Insets(3, 3, 3, 3));

    helper.changePanel(colorPanel, colorConstraints);

    splitRatioLabel = helper.createLabel("Split Preview Ratio", 1, 0);
    splitRatioLabel.setHorizontalAlignment(SwingConstants.CENTER);
    splitViewSlider = helper.createSlider(0, 100, 0, 20, 10, 0, 0);
    splitViewSlider.addChangeListener(e -> {
      splitValue = splitViewSlider.getValue();
      splitViewSlider.setToolTipText(splitValue + "%");
    });

    previewMode = helper.createRadioButton("Preview Mode", false, 0, 1);
    standardMode = helper.createRadioButton("Standard Mode", true, 1, 1);
    // split view transformations
    ButtonGroup splitView = new ButtonGroup();
    splitView.add(previewMode);
    splitView.add(standardMode);


    greyscaleTypes = new JComboBox<>(new String[]{
        "Red Component", "Blue Component", "Green Component",
        "Luma Component", "Intensity Component", "Value Component"
    });
    greyscaleTypes.setToolTipText("Select the grayscale filter to be applied.");
    greyscaleTypes.setSelectedItem(null);
    colorConstraints.gridx = 0;
    colorConstraints.gridy = 2;
    greyscaleTypes.addActionListener(e -> this.selectedGrayscale =
            (String) greyscaleTypes.getSelectedItem());
    colorPanel.add(greyscaleTypes, colorConstraints);

    grayscaleButton = helper.createButton("Apply Grayscale",
            "Applies the selected grayscale filter on the image", 1, 2);

    blurButton = helper.createButton("Blur",
            "Blurs the image", 0, 3);

    sharpenButton = helper.createButton("Sharpen",
            "Sharpens the image", 1, 3);

    sepiaButton = helper.createButton("Sepia",
            "Applies a sepia color filter on the image", 0, 4);

    colorCorrectButton = helper.createButton("Color Correct",
            "Corrects the colors of the image by aligning the peaks", 1, 4);

    blackLevelLabel = helper.createLabel("Black Point", 1, 5);
    blackLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    midLevelLabel = helper.createLabel("Mid Tones", 1, 6);
    midLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    whiteLevelLabel = helper.createLabel("White Point", 1, 7);
    whiteLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);

    blackLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 0, 5);
    blackLevelSlider.addChangeListener(e -> {
      blackLevelValue = blackLevelSlider.getValue();
      blackLevelSlider.setToolTipText(String.valueOf(blackLevelValue));
    });

    midLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 0, 6);
    midLevelSlider.addChangeListener(e -> {
      midLevelValue = midLevelSlider.getValue();
      midLevelSlider.setToolTipText(String.valueOf(midLevelValue));
    });

    whiteLevelSlider = helper.createSlider(0, 255, 0, 50, 25, 0, 7);
    whiteLevelSlider.addChangeListener(e -> {
      whiteLevelValue = whiteLevelSlider.getValue();
      whiteLevelSlider.setToolTipText(String.valueOf(whiteLevelValue));
    });

    levelsAdjustButton = helper.createButton("Levels Adjust",
            "Adjusts the black, mid, and white components of the image", 0, 8);

    leftPanel.add(colorPanel);


    // histogram
    histogramLabel = new JLabel();
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 300));
    histogramPanel.add(histogramLabel);
    leftPanel.add(histogramPanel);

    GridBagConstraints leftConstraints = helper.createConstraints(-1, GridBagConstraints.BOTH,
            0, 0, 0.1, 1, null);
    parentPanel.add(leftPanel, leftConstraints);

    JPanel imagePanel = new JPanel();
    imagePanel.setPreferredSize(new Dimension(1024, 768));
    imagePanel.setBackground(Color.DARK_GRAY);
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    rightPanel.add(scroller);

    // non-split view operations
    JPanel imageTFPanel = new JPanel(new GridBagLayout());
    imageTFPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    GridBagConstraints imageTFConstraints = helper.createConstraints(GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, 1, 1, 0, 0,
            new Insets(1, 1, 1, 1));

    helper.changePanel(imageTFPanel, imageTFConstraints);

    flipsLabel = helper.createLabel("Flip Image", 0, 0);
    flipsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    horizontalFlipButton = helper.createButton("Flip Horizontally",
            "Flips the image horizontally", 0, 1);

    verticalFlipButton = helper.createButton("Flip Vertically",
            "Flips the image vertically", 0, 2);

    brightenLabel = helper.createLabel("Brightness Value", 1, 0);
    brightenLabel.setHorizontalAlignment(SwingConstants.CENTER);

    brightnessValueSlider = helper.createSlider(-255, 255, 0, 85, 17, 1, 1);
    brightnessValueSlider.addChangeListener(e -> {
      brightnessValue = brightnessValueSlider.getValue();
      brightnessValueSlider.setToolTipText(String.valueOf(brightnessValue));
    });

    brightenButton = helper.createButton("Brighten",
            "Brightens the image by the given amount", 1, 2);

    compressLabel = helper.createLabel("Compression Ratio", 2, 0);
    compressLabel.setHorizontalAlignment(SwingConstants.CENTER);
    compressValueSlider = helper.createSlider(0, 100, 0, 20, 10, 2, 1);
    compressValueSlider.addChangeListener(e -> {
      compressValue = compressValueSlider.getValue();
      compressValueSlider.setToolTipText(String.valueOf(compressValue));
    });

    compressButton = helper.createButton("Compress",
            "Compresses the image by the given amount", 2, 2);

    heightLabel = helper.createLabel("Height: ", 3, 0);
    heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
    widthLabel = helper.createLabel("Width: ", 3, 1);
    widthLabel.setHorizontalAlignment(SwingConstants.CENTER);
    heightValue = helper.createTextField("", 3, 4, 0);
    widthValue = helper.createTextField("", 3, 4, 1);
    downscaleButton = helper.createButton("Downscale",
            "Downscales the image by the given height and width", 4, 2);
    rightPanel.add(imageTFPanel);

    GridBagConstraints rightConstraints = helper.createConstraints(-1, GridBagConstraints.BOTH,
            1, 0, 0.9, 1, null);
    parentPanel.add(rightPanel, rightConstraints);

    add(mainScreen);

    JPanel labelWrapperPanel = new JPanel(new GridBagLayout());
    splitPreviewLabel = new JLabel();
    labelWrapperPanel.add(splitPreviewLabel);


    JPanel combinedLabelPanel = new JPanel(new BorderLayout());
    JLabel topLabel = new JLabel("Would you like to apply this transformation?",
            JLabel.CENTER);
    JScrollPane scrollPane = new JScrollPane(labelWrapperPanel);
    scrollPane.setPreferredSize(new Dimension(600, 600));
    combinedLabelPanel.add(topLabel, BorderLayout.NORTH);
    combinedLabelPanel.add(scrollPane, BorderLayout.CENTER);

    applyFilterButton = new JButton("Apply");
    cancelFilterButton = new JButton("Cancel");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(applyFilterButton);
    buttonPanel.add(cancelFilterButton);

    splitPreviewPanel = new JPanel(new BorderLayout());
    splitPreviewPanel.add(combinedLabelPanel, BorderLayout.CENTER);
    splitPreviewPanel.add(buttonPanel, BorderLayout.SOUTH);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setPreferredSize(new Dimension(1920, 1080));
    setMinimumSize(new Dimension(1280, 720));
    pack();
  }

  @Override
  public void setAllInputs(boolean set) {

    this.blackLevelLabel.setEnabled(set);
    this.midLevelLabel.setEnabled(set);
    this.whiteLevelLabel.setEnabled(set);
    this.flipsLabel.setEnabled(set);
    this.brightenLabel.setEnabled(set);
    this.compressLabel.setEnabled(set);
    this.heightLabel.setEnabled(set);
    this.widthLabel.setEnabled(set);
    this.splitRatioLabel.setEnabled(set);

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

    this.previewMode.setEnabled(set);
    this.standardMode.setEnabled(set);

    this.splitViewSlider.setEnabled(set);
    this.blackLevelSlider.setEnabled(set);
    this.midLevelSlider.setEnabled(set);
    this.whiteLevelSlider.setEnabled(set);
    this.brightnessValueSlider.setEnabled(set);
    this.compressValueSlider.setEnabled(set);

    this.heightValue.setEnabled(set);
    this.widthValue.setEnabled(set);
    this.greyscaleTypes.setEnabled(set);
  }

  @Override
  public void displayMessage(String informMessage, String title, int type) {
    JOptionPane.showMessageDialog(mainScreen, informMessage, title, type);
  }

  @Override
  public boolean displayConfirmation(String confirmationMessage, String title) {

    JDialog dialogConfirmation = new JDialog(this, title, true);
    AtomicBoolean ret = new AtomicBoolean(false);

    JLabel topLabel = new JLabel(confirmationMessage, JLabel.CENTER);
    JButton apply = new JButton("Yes");
    JButton cancel = new JButton("Cancel");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(apply);
    buttonPanel.add(cancel);

    JPanel confirmationPanel = new JPanel(new BorderLayout());
    confirmationPanel.add(topLabel, BorderLayout.NORTH);
    confirmationPanel.add(buttonPanel, BorderLayout.SOUTH);

    dialogButtonEnabling(dialogConfirmation, ret, apply, cancel);
    dialogConfirmation.setSize(500, 100);
    dialogConfirmation.setLocationRelativeTo(this);

    dialogConfirmation.add(confirmationPanel);
    dialogConfirmation.setVisible(true);

    return ret.get();
  }

  private void dialogButtonEnabling(JDialog dialogConfirmation, AtomicBoolean ret,
                                    JButton apply, JButton cancel) {
    apply.addActionListener(e -> {
      dialogConfirmation.setEnabled(false);
      dialogConfirmation.setVisible(false);
      ret.set(true);
    });

    cancel.addActionListener(e -> {
      dialogConfirmation.setEnabled(false);
      dialogConfirmation.setVisible(false);
      ret.set(false);
    });

    dialogConfirmation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  @Override
  public void listen(Features controller) {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        controller.exit();
      }
    });

    // file menu
    loadItem.addActionListener(e -> loadImage(controller));
    saveItem.addActionListener(e -> saveImage(controller));
    undoItem.addActionListener(e -> controller.undo());
    exitItem.addActionListener(e -> controller.exit());

    // split view operations
    grayscaleButton.addActionListener(e ->
            controller.applyGrayscale(selectedGrayscale, getSplit()));
    blurButton.addActionListener(e -> controller.applyBlur(getSplit()));
    sharpenButton.addActionListener(e -> controller.applySharpen(getSplit()));
    sepiaButton.addActionListener(e -> controller.applySepia(getSplit()));
    colorCorrectButton.addActionListener(e -> controller.applyColorCorrect(getSplit()));
    levelsAdjustButton.addActionListener(e -> controller.applyLevelsAdjust(
            blackLevelValue, midLevelValue, whiteLevelValue, getSplit()));

    // non-split view operations
    brightenButton.addActionListener(e -> controller.applyBrighten(brightnessValue));
    horizontalFlipButton.addActionListener(e -> controller.applyHorizontalFlip());
    verticalFlipButton.addActionListener(e -> controller.applyVerticalFlip());
    compressButton.addActionListener(e -> controller.applyCompress(compressValue));
    downscaleButton.addActionListener(e -> controller.applyDownscale(
            heightValue.getText(), widthValue.getText()));

    setVisible(true);
  }

  @Override
  public void refreshScreen(BufferedImage currentImage, BufferedImage histogram) {
    if (currentImage == null || histogram == null) {
      imageLabel.setIcon(null);
      histogramLabel.setIcon(null);
    } else {
      imageLabel.setIcon(new ImageIcon(currentImage));
      histogramLabel.setIcon(new ImageIcon(histogram));
    }
  }

  private void loadImage(Features controller) {
    final JFileChooser fChooser = new JFileChooser();
    fChooser.setFileFilter(fileFilter);
    int retValue = fChooser.showOpenDialog(this);

    if (retValue == JFileChooser.APPROVE_OPTION) {
      controller.load(fChooser.getSelectedFile());
    }
  }

  private void saveImage(Features controller) {
    final JFileChooser fChooser = new JFileChooser();
    int retValue = fChooser.showSaveDialog(this);

    if (retValue == JFileChooser.APPROVE_OPTION) {
      controller.save(fChooser.getSelectedFile());
    }
  }

  @Override
  public boolean splitViewWindow(BufferedImage splitImage) {
    splitPreviewLabel.setIcon(new ImageIcon(splitImage));
    AtomicBoolean ret = new AtomicBoolean(false);
    JDialog dialog = new JDialog(this, "Split Preview", true);
    JPanel dialogPanel = new JPanel();
    dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

    dialogButtonEnabling(dialog, ret, applyFilterButton, cancelFilterButton);
    dialog.setSize(1280, 720);
    dialog.setLocationRelativeTo(this);

    dialogPanel.add(splitPreviewPanel);
    dialog.add(dialogPanel);
    dialog.setVisible(true);

    return ret.get();
  }

  private int getSplit() {
    if (previewMode.isSelected()) {
      return splitValue;
    }
    return 0;
  }
}