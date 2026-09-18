import controlador.ControladorUsuarios;
import vista.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorUsuarios controlador = new ControladorUsuarios();
            Login ventanaLogin = new Login(controlador);
            ventanaLogin.setVisible(true);
        });
    }
}
