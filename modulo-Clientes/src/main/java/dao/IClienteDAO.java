/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import entidades.Cliente;
import java.util.List;

/**
 *
 * @author Jesús
 */
public interface IClienteDAO {
    boolean agregarCliente(Cliente cliente);
    boolean eliminarCliente(String telefono);
    boolean actualizarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente buscarCliente(String telefono); 
}
