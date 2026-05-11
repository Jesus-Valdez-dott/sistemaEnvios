/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dto.ClienteDTO;
import entidades.Cliente;

/**
 *
 * @author Jesús
 */
public class ClienteMapper {
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

    public static ClienteDTO toDTO(Cliente entidad) {
        if (entidad == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setId_Cliente(entidad.getId_cliente());
        dto.setNombre(entidad.getNombre());
        dto.setTelefono(entidad.getTelefono());
        dto.setDireccion(entidad.getDireccion());
        dto.setRfc(entidad.getRfc());

        return dto;
    }
}
