/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import Enums.EstadoEnvio;
import daos.EnvioDAO;
import daos.IEnvioDAO;
import dtos.EnvioDTO;
import dtos.RegistroEnvioDTO;
import entidades.Envio;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;
import mappers.EnvioMapper;

/**
 *
 * @author Jesús
 */
public class EnvioControlador {
 
    private final IEnvioDAO envioDAO;
 
    public EnvioControlador() {
        this.envioDAO = new EnvioDAO();
    }
 
    /**
     * Registra un nuevo envio. Genera codigo de rastreo si no viene uno.
     */
    public boolean guardarEnvio(EnvioDTO datos) {
        try {
            if (datos.getCodigo_rastreo() == null || datos.getCodigo_rastreo().isEmpty()) {
                datos.setCodigo_rastreo("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            }
            // Estado inicial siempre es REGISTRADO
            datos.setEstado(EstadoEnvio.REGISTRADO);
 
            Envio envioNuevo = EnvioMapper.toEntity(datos);
            boolean exito = envioDAO.registrarEnvio(envioNuevo);
 
            if (exito) {
                System.out.println("Envio registrado con codigo: " + datos.getCodigo_rastreo());
            }
            return exito;
 
        } catch (Exception e) {
            System.err.println("Error al procesar el envio: " + e.getMessage());
            return false;
        }
    }
 
    /**
     * Busca un envio por su _id de MongoDB.
     * Usado internamente entre modulos.
     */
    public EnvioDTO obtenerDetallesPorId(String idEnvio) {
        Envio envio = envioDAO.obtenerDetalles(idEnvio);
        return EnvioMapper.toDTO(envio);
    }
 
    /**
     * Busca un envio por su codigo de rastreo (TRK-XXXX).
     * CORREGIDO: ahora llama a rastrearPaquete, no a obtenerDetalles.
     */
    public EnvioDTO rastrearEnvio(String codigo) {
        Envio envio = envioDAO.rastrearPaquete(codigo);
        if (envio == null) return null;
 
        EnvioDTO dto = EnvioMapper.toDTO(envio);
 
        // Ordena historial del mas reciente al mas antiguo
        if (dto.getHistorial_envio() != null) {
            dto.getHistorial_envio().sort((r1, r2) -> r2.getFecha().compareTo(r1.getFecha()));
        }
        return dto;
    }
 
    /**
     * Cambia el estado del envio y agrega un hito al historial automaticamente.
     */
    public boolean actualizarEstado(String idEnvio, String nuevoEstadoStr) {
        try {
            EstadoEnvio estado = EstadoEnvio.valueOf(nuevoEstadoStr);
            return envioDAO.actualizarEdo(idEnvio, estado);
        } catch (IllegalArgumentException e) {
            System.err.println("Estado no valido: " + nuevoEstadoStr);
            return false;
        }
    }
 
    /**
     * Agrega un hito de ubicacion al historial de rastreo de un envio.
     */
    public boolean actualizarHistorial(String idEnvio, RegistroEnvioDTO movimiento) {
        return envioDAO.agregarHitoHistorial(idEnvio, movimiento);
    }
 
    /**
     * Devuelve todos los envios de un cliente por su id.
     */
    public List<EnvioDTO> obtenerEnviosPorCliente(String idCliente) {
        List<Envio> listaEntidades = envioDAO.obtenerHistCliente(idCliente);
        return listaEntidades.stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList());
    }
}