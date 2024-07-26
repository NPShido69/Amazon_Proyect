package com.mycompany.amazon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

public class CCarrito {
    private List<MProductoVendido> productos; 
    private static final String PATH_ARCHIVO = "C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\RESPALDOS JORDAN\\Amazon6"; // Ruta del archivo

    public CCarrito() { 
        this.productos = new ArrayList<>();
        cargarDatos(); // Cargar datos al iniciar
        resetCantidadComprada(); // Restablecer cantidad comprada a cero al iniciar
    }

    // Método para agregar un producto al carrito
    public void agregarProducto(int codigo, String descripcion, int stock, double preciou) throws IllegalArgumentException {
        for (MProductoVendido p : productos) {
            if (p.getCodigo() == codigo) {
                throw new IllegalArgumentException("El código del producto ya existe.");
            }
        }
        
        MProductoVendido producto = new MProductoVendido(codigo, descripcion, stock, preciou);
        productos.add(producto);
        guardarDatos(); // Guardar datos después de agregar un producto
    }

    // Método para buscar un producto en el carrito por su código
    public MProductoVendido buscarProducto(String codigo) {
        int codigoInt = Integer.parseInt(codigo);
        for (MProductoVendido producto : productos) {
            if (producto.getCodigo() == codigoInt) {
                return producto;
            }
        }
        return null;
    }

    // Método para actualizar el stock de un producto (sin guardar automáticamente)
    public void actualizarStock(int codigo, int cantidad) {
        MProductoVendido producto = buscarProducto(String.valueOf(codigo));
        if (producto != null) {
            producto.setStock(cantidad);
        }
    }

    // Método para eliminar un producto del carrito
    public void eliminarProducto(MProductoVendido producto) {
        productos.remove(producto);
        guardarDatos(); // Guardar datos después de eliminar un producto
    }

    // Método para listar todos los productos del carrito
    public List<MProductoVendido> listarProductos() {
        return productos;
    }

    // Método para listar todos los productos vendidos
    public List<MProductoVendido> listarProductosVendidos() {
        return productos;
    }

    // Restablecer cantidad comprada
    public void resetCantidadComprada() {
        for (MProductoVendido producto : productos) {
            producto.setCantidadComprada(0);
        }
        guardarDatos(); // Guardar datos después de restablecer la cantidad comprada
    }

    // Método para cargar datos desde el archivo
    private void cargarDatos() {
        try (ObjectInputStream archivoEntrada = new ObjectInputStream(new FileInputStream(PATH_ARCHIVO))) {
            productos = (ArrayList<MProductoVendido>) archivoEntrada.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, no se hace nada
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar datos en el archivo
    public void guardarDatos() {
        try (ObjectOutputStream archivoSalida = new ObjectOutputStream(new FileOutputStream(PATH_ARCHIVO))) {
            archivoSalida.writeObject(new ArrayList<>(productos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar y actualizar cantidad comprada
    public void verificarYActualizarCantidadComprada(int rowIndex, DefaultTableModel modeloTabla) {
        MProductoVendido producto = productos.get(rowIndex);
        int cantidadComprada = Integer.parseInt(modeloTabla.getValueAt(rowIndex, 4).toString());

        if (cantidadComprada > producto.getStock()) {
            JOptionPane.showMessageDialog(null, "Cantidad comprada excede el stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setValueAt(producto.getCantidadComprada(), rowIndex, 4);
        } else {
            producto.setCantidadComprada(cantidadComprada);
            modeloTabla.setValueAt(producto.getStock() - cantidadComprada, rowIndex, 2); // Actualiza la columna del stock en la tabla visualmente
        }
    }

    // Método para verificar si el código del producto ya existe y devolver el modelo
    public MProductoVendido verificarCodigoProducto(String codigoStr) {
        if (!codigoStr.isEmpty()) {
            return buscarProducto(codigoStr);
        }
        return null;
    }

    // Método para actualizar un producto existente
    public void actualizarProducto(int codigo, String descripcion, int cantidad, double precioU) {
        MProductoVendido producto = buscarProducto(String.valueOf(codigo));
        if (producto != null) {
            producto.setDescripcion(descripcion);
            producto.setStock(cantidad);
            producto.setPreciou(precioU);
            guardarDatos();
        }
    }

    // Método para abrir la ventana de factura
    public void abrirVentanaFactura(List<MCliente> clientes, CCliente controladorCliente) {
        List<MProductoVendido> productosFiltrados = listarProductosVendidos().stream()
                .filter(producto -> producto.getCantidadComprada() > 0)
                .collect(Collectors.toList());
        CFactura controladorFactura = new CFactura(productosFiltrados, clientes, this, controladorCliente);
        VFactura ventanaFactura = new VFactura(productosFiltrados, clientes, this, controladorCliente, controladorFactura);
        ventanaFactura.setVisible(true);
    }
}
