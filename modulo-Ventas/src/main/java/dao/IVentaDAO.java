/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import entidades.Venta;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jesús
 */
public interface IVentaDAO {
    boolean guardarVenta(Venta entidad);
    Venta buscarVentaPorId(String idVenta);
    List<Venta> listarVentasPorCliente(String idCliente);
    List<Venta> obtenerVentasMesPasado(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
