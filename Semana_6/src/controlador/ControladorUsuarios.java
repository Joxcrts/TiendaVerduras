package controlador;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarios {
    private List<Usuario> usuarios;

    public ControladorUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarUsuariosPorDefecto();
    }

    private void cargarUsuariosPorDefecto() {
        usuarios.add(new Usuario("admin", "123", "administrador"));
        usuarios.add(new Usuario("vendedor", "123", "vendedor"));
    }

    public Usuario autenticar(String nombreUsuario, String contrasenia) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)
                    && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
