/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dto.ClienteDTO;
import entidades.Cliente;
import java.util.stream.Collectors;
//import mappers

/**
 *
 * @author Jesús
 */
public class ClienteMapper {
    /**
     * Convierte de DTO a Entidad.
     * La lista de envíos no se mapea a la entidad de base de datos
     * para mantener la persistencia limpia.
     */
    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente entidad = new Cliente();
        entidad.setId_cliente(dto.getId_Cliente()); 
        entidad.setNombre(dto.getNombre());
        entidad.setTelefono(dto.getTelefono());
        entidad.setDireccion(dto.getDireccion());
        entidad.setRfc(dto.getRfc());

        return entidad;
    }

    /**
     * Convierte de Entidad a DTO.
     * Deja la lista de envíos inicializada pero vacía.
     */
    public static ClienteDTO toDTO(Cliente entidad) {
        if (entidad == null) return null;

        //Se usa el constructor que inicializa la lista vacía
        ClienteDTO dto = new ClienteDTO(
            entidad.getId_cliente(),
            entidad.getNombre(),
            entidad.getTelefono(),
            entidad.getDireccion(),
            entidad.getRfc()
        );
        
        //No se interactua con el EnvioMapper aquí. 
        //la lista se queda vacía para que el Mediador la llene después.
        
        return dto;
    }
}
