package window;

import static java.awt.Color.BLACK;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.util.Arrays.asList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import algorithm.AlgorithmFactory;
import algorithm.Atbash;
import algorithm.RC4;
import algorithm.Vigenere;
import algorithm.XOR;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Window {

  private static final int WIDTH = 750;
  private static final int HEIGHT = 620;
  private static final boolean FRAME_VISIBILITY = true;
  private static final boolean FRAME_RESIZABILITY = false;
  private static final int DEFAULT_CLOSE_OPERATION = EXIT_ON_CLOSE;

  private static final String ICON_NAME = "icon.png";
  private static final String UTM_ICON_NAME = "UTM.png";
  private static final String FONT_FILE_NAME = "Montserrat-Medium.ttf";
  private static final String FONT_NAME = "Montserrat Medium";
  private static final String IMAGE_NOT_FOUND = "Image not %s found";

  private static final String INPUT_TEXT = "Input text:";
  private static final String SECRET_TEXT = "Secret key:";
  private static final String OUTPUT_TEXT = "Output text:";
  private static final String ENCRYPT_TEXT = "Encrypt";
  private static final String DECRYPT_TEXT = "Decrypt";
  private static final String COPY_TEXT = "Copy";

  private static final List<String> algorithms = asList("RC4", "XOR", "Atbash", "Vigenere");

  private static final AlgorithmFactory algorithmFactory = new AlgorithmFactory(new RC4(), new XOR(), new Atbash(),
      new Vigenere());

  static {
    loadFont(FONT_FILE_NAME);
  }

  public static void create(JFrame jFrame) {
    //Input text
    final JLabel inputTextLabel = createJLabel(21, 10, 170, 30, INPUT_TEXT);
    final JTextArea inputTextArea = createJTextArea(20, 40, 500, 200, true, true);

    //Secret key
    final JLabel secretKeyTextLabel = createJLabel(21, 245, 170, 30, SECRET_TEXT);
    final JTextField secretKeyTextField = createJTextField(20, 275, 500, 35);

    //Output text
    final JLabel outputTextLabel = createJLabel(21, 320, 170, 30, OUTPUT_TEXT);
    final JTextArea outputTextArea = createJTextArea(20, 350, 500, 200, false, false);

    //Buttons
    final JButton encryptButton = createJButton(550, 40, 150, 30, ENCRYPT_TEXT, false);
    final JButton decryptButton = createJButton(550, 80, 150, 30, DECRYPT_TEXT, false);
    final JButton copyButton = createJButton(550, 120, 150, 30, COPY_TEXT, false);

    //Image
    final JLabel utmImage = getImage(550, 465, UTM_ICON_NAME);

    //Dropdown
    final JComboBox<String> algorithmsComboBox = createComboBox(550, 160, 150, 30, algorithms);

    setNextFocus(inputTextArea, secretKeyTextField);
    setNextFocus(secretKeyTextField, encryptButton);
    setNextFocusOnNonActive(algorithmsComboBox, inputTextArea);

    copyButton.addActionListener(e -> Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(new StringSelection(outputTextArea.getText()), null));

    encryptButton.addActionListener(
        e -> {
          final String selectedItem = Objects.requireNonNull(algorithmsComboBox.getSelectedItem()).toString();
          secretKeyTextField.setText(algorithmFactory.getAlgorithm(selectedItem).updateKey(secretKeyTextField.getText()));
          outputTextArea.setText(algorithmFactory.getAlgorithm(Objects.requireNonNull(selectedItem))
              .encrypt(inputTextArea.getText().getBytes(), secretKeyTextField.getText().getBytes()));
        });
    decryptButton.addActionListener(
        e -> {
          final String selectedItem = Objects.requireNonNull(algorithmsComboBox.getSelectedItem()).toString();
          secretKeyTextField.setText(algorithmFactory.getAlgorithm(selectedItem).updateKey(secretKeyTextField.getText()));
          outputTextArea.setText(algorithmFactory.getAlgorithm(Objects.requireNonNull(selectedItem))
              .decrypt(inputTextArea.getText(), secretKeyTextField.getText().getBytes()));
        });

    addAll(jFrame, inputTextLabel, inputTextArea, secretKeyTextLabel, secretKeyTextField, outputTextLabel, outputTextArea,
        encryptButton, decryptButton, copyButton, utmImage, algorithmsComboBox);

    jFrame.setSize(WIDTH, HEIGHT);
    jFrame.setDefaultCloseOperation(DEFAULT_CLOSE_OPERATION);
    jFrame.setResizable(FRAME_RESIZABILITY);
    jFrame.setLocationRelativeTo(null);
    jFrame.setLayout(null);
    jFrame.setVisible(FRAME_VISIBILITY);
    setImageIcon(jFrame, ICON_NAME);
  }

  //===========================ACTIONS ON JFRAME===========================
  private static void setImageIcon(JFrame jFrame, String iconName) {
    final ImageIcon iconImage = new ImageIcon(iconName);
    jFrame.setIconImage(iconImage.getImage());
  }

  private static void addAll(JFrame jFrame, JComponent... components) {
    Arrays.stream(components).forEach(jFrame::add);
  }
  //===========================ACTIONS ON JFRAME===========================

  //===========================CREATE AND CUSTOMIZE JCOMPONENTS===========================
  private static JLabel getImage(int x, int y, String imagePath) {
    try {
      final BufferedImage myPicture = ImageIO.read(new File(imagePath));
      final JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      Dimension size = picLabel.getPreferredSize();
      picLabel.setBounds(x, y, size.width, size.height);
      return picLabel;
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new RuntimeException(String.format(IMAGE_NOT_FOUND, imagePath));
  }

  private static JTextArea createJTextArea(int x, int y, int width, int height, boolean isEditable, boolean isFocusable) {
    final JTextArea jTextArea = new JTextArea();
    jTextArea.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jTextArea.setFont(font);
    jTextArea.setWrapStyleWord(true);
    jTextArea.setLineWrap(true);
    jTextArea.setEditable(isEditable);
    jTextArea.setFocusable(isFocusable);

    final Border outsideBorder = BorderFactory.createLineBorder(BLACK);
    final Border insideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    jTextArea.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
    return jTextArea;
  }

  private static JTextField createJTextField(int x, int y, int width, int height) {
    final JTextField jTextField = new JTextField();
    jTextField.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jTextField.setFont(font);

    final Border outsideBorder = BorderFactory.createLineBorder(BLACK);
    final Border insideBorder = BorderFactory.createEmptyBorder(4, 10, 4, 10);
    jTextField.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
    return jTextField;
  }

  private static JLabel createJLabel(int x, int y, int width, int height, String text) {
    final JLabel jLabel = new JLabel(text);
    jLabel.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jLabel.setFont(font);
    return jLabel;
  }

  private static JButton createJButton(int x, int y, int width, int height, String text, boolean isFocusPainted) {
    final JButton jButton = new JButton(text);
    jButton.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jButton.setFont(font);
    jButton.setFocusPainted(isFocusPainted);
    return jButton;
  }

  private static <T> JComboBox<T> createComboBox(int x, int y, int width, int height, List<T> elements) {
    final JComboBox<T> comboBox = new JComboBox(elements.toArray());
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    comboBox.setFont(font);
    comboBox.setBounds(x, y, width, height);
    return comboBox;
  }
  //===========================CREATE AND CUSTOMIZE JCOMPONENTS===========================

  //===========================EVENTS ON JCOMPONENTS===========================
  private static void setNextFocus(JComponent jComponentOwner, JComponent nextComponent) {
    jComponentOwner.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_TAB) {
          nextComponent.requestFocusInWindow();
          e.consume();
        }
      }
    });
  }

  private static void setNextFocusOnNonActive(JComponent jComponent, JComponent nextComponent) {
    jComponent.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
      }

      @Override
      public void focusLost(FocusEvent e) {
        nextComponent.requestFocusInWindow();
      }
    });
  }
  //===========================EVENTS ON JCOMPONENTS===========================

  //===========================APPLICATION INITIALIZATION===========================
  private static void loadFont(String fontName) {
    try {
      final Font font = Font.createFont(TRUETYPE_FONT, new File(fontName));
      final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(font);
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }
  //===========================APPLICATION INITIALIZATION===========================
}