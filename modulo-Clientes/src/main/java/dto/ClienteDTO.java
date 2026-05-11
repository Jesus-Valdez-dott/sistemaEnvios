/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import entidades.Envio;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class ClienteDTO {
    private String id_Cliente;
    private String nombre;
    private String telefono;
    private String direccion;
//    private List<EnvioDTO> envios;
    private String rfc;

    public ClienteDTO() {}

    public ClienteDTO(String id, String nombre, String telefono, String direccion, String rfc) {
        this.id_Cliente = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;
    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}
