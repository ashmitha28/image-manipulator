package ime.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ime.controller.Features;

import static javax.swing.JOptionPane.YES_NO_OPTION;


/**
 * This class implements {@link GUIView} and utilizes the Java Swing library to perform and display
 * operations on images.
 */
public class JFrameView extends JFrame implements GUIView {

  private Features features;
  private JPanel mainPanel;
  private JLabel mainImage;
  private JLabel histogramImage;
  private JPanel applyPanel;
  private JPanel previewPanel;
  private JPanel togglePanel;

  /**
   * Constructor initializing the JFrame and setting up the UI components.
   */
  public JFrameView() {

    setDefaultLookAndFeelDecorated(false);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    setTitle("Image processing");
    setSize(800, 800);

    setUpContents();

    this.setVisible(true);

  }

  private void setUpContents() {
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    addImagePane();
    addTogglePanel();

    addOptions();

    addConfirmationPanel();
  }

  private void addOptions() {
    JPanel optionsPanel = new JPanel();
    optionsPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    optionsPanel.setLayout(new GridLayout(0, 4));
    mainPanel.add(optionsPanel);
    addButtonToPanel("Load Image", optionsPanel, event -> features.loadImage());
    addButtonToPanel("Save Image", optionsPanel, event -> features.saveImage());

    addButtonToPanel("Flip Horizontal", optionsPanel, event -> features.chooseHorizontalFlip());
    addButtonToPanel("Flip Vertical", optionsPanel, event -> features.chooseVerticalFlip());
    addButtonToPanel("Levels Adjust", optionsPanel, event -> features.chooseLevelsAdjust());
    addButtonToPanel("Blur", optionsPanel, event -> features.chooseBlur());
    addButtonToPanel("Sharpen", optionsPanel, event -> features.chooseSharpen());
    addButtonToPanel("Compression", optionsPanel, event -> features.chooseCompression());
    addButtonToPanel("Visualize Red", optionsPanel, event -> features.chooseVisualizeRed());
    addButtonToPanel("Visualize Green", optionsPanel, event -> features.chooseVisualizeGreen());
    addButtonToPanel("Visualize Blue", optionsPanel, event -> features.chooseVisualizeBlue());
    addButtonToPanel("Sepia", optionsPanel, event -> features.chooseSepia());
    addButtonToPanel("Color Correct", optionsPanel, event -> features.chooseColorCorrect());
    addButtonToPanel("Luma Greyscale", optionsPanel, event -> features.chooseLumaGreyscale());
  }

  private void addButtonToPanel(String name, JPanel panel, ActionListener listener) {
    JButton newButton = new JButton(name);
    newButton.setActionCommand(name);
    newButton.addActionListener(listener);
    panel.add(newButton);
  }

  private void addTogglePanel() {
    togglePanel = new JPanel();
    togglePanel.setVisible(false);
    mainPanel.add(togglePanel);
    addButtonToPanel("Toggle", togglePanel, event -> features.toggle());
  }

  private void addConfirmationPanel() {
    JPanel confirmationPanel = new JPanel();
    confirmationPanel.setLayout(new FlowLayout());

    mainPanel.add(confirmationPanel);
    applyPanel = new JPanel();
    applyPanel.setVisible(false);
    confirmationPanel.add(applyPanel);
    previewPanel = new JPanel();
    previewPanel.setVisible(false);
    confirmationPanel.add(previewPanel);

    addButtonToPanel("Apply Filter", applyPanel, event -> features.applyChosenOperation());
    addButtonToPanel("Preview", previewPanel, event -> features.previewChosenOperation());
  }


  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void setImage(Image image) {
    ImageIcon imageIcon = new ImageIcon(image);
    mainImage.setIcon(imageIcon);
  }

  @Override
  public void setHistogram(Image image) {
    ImageIcon imageIcon = new ImageIcon(image);
    histogramImage.setIcon(imageIcon);
  }

  @Override
  public void enablePreview(boolean show) {
    previewPanel.setVisible(show);
  }

  @Override
  public void enableApply(boolean show) {
    applyPanel.setVisible(show);
  }

  @Override
  public void enableToggle(boolean show) {
    togglePanel.setVisible(show);
  }

  @Override
  public int getInput(String message) {
    String input = JOptionPane.showInputDialog(message);
    if (input == null) {
      throw new IllegalStateException("Cancelled operation");
    }
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer.", "Error",
              JOptionPane.ERROR_MESSAGE);
      return getInput(message);
    }
  }

  @Override
  public boolean getConfirmation(String message) {
    int confirmation = JOptionPane.showConfirmDialog(this, message, "Overwrite changes",
            YES_NO_OPTION);
    return confirmation == JOptionPane.YES_OPTION;
  }

  @Override
  public String getFilePathToLoad(List<String> supportedFormats) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Supported : " + supportedFormats,
            supportedFormats.toArray(new String[0]));
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
  }

  @Override
  public String getFilePathToSave() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(null);
    try {
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        return f.getCanonicalPath();
      }
    } catch (IOException e) {
      //This would not occur when user chooses a file
    }
    return null;
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  private void addImagePane() {
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Active Image"));
    imagePanel.setLayout(new BorderLayout());
    mainPanel.add(imagePanel);
    mainImage = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(mainImage);
    imageScrollPane.setPreferredSize(new Dimension(320, 256));
    histogramImage = new JLabel();
    JScrollPane imageScrollPaneHistogram = new JScrollPane(histogramImage);
    imageScrollPaneHistogram.setPreferredSize(new Dimension(256, 256));

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageScrollPane,
            imageScrollPaneHistogram);
    splitPane.setOneTouchExpandable(true);

    splitPane.setResizeWeight(0.7);
    imagePanel.add(splitPane);
  }
}
