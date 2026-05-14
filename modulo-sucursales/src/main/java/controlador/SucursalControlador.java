/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ISucursalDAO;
import dao.SucursalDAO;
import dto.EmpleadoDTO;
import dto.SucursalDTO;
import entidades.Empleado;
import entidades.Sucursal;
import java.util.stream.Collectors;
import java.util.List;
import mappers.SucursalesMapper;

/**
 *
 * @author Jesús
 */
public class SucursalControlador {
    private final ISucursalDAO sucursalDAO;

    public SucursalControlador() {
        this.sucursalDAO = new SucursalDAO();
    }

    public boolean registrarSucursal(SucursalDTO dto) {
        //Validación para que el nombre no esté vacío
        if (dto.getNombre() == null || dto.getNombre().isEmpty()) return false;
        
        Sucursal entidad = SucursalesMapper.toEntity(dto);
        return sucursalDAO.insertar(entidad);
    }

    public List<SucursalDTO> obtenerSucursales() {
        return sucursalDAO.listarTodas().stream()
                .map(SucursalesMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public boolean insertarEmpleado(String idSucursal, EmpleadoDTO empDto) {
    if (empDto.getNombre_completo()== null) return false;

    //Convertimos DTO a Entidad (puedes crear un EmpleadoMapper sencillo)
    Empleado empleado = new Empleado();
    empleado.setNombre_completo(empDto.getNombre_completo());
    empleado.setRol(empDto.getRol());

    return sucursalDAO.agregarEmpleado(idSucursal, empleado);
}
}
