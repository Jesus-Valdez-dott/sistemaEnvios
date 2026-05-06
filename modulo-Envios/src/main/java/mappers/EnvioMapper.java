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
import entidades.RegistroEnvio;
import entidades.Paquete;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author Jesús
 */
public class EnvioMapper {
    //Convierte un DTO a Entidad
    public static Envio toEntity(EnvioDTO dto) {
        if (dto == null) return null;

        Envio envio = new Envio();
        envio.setCodigo_rastreo(dto.getCodigo_rastreo());
        envio.setNombre_destinatario(dto.getNombre_destinatario());
        envio.setDireccion_destino(dto.getDireccion_destino());
        envio.setFecha_envio(dto.getFecha_envio());
        
        //Mapeo de la lista de paquetes
        List<Paquete> paquetes = new ArrayList<>();
        if (dto.getPaquetes() != null) {
            for (PaqueteDTO pDto : dto.getPaquetes()) {
                Paquete p = new Paquete();
                p.setAlto(pDto.getAlto());
                p.setAncho(pDto.getAncho());
                p.setLargo(pDto.getLargo());
                p.setPeso(pDto.getPeso());
                p.setDescripcion(pDto.getDescripcion());
                paquetes.add(p);
            }
        }
        envio.setPaquetes(paquetes);

        //Por defecto un envío nuevo entra como Registrado
        envio.setEstado(EstadoEnvio.REGISTRADO);

        return envio;
    }

    //Convierte de Entidad a DTO
    public static EnvioDTO toDTO(Envio entity) {
        if (entity == null) return null;

        EnvioDTO dto = new EnvioDTO();
        dto.setId_envio(entity.getId_envio()); //Aqui se convierte el ObjectId a String
        dto.setCodigo_rastreo(entity.getCodigo_rastreo());
        dto.setNombre_destinatario(entity.getNombre_destinatario());
        dto.setDireccion_destino(entity.getDireccion_destino());
        dto.setFecha_envio(entity.getFecha_envio());
        dto.setEstado(entity.getEstado());
        
        List<PaqueteDTO> paquetesDTO = new ArrayList<>();
        if (entity.getPaquetes() != null) {
            for (Paquete p : entity.getPaquetes()) {
                PaqueteDTO pDto = new PaqueteDTO();
                // Si tu paquete tiene un ID propio, agrégalo aquí también
                pDto.setAlto(p.getAlto());
                pDto.setAncho(p.getAncho());
                pDto.setLargo(p.getLargo());
                pDto.setPeso(p.getPeso());
                pDto.setDescripcion(p.getDescripcion());
                paquetesDTO.add(pDto);
            }
        }
        dto.setPaquetes(paquetesDTO);

        List<RegistroEnvioDTO> historialDTO = new ArrayList<>();
        if (entity.getHistorial_envio()!= null) { 
            for (RegistroEnvio registro : entity.getHistorial_envio()) {
                RegistroEnvioDTO rDto = new RegistroEnvioDTO();
                rDto.setId_registro(registro.getId_registro());
                rDto.setFecha(registro.getFecha());
                rDto.setDireccion(registro.getDireccion());
                historialDTO.add(rDto);
            }
        }
        dto.setHistorial_envio(historialDTO);
        
        return dto;
    }
}
