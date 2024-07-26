package com.mycompany.amazon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase para la vista de listar clientes.
 */
public class VListarClientes extends JFrame {
    private JTextField txtCedulaBuscar; // Campo de texto para buscar por cédula
    private JButton btnBuscar; // Botón para buscar cliente
    private JButton btnListar; // Botón para listar todos los clientes
    private JButton btnCancelar; // Botón para cancelar y cerrar la ventana
    private JTable table; // Tabla para mostrar clientes
    private DefaultTableModel tableModel; // Modelo de la tabla
    private List<MCliente> clientes; // Lista de clientes

    /**
     * Constructor de la clase.
     */
    public VListarClientes(List<MCliente> clientes) {
        this.clientes = clientes;
        initComponents(); // Inicializa los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz.
     */
    private void initComponents() {
        setTitle("Listar Clientes"); // Establece el título de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Configura el cierre de la ventana
        setAlwaysOnTop(true); // Hace que la ventana esté siempre en la parte superior
        setSize(800, 400); // Establece el tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Panel para buscar clientes
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCedulaBuscar = new JLabel("Cédula a buscar:");
        txtCedulaBuscar = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCliente(txtCedulaBuscar.getText()));
        btnListar = new JButton("Listar Todos");
        btnListar.addActionListener(e -> listarClientes());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        
        panelBuscar.add(lblCedulaBuscar);
        panelBuscar.add(txtCedulaBuscar);
        panelBuscar.add(btnBuscar);
        panelBuscar.add(btnListar);
        panelBuscar.add(btnCancelar);

        // Panel para la tabla de clientes
        JPanel panelTable = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Cédula");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Mail");
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Género");
        tableModel.addColumn("Promociones");
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane, BorderLayout.CENTER);

        // Añadir paneles a la ventana
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelBuscar, BorderLayout.NORTH);
        getContentPane().add(panelTable, BorderLayout.CENTER);
    }

    /**
     * Busca un cliente por cédula y lo muestra en la tabla.
     */
    private void buscarCliente(String cedula) {
        tableModel.setRowCount(0); // Limpiar la tabla

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una cédula para buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean encontrado = false;
        for (MCliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
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
                encontrado = true;
                break; // Termina la búsqueda después de encontrar el cliente
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Lista todos los clientes en la tabla.
     */
    private void listarClientes() {
        tableModel.setRowCount(0); // Limpiar la tabla

        for (MCliente cliente : clientes) {
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
        }
    }
}

