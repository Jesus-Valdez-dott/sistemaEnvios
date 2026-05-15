/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mediadores;

import controladores.ClienteControlador;
import controladores.EnvioControlador;
import controladores.VentaControlador;
import controlador.SucursalControlador;
import apiMapa.NominatimService;
import dto.ClienteDTO;
import dto.VentaDTO;
import dto.EmpleadoDTO;
import dto.SucursalDTO;
import dtos.EnvioDTO;
import dtos.RegistroEnvioDTO;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class LogisticaMediador {
    private final VentaControlador ventaControlador;
    private final EnvioControlador envioControlador;
    private final ClienteControlador clienteControlador;
    private final SucursalControlador sucursalControlador;
    private final NominatimService mapaService;

    public LogisticaMediador() {
        this.ventaControlador = new VentaControlador();
        this.envioControlador = new EnvioControlador();
        this.clienteControlador = new ClienteControlador();
        this.sucursalControlador = new SucursalControlador();
        this.mapaService = new NominatimService();
    }

    // --- SECCIÓN: VENTAS Y PAGOS ---
    
    public boolean procesarVentaFinal(VentaDTO<?> venta) {
        // Llama al controlador que ya tiene integrado Stripe
        return ventaControlador.procesarVenta(venta);
    }

    // --- SECCIÓN: ENVÍOS Y LOGÍSTICA ---

    public boolean registrarEnvioConGeocodificacion(EnvioDTO envio) {
        // 1. Usamos el servicio de mapas para obtener coordenadas del destino
        double[] coords = mapaService.obtenerCoordenadas(envio.getDireccion_destino());
        envio.setLatitud_destino(String.valueOf(coords[0]));
        envio.setLongitud_destino(String.valueOf(coords[1]));

        // 2. Guardamos el envío a través del controlador
        return envioControlador.guardarEnvio(envio);
    }

    /**
     * Registra un nuevo hito en el historial del paquete (ej. llegada a sucursal)
     */
    public boolean registrarMovimiento(String idEnvio, RegistroEnvioDTO movimiento) {
        // Si el movimiento viene de una sucursal, podríamos jalar las coordenadas de la sucursal aquí
        return envioControlador.actualizarHistorial(idEnvio, movimiento);
    }
    
    public boolean registrarLlegadaASucursal(String idEnvio, String nombreSucursal, String lat, String lon) {
        RegistroEnvioDTO hito = new RegistroEnvioDTO();
        hito.setDireccion(nombreSucursal);
        hito.setLatitud(lat);
        hito.setLongitud(lon);

        // Llamamos al método que acabamos de crear
        return envioControlador.actualizarHistorial(idEnvio, hito);
    }

    // --- SECCIÓN: RASTREO (API + HISTORIAL) ---

    public EnvioDTO ejecutarRastreoCompleto(String codigo) {
        EnvioDTO envio = envioControlador.obtenerSeguimientoCompleto(codigo);

        if (envio != null && envio.getHistorial_envio()!= null && !envio.getHistorial_envio().isEmpty()) {
            // Obtenemos el punto más reciente
            RegistroEnvioDTO actual = envio.getHistorial_envio().get(0);
        }
        return envio;
    }
    
    public void abrirMapaUbicacionActual(String codigo) {
    // 1. Buscamos el envío completo (que ya trae su historial)
    EnvioDTO envio = envioControlador.obtenerSeguimientoCompleto(codigo);

    if (envio != null && envio.getHistorial_envio()!= null && !envio.getHistorial_envio().isEmpty()) {
        // 2. Obtenemos el hito más reciente (índice 0 si está ordenado por fecha)
        RegistroEnvioDTO actual = envio.getHistorial_envio().get(0);
        
        // 3. Extraemos las coordenadas (que tienes como String)
        String lat = actual.getLatitud();
        String lon = actual.getLongitud();

        // 4. Validamos que no estén vacías antes de abrir
        if (lat != null && !lat.isEmpty() && lon != null && !lon.isEmpty()) {
            mapaService.abrirMapaEnNavegador(lat, lon);
        }
    }
}

    // --- SECCIÓN: SUCURSALES Y EMPLEADOS ---

    public List<SucursalDTO> obtenerCatalogoSucursales() {
        return sucursalControlador.obtenerSucursales();
    }

    public boolean vincularEmpleadoASucursal(String idSucursal, EmpleadoDTO empleado) {
        return sucursalControlador.insertarEmpleado(idSucursal, empleado);
    }

    // --- SECCIÓN: CLIENTES ---

    public ClienteDTO buscarCliente(String id) {
        return clienteControlador.buscarPorId(id);
    }

    public boolean darDeAltaCliente(ClienteDTO cliente) {
        return clienteControlador.registrarCliente(cliente);
    }
}
