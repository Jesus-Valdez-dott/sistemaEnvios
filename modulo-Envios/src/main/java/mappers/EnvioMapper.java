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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;


/**
 *
 * @author Jesús
 */
public class EnvioMapper {
 
    // DTO -> Entidad (para guardar en BD)
    public static Envio toEntity(EnvioDTO dto) {
        if (dto == null) return null;
 
        Envio envio = new Envio();
        if (dto.getId_envio() != null) envio.setId_envio(dto.getId_envio());
 
        envio.setId_cliente(dto.getId_cliente());
        envio.setCodigo_rastreo(dto.getCodigo_rastreo());
        envio.setNombre_destinatario(dto.getNombre_destinatario());
        envio.setDireccion_destino(dto.getDireccion_destino());
        envio.setLatitud_destino(dto.getLatitud_destino());
        envio.setLongitud_destino(dto.getLongitud_destino());
        envio.setTelefono_destinatario(dto.getTelefono_destinatario());
        envio.setFecha_envio(dto.getFecha_envio());
        envio.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoEnvio.REGISTRADO);
 
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
 
    // Entidad -> DTO (para mostrar en pantalla)
    public static EnvioDTO toDTO(Envio entity) {
        if (entity == null) return null;
 
        EnvioDTO dto = new EnvioDTO();
        dto.setId_envio(entity.getId_envio());
        dto.setId_cliente(entity.getId_cliente());
        dto.setCodigo_rastreo(entity.getCodigo_rastreo());
        dto.setNombre_destinatario(entity.getNombre_destinatario());
        dto.setDireccion_destino(entity.getDireccion_destino());
        dto.setLatitud_destino(entity.getLatitud_destino());
        dto.setLongitud_destino(entity.getLongitud_destino());
        dto.setTelefono_destinatario(entity.getTelefono_destinatario());
        dto.setFecha_envio(entity.getFecha_envio());
        dto.setEstado(entity.getEstado());
 
        if (entity.getPaquetes() != null) {
            dto.setPaquetes(entity.getPaquetes().stream().map(p ->
                new PaqueteDTO(null, p.getAlto(), p.getLargo(), p.getAncho(), p.getPeso(), p.getDescripcion())
            ).collect(Collectors.toList()));
        }
 
        if (entity.getHistorial_envio() != null) {
            dto.setHistorial_envio(entity.getHistorial_envio().stream().map(reg ->
                new RegistroEnvioDTO(reg.getId_registro(), reg.getFecha(), reg.getDireccion())
            ).collect(Collectors.toList()));
        }
 
        return dto;
    }
 
    // Documento MongoDB -> Entidad (reconstruccion completa)
    public static Envio fromDocumentToEntity(Document doc) {
        if (doc == null) return null;
 
        Envio entity = new Envio();
        entity.setId_envio(doc.getObjectId("_id").toString());
        entity.setId_cliente(doc.getString("id_cliente"));
        entity.setCodigo_rastreo(doc.getString("codigo_rastreo"));
        entity.setNombre_destinatario(doc.getString("nombre_destinatario"));
        entity.setDireccion_destino(doc.getString("direccion_destino"));
        entity.setLatitud_destino(doc.getString("latitud_destino"));
        entity.setLongitud_destino(doc.getString("longitud_destino"));
        entity.setTelefono_destinatario(doc.getString("telefono_destinatario"));
 
        String fechaStr = doc.getString("fecha_envio");
        if (fechaStr != null) {
            entity.setFecha_envio(java.time.LocalDate.parse(fechaStr));
        }
 
        String estadoStr = doc.getString("estado");
        if (estadoStr != null) {
            entity.setEstado(EstadoEnvio.valueOf(estadoStr));
        }
 
        // CORREGIDO: reconstruir paquetes embebidos
        List<Document> docsPaquetes = (List<Document>) doc.get("paquetes");
        if (docsPaquetes != null) {
            List<Paquete> listaPaquetes = new ArrayList<>();
            for (Document d : docsPaquetes) {
                Paquete p = new Paquete();
                p.setAlto(d.getDouble("alto"));
                p.setAncho(d.getDouble("ancho"));
                p.setLargo(d.getDouble("largo"));
                p.setPeso(d.getDouble("peso"));
                p.setDescripcion(d.getString("descripcion"));
                listaPaquetes.add(p);
            }
            entity.setPaquetes(listaPaquetes);
        }
 
        // CORREGIDO: reconstruir historial embebido
        List<Document> docsHistorial = (List<Document>) doc.get("historial_envio");
        if (docsHistorial != null) {
            List<entidades.RegistroEnvio> listaHistorial = new ArrayList<>();
            for (Document h : docsHistorial) {
                entidades.RegistroEnvio reg = new entidades.RegistroEnvio();
                String fechaHito = h.getString("fecha");
                if (fechaHito != null) {
                    reg.setFecha(LocalDateTime.parse(fechaHito));
                }
                reg.setDireccion(h.getString("direccion"));
                reg.setLatitud(h.getString("latitud"));
                reg.setLongitud(h.getString("longitud"));
                listaHistorial.add(reg);
            }
            entity.setHistorial_envio(listaHistorial);
        }
 
        return entity;
    }
}
