package controlador;

import modelo.Producto;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControladorProductos {
    private final ArrayList<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto, DefaultTableModel model) {
        productos.add(producto);
        model.addRow(new Object[]{
                producto.getNombre(),
                producto.getCategoria(),
                producto.getStock(),
                producto.getValor()
        });
    }

    public void editarProducto(int index, Producto producto, DefaultTableModel model) {
        productos.set(index, producto);
        model.setValueAt(producto.getNombre(), index, 0);
        model.setValueAt(producto.getCategoria(), index, 1);
        model.setValueAt(producto.getStock(), index, 2);
        model.setValueAt(producto.getValor(), index, 3);
    }

    public void eliminarProducto(int index, DefaultTableModel model) {
        productos.remove(index);
        model.removeRow(index);
    }

    public Producto getProductoByIndex(int index) {
        return productos.get(index);
    }

    public void agregarProductosIniciales(DefaultTableModel model) {
        agregarProducto(new Producto("Tomate", "Fruta", 50, 890), model);
        agregarProducto(new Producto("Papa", "Tubérculo", 100, 450), model);
        agregarProducto(new Producto("Zanahoria", "Hortaliza", 80, 620), model);
        agregarProducto(new Producto("Lechuga", "Verdura", 40, 700), model);
        agregarProducto(new Producto("Plátano", "Fruta", 70, 950), model);
    }
}
