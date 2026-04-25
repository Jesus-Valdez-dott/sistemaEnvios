/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;
import java.util.List;
import Enums.EstadoEnvio;

/**
 *
 * @author Jesús
 */
public class Envio {
    private String id_envio;
    private String codigo_rastreo;
    private LocalDate fecha_envio;
    private EstadoEnvio estado;
    private List<Paquete> paquetes;
    private List<RegistroEnvio> historial_envio;
    private String nombre_destinatario;
    private String direccion_destino;
    private String telefono_destinatario;
    private LocalDate fecha_entrega;
    private String id_empleado_entrega;
    
}
