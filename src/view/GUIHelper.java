package view;

import java.awt.*;

import javax.swing.*;

public class GUIHelper {
  JPanel panel;
  GridBagConstraints constraints;

  public GUIHelper(JPanel panel, GridBagConstraints constraints) {
    this.panel = panel;
    this.constraints = constraints;
  }

  public void changePanel(JPanel panel, GridBagConstraints constraints) {
    this.panel = panel;
    this.constraints = constraints;
  }

  public GridBagConstraints createConstraints(int anchor, int fill, int gridx, int gridy,
                                              double weightx, double weighty, Insets insets) {

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.anchor = anchor >= 0 ? anchor : GridBagConstraints.CENTER;
    constraints.fill = fill >= 0 ? fill : GridBagConstraints.NONE;

    constraints.gridx = gridx >= 0 ? gridx : GridBagConstraints.RELATIVE;
    constraints.gridy = gridy >= 0 ? gridy : GridBagConstraints.RELATIVE;

    constraints.weightx = Math.max(weightx, 0);
    constraints.weighty = Math.max(weighty, 0);

    constraints.insets = insets;

    return constraints;
  }

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

  public JSlider createSlider(int min, int max, int def, int x, int y) {
    JSlider slider = new JSlider(min, max, def);
    slider.setPaintTrack(true);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    if (x >= 0) {
      constraints.gridx = x;
    }
    if (y >= 0) {
      constraints.gridy = y;
    }

    panel.add(slider, constraints);
    return slider;
  }

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
