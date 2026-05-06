/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Jesús
 */
public class RegistroEnvioDTO {
    private String id_registro;
    private LocalDateTime fecha;
    private String direccion;

    public RegistroEnvioDTO() {
    }

    public RegistroEnvioDTO(String id_registro, LocalDateTime fecha, String direccion) {
        this.id_registro = id_registro;
        this.fecha = fecha;
        this.direccion = direccion;
    }

    public String getId_registro() {
        return id_registro;
    }

    public void setId_registro(String id_registro) {
        this.id_registro = id_registro;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
