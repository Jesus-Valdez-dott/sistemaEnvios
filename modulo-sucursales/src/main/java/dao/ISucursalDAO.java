/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import entidades.Sucursal;
import entidades.Empleado;
import java.util.List;

/**
 *
 * @author Jesús
 */
public interface ISucursalDAO {
    boolean insertar(Sucursal sucursal);
    Sucursal buscarPorId(String id);
    List<Sucursal> listarTodas();
    boolean agregarEmpleado(String idSucursal, Empleado empleado);
}
