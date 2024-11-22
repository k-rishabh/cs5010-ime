package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSlider;

/**
 * Helper class for the View that provides functionality for easily creating java swing visual
 * objects such as buttons and text fields.
 */
public class GUIHelper {
  JPanel panel;
  GridBagConstraints constraints;

  /**
   * Constructor function that initializes the current panel and constraints.
   * Each object created by this class will be a part of the panel and follow the set constraints.
   *
   * @param panel the JPanel where the object is added
   * @param constraints the constraints of the object
   */
  public GUIHelper(JPanel panel, GridBagConstraints constraints) {
    this.panel = panel;
    this.constraints = constraints;
  }

  /**
   * Changes the panel and constraints of the helper class, to add objects to the new panel with
   * new constraints.
   *
   * @param panel the new JPanel
   * @param constraints the new constraints that the object must follow
   */
  public void changePanel(JPanel panel, GridBagConstraints constraints) {
    this.panel = panel;
    this.constraints = constraints;
  }

  /**
   * Creates GridBagConstraints with the specified formatting/parameters.
   *
   * @param anchor the enum for anchor
   * @param fill the enum for fill
   * @param gridx the x position
   * @param gridy the y position
   * @param weightx the x weight
   * @param weighty the y weight
   * @param insets the borders
   * @return the created GridBagConstraints with the provided parameters
   */
  public GridBagConstraints createConstraints(int anchor, int fill, int gridx, int gridy,
                                              double weightx, double weighty, Insets insets) {

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.anchor = anchor >= 0 ? anchor : GridBagConstraints.CENTER;
    constraints.fill = fill >= 0 ? fill : GridBagConstraints.NONE;

    constraints.gridx = gridx >= 0 ? gridx : GridBagConstraints.RELATIVE;
    constraints.gridy = gridy >= 0 ? gridy : GridBagConstraints.RELATIVE;

    constraints.weightx = Math.max(weightx, 0);
    constraints.weighty = Math.max(weighty, 0);

    if (insets != null) {
      constraints.insets = insets;
    }

    return constraints;
  }

  /**
   * Creates a JLabel with the provided constraints.
   *
   * @param text the text of the JLabel
   * @param x the x position
   * @param y the y position
   * @return the newly constructed JLabel in the given panel and constraints
   */
  public JLabel createLabel(String text, int x, int y) {
    JLabel label = new JLabel(text);
    constraints.gridx = x;
    constraints.gridy = y;
    panel.add(label, constraints);
    return label;
  }

  /**
   * Creates a JRadioButton with the provided constraints.
   *
   * @param set the default setting of the radio button
   * @param x the x position
   * @param y the y position
   * @return the newly constructed JRadioButton in the given panel and constraints
   */
  public JRadioButton createRadioButton(String name, boolean set, int x, int y) {
    JRadioButton button = new JRadioButton(name);
    button.setSelected(set);
    if (x >= 0) {
      constraints.gridx = x;
    }
    if (y >= 0) {
      constraints.gridy = y;
    }

    panel.add(button, constraints);
    return button;
  }

  /**
   * Creates a JButton with the provided constraints.
   *
   * @param name the name of the button
   * @param desc the description of the button
   * @param x the x position
   * @param y the y position
   * @return the newly constructed JButton in the given panel and constraints
   */
  public JButton createButton(String name, String desc, int x, int y) {
    JButton button = new JButton(name);
    button.setToolTipText(desc);

    if (x >= 0) {
      constraints.gridx = x;
    }
    if (y >= 0) {
      constraints.gridy = y;
    }

    panel.add(button, constraints);
    return button;
  }

  /**
   * Creates a JSlider with the provided constraints.
   *
   * @param min the min value on the slider
   * @param max the max value on the slider
   * @param def the starting value on the slider
   * @param majTicks the major tick values displayed
   * @param minTicks the minor tick values displayed
   * @param x the x position
   * @param y the y position
   * @return the newly constructed JSlider in the given panel and constraints
   */
  public JSlider createSlider(int min, int max, int def, int majTicks, int minTicks,
                              int x, int y) {
    JSlider slider = new JSlider(min, max, def);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);

    slider.setMajorTickSpacing(majTicks);
    slider.setMinorTickSpacing(minTicks);

    if (x >= 0) {
      constraints.gridx = x;
    }
    if (y >= 0) {
      constraints.gridy = y;
    }

    panel.add(slider, constraints);
    return slider;
  }

  /**
   * Creates a JTextField with the provided constraints.
   *
   * @param name the name of the text field
   * @param cols the length of the text field
   * @param x the x position
   * @param y the y position
   * @return the newly constructed JTextField in the given panel and constraints
   */
  public JTextField createTextField(String name, int cols, int x, int y) {
    JTextField text = new JTextField(name, cols);
    if (x >= 0) {
      constraints.gridx = x;
    }
    if (y >= 0) {
      constraints.gridy = y;
    }

    panel.add(text, constraints);
    return text;
  }
}
