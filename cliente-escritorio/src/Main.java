import com.formdev.flatlaf.themes.FlatMacLightLaf;
import vista.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Insets;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatMacLightLaf.setup();
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("TextField.margin", new Insets(4, 10, 4, 10));
            UIManager.put("PasswordField.margin", new Insets(4, 10, 4, 10));
            UIManager.put("PasswordField.showRevealButton", true);

            LoginFrame ventana = new LoginFrame();
            ventana.setVisible(true);
        });
    }
}

