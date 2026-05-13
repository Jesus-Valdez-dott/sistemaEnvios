/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dto.VentaDTO;
import entidades.Venta;
import Enums.MetodoPago;
import entidades.Envio;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class VentaMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Convierte de DTO a Entidad.
     */
    public static <T> Venta toEntity(VentaDTO<T> dto) {
        if (dto == null) return null;

        Venta entity = new Venta();
        if (dto.getId_venta() != null && !dto.getId_venta().isEmpty()) {
            entity.setId_venta(dto.getId_venta());
        }
        
        entity.setFolio(dto.getFolio());
        entity.setMonto(dto.getMonto());
        entity.setFecha(dto.getFecha());
        entity.setMetodo_pago(dto.getMetodo_pago());
        entity.setEstadoPago(dto.getEstadoPago());
        entity.setEnvios((List<Envio>) dto.getEnvios());

        return entity;
    }
    
    /**
     * Convierte de Entidad a DTO.
     */
    @SuppressWarnings("unchecked")
    public static <T> VentaDTO<T> toDTO(Venta entity) {
        if (entity == null) return null;

        VentaDTO<T> dto = new VentaDTO<>();
        
        dto.setId_venta(entity.getId_venta());
        dto.setFolio(entity.getFolio());
        dto.setMonto(entity.getMonto());
        dto.setFecha(entity.getFecha());
        dto.setMetodo_pago(entity.getMetodo_pago());
        dto.setEstadoPago(entity.getEstadoPago());
        if (entity.getEnvios() != null) {
            //Al ser un DTO genérico <T>, casteamos la lista de la entidad
            //para que coincida con el tipo esperado por el DTO.
            dto.setEnvios((List<T>) entity.getEnvios());
        }

        return dto;
    }

    /**
     * Convierte de Documento de MongoDB a DTO.
     */
    public static VentaDTO<?> fromDocumentToDTO(Document doc) {
        if (doc == null) return null;

        VentaDTO<Object> dto = new VentaDTO<>();
        dto.setId_venta(doc.getObjectId("_id").toString());
        dto.setFolio(doc.getString("folio"));
        dto.setMonto(doc.getDouble("monto"));
        dto.setEstadoPago(doc.getString("estadoPago"));

        // Conversión de String a LocalDateTime
        String fechaStr = doc.getString("fecha");
        if (fechaStr != null) {
            dto.setFecha(LocalDateTime.parse(fechaStr, formatter));
        }

        // Conversión de String a Enum MetodoPago
        String metodoStr = doc.getString("metodo_pago");
        if (metodoStr != null) {
            dto.setMetodo_pago(MetodoPago.valueOf(metodoStr));
        }

        // Para los envíos, en el documento solemos guardar solo los IDs 
        // o el objeto embebido. Por ahora lo inicializamos vacío para que el 
        // Mediador lo llene con los DTOs reales.
        dto.setEnvios(new ArrayList<>());

        return dto;
    }

    /**
     * Convierte de DTO a Documento para MongoDB.
     */
    public static Document toDocument(VentaDTO<?> dto) {
        if (dto == null) return null;

        Document doc = new Document();
        
        if (dto.getId_venta() != null && !dto.getId_venta().isEmpty()) {
            doc.append("_id", new ObjectId(dto.getId_venta()));
        } else {
            doc.append("_id", new ObjectId());
        }

        doc.append("folio", dto.getFolio())
           .append("monto", dto.getMonto())
           .append("fecha", dto.getFecha() != null ? dto.getFecha().format(formatter) : LocalDateTime.now().format(formatter))
           .append("metodo_pago", dto.getMetodo_pago() != null ? dto.getMetodo_pago().name() : null)
           .append("estadoPago", dto.getEstadoPago())
           .append("id_transaccion_stripe", dto.getId_venta_Stripe());

        //Generalmente no se guarda la lista completa de envíos dentro de la venta 
        // en Mongo para evitar documentos gigantes
        if (dto.getEnvios() != null) {
            //Guardamos solo los IDs de los envíos como referencia
            doc.append("envios_ids", dto.getEnvios()); 
        }

        return doc;
    }
}
