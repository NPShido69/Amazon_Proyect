// Clase para controlar los datos del cliente
package com.mycompany.amazon;

import java.util.ArrayList;
import java.util.List;

public class CCliente {
    private List<MCliente> clientes; // Lista de clientes
    private CErrores errores; // Controlador de errores

    /**
     * Constructor para inicializar la lista de clientes y el controlador de errores.
     */
    public CCliente() {
        this.clientes = new ArrayList<>(); // Inicializar la lista vacía
        this.errores = new CErrores(); // Inicializar el controlador de errores
    }

    /**
     * Método para agregar un cliente a la lista.
     * @param cliente El cliente a agregar.
     * @throws IllegalArgumentException si la cédula es inválida o ya existe.
     * @throws com.mycompany.amazon.CErrores.CedulaException
     */
   public void agregarCliente(MCliente cliente) {
    try {
        // Validar la cédula
        int resultado = cliente.validarCedula(cliente.getCedula());
        if (resultado != 0) {
            String mensajeError = errores.getMensajeError(resultado);
            throw new IllegalArgumentException(mensajeError);
        }

        // Verificar si la cédula ya existe
        if (buscarCliente(cliente.getCedula()) != null) {
            throw new IllegalArgumentException("La cédula ya existe.");
        }
        // Agregar el cliente a la lista

        clientes.add(cliente);
    } catch (Exception e) {
    } 
}

    /**
     * Método para obtener la lista de clientes.
     * @return La lista de clientes.
     */
    public List<MCliente> listarClientes() {
        return clientes;
    }

    /**
     * Método para buscar un cliente por su cédula.
     * @param cedula La cédula del cliente a buscar.
     * @return El cliente encontrado, o null si no se encuentra.
     */
    public MCliente buscarCliente(String cedula) {
        for (MCliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente; // Retorna el cliente si se encuentra
            }
        }
        return null; // Retorna null si el cliente no se encuentra
    }

    /**
     * Método para eliminar un cliente de la lista.
     * @param cliente El cliente a eliminar.
     */
    public void eliminarCliente(MCliente cliente) {
        clientes.remove(cliente);
    }

    /**
     * Método para validar la cédula.
     * @param cedula La cédula a validar.
     * @return El código de error, o 0 si es válida.
     */
    public int validarCedula(String cedula) throws CErrores.CedulaException {
        MPersona persona = new MPersona();
        return persona.validarCedula(cedula);
    }

    /**
     * Método para obtener el mensaje de error correspondiente a un código de error.
     * @param codigoError El código de error.
     * @return El mensaje de error.
     */
    public String getMensajeError(int codigoError) {
        return errores.getMensajeError(codigoError);
    }

    /**
     * Método para verificar si una cédula ya existe.
     * @param cedula La cédula a verificar.
     * @return true si la cédula ya existe, false en caso contrario.
     */
    public boolean cedulaExiste(String cedula) {
        return buscarCliente(cedula) != null;
    }
}


















