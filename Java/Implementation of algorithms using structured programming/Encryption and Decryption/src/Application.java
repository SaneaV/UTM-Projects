import static java.util.Objects.isNull;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import window.Window;

public class Application {

  private static final String APPLICATION_NAME = "Encryption and Decryption";
  private static final String LOOK_AND_FEEL = System.getenv("LOOK_AND_FEEL");
  private static final String SYSTEM_LOOK_AND_FEEL = "SYSTEM";

  public static void main(String[] args) {
    if (!isNull(LOOK_AND_FEEL) && LOOK_AND_FEEL.equalsIgnoreCase(SYSTEM_LOOK_AND_FEEL)) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        e.printStackTrace();
      }
    }

    final JFrame FRAME = new JFrame(APPLICATION_NAME);
    Window.create(FRAME);
  }
}