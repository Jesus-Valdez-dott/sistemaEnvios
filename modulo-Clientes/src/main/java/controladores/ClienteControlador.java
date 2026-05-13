/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.ClienteDAO;
import dao.IClienteDAO;
import dto.ClienteDTO;
import entidades.Cliente;
import java.util.List;
import java.util.stream.Collectors;
import mappers.ClienteMapper;

/**
 *
 * @author Jesús
 */
public class ClienteControlador {
    private final IClienteDAO clienteDAO;

    public ClienteControlador() {
        this.clienteDAO = new ClienteDAO();
    }

    /**
     * Registra un cliente nuevo. 
     * El DTO puede traer una lista de envíos vacía, no importa.
     */
    public boolean registrarCliente(ClienteDTO dto) {
        // El Mapper solo convierte los datos del Cliente (nombre, rfc, etc.)
        Cliente entidad = ClienteMapper.toEntity(dto);
        return clienteDAO.agregarCliente(entidad);
    }

    /**
     * Obtiene la lista de clientes.
     * Los DTOs resultantes tendrán su lista de envíos vacía por defecto.
     */
    public List<ClienteDTO> obtenerTodos() {
        return clienteDAO.listarClientes().stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un cliente por ID.
     * Retorna solo la información del cliente.
     */
    public ClienteDTO buscarPorId(String id) {
        Cliente entidad = clienteDAO.buscarCliente(id);
        return ClienteMapper.toDTO(entidad);
    }

    public boolean eliminarCliente(String id) {
        return clienteDAO.eliminarCliente(id);
    }
}
