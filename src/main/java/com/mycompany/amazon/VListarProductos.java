package com.mycompany.amazon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase para la vista de listar productos.
 */
public class VListarProductos extends JFrame {
    private JTextField txtCodigoBuscar; // Campo de texto para buscar por código
    private JButton btnBuscar; // Botón para buscar producto
    private JButton btnListar; // Botón para listar todos los productos
    private JButton btnCancelar; // Botón para cancelar y cerrar la ventana
    private JTable table; // Tabla para mostrar productos
    private DefaultTableModel tableModel; // Modelo de la tabla
    private List<MProductoVendido> productos; // Lista de productos

    /**
     * Constructor de la clase.
     */
    public VListarProductos(List<MProductoVendido> productos) {
        this.productos = productos;
        initComponents(); // Inicializa los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {
        setTitle("Listar Productos"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura el cierre de la ventana
        setAlwaysOnTop(true); // Hace que la ventana esté siempre en la parte superior
        setSize(800, 400); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Panel para buscar productos
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCodigoBuscar = new JLabel("Código a buscar:");
        txtCodigoBuscar = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProducto(txtCodigoBuscar.getText()));
        btnListar = new JButton("Listar Todos");
        btnListar.addActionListener(e -> listarProductos());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        panelBuscar.add(lblCodigoBuscar);
        panelBuscar.add(txtCodigoBuscar);
        panelBuscar.add(btnBuscar);
        panelBuscar.add(btnListar);
        panelBuscar.add(btnCancelar);

        // Panel para la tabla de productos
        JPanel panelTable = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Código");
        tableModel.addColumn("Descripción");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Precio Unitario");
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        // Añadir paneles a la ventana
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBuscar, BorderLayout.NORTH);
        getContentPane().add(panelTable, BorderLayout.CENTER);
    }

    /**
     * Busca un producto por código y lo muestra en la tabla.
     */
    private void buscarProducto(String codigo) {
        tableModel.setRowCount(0); // Limpiar la tabla

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un código para buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigoBuscar = Integer.parseInt(codigo);
        boolean encontrado = false;
        for (MProductoVendido producto : productos) {
            if (producto.getCodigo() == codigoBuscar) {
                Object[] rowData = {producto.getCodigo(), producto.getDescripcion(), producto.getStock(), producto.getPreciou()};
                tableModel.addRow(rowData);
                encontrado = true;
                break; // Termina la búsqueda después de encontrar el producto
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lista todos los productos en la tabla.
     */
    private void listarProductos() {
        tableModel.setRowCount(0); // Limpiar la tabla

        for (MProductoVendido producto : productos) {
            Object[] rowData = {producto.getCodigo(), producto.getDescripcion(), producto.getStock(), producto.getPreciou()};
            tableModel.addRow(rowData);
        }
    }
}






