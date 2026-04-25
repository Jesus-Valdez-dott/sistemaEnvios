/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.EnumRol;
/**
 *
 * @author Jesús
 */
public class EmpleadoDTO {
    private String id_empleado;
    private String nombre_completo;
    private EnumRol rol;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(String id_empleado, String nombre_completo, EnumRol rol) {
        this.id_empleado = id_empleado;
        this.nombre_completo = nombre_completo;
        this.rol = rol;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public EnumRol getRol() {
        return rol;
    }

    public void setRol(EnumRol rol) {
        this.rol = rol;
    }
    
}
