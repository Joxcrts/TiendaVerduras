package cl.tienda.vista;

import cl.tienda.controlador.ControladorProductos;
import cl.tienda.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TiendaVerduras extends JFrame {
    private final ControladorProductos controlador = new ControladorProductos();
    private final String rol;

    private final JTextField txtNombre = new JTextField(12);
    private final JTextField txtPrecio = new JTextField(8);
    private final JTextField txtStock = new JTextField(6);
    private final JComboBox<String> cmbCategoria =
            new JComboBox<>(new String[]{"Verdura", "Fruta", "Hortaliza", "Legumbre"});

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new String[]{"Nombre", "Precio", "Stock", "Categoría"}, 0) {
                @Override
                public boolean isCellEditable(int fila, int col) { return false; }
            };
    private final JTable tabla = new JTable(modeloTabla);

    public TiendaVerduras(String rol) {
        this.rol = rol;
        setTitle("Tienda de Verduras Al Paso - Rol: " + rol);
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirInterfaz();
        conectarEventos();
        aplicarRestriccionesPorRol();
    }

    private void construirInterfaz() {
        JPanel panelFormulario = new JPanel(new GridLayout(2, 4, 8, 4));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(txtStock);
        panelFormulario.add(cmbCategoria);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(new BorderLayout(5, 5));
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void conectarEventos() {
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0 && !e.getValueIsAdjusting()) {
                txtNombre.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPrecio.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtStock.setText(modeloTabla.getValueAt(fila, 2).toString());
                cmbCategoria.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
            }
        });
    }

    private void aplicarRestriccionesPorRol() {
        if (rol.equalsIgnoreCase("vendedor")) {
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnEditar.setToolTipText("Solo el admin puede editar");
            btnEliminar.setToolTipText("Solo el admin puede eliminar");
            setTitle(getTitle() + " (Vista restringida)");
        }
    }

    private void agregarProducto() {
        try {
            Producto p = controlador.agregar(txtNombre.getText(), txtPrecio.getText(),
                    txtStock.getText(), (String) cmbCategoria.getSelectedItem());
            modeloTabla.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getStock(), p.getCategoria()});
            limpiarCampos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        try {
            Producto p = controlador.editar(fila, txtNombre.getText(), txtPrecio.getText(),
                    txtStock.getText(), (String) cmbCategoria.getSelectedItem());
            modeloTabla.setValueAt(p.getNombre(), fila, 0);
            modeloTabla.setValueAt(p.getPrecio(), fila, 1);
            modeloTabla.setValueAt(p.getStock(), fila, 2);
            modeloTabla.setValueAt(p.getCategoria(), fila, 3);
            limpiarCampos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar este producto?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            controlador.eliminar(fila);
            modeloTabla.removeRow(fila);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        cmbCategoria.setSelectedIndex(0);
        tabla.clearSelection();
    }
}