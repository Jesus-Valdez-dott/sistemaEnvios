/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.MetodoPago;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class VentaDTO<T> {
    private String id_venta;
    private String id_venta_Stripe;
    private String folio;
    private double monto;
    private List<T> envios;
    private LocalDateTime fecha;
    private MetodoPago metodo_pago;
    private String estadoPago;

    public VentaDTO() {
        this.fecha = LocalDateTime.now();
        this.estadoPago = "PENDIENTE";
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public List<T> getEnvios() {
        return envios;
    }

    public void setEnvios(List<T> envios) {
        this.envios = envios;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(MetodoPago metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getId_venta_Stripe() {
        return id_venta_Stripe;
    }

    public void setId_venta_Stripe(String id_venta_Stripe) {
        this.id_venta_Stripe = id_venta_Stripe;
    }
}
