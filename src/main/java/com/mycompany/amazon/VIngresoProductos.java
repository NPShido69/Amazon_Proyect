// Clase para la vista de productos vendidos
package com.mycompany.amazon;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class VIngresoProductos extends JFrame implements ActionListener {
    private JTextField txtCodigo; // Campo de texto para el código del producto
    private JTextField txtDescripcion; // Campo de texto para la descripción del producto
    private JTextField txtStock; // Campo de texto para la cantidad del producto
    private JTextField txtPrecioU; // Campo de texto para el precio unitario del producto
    private JButton btnGuardar; // Botón para guardar el producto
    private JButton btnCancelar; // Botón para cancelar
    private JButton btnActualizarStock; // Botón para actualizar el stock
    private CCarrito controlador; // Controlador del carrito
    private MProductoVendido productoActual; // Producto actual

    public VIngresoProductos(CCarrito controlador) { //Abriendo la excepción de errores desde el constructor [notVoid].
        try { 
            this.controlador = controlador;
            initComponents(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al inicializar los componentes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        try {
            // Configuración de la ventana
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
            setSize(300, 300); 
            setLocationRelativeTo(null); 
            setAlwaysOnTop(true); 

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            txtCodigo = new JTextField(5); 
            txtDescripcion = new JTextField(5); 
            txtStock = new JTextField(5); 
            txtPrecioU = new JTextField(5); 
            btnGuardar = new JButton("Guardar"); 
            btnCancelar = new JButton("Cancelar"); 
            btnActualizarStock = new JButton("Actualizar Stock"); 
            btnActualizarStock.setVisible(false); 

            panel.add(new JLabel("Código:"));
            panel.add(txtCodigo);
            panel.add(new JLabel("Descripción:"));
            panel.add(txtDescripcion);
            panel.add(new JLabel("Stock:"));
            panel.add(txtStock);
            panel.add(new JLabel("Precio Unitario:"));
            panel.add(txtPrecioU);
            panel.add(btnGuardar);
            panel.add(btnCancelar);
            panel.add(btnActualizarStock);

            btnGuardar.addActionListener(this);
            btnCancelar.addActionListener(e -> dispose());
            btnActualizarStock.addActionListener(e -> abrirActualizarStock());

            txtCodigo.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                }

                @Override
                public void focusLost(FocusEvent e) {
                    verificarCodigoProducto();
                }
            });

            getContentPane().add(panel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al configurar los componentes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnGuardar) {
                guardarProducto();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar la acción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarProducto() {
        try {
            String codigoStr = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            String cantidadStr = txtStock.getText();
            String precioUStr = txtPrecioU.getText();
            int codigo, cantidad;
            double precioU;

            if (codigoStr.isEmpty() || descripcion.isEmpty() || cantidadStr.isEmpty() || precioUStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                codigo = Integer.parseInt(codigoStr);
                cantidad = Integer.parseInt(cantidadStr);
                precioU = Double.parseDouble(precioUStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Debe agregar datos numéricos en esos campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                controlador.agregarProducto(codigo, descripcion, cantidad, precioU);
                limpiarCampos();
                btnActualizarStock.setVisible(false);
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                btnActualizarStock.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarCodigoProducto() {
        try {
            String codigoStr = txtCodigo.getText();
            if (!codigoStr.isEmpty()) {
                MProductoVendido producto = controlador.verificarCodigoProducto(codigoStr);
                if (producto != null) {
                    rellenarCampos(producto);
                    productoActual = producto;
                } else {
                    limpiarCampos();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al verificar el código del producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirActualizarStock() {
        try {
            if (productoActual != null) {
                String nuevaCantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a agregar:", "Actualizar Stock", JOptionPane.PLAIN_MESSAGE);
                if (nuevaCantidadStr != null) {
                    try {
                        int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                        int stockActual = productoActual.getStock();
                        controlador.actualizarStock(productoActual.getCodigo(), stockActual + nuevaCantidad);
                        JOptionPane.showMessageDialog(this, "Stock actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        try {
            setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar la ventana: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void rellenarCampos(MProductoVendido producto) {
        try {
            txtDescripcion.setText(producto.getDescripcion());
            txtStock.setText(String.valueOf(producto.getStock()));
            txtPrecioU.setText(String.valueOf(producto.getPreciou()));
            txtDescripcion.setEditable(false);
            txtStock.setEditable(false);
            txtPrecioU.setEditable(false);
            btnActualizarStock.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al rellenar los campos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        try {
            txtDescripcion.setText("");
            txtStock.setText("");
            txtPrecioU.setText("");
            txtDescripcion.setEditable(true);
            txtStock.setEditable(true);
            txtPrecioU.setEditable(true);
            btnActualizarStock.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al limpiar los campos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
