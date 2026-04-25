/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import java.time.LocalDateTime;
import Enums.MetodoPago;

/**
 *
 * @author Jesús
 */
public class Venta {
    private String id_venta;
    private String folio;
    private double monto;
    private List<Envio> envios;
    private LocalDateTime fecha;
    private MetodoPago metodo_pago;
    
}
