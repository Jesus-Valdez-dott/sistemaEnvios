/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Jesús
 */
public class PaqueteDTO {
    private String id_paquete;
    private double alto;
    private double largo;
    private double ancho;
    private double peso;
    private String descripcion;

    public PaqueteDTO() {
    }

    public PaqueteDTO(String id_paquete, double alto, double largo, double ancho, double peso, String descripcion) {
        this.id_paquete = id_paquete;
        this.alto = alto;
        this.largo = largo;
        this.ancho = ancho;
        this.peso = peso;
        this.descripcion = descripcion;
    }

    public String getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(String id_paquete) {
        this.id_paquete = id_paquete;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
