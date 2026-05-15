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
import dtos.PaqueteDTO;
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
 
    // --- VENTAS ---
 
    public boolean procesarVentaFinal(VentaDTO<?> venta) {
        return ventaControlador.procesarVenta(venta);
    }
    
    public List<VentaDTO<?>> obtenerVentasUltimoMes() {
        return ventaControlador.generarReporteUltimoMes();
    }
 
    // --- ENVIOS ---
 
    public boolean registrarEnvioConGeocodificacion(EnvioDTO envio) {
        // Obtiene coordenadas de la direccion y las guarda en el DTO
        double[] coords = mapaService.obtenerCoordenadas(envio.getDireccion_destino());
        
        envio.setLatitud_destino(String.valueOf(coords[0]));
        envio.setLongitud_destino(String.valueOf(coords[1]));
        return envioControlador.guardarEnvio(envio);
    }
 
    public boolean registrarMovimiento(String idEnvio, RegistroEnvioDTO movimiento) {
        
        return envioControlador.actualizarHistorial(idEnvio, movimiento);
    }
    
    public double calcularCostoTotal(EnvioDTO envio) {
        double tarifaBase = 150.00;
        double precioPorKg = 25.50;
        double costoTotal = 0;
 
        if (envio.getPaquetes() == null || envio.getPaquetes().isEmpty()) {
            return tarifaBase;
        }
 
        for (PaqueteDTO p : envio.getPaquetes()) {
            // 1. Calculamos peso volumétrico
            double pesoVolumetrico = (p.getLargo() * p.getAncho() * p.getAlto()) / 5000;
 
            // 2. Tomamos el mayor entre el real y el volumétrico
            double pesoACobrar = Math.max(p.getPeso(), pesoVolumetrico);
 
            // 3. Sumamos al total del envío
            costoTotal += tarifaBase + (pesoACobrar * precioPorKg);
        }
 
        return costoTotal;
    }
    
    public List<EnvioDTO> consultarEnviosPorCliente(String id) {
        // El controlador se encarga de buscar en la BD ya sea por nombre o teléfono
        return envioControlador.obtenerEnviosPorCliente(id);
    }
 
    // --- RASTREO ---
 
    /**
     * Busca el envio por codigo y devuelve el DTO completo con historial.
     * La vista usa este metodo para mostrar la informacion.
     */
    public EnvioDTO rastrearEnvio(String codigo) {
        
        return envioControlador.rastrearEnvio(codigo);
    }
 
    /**
     * Abre el mapa en el navegador con la ubicacion mas reciente del envio.
     */
    public void abrirMapaUbicacionActual(String codigo) {
        EnvioDTO envio = envioControlador.rastrearEnvio(codigo);
 
        if (envio != null && envio.getHistorial_envio() != null && !envio.getHistorial_envio().isEmpty()) {
            // El historial ya viene ordenado del mas reciente al mas antiguo
            RegistroEnvioDTO actual = envio.getHistorial_envio().get(0);
            // CORREGIDO: getLatitud() y getLongitud() existen en RegistroEnvioDTO
            mapaService.abrirMapaEnNavegador(actual.getLatitud(), actual.getLongitud());
        }
    }
 
    // --- SUCURSALES ---
 
    public List<SucursalDTO> obtenerCatalogoSucursales() {
        return sucursalControlador.obtenerSucursales();
    }
 
    public boolean vincularEmpleadoASucursal(String idSucursal, EmpleadoDTO empleado) {
        return sucursalControlador.insertarEmpleado(idSucursal, empleado);
    }
 
    // --- CLIENTES ---
 
    public ClienteDTO buscarCliente(String id) {
        return clienteControlador.buscarPorId(id);
    }
 
    public boolean darDeAltaCliente(ClienteDTO cliente) {
        return clienteControlador.registrarCliente(cliente);
    }
 
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteControlador.obtenerTodos();
    }
}