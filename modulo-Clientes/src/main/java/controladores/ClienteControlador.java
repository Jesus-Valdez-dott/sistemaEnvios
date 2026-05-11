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

    public boolean registrarCliente(ClienteDTO dto) {
        Cliente entidad = ClienteMapper.toEntity(dto);
        
        return clienteDAO.agregarCliente(entidad);
    }

    public List<ClienteDTO> obtenerTodos() {
        return clienteDAO.listarClientes().stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(String id) {
        Cliente entidad = clienteDAO.buscarCliente(id);
        return ClienteMapper.toDTO(entidad);
    }

    public boolean eliminarCliente(String id) {
        return clienteDAO.eliminarCliente(id);
    }
}
