import static java.awt.Color.BLACK;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.util.Arrays.asList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

  private static final List<String> algorithms = asList("RC4", "DES", "BLOWFISH");

  static {
    loadFont(FONT_FILE_NAME);
  }

  public static void create(JFrame jFrame) {

    //Input text
    final JLabel inputTextLabel = createJLabel(21, 10, 170, 30, INPUT_TEXT);
    final JTextArea inputTextArea = createJTextArea(20, 40, 500, 200);

    //Secret key
    final JLabel secretKeyTextLabel = createJLabel(21, 245, 170, 30, SECRET_TEXT);
    final JTextField secretKeyTextField = createJTextField(20, 275, 500, 35);

    //Output text
    final JLabel outputTextLabel = createJLabel(21, 320, 170, 30, OUTPUT_TEXT);
    final JTextArea outputTextArea = createJTextArea(20, 350, 500, 200);

    //Buttons
    final JButton encryptButton = createJButton(550, 40, 150, 30, ENCRYPT_TEXT);
    final JButton decryptButton = createJButton(550, 80, 150, 30, DECRYPT_TEXT);

    //Image
    final JLabel utmImage = getImage(550, 455, UTM_ICON_NAME);

    //Dropdown
    final JComboBox<String> algorithmsComboBox = createComboBox(550, 120, 150, 30, algorithms);

    setNextFocus(inputTextArea, secretKeyTextField);
    setNextFocus(secretKeyTextField, outputTextLabel);
    setNextFocus(outputTextArea, encryptButton);
    setNextFocusOnNonActive(algorithmsComboBox, inputTextArea);

    addAll(jFrame, inputTextLabel, inputTextArea, secretKeyTextLabel, secretKeyTextField, outputTextLabel, outputTextArea,
        encryptButton, decryptButton, utmImage, algorithmsComboBox);

    setSize(jFrame, WIDTH, HEIGHT);
    setDefaultCloseOperation(jFrame, DEFAULT_CLOSE_OPERATION);
    setResizable(jFrame, FRAME_RESIZABILITY);
    setLocationRelativeTo(jFrame, null);
    setImageIcon(jFrame, ICON_NAME);
    setLayout(jFrame, null);
    setVisible(jFrame, FRAME_VISIBILITY);
  }

  private static void setSize(JFrame jFrame, int width, int height) {
    jFrame.setSize(width, height);
  }

  private static void setLayout(JFrame jFrame, LayoutManager layoutManager) {
    jFrame.setLayout(layoutManager);
  }

  private static void setVisible(JFrame jFrame, boolean visibility) {
    jFrame.setVisible(visibility);
  }

  private static void setLocationRelativeTo(JFrame jFrame, Component component) {
    jFrame.setLocationRelativeTo(component);
  }

  private static void setResizable(JFrame jFrame, boolean resizability) {
    jFrame.setResizable(resizability);
  }

  private static void setDefaultCloseOperation(JFrame jFrame, int defaultCloseOperation) {
    jFrame.setDefaultCloseOperation(defaultCloseOperation);
  }

  private static void setImageIcon(JFrame jFrame, String iconName) {
    final ImageIcon iconImage = new ImageIcon(iconName);
    jFrame.setIconImage(iconImage.getImage());
  }

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

  private static JTextArea createJTextArea(int x, int y, int width, int height) {
    final JTextArea jTextArea = new JTextArea();
    jTextArea.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jTextArea.setFont(font);
    jTextArea.setWrapStyleWord(true);
    jTextArea.setLineWrap(true);

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

  private static JButton createJButton(int x, int y, int width, int height, String text) {
    final JButton jButton = new JButton(text);
    jButton.setBounds(x, y, width, height);
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    jButton.setFont(font);
    return jButton;
  }

  private static <T> JComboBox<T> createComboBox(int x, int y, int width, int height, List<T> elements) {
    final JComboBox<T> comboBox = new JComboBox(elements.toArray());
    final Font font = new Font(FONT_NAME, PLAIN, 16);
    comboBox.setFont(font);
    comboBox.setBounds(x, y, width, height);
    return comboBox;
  }

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

  private static void loadFont(String fontName) {
    try {
      final Font font = Font.createFont(TRUETYPE_FONT, new File(fontName));
      final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      graphicsEnvironment.registerFont(font);
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }

  private static void addAll(JFrame jFrame, JComponent... components) {
    Arrays.stream(components).forEach(jFrame::add);
  }
}