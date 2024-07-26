package com.mycompany.amazon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CCliente {
    private List<MCliente> clientes; // Lista de clientes
    private CErrores errores; // Controlador de errores
    private static final String FILE_PATH = "clientes.ser"; // Archivo para guardar los clientes

    public CCliente() {
        this.clientes = new ArrayList<>(); 
        this.errores = new CErrores(); 
        cargarClientes(); // Cargar los clientes al iniciar
    }

    public void agregarCliente(MCliente cliente) {
        try {
            int resultado = cliente.validarCedula(cliente.getCedula());
            if (resultado != 0) {
                String mensajeError = errores.getMensajeError(resultado);
                throw new IllegalArgumentException(mensajeError);
            }

            if (buscarCliente(cliente.getCedula()) != null) {
                throw new IllegalArgumentException("La cédula ya existe.");
            }

            clientes.add(cliente);
            guardarClientes(); // Guardar después de agregar un cliente
        } catch (Exception e) {
            // Manejo de excepciones
        } 
    }

    public List<MCliente> listarClientes() {
        return clientes;
    }

    public MCliente buscarCliente(String cedula) {
        for (MCliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    public void eliminarCliente(MCliente cliente) {
        clientes.remove(cliente);
        guardarClientes(); // Guardar después de eliminar un cliente
    }

    public int validarCedula(String cedula) throws CErrores.CedulaException {
        MPersona persona = new MPersona();
        return persona.validarCedula(cedula);
    }

    public String getMensajeError(int codigoError) {
        return errores.getMensajeError(codigoError);
    }

    public boolean cedulaExiste(String cedula) {
        return buscarCliente(cedula) != null;
    }

    // Método para guardar la lista de clientes
    private void guardarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace(); // Manejar excepción de E/S
        }
    }

    // Método para cargar la lista de clientes
    @SuppressWarnings("unchecked")
    private void cargarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            clientes = (List<MCliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            clientes = new ArrayList<>(); // Inicializar lista si hay un error
            e.printStackTrace(); // Manejar excepción de E/S o de clase no encontrada
        }
    }
}
