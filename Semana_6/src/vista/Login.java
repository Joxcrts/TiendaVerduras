package vista;

import controlador.ControladorUsuarios;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel login;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnAccederLogin;
    private JButton btnCancelarLogin;
    private ControladorUsuarios controladorUsuarios;

    public Login(ControladorUsuarios controladorUsuarios) {
        this.controladorUsuarios = controladorUsuarios;
        construirPanel(); // reemplaza al archivo .form del GUI Designer
        setTitle("Login Verdulería al Paso");
        setContentPane(login);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        btnAccederLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        btnCancelarLogin.addActionListener(e -> System.exit(0));
    }

    private void construirPanel() {
        login = new JPanel(new BorderLayout(10, 10));
        login.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("Login Verduras al Paso", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));

        JPanel campos = new JPanel(new GridLayout(2, 2, 8, 8));
        txtUsuario = new JTextField();
        txtContrasenia = new JPasswordField();
        campos.add(new JLabel("Usuario:"));
        campos.add(txtUsuario);
        campos.add(new JLabel("Contraseña:"));
        campos.add(txtContrasenia);

        JPanel botones = new JPanel();
        btnAccederLogin = new JButton("Acceder");
        btnCancelarLogin = new JButton("Cancelar");
        botones.add(btnAccederLogin);
        botones.add(btnCancelarLogin);

        login.add(titulo, BorderLayout.NORTH);
        login.add(campos, BorderLayout.CENTER);
        login.add(botones, BorderLayout.SOUTH);
    }

    private void autenticarUsuario() {
        String nombre = txtUsuario.getText().trim();
        String pass = new String(txtContrasenia.getPassword());
        Usuario usuario = controladorUsuarios.autenticar(nombre, pass);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario.getRol());
            new TiendaVerduras(usuario.getRol()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
