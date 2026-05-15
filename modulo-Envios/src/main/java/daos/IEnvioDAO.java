/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import Enums.EstadoEnvio;
import entidades.Envio;
import java.util.List;

/**
 *
 * @author Jesús
 */
public interface IEnvioDAO {
    boolean registrarEnvio(Envio e);
    List<Envio> obtenerHistCliente(String id_Cliente);
    boolean actualizarEdo(String id_Envio, EstadoEnvio edo);
    Envio obtenerDetalles(String id_Envio);
    Envio rastrearPaquete(String codigo_rastreo);
    boolean agregarHitoHistorial(String idEnvio, RegistroEnvioDTO movimiento);
}
