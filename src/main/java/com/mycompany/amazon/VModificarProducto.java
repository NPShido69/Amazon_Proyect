package com.mycompany.amazon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase para la vista de modificación de productos.
 */
public class VModificarProducto extends JFrame implements ActionListener {
    private JTextField txtCodigo; // Campo de texto para el código del producto
    private JTextField txtDescripcion; // Campo de texto para la descripción del producto
    private JTextField txtStock; // Campo de texto para la cantidad del producto
    private JTextField txtPrecioU; // Campo de texto para el precio unitario del producto
    private JButton btnGuardar; // Botón para guardar los cambios
    private JButton btnCancelar; // Botón para cancelar y cerrar la ventana
    private CCarrito controlador; // Controlador del carrito
    private MProductoVendido producto; // Producto a modificar

    /**
     * Constructor de la clase.
     */
    public VModificarProducto(CCarrito controlador, MProductoVendido producto) {
        this.controlador = controlador;
        this.producto = producto;
        initComponents(); // Inicializa los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {
        setTitle("Modificar Producto"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura el cierre de la ventana
        setSize(300, 250); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        JPanel panel = new JPanel(); // Crea un panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establece el diseño del panel

        txtCodigo = new JTextField(10); // Crea el campo de texto para el código
        txtCodigo.setText(String.valueOf(producto.getCodigo())); // Establece el valor del campo
        txtCodigo.setEnabled(false); // Desactiva la edición del código

        txtDescripcion = new JTextField(producto.getDescripcion(), 10); // Crea el campo de texto para la descripción
        txtStock = new JTextField(String.valueOf(producto.getStock()), 10); // Crea el campo de texto para la cantidad
        txtPrecioU = new JTextField(String.valueOf(producto.getPreciou()), 10); // Crea el campo de texto para el precio unitario
        btnGuardar = new JButton("Guardar"); // Crea el botón de guardar
        btnCancelar = new JButton("Cancelar"); // Crea el botón de cancelar

        panel.add(new JLabel("Código:")); // Añade etiqueta y campo de texto para el código al panel
        panel.add(txtCodigo);
        panel.add(new JLabel("Descripción:")); // Añade etiqueta y campo de texto para la descripción al panel
        panel.add(txtDescripcion);
        panel.add(new JLabel("Stock:")); // Añade etiqueta y campo de texto para la cantidad al panel
        panel.add(txtStock);
        panel.add(new JLabel("Precio Unitario:")); // Añade etiqueta y campo de texto para el precio unitario al panel
        panel.add(txtPrecioU);
        panel.add(btnGuardar); // Añade el botón de guardar al panel
        panel.add(btnCancelar); // Añade el botón de cancelar al panel

        btnGuardar.addActionListener(this); // Asigna el ActionListener al botón de guardar
        btnCancelar.addActionListener(e -> dispose()); // Asigna el ActionListener al botón de cancelar

        getContentPane().add(panel); // Añade el panel a la ventana
    }

    /**
     * Maneja las acciones de los componentes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            String descripcion = txtDescripcion.getText(); // Obtiene la descripción del producto
            int cantidad;
            double precioU;

            try {
                cantidad = Integer.parseInt(txtStock.getText()); // Obtiene la cantidad del producto
                precioU = Double.parseDouble(txtPrecioU.getText()); // Obtiene el precio unitario del producto
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser un número entero y Precio Unitario debe ser un número decimal", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlador.actualizarProducto(producto.getCodigo(), descripcion, cantidad, precioU); // Actualiza el producto en el controlador
            JOptionPane.showMessageDialog(this, "Producto modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE); // Muestra un mensaje de éxito
            dispose(); // Cierra la ventana después de guardar
        }
    }

    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true); // Hace visible la ventana
    }
}








