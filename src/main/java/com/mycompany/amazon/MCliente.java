// Clase que representa un cliente
package com.mycompany.amazon;

/**
 * Clase que representa un cliente.
 */
/**
 * Modelo del cliente.
 */
public class MCliente extends MPersona {
    private String telefono; // RUC del cliente
    private boolean genero; // Género del cliente
    private boolean promociones; // Indica si el cliente desea recibir promociones

    // Métodos getter y setter para los atributos del cliente
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public boolean getPromocion() {
        return promociones;
    }

    public void setPromocion(boolean promociones) {
        this.promociones = promociones;
    }

    public MCliente() {
    }
}



