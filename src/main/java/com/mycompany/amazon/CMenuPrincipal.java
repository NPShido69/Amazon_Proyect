package com.mycompany.amazon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador para manejar las acciones del menú principal.
 */
public class CMenuPrincipal implements ActionListener {
    private VMenuPrincipal vista; // Vista del menú principal
    private CCarrito cCarrito; // Controlador del carrito (productos)
    private CCliente cCliente; // Controlador de clientes

    // Constructor de la clase
    public CMenuPrincipal(VMenuPrincipal vista, CCarrito cCarrito, CCliente cCliente) {
        this.vista = vista;
        this.cCarrito = cCarrito;
        this.cCliente = cCliente;
    }

    // Método para inicializar los ActionListener
    public void initListeners() {
        this.vista.getSalir().addActionListener(this);
        this.vista.getIngresoProductos().addActionListener(this);
        this.vista.getEliminarProductos().addActionListener(this);
        this.vista.getListarProductos().addActionListener(this);
        this.vista.getModificarProductos().addActionListener(this);
        this.vista.getIngresoClientes().addActionListener(this);
        this.vista.getEliminarClientes().addActionListener(this);
        this.vista.getListarClientes().addActionListener(this);
        this.vista.getModificarClientes().addActionListener(this);
        this.vista.getCompra().addActionListener(this);
        this.vista.getAcercaDe().addActionListener(this);
    }

    // Método que maneja las acciones de los elementos del menú
    @Override
    public void actionPerformed(ActionEvent e) {
        // Acción para salir de la aplicación
        if (e.getSource() == vista.getSalir()) {
            System.exit(0);
        }

        // Acciones relacionadas con productos
        if (e.getSource() == vista.getIngresoProductos()) {
            VIngresoProductos vIngresoProductos = new VIngresoProductos(cCarrito);
            vIngresoProductos.setVisible(true);
        } else if (e.getSource() == vista.getEliminarProductos()) {
            List<MProductoVendido> productos = cCarrito.listarProductos();
            VEliminarProducto vEliminarProducto = new VEliminarProducto(productos, cCarrito);
            vEliminarProducto.setVisible(true);
        } else if (e.getSource() == vista.getListarProductos()) {
            // Obtener los productos del carrito directamente desde CCarrito
            List<MProductoVendido> productos = cCarrito.listarProductos();

            // Mostrar la nueva vista de listado de productos
            VListarProductos vListarProductos = new VListarProductos(productos);
            vListarProductos.setVisible(true);
        } else if (e.getSource() == vista.getModificarProductos()) {
            String codigo = JOptionPane.showInputDialog(vista, "Ingrese el código del producto a modificar:");
            MProductoVendido producto = cCarrito.buscarProducto(codigo);
            if (producto != null) {
                VModificarProducto vModificarProducto = new VModificarProducto(cCarrito, producto);
                vModificarProducto.mostrar();
            } else {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Acciones relacionadas con clientes
        if (e.getSource() == vista.getIngresoClientes()) {
            // Mostrar ventana de ingreso de clientes
            VIngresoCliente vIngresoCliente = new VIngresoCliente(cCliente);
            vIngresoCliente.setVisible(true);
        } else if (e.getSource() == vista.getEliminarClientes()) {
            // Mostrar ventana de eliminación de clientes
            VEliminarClientes vEliminarCliente = new VEliminarClientes(cCliente);
            vEliminarCliente.setVisible(true);
        } else if (e.getSource() == vista.getListarClientes()) {
            // Obtener lista de clientes y mostrar ventana de listado de clientes
            List<MCliente> clientes = cCliente.listarClientes();
            VListarClientes vListarClientes = new VListarClientes(clientes);
            vListarClientes.setVisible(true);
        } else if (e.getSource() == vista.getModificarClientes()) {
            String cedula = JOptionPane.showInputDialog(vista, "Ingrese la cédula del cliente a modificar:");
            MCliente cliente = cCliente.buscarCliente(cedula);
            if (cliente != null) {
                VModificarCliente vModificarCliente = new VModificarCliente(cCliente, cliente);
                vModificarCliente.mostrar();
            } else {
                JOptionPane.showMessageDialog(vista, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Acción para mostrar información sobre la aplicación
        if (e.getSource() == vista.getAcercaDe()) {
            String mensajeAcercaDe = "*** Mercado Al-Mussayar***\n" +
                    "Desarrollado por: Luis Coronel, Jordan Carmona, Damian Ortiz \n" +
                    "UPS - 2024";
            JOptionPane.showMessageDialog(vista, mensajeAcercaDe, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        }

        // Acción para mostrar el carrito de compras
        if (e.getSource() == vista.getCompra()) {
            List<MProductoVendido> productosVendidos = cCarrito.listarProductos();
            VCarrito vCarrito = new VCarrito(productosVendidos, cCarrito, cCliente); // Pasar el controlador de carrito y clientes
            vCarrito.setVisible(true);
        }
    }
}





















