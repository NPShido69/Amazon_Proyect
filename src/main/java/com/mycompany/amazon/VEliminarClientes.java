package com.mycompany.amazon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Clase para la vista de eliminar clientes.
 */
public class VEliminarClientes extends JFrame implements ActionListener {
    private JTextField txtCedula; // Campo de texto para la cédula del cliente
    private JButton btnBuscar; // Botón para buscar el cliente
    private JButton btnEliminar; // Botón para eliminar el cliente
    private JButton btnCancelar; // Botón para cancelar y cerrar la ventana
    private JTable table; // Tabla para mostrar clientes
    private DefaultTableModel tableModel; // Modelo de la tabla
    private CCliente cCliente; // Controlador de clientes

    /**
     * Constructor de la clase.
     */
    public VEliminarClientes(CCliente cCliente) {
        this.cCliente = cCliente;
        presentaPantalla(); // Inicializa la interfaz
        setTitle("Eliminar Cliente"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura el cierre de la ventana
        setAlwaysOnTop(true); // Hace que la ventana esté siempre en la parte superior
    }

    /**
     * Configura y muestra la interfaz.
     */
    private void presentaPantalla() {
        setSize(800, 400); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        JLabel labCedula = new JLabel("Cédula del cliente");
        txtCedula = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        // Configuración de la tabla
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Cédula");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Mail");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Género");
        tableModel.addColumn("Promociones");

        // Panel para los controles de búsqueda y listado
        JPanel panel = new JPanel();
        panel.add(labCedula);
        panel.add(txtCedula);
        panel.add(btnBuscar);
        panel.add(btnEliminar);
        panel.add(btnCancelar);

        // Añadir componentes a la ventana
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true); // Hace visible la ventana
    }

    /**
     * Obtiene la cédula del cliente ingresada en el campo de texto.
     */
    public String getCedulaCliente() {
        return txtCedula.getText();
    }

    /**
     * Maneja las acciones de los componentes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBuscar) {
            String cedula = getCedulaCliente();
            MCliente cliente = cCliente.buscarCliente(cedula);
            if (cliente != null) {
                // Limpiar la tabla antes de agregar el cliente encontrado
                tableModel.setRowCount(0);
                Object[] rowData = {
                    cliente.getCedula(),
                    cliente.getNombre(),
                    cliente.getDireccion(),
                    cliente.getMail(),
                    cliente.getTelefono(),
                    cliente.getGenero() ? "Masculino" : "Femenino",
                    cliente.getPromocion() ? "Sí" : "No"
                };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == btnEliminar) {
            String cedula = getCedulaCliente();
            MCliente cliente = cCliente.buscarCliente(cedula);
            if (cliente != null) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Eliminar cliente: " + cliente.getNombre(), "Confirmar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    cCliente.eliminarCliente(cliente);
                    JOptionPane.showMessageDialog(this, "Cliente eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    tableModel.setRowCount(0); // Limpiar la tabla después de eliminar
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

