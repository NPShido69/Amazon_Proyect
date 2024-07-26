package com.mycompany.amazon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VFactura extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboBoxClientes;
    private JLabel lblNombreCliente, lblDireccionCliente, lblMailCliente, lblTelefonoCliente;
    private JLabel etiquetaTotal, lblNumeroOrden;
    private List<MProductoVendido> productosFiltrados;
    private List<MCliente> clientes;
    private CCarrito controladorCarrito;
    private CCliente controladorCliente;
    private CFactura controladorFactura;

    private JRadioButton rbTarjetaCredito; // Radio button para Tarjeta de Crédito
    private JRadioButton rbTransferenciaBancaria; // Radio button para Transferencia Bancaria
    private JRadioButton rbDHL; // Radio button para DHL
    private JRadioButton rbFedex; // Radio button para Fedex
    private JRadioButton rbServientrega; // Radio button para Servientrega

    public VFactura(List<MProductoVendido> productosFiltrados, List<MCliente> clientes, CCarrito controladorCarrito, CCliente controladorCliente, CFactura controladorFactura) {
        this.controladorFactura = controladorFactura;
        this.productosFiltrados = productosFiltrados;
        this.clientes = clientes;
        this.controladorCarrito = controladorCarrito;
        this.controladorCliente = controladorCliente;
        setTitle("Factura");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel superior para número de orden y selección de clientes
        JPanel panelSuperior = new JPanel(new GridLayout(5, 1));

        JPanel panelOrden = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOrden.add(new JLabel("Número de Orden:"));
        lblNumeroOrden = new JLabel();
        panelOrden.add(lblNumeroOrden);

        JPanel panelClientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelClientes.add(new JLabel("Seleccionar Cliente:"));
        comboBoxClientes = new JComboBox<>();
        comboBoxClientes.addItem("Seleccionar cliente");
        for (MCliente cliente : clientes) {
            comboBoxClientes.addItem(cliente.getCedula() + " - " + cliente.getNombre());
        }
        comboBoxClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxClientes.getSelectedIndex() != 0) {
                    rellenarDatosCliente(comboBoxClientes.getSelectedItem().toString());
                } else {
                    limpiarDatosCliente();
                }
            }
        });
        panelClientes.add(comboBoxClientes);

        JPanel panelDatosCliente = new JPanel(new GridLayout(2, 4));
        panelDatosCliente.add(new JLabel("Nombre:"));
        lblNombreCliente = new JLabel("");
        panelDatosCliente.add(lblNombreCliente);
        panelDatosCliente.add(new JLabel("Dirección:"));
        lblDireccionCliente = new JLabel("");
        panelDatosCliente.add(lblDireccionCliente);
        panelDatosCliente.add(new JLabel("Email:"));
        lblMailCliente = new JLabel("");
        panelDatosCliente.add(lblMailCliente);
        panelDatosCliente.add(new JLabel("Teléfono:"));
        lblTelefonoCliente = new JLabel("");
        panelDatosCliente.add(lblTelefonoCliente);

        // Radio Buttons para la forma de pago
        JPanel panelFormaPago = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormaPago.add(new JLabel("Forma de Pago:"));
        rbTarjetaCredito = new JRadioButton("Tarjeta de Crédito");
        rbTransferenciaBancaria = new JRadioButton("Transferencia Bancaria");
        ButtonGroup grupoPago = new ButtonGroup();
        grupoPago.add(rbTarjetaCredito);
        grupoPago.add(rbTransferenciaBancaria);
        panelFormaPago.add(rbTarjetaCredito);
        panelFormaPago.add(rbTransferenciaBancaria);

        // Radio Buttons para el modo de envío
        JPanel panelModoEnvio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelModoEnvio.add(new JLabel("Modo de Envío:"));
        rbDHL = new JRadioButton("DHL");
        rbFedex = new JRadioButton("Fedex");
        rbServientrega = new JRadioButton("Servientrega");
        ButtonGroup grupoEnvio = new ButtonGroup();
        grupoEnvio.add(rbDHL);
        grupoEnvio.add(rbFedex);
        grupoEnvio.add(rbServientrega);
        panelModoEnvio.add(rbDHL);
        panelModoEnvio.add(rbFedex);
        panelModoEnvio.add(rbServientrega);

        panelSuperior.add(panelOrden);
        panelSuperior.add(panelClientes);
        panelSuperior.add(panelDatosCliente);
        panelSuperior.add(panelFormaPago);
        panelSuperior.add(panelModoEnvio);

        add(panelSuperior, BorderLayout.NORTH);

        // Crear la tabla
        String[] columnNames = {"Descripción", "Precio Unitario", "Cantidad Comprada", "Costo Total"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        tabla = new JTable(modeloTabla);

        // Llenar la tabla con los productos filtrados
        double total = 0.0;
        for (MProductoVendido producto : this.productosFiltrados) {
            int cantidadComprada = producto.getCantidadComprada();
            double costoTotal = cantidadComprada * producto.getPreciou();
            total += costoTotal;
            modeloTabla.addRow(new Object[]{producto.getDescripcion(), producto.getPreciou(), cantidadComprada, costoTotal});
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para el total y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        // Panel para el total
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        etiquetaTotal = new JLabel("Total: $" + String.format("%.2f", total));
        totalPanel.add(etiquetaTotal);
        panelInferior.add(totalPanel, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonFacturar = new JButton("Facturar");
        JButton botonCancelar = new JButton("Cancelar");
        panelBotones.add(botonFacturar);
        panelBotones.add(botonCancelar);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // Añadir ActionListener a los botones
        botonFacturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facturar();
            }
        });
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        // Cargar número de orden
        cargarNumeroOrden();
    }

    private void rellenarDatosCliente(String clienteInfo) {
        String cedula = clienteInfo.split(" - ")[0];
        MCliente cliente = controladorFactura.buscarCliente(cedula);

        if (cliente != null) {
            lblNombreCliente.setText(cliente.getNombre());
            lblDireccionCliente.setText(cliente.getDireccion());
            lblMailCliente.setText(cliente.getMail());
            lblTelefonoCliente.setText(cliente.getTelefono());
        }
    }

    private void limpiarDatosCliente() {
        lblNombreCliente.setText("");
        lblDireccionCliente.setText("");
        lblMailCliente.setText("");
        lblTelefonoCliente.setText("");
    }

    private void facturar() {
        try {
            controladorFactura.facturar();
            this.dispose();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        controladorCarrito.resetCantidadComprada();
        this.dispose();
    }

    private void cargarNumeroOrden() {
        int numeroOrden = controladorFactura.cargarNumeroOrden();
        lblNumeroOrden.setText(String.valueOf(numeroOrden));
    }
}

