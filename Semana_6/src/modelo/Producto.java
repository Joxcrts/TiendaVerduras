package modelo;

public class Producto {
    private String nombre;
    private String categoria;
    private int stock;
    private int valor;

    public Producto(String nombre, String categoria, int stock, int valor) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.valor = valor;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public int getValor() { return valor; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStock(int stock) { this.stock = stock; }
    public void setValor(int valor) { this.valor = valor; }
}
