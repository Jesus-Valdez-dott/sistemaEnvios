/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.List;

/**
 *
 * @author Jesús
 */
public class ClienteDTO {
    private String id_cliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private List<EnvioDTO> envios;
    private String rfc;

    public ClienteDTO() {
    }

    public ClienteDTO(String id_cliente, String nombre, String telefono, String direccion, List<EnvioDTO> envios, String rfc) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.envios = envios;
        this.rfc = rfc;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
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

    public List<EnvioDTO> getEnvios() {
        return envios;
    }

    public void setEnvios(List<EnvioDTO> envios) {
        this.envios = envios;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
}
