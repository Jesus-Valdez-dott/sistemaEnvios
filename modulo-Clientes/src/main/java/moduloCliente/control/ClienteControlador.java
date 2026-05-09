/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moduloCliente.control;

import java.util.List;
import moduloCliente.dao.ClienteDAO;
import moduloCliente.dto.ClienteDTO;

public class ClienteControlador {

    private final ClienteDAO dao;

    public ClienteControlador() {
        dao = new ClienteDAO();
    }

    public void registrarCliente(ClienteDTO cliente) {
        dao.agregarCliente(cliente);
    }

    public List<ClienteDTO> obtenerClientes() {
        return dao.obtenerClientes();
    }
}