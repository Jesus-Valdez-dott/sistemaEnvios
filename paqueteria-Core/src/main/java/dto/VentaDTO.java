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
public class VentaDTO {
    private String id_venta;
    private String folio;
    private double monto;
    private List<EnvioDTO> envios;
    private LocalDateTime fecha;
    private MetodoPago metodo_pago;

    public VentaDTO() {
    }

    public VentaDTO(String id_venta, String folio, double monto, List<EnvioDTO> envios, LocalDateTime fecha, MetodoPago metodo_pago) {
        this.id_venta = id_venta;
        this.folio = folio;
        this.monto = monto;
        this.envios = envios;
        this.fecha = fecha;
        this.metodo_pago = metodo_pago;
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

    public List<EnvioDTO> getEnvios() {
        return envios;
    }

    public void setEnvios(List<EnvioDTO> envios) {
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
    
}
