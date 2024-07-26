
// Clase principal que contiene el método main
package com.mycompany.amazon;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Amazon {
    public static void main(String[] args) {
        // Crear una instancia de CCarrito
        CCarrito cCarrito = new CCarrito();
        
        // Crear una instancia de CCliente
        CCliente cCliente = new CCliente(); 

        // Crear una instancia de VMenuPrincipal y pasarle el CCarrito y CCliente
        VMenuPrincipal menuPrincipal = new VMenuPrincipal(cCarrito, cCliente);

        // Mostrar la ventana principal
        menuPrincipal.setVisible(true);
    }

    // Método para dar formato a la fecha
    private static String formatoFecha(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
}





















