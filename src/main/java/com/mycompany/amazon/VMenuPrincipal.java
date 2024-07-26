package com.mycompany.amazon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Clase para la vista del menú principal.
 */
public class VMenuPrincipal extends JFrame {
    private  JMenuBar menu; // Barra de menú
    private JMenu menuArchivo; // Menú Archivo
    private JMenu menuProductos; // Menú Productos
    private JMenu menuClientes; // Menú Clientes
    private JMenu menuCarrito; // Menú Carrito
    private JMenu menuAyuda; // Menú Ayuda
    private JMenuItem salir; // Opción de menú para salir
    private JMenuItem ingresoProductos; // Opción de menú para ingresar productos
    private JMenuItem eliminarProductos; // Opción de menú para eliminar productos
    private JMenuItem listarProductos; // Opción de menú para listar productos
    private JMenuItem modificarProductos; // Opción de menú para modificar productos
    private JMenuItem ingresoClientes; // Opción de menú para ingresar clientes
    private JMenuItem eliminarClientes; // Opción de menú para eliminar clientes
    private JMenuItem listarClientes; // Opción de menú para listar clientes
    private JMenuItem modificarClientes; // Opción de menú para modificar clientes
    private JMenuItem compra; // Opción de menú para realizar compras en el carrito
    private JMenuItem acercaDe; // Opción de menú para información "Acerca de"
    private CCarrito cCarrito; // Controlador del carrito
    private CCliente cCliente; // Controlador de clientes

    // Constructor de la clase
    public VMenuPrincipal(CCarrito cCarrito, CCliente cCliente) {
        this.cCarrito = cCarrito;
        this.cCliente = cCliente;

        // Creación del menú
        menu = new JMenuBar();

        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        salir = new JMenuItem("Salir");
        salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK)); // Atajo para salir
        menuArchivo.add(salir);

        // Menú Clientes
        menuClientes = new JMenu("Clientes");
        ingresoClientes = new JMenuItem("Ingresar");
        menuClientes.add(ingresoClientes);
        modificarClientes = new JMenuItem("Modificar");
        menuClientes.add(modificarClientes);
        eliminarClientes = new JMenuItem("Eliminar");
        menuClientes.add(eliminarClientes);
        listarClientes = new JMenuItem("Listar");
        menuClientes.add(listarClientes);
        
        // Menú Productos
        menuProductos = new JMenu("Productos");
        ingresoProductos = new JMenuItem("Ingresar");
        menuProductos.add(ingresoProductos);
        modificarProductos = new JMenuItem("Modificar");
        menuProductos.add(modificarProductos);
        eliminarProductos = new JMenuItem("Eliminar");
        menuProductos.add(eliminarProductos);
        listarProductos = new JMenuItem("Listar Productos");
        menuProductos.add(listarProductos);

        // Menú Carrito
        menuCarrito = new JMenu("Carrito");
        compra = new JMenuItem("Compra");
        menuCarrito.add(compra);

        // Menú Ayuda
        menuAyuda = new JMenu("Ayuda");
        acercaDe = new JMenuItem("Acerca de");
        menuAyuda.add(acercaDe);

        // Agregar elementos al menú
        menu.add(menuArchivo);
        menu.add(menuClientes);
        menu.add(menuProductos);
        menu.add(menuCarrito);
        menu.add(menuAyuda);

        // Configuración de la ventana principal
        setLayout(null);
        setTitle("Mercado Al-Mussayar");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setJMenuBar(menu);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Crear el controlador y pasarle la vista y los modelos
        CMenuPrincipal controlador = new CMenuPrincipal(this, cCarrito, cCliente);

        // Asignar el controlador como ActionListener a los elementos del menú
        controlador.initListeners();
    }

    // Getters para los elementos del menú
    public JMenuItem getSalir() {
        return salir;
    }

    public JMenuItem getIngresoProductos() {
        return ingresoProductos;
    }

    public JMenuItem getEliminarProductos() {
        return eliminarProductos;
    }

    public JMenuItem getListarProductos() {
        return listarProductos;
    }

    public JMenuItem getModificarProductos() {
        return modificarProductos;
    }

    public JMenuItem getIngresoClientes() {
        return ingresoClientes;
    }

    public JMenuItem getEliminarClientes() {
        return eliminarClientes;
    }

    public JMenuItem getListarClientes() {
        return listarClientes;
    }

    public JMenuItem getModificarClientes() {
        return modificarClientes;
    }

    public JMenuItem getCompra() {
        return compra;
    }

    public JMenuItem getAcercaDe() {
        return acercaDe;
    }
}










