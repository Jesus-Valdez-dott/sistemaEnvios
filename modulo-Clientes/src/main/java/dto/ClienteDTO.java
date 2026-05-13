/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jesús
 */

/**
 * @param <T> Representa la lista de Envíos,
 * sin crear dependencia fuerte con otros módulos.
 */
public class ClienteDTO<T> {
    private String id_Cliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String rfc;
    private List<T> listaEnvios;

    public ClienteDTO() {
        this.listaEnvios = new ArrayList<T>();
    }

    public ClienteDTO(String id, String nombre, String telefono, String direccion, String rfc) {
        this.id_Cliente = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rfc = rfc;
        this.listaEnvios = new ArrayList<>();
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

    public List<T> getListaEnvios() {
        return listaEnvios;
    }

    public void setListaEnvios(List<T> listaEnvios) {
        this.listaEnvios = listaEnvios;
    }
    
    public void agregarEnvio(T envio) {
        this.listaEnvios.add(envio);
    }
}
