package com.mycompany.amazon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class VCarrito extends JFrame implements ActionListener {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel etiquetaTotal;
    private List<MProductoVendido> productosVendidos;
    private CCarrito controladorCarrito;
    private CCliente controladorCliente;

    public VCarrito(List<MProductoVendido> productosVendidos, CCarrito controladorCarrito, CCliente controladorCliente) {
        this.productosVendidos = productosVendidos;
        this.controladorCarrito = controladorCarrito;
        this.controladorCliente = controladorCliente;
        setTitle("Carrito de Compras");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true); // La ventana siempre está al frente
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear la tabla
        String[] columnNames = {"Foto", "Descripción", "Stock", "Precio Unitario", "Cantidad Comprada"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(100);

        // Llenar la tabla con los productos vendidos
        for (MProductoVendido producto : productosVendidos) {
            ImageIcon foto = cargarImagen(producto.getCodigo());
            modeloTabla.addRow(new Object[]{foto, producto.getDescripcion(), producto.getStock(), producto.getPreciou(), producto.getCantidadComprada()});
        }

        // Agregar un listener para actualizar la cantidad comprada
        tabla.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 4) {
                int row = e.getFirstRow();
                controladorCarrito.verificarYActualizarCantidadComprada(row, modeloTabla);
                actualizarTotal();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para el total y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        // Panel para el total
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        etiquetaTotal = new JLabel("Total: $0.00");
        totalPanel.add(etiquetaTotal);
        panelInferior.add(totalPanel, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonFacturar = new JButton("Facturar");
        botonFacturar.addActionListener(this);
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(this);
        panelBotones.add(botonFacturar);
        panelBotones.add(botonCancelar);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // Listener para restablecer la cantidad comprada a cero al cerrar la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                controladorCarrito.resetCantidadComprada();
            }
        });
    }

    private ImageIcon cargarImagen(int codigo) {
        String ruta = "C:\\Users\\USUARIO\\Pictures\\Img - Proyecto POO\\" + codigo + ".jpg";
        File archivoImagen = new File(ruta);
        if (archivoImagen.exists()) {
            ImageIcon iconoOriginal = new ImageIcon(ruta);
            Image imagen = iconoOriginal.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenRedimensionada);
        } else {
            String error = "C:\\Users\\USUARIO\\Pictures\\Img - Proyecto POO\\ERROR\\sinFoto.jpg";
            ImageIcon iconoOriginal = new ImageIcon(error);
            Image imagen = iconoOriginal.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenRedimensionada);
        }
    }

    // Método para obtener el modelo de tabla
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    // Método para establecer el total
    public void setTotal(String total) {
        etiquetaTotal.setText("Total: " + total);
    }

    // Método actionPerformed
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Facturar")) {
            controladorCarrito.abrirVentanaFactura(controladorCliente.listarClientes(), controladorCliente);
            dispose();
        } else if (comando.equals("Cancelar")) {
            controladorCarrito.resetCantidadComprada();
            cancelar();
        }
    }

    private void cancelar() {
        this.dispose();
    }

    // Actualizar total
    private void actualizarTotal() {
        double total = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int cantidadComprada = Integer.parseInt(modeloTabla.getValueAt(i, 4).toString());
            double precio = (double) modeloTabla.getValueAt(i, 3);
            total += cantidadComprada * precio;
        }
        etiquetaTotal.setText("Total: $" + String.format("%.2f", total));
    }
    
}
