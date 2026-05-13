/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import Enums.EstadoEnvio;
import dtos.EnvioDTO;
import dtos.PaqueteDTO;
import dtos.RegistroEnvioDTO;
import entidades.Envio;
import entidades.Paquete;
import java.util.stream.Collectors;
import org.bson.Document;


/**
 *
 * @author Jesús
 */
public class EnvioMapper {
    //Convierte un DTO a Entidad
    public static Envio toEntity(EnvioDTO dto) {
        if (dto == null) return null;

        Envio envio = new Envio();
        // El ID solo se setea si no es nuevo (para actualizaciones)
        if (dto.getId_envio() != null) envio.setId_envio(dto.getId_envio());
        
        envio.setId_cliente(dto.getId_cliente());
        envio.setCodigo_rastreo(dto.getCodigo_rastreo());
        envio.setNombre_destinatario(dto.getNombre_destinatario());
        envio.setDireccion_destino(dto.getDireccion_destino());
        envio.setTelefono_destinatario(dto.getTelefono_destinatario()); // Agregado
        envio.setFecha_envio(dto.getFecha_envio());
        envio.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoEnvio.REGISTRADO);

        // Mapeo de paquetes
        if (dto.getPaquetes() != null) {
            envio.setPaquetes(dto.getPaquetes().stream().map(pDto -> {
                Paquete p = new Paquete();
                p.setAlto(pDto.getAlto());
                p.setAncho(pDto.getAncho());
                p.setLargo(pDto.getLargo());
                p.setPeso(pDto.getPeso());
                p.setDescripcion(pDto.getDescripcion());
                return p;
            }).collect(Collectors.toList()));
        }
        return envio;
    }

    public static EnvioDTO toDTO(Envio entity) {
        if (entity == null) return null;

        EnvioDTO dto = new EnvioDTO();
        dto.setId_envio(entity.getId_envio());
        dto.setId_cliente(entity.getId_cliente());
        dto.setCodigo_rastreo(entity.getCodigo_rastreo());
        dto.setNombre_destinatario(entity.getNombre_destinatario());
        dto.setDireccion_destino(entity.getDireccion_destino());
        dto.setTelefono_destinatario(entity.getTelefono_destinatario());
        dto.setFecha_envio(entity.getFecha_envio());
        dto.setEstado(entity.getEstado());
        
        // Mapeo de paquetes a DTO
        if (entity.getPaquetes() != null) {
            dto.setPaquetes(entity.getPaquetes().stream().map(p -> 
                new PaqueteDTO(null, p.getAlto(), p.getLargo(), p.getAncho(), p.getPeso(), p.getDescripcion())
            ).collect(Collectors.toList()));
        }

        // Mapeo de Historial a DTO
        if (entity.getHistorial_envio() != null) {
            dto.setHistorial_envio(entity.getHistorial_envio().stream().map(reg -> 
                new RegistroEnvioDTO(reg.getId_registro(), reg.getFecha(), reg.getDireccion())
            ).collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public static Envio fromDocumentToEntity (Document doc){
        if (doc == null) return null;

        Envio entity = new Envio();
        
        // Extraemos del documento de Mongo. 
        entity.setId_envio(doc.getObjectId("_id").toString());
        entity.setCodigo_rastreo(doc.getString("codigo_rastreo"));
        entity.setNombre_destinatario(doc.getString("nombre_destinatario"));
        entity.setDireccion_destino(doc.getString("direccion_destino"));
        entity.setTelefono_destinatario(doc.getString("telefono_destinatario"));
        String fechaStr = doc.getString("fecha_envio");
        // Conversión de String a LocalDate
        if (fechaStr != null) {
            entity.setFecha_envio(java.time.LocalDate.parse(fechaStr));
        }
        // Conversión de String a Enum (EstadoEnvio)
        String estadoStr = doc.getString("estado");
        if (estadoStr != null) {
            entity.setEstado(Enums.EstadoEnvio.valueOf(estadoStr));
        }
        entity.setId_cliente(doc.getString("id_cliente"));

        return entity;
    }
}
