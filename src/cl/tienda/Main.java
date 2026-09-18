package cl.tienda;

import cl.tienda.vista.TiendaVerduras;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] roles = {"admin", "vendedor"};
            String rol = (String) JOptionPane.showInputDialog(null, "Selecciona el rol:",
                    "Inicio de sesión", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
            if (rol != null) {
                new TiendaVerduras(rol).setVisible(true);
            }
        });
    }
}