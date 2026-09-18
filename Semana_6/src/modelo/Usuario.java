package modelo;

public class Usuario {
    private String nombreUsuario;
    private String contrasenia;
    private String rol; // administrador, vendedor

    public Usuario(String nombreUsuario, String contrasenia, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol.toLowerCase(); // estandariza
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenia() { return contrasenia; }
    public String getRol() { return rol; }

    @Override
    public String toString() {
        return nombreUsuario + " (" + rol + ")";
    }
}
