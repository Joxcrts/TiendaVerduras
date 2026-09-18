package vista;

import controlador.ControladorProductos;
import modelo.CategoriaProducto;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TiendaVerduras extends JFrame {
    private JTextField txtNombre;
    private JComboBox<CategoriaProducto> cmbCategoria;
    private JSpinner spnStock;
    private JTextField txtValor;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tblProductos;
    private DefaultTableModel modelo;

    private final ControladorProductos controlador = new ControladorProductos();
    private final String rol;

    public TiendaVerduras(String rol) {
        this.rol = rol;
        setTitle("Tienda Verduras al Paso - " + rol);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 480);
        setLocationRelativeTo(null);

        construirComponentes();
        inicializarTabla();
        cargarCategorias();
        inicializarBotones();
        aplicarRestriccionesPorRol();
        controlador.agregarProductosIniciales(modelo);
    }

    private void construirComponentes() {
        txtNombre = new JTextField();
        cmbCategoria = new JComboBox<>();
        spnStock = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
        txtValor = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(2, 4, 8, 4));
        formulario.setBorder(BorderFactory.createTitledBorder("Producto"));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(new JLabel("Categoría:"));
        formulario.add(new JLabel("Stock:"));
        formulario.add(new JLabel("Valor:"));
        formulario.add(txtNombre);
        formulario.add(cmbCategoria);
        formulario.add(spnStock);
        formulario.add(txtValor);

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        JPanel botones = new JPanel();
        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        tblProductos = new JTable();

        setLayout(new BorderLayout(5, 5));
        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tblProductos), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void inicializarTabla() {
        modelo = new DefaultTableModel(new String[]{"Nombre", "Categoría", "Stock", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblProductos.setModel(modelo);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblProductos.getSelectedRow();
                if (fila >= 0) {
                    Producto p = controlador.getProductoByIndex(fila);
                    txtNombre.setText(p.getNombre());
                    for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                        if (cmbCategoria.getItemAt(i).toString().equals(p.getCategoria())) {
                            cmbCategoria.setSelectedIndex(i);
                        }
                    }
                    spnStock.setValue(p.getStock());
                    txtValor.setText(String.valueOf(p.getValor()));
                }
            }
        });
    }

    private void cargarCategorias() {
        for (CategoriaProducto c : CategoriaProducto.values()) {
            cmbCategoria.addItem(c);
        }
    }

    private void aplicarRestriccionesPorRol() {
        if (rol.equalsIgnoreCase("vendedor")) {
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            setTitle(getTitle() + " (Vista restringida)");
        }
    }

    private void inicializarBotones() {
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    // Valida los campos y devuelve un Producto, o null si hay error
    private Producto leerFormulario() {
        String nombre = txtNombre.getText().trim();
        int stock = (Integer) spnStock.getValue();
        int valor;

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return null;
        }
        if (stock <= 0) {
            JOptionPane.showMessageDialog(this, "El stock debe ser mayor que 0.");
            return null;
        }
        try {
            valor = Integer.parseInt(txtValor.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor debe ser un número entero.");
            return null;
        }
        if (valor <= 0) {
            JOptionPane.showMessageDialog(this, "El valor debe ser positivo.");
            return null;
        }
        String categoria = cmbCategoria.getSelectedItem().toString();
        return new Producto(nombre, categoria, stock, valor);
    }

    private void agregarProducto() {
        Producto p = leerFormulario();
        if (p != null) {
            controlador.agregarProducto(p, modelo);
            limpiarFormulario();
        }
    }

    private void editarProducto() {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        Producto p = leerFormulario();
        if (p != null) {
            controlador.editarProducto(fila, p, modelo);
            limpiarFormulario();
        }
    }

    private void eliminarProducto() {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Eliminar el producto seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            controlador.eliminarProducto(fila, modelo);
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        cmbCategoria.setSelectedIndex(0);
        spnStock.setValue(1);
        txtValor.setText("");
        tblProductos.clearSelection();
    }
}
