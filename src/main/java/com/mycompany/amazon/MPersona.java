// Clase padre persona
package com.mycompany.amazon;

import com.mycompany.amazon.CErrores.CedulaException;
import java.io.Serializable;

/**
 * Clase que representa una persona.
 */
public class MPersona implements Serializable {

    private static final long serialVersionUID = 1L; // ID para la compatibilidad de versiones de la clase

    private String cedula; // Cédula de la persona
    private String nombre; // Nombre de la persona
    private String direccion; // Dirección de la persona
    private String mail; // Correo electrónico de la persona

    // Constructor vacío de la clase
    public MPersona() {
    }

    // Obtiene la cédula de la persona
    public String getCedula() {
        return cedula;
    }

    // Establece la cédula de la persona
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    // Obtiene el nombre de la persona
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre de la persona
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Obtiene la dirección de la persona
    public String getDireccion() {
        return direccion;
    }

    // Establece la dirección de la persona
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Obtiene el correo electrónico de la persona
    public String getMail() {
        return mail;
    }

    // Establece el correo electrónico de la persona
    public void setMail(String mail) {
        this.mail = mail;
    }

    // Valida la cédula de la persona
    public int validarCedula(String ced) throws CedulaException {
        CErrores cErrores = new CErrores();
        try {
            if (ced == null || ced.isEmpty()) {
                cErrores.lanzarExcepcion(4); // Error: cédula vacía
            }

            if (ced.length() != 10) {
                cErrores.lanzarExcepcion(1); // Error: longitud incorrecta
            } else {
                for (int i = 0; i < 10; i++) {
                    if (!Character.isDigit(ced.charAt(i))) {
                        cErrores.lanzarExcepcion(2); // Error: no es numérica
                    }
                }

                int sumatoria = 0;
                for (int j = 0; j < 9; j++) {
                    int digito = Character.getNumericValue(ced.charAt(j));
                    if (j % 2 == 0) {
                        digito *= 2;
                        if (digito > 9) {
                            digito -= 9;
                        }
                    }
                    sumatoria += digito;
                }
                int ultimoDigito = Character.getNumericValue(ced.charAt(9));
                int residuo = sumatoria % 10;
                int verificador = (residuo == 0) ? 0 : (10 - residuo);

                if (verificador != ultimoDigito) {
                    cErrores.lanzarExcepcion(3); // Error: cédula inválida
                }
            }
            return 0; // Cédula válida
        } catch (CedulaException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            cErrores.lanzarExcepcion(5); // Error: excepción no controlada
        }
        return -1;
    }
}
