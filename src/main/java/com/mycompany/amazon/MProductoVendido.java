// Clase que representa un producto vendido
package com.mycompany.amazon;

/**
 * Clase que representa un producto vendido.
 */
import java.io.Serializable;
public class MProductoVendido  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int codigo;
    private String descripcion;
    private int stock;
    private double preciou;
    private int cantidadComprada; // Nuevo atributo para la cantidad comprada

    public MProductoVendido(int codigo, String descripcion, int stock, double preciou) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.preciou = preciou;
        this.cantidadComprada = 0; // Inicializar en 0
    }

    // Getters y setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPreciou() {
        return preciou;
    }

    public void setPreciou(double preciou) {
        this.preciou = preciou;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }
}


