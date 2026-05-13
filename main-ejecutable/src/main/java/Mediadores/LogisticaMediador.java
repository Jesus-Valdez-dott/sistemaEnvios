/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mediadores;

import controladores.ClienteControlador;
import controladores.EnvioControlador;
import dto.ClienteDTO;
import dtos.EnvioDTO;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class LogisticaMediador {
    private final ClienteControlador clienteCtrl;
    private final EnvioControlador envioCtrl;

    public LogisticaMediador() {
        // El mediador conoce a ambos coordinadores
        this.clienteCtrl = new ClienteControlador();
        this.envioCtrl = new EnvioControlador();
    }

    /**
     * Este método resuelve tu problema: junta los dos mundos 
     * sin que los módulos se peleen entre sí.
     */
    public ClienteDTO obtenerClienteConSusEnvios(String idCliente) {
        // 1. Pedimos los datos básicos al módulo de Clientes
        ClienteDTO cliente = clienteCtrl.buscarPorId(idCliente);
        
        if (cliente != null) {
            // 2. Pedimos la lista de envíos al módulo de Envíos
            // Nota: Aquí ya recibimos DTOs, por eso no hay error de mappers
//            List<EnvioDTO> envios = envioCtrl.obtenerEnviosPorCliente(idCliente);
            
            // 3. Los inyectamos en el DTO de cliente
//            cliente.setListaEnvios(envios);
        }
        
        return cliente;
    }

    /**
     * Ejemplo de proceso complejo: Registrar una venta que implica 
     * crear un cliente (si es nuevo) y sus envíos.
     */
    public void registrarOperacionCompleta(ClienteDTO clienteDTO, List<EnvioDTO> nuevosEnvios) {
        // 1. Registrar cliente mediante su propio experto
        clienteCtrl.registrarCliente(clienteDTO);
        
        // 2. Registrar cada envío mediante su propio experto
        for (EnvioDTO envio : nuevosEnvios) {
//            envio.setId_Cliente(clienteDTO.getId_Cliente()); // Vinculamos
            envioCtrl.guardarEnvio(envio);
        }
    }
}
