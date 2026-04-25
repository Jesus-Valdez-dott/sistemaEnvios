/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Enums.EstadoEnvio;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class EnvioDTO {
    private String id_envio;
    private String codigo_rastreo;
    private LocalDate fecha_envio;
    private EstadoEnvio estado;
    private List<PaqueteDTO> paquetes;
    private String nombre_destinatario;
    private String direccion_destino;
    private String telefono_destinatario;
    private LocalDate fecha_entrega;
    private String id_empleado_entrega;

    public EnvioDTO() {
    }

    public EnvioDTO(String id_envio, String codigo_rastreo, LocalDate fecha_envio, EstadoEnvio estado, List<PaqueteDTO> paquetes, String nombre_destinatario, String direccion_destino, String telefono_destinatario, LocalDate fecha_entrega, String id_empleado_entrega) {
        this.id_envio = id_envio;
        this.codigo_rastreo = codigo_rastreo;
        this.fecha_envio = fecha_envio;
        this.estado = estado;
        this.paquetes = paquetes;
        this.nombre_destinatario = nombre_destinatario;
        this.direccion_destino = direccion_destino;
        this.telefono_destinatario = telefono_destinatario;
        this.fecha_entrega = fecha_entrega;
        this.id_empleado_entrega = id_empleado_entrega;
    }

    public String getId_envio() {
        return id_envio;
    }

    public void setId_envio(String id_envio) {
        this.id_envio = id_envio;
    }

    public String getCodigo_rastreo() {
        return codigo_rastreo;
    }

    public void setCodigo_rastreo(String codigo_rastreo) {
        this.codigo_rastreo = codigo_rastreo;
    }

    public LocalDate getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(LocalDate fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    public List<PaqueteDTO> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<PaqueteDTO> paquetes) {
        this.paquetes = paquetes;
    }

    public String getNombre_destinatario() {
        return nombre_destinatario;
    }

    public void setNombre_destinatario(String nombre_destinatario) {
        this.nombre_destinatario = nombre_destinatario;
    }

    public String getDireccion_destino() {
        return direccion_destino;
    }

    public void setDireccion_destino(String direccion_destino) {
        this.direccion_destino = direccion_destino;
    }

    public String getTelefono_destinatario() {
        return telefono_destinatario;
    }

    public void setTelefono_destinatario(String telefono_destinatario) {
        this.telefono_destinatario = telefono_destinatario;
    }

    public LocalDate getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(LocalDate fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getId_empleado_entrega() {
        return id_empleado_entrega;
    }

    public void setId_empleado_entrega(String id_empleado_entrega) {
        this.id_empleado_entrega = id_empleado_entrega;
    }
    
}
