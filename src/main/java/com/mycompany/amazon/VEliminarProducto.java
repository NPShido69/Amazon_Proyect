// Clase que representa la vista del carrito
package com.mycompany.amazon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase para la vista de eliminar productos.
 */
public class VEliminarProducto extends JFrame implements ActionListener {
    private JTextField txtCodigo; // Campo de texto para el código del producto
    private JButton btnBuscar; // Botón para buscar el producto
    private JButton btnEliminar; // Botón para eliminar el producto
    private JButton btnCancelar; // Botón para cancelar y cerrar la ventana
    private JTable table; // Tabla para mostrar productos
    private DefaultTableModel tableModel; // Modelo de la tabla
    private CCarrito cCarrito; // Controlador del carrito
    private List<MProductoVendido> productos; // Lista de productos

    /**
     * Constructor de la clase.
     */
    public VEliminarProducto(List<MProductoVendido> productos, CCarrito cCarrito) {
        this.productos = productos;
        this.cCarrito = cCarrito;
        initComponents(); // Inicializa los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {
        setTitle("Eliminar Producto"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura el cierre de la ventana
        setAlwaysOnTop(true); // Hace que la ventana esté siempre en la parte superior
        setSize(800, 400); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        JLabel lblCodigo = new JLabel("Código del producto");
        txtCodigo = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        // Configuración de la tabla
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Código");
        tableModel.addColumn("Descripción");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Precio Unitario");

        // Panel para los controles de búsqueda y eliminación
        JPanel panel = new JPanel();
        panel.add(lblCodigo);
        panel.add(txtCodigo);
        panel.add(btnBuscar);
        panel.add(btnEliminar);
        panel.add(btnCancelar);

        // Añadir componentes a la ventana
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true); // Hace visible la ventana
    }

    /**
     * Obtiene el código del producto ingresado en el campo de texto.
     */
    public String getCodigoProducto() {
        return txtCodigo.getText();
    }

    /**
     * Maneja las acciones de los componentes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar) {
            String codigo = getCodigoProducto();
            MProductoVendido producto = cCarrito.buscarProducto(codigo);
            if (producto != null) {
                // Limpiar la tabla antes de agregar el producto encontrado
                tableModel.setRowCount(0);
                Object[] rowData = {
                    producto.getCodigo(),
                    producto.getDescripcion(),
                    producto.getStock(),
                    producto.getPreciou()
                };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnEliminar) {
            String codigo = getCodigoProducto();
            MProductoVendido producto = cCarrito.buscarProducto(codigo);
            if (producto != null) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Eliminar producto: " + producto.getDescripcion(), "Confirmar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    cCarrito.eliminarProducto(producto);
                    JOptionPane.showMessageDialog(this, "Producto eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    tableModel.setRowCount(0); // Limpiar la tabla después de eliminar
                }
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}













