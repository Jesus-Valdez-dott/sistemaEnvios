/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import Enums.EstadoEnvio;
import daos.IEnvioDAO;
import dtos.EnvioDTO;
import entidades.Envio;
import java.util.UUID;
import mappers.EnvioMapper;

/**
 *
 * @author Jesús
 */
public class EnvioControlador {
    private final IEnvioDAO envioDAO;

    public EnvioControlador(IEnvioDAO envioDAO) {
        this.envioDAO = envioDAO;
    }

    /**
     * Recibe los datos de la pantalla FormularioEnvio y los procesa.
     */
    public boolean guardarEnvio(EnvioDTO datos) {
        try {
            // 1. Generar un código de rastreo único (ej. TRK-8f9a...) si no viene uno
            if (datos.getCodigo_rastreo() == null || datos.getCodigo_rastreo().isEmpty()) {
                datos.setCodigo_rastreo("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            }

            // 2. Usar el Mapper para convertir el DTO a Entidad
            Envio envioNuevo = EnvioMapper.toEntity(datos);

            // 3. Mandarlo a guardar a la base de datos
            boolean exito = envioDAO.registrarEnvio(envioNuevo);
            
            if (exito) {
                System.out.println("Envío registrado exitosamente con código: " + envioNuevo.getCodigo_rastreo());
                // FUTURO: Aquí es donde el Mediador gritará "¡Nuevo envío creado!"
            }
            
            return exito;
            
        } catch (Exception e) {
            System.err.println("Error al procesar el envío: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recibe un código desde la VistaRastreo y devuelve la información empaquetada.
     */
    public EnvioDTO rastrearEnvio(String codigo) {
        //Buscar en la base de datos
        Envio envioEncontrado = envioDAO.obtenerDetalles(codigo);
        
        if (envioEncontrado == null) {
            return null;
        }

        //Mapear la entidad de vuelta a un DTO para que la pantalla lo pueda mostrar
        return EnvioMapper.toDTO(envioEncontrado);
    }

    /**
     * Actualiza el estado de un envío (Ej. de 'Registrado' a 'En_Transito').
     */
    public boolean actualizarEstado(String idEnvio, String nuevoEstadoStr) {
        try {
            //Se convierte el String al Enum correspondiente
            EstadoEnvio estado = EstadoEnvio.valueOf(nuevoEstadoStr);
            
            //Actualizar en la base de datos
            return envioDAO.actualizarEdo(idEnvio, estado);
            
        } catch (IllegalArgumentException e) {
            System.err.println("El estado proporcionado no es válido: " + nuevoEstadoStr);
            return false;
        }
    }
}
