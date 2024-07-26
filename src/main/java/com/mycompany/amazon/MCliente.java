// Clase que representa un cliente
package com.mycompany.amazon;

import java.io.Serializable;

/**
 * Clase que representa un cliente.
 * Extiende la clase MPersona e implementa Serializable para la persistencia de datos.
 */
public class MCliente extends MPersona implements Serializable {

    private static final long serialVersionUID = 1L; // ID para la compatibilidad de versiones de la clase

    private String telefono; // Teléfono del cliente
    private boolean genero; // Género del cliente (true para masculino, false para femenino)
    private boolean promociones; // Indica si el cliente desea recibir promociones

    /**
     * Constructor por defecto de la clase MCliente.
     */
    public MCliente() {
    }

    /**
     * Obtiene el teléfono del cliente.
     * @return El teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * @param telefono El teléfono a establecer.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el género del cliente.
     * @return true si el cliente es masculino, false si es femenino.
     */
    public boolean getGenero() {
        return genero;
    }

    /**
     * Establece el género del cliente.
     * @param genero true para masculino, false para femenino.
     */
    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el estado de las promociones del cliente.
     * @return true si el cliente desea recibir promociones, false en caso contrario.
     */
    public boolean getPromocion() {
        return promociones;
    }

    /**
     * Establece el estado de las promociones del cliente.
     * @param promociones true si el cliente desea recibir promociones, false en caso contrario.
     */
    public void setPromocion(boolean promociones) {
        this.promociones = promociones;
    }

    // Otros métodos heredados de MPersona pueden ser implementados o sobrescritos aquí.
}
