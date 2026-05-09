/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package moduloCliente.dao;

import java.util.List;
import moduloCliente.dto.ClienteDTO;

public interface IClienteDAO {

    void agregarCliente(ClienteDTO cliente);

    List<ClienteDTO> obtenerClientes();

    ClienteDTO buscarCliente(long idCliente);

    boolean eliminarCliente(long idCliente);
}