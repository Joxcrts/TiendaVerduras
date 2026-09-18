package cl.tienda.controlador;

import cl.tienda.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

public class ControladorProductos {
    private final List<Producto> productos = new ArrayList<>();

    public Producto crearProducto(String nombre, String precioTxt, String stockTxt, String categoria) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioTxt.trim().replace(",", "."));
            stock = Integer.parseInt(stockTxt.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Precio y stock deben ser números válidos.");
        }
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser positivo.");
        if (stock <= 0)  throw new IllegalArgumentException("El stock debe ser mayor que 0.");

        return new Producto(nombre.trim(), precio, stock, categoria);
    }

    public Producto agregar(String nombre, String precio, String stock, String categoria) {
        Producto p = crearProducto(nombre, precio, stock, categoria);
        productos.add(p);
        return p;
    }

    public Producto editar(int indice, String nombre, String precio, String stock, String categoria) {
        Producto nuevo = crearProducto(nombre, precio, stock, categoria);
        productos.set(indice, nuevo);
        return nuevo;
    }

    public void eliminar(int indice) {
        productos.remove(indice);
    }

    public List<Producto> getProductos() {
        return productos;
    }
}