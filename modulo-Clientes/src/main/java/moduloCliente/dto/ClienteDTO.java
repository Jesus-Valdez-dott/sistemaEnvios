/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moduloCliente.dto;

import java.util.ArrayList;
import java.util.List;
import moduloEnvios.dto.EnvioDTO;

public class ClienteDTO {

    private long idCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String rfc;

    private List<EnvioDTO> listaEnvios;

    public ClienteDTO() {
        listaEnvios = new ArrayList<>();
    }

    public ClienteDTO(long idCliente,
                      String nombre,
                      String telefono,
                      String direccion,
                      String rfc) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;

        listaEnvios = new ArrayList<>();
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
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

    public List<EnvioDTO> getListaEnvios() {
        return listaEnvios;
    }

    public void setListaEnvios(List<EnvioDTO> listaEnvios) {
        this.listaEnvios = listaEnvios;
    }

    public void agregarEnvio(EnvioDTO envio) {
        listaEnvios.add(envio);
    }
}