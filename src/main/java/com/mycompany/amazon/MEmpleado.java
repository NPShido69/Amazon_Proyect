// Clase que representa un empleado
package com.mycompany.amazon;

/**
 * Clase que representa un empleado.
 */
public class MEmpleado extends MPersona {
    private float comision; // Comisión del empleado
    private int numeroVentas; // Número de ventas del empleado

    // Constructor de la clase
    public MEmpleado(float comision) {
        this.comision = comision;
    }

    // Obtiene la comisión del empleado
    public float getComision() {
        return comision;
    }

    // Establece la comisión del empleado
    public void setComision(float comision) {
        this.comision = comision;
    }

    // Obtiene el número de ventas del empleado
    public int getNumeroVentas() {
        return numeroVentas;
    }

    // Establece el número de ventas del empleado
    public void setNumeroVentas(int numeroVentas) {
        this.numeroVentas = numeroVentas;
    }

    // Calcula la comisión total del empleado
    public float calcularComision(int numeroVentas, float valorVentas) {
        float comisionTotal = 0;

        if (numeroVentas == 0) {
            comisionTotal = valorVentas * 0.5f * comision;
        } else if (numeroVentas >= 1 && numeroVentas <= 3) {
            comisionTotal = valorVentas * 0.5f * comision;
        } else {
            comisionTotal = valorVentas * comision;
        }

        return comisionTotal;
    }
}


