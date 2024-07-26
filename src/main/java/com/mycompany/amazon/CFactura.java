package com.mycompany.amazon;

/**
 *
 * @author Sebastián
 */
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
//Imports para hacer el PDF
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import java.awt.Desktop;

public class CFactura {

    private List<MProductoVendido> productosVendidos;
    private List<MCliente> clientes;
    private CCarrito controladorCarrito;
    private CCliente controladorCliente;
    private MCliente clienteSeleccionado;

    private static final String PATH_ARCHIVO_TXT = "C:\\Users\\USUARIO\\Desktop\\Tareas\\Recursos\\FacturasDeTecsto\\Factura.txt";

    public CFactura(List<MProductoVendido> productosVendidos, List<MCliente> clientes, CCarrito controladorCarrito, CCliente controladorCliente) {
        this.productosVendidos = productosVendidos;
        this.clientes = clientes;
        this.controladorCarrito = controladorCarrito;
        this.controladorCliente = controladorCliente;
    }

    // Filtrar productos con cantidad comprada mayor a cero
    public List<MProductoVendido> getProductosFiltrados() {
        return productosVendidos.stream()
                .filter(producto -> producto.getCantidadComprada() > 0)
                .collect(Collectors.toList());
    }

    public List<MCliente> getClientes() {
        return clientes;
    }

    public MCliente buscarCliente(String cedula) {
        for (MCliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                this.clienteSeleccionado = cliente;
                return cliente;
            }
        }
        return null;
    }

    public int cargarNumeroOrden() {
        File archivo = new File(PATH_ARCHIVO_TXT);
        try (BufferedReader leer = new BufferedReader(new FileReader(archivo))) {
            String numeroTexto = leer.readLine();
            if (numeroTexto != null) {
                return Integer.parseInt(numeroTexto.trim());
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de orden.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 1; // Valor predeterminado si no se puede leer el archivo
    }

    private void actualizarNumeroOrden(int nuevoNumero) {
        File archivo = new File(PATH_ARCHIVO_TXT);
        try (BufferedWriter escribir = new BufferedWriter(new FileWriter(archivo))) {
            escribir.write(String.valueOf(nuevoNumero));
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al escribir el archivo de orden.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void facturar() {
        if (clienteSeleccionado == null) {
            throw new IllegalStateException("Debe seleccionar un cliente primero.");
        }

        // Actualizar stock de los productos
        actualizarStock();

        // Lógica para confirmar la factura
        JOptionPane.showMessageDialog(null, "Factura confirmada para el cliente: " + clienteSeleccionado.getNombre(), "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        // Cargar el número de orden actual y actualizarlo
        int numeroOrdenActual = cargarNumeroOrden();
        actualizarNumeroOrden(numeroOrdenActual + 1);

        // Cerrar la ventana y reiniciar la cantidad comprada
        controladorCarrito.resetCantidadComprada();
    }

    private void actualizarStock() {
        for (MProductoVendido producto : productosVendidos) {
            int nuevaCantidad = producto.getStock() - producto.getCantidadComprada();
            if (nuevaCantidad < 0) {
                throw new IllegalStateException("No hay suficiente stock para el producto: " + producto.getDescripcion());
            }
            producto.setStock(nuevaCantidad);
        }
        controladorCarrito.guardarDatos(); // Guardar los cambios en el archivo
    }

    private void generarPDF(String contenido, String orden) {
        String ruta = "C:\\Users\\USUARIO\\Desktop\\Tareas\\Recursos\\FacturasDeTecsto\\";
        String destino = ruta + orden + ".pdf";

        try {
            // Verificar si la ruta de destino existe
            File directorio = new File(ruta);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear la ruta de destino si no existe
            }

            // Generar el archivo PDF
            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document documento = new Document(pdfDoc);

            // Agregar un título en negrita
            Text titulo = new Text("Factura\n").setBold().setFontSize(20);
            Paragraph parrafoTitulo = new Paragraph(titulo);
            documento.add(parrafoTitulo);

            // Agregar contenido con formato
            Text textoFormateado = new Text(contenido)
                    .setFontSize(12)
                    .setFontColor(new DeviceRgb(0, 0, 255)); // Color azul
            Paragraph parrafo = new Paragraph(textoFormateado);
            documento.add(parrafo);

            // Agregar texto normal
            documento.add(new Paragraph("Fin de la factura"));

            documento.close();

            // Abrir el PDF en vista previa
            File file = new File(destino);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) { // Si existe aplicación para abrir PDFs
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("No se reconoce aplicación para PDFs");
                }
            } else {
                System.out.println("El archivo PDF no se encuentra.");
            }
        } catch (IOException ex) {
            System.out.println("Error al crear o abrir el archivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public MCliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public CCarrito getControladorCarrito() {
        return controladorCarrito;
    }

    public CCliente getControladorCliente() {
        return controladorCliente;
    }
}
