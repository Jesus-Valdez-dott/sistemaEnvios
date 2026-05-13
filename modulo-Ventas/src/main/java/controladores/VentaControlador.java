/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dto.VentaDTO;
import dao.IVentaDAO;
import dao.VentaDAO;
import servicios.StripeService;
import mappers.VentaMapper;
import entidades.Venta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Jesús
 */
public class VentaControlador {
    private final IVentaDAO ventaDAO;
    private final StripeService stripeService;

    public VentaControlador() {
        this.ventaDAO = new VentaDAO();
        this.stripeService = new StripeService();
    }

    /**
     * Procesa una venta: Realiza el cobro en Stripe y persiste los datos.
     * @param <T> Tipo de objeto vendido (Envios)
     * @param dto El DTO con la información de la venta
     * @return true si el pago y el registro fueron exitosos
     */
    public <T> boolean procesarVenta(VentaDTO<T> dto) {
        //Se Llama al servicio (que ahora usa la API real de Test)
        String stripeId = stripeService.procesarPagoSimulado(dto.getMonto());

        if (stripeId != null) {
            // 2. Si Stripe nos da un ID, la venta es válida
            dto.setId_venta_Stripe(stripeId);
            dto.setEstadoPago("PAGADO");

            //Se Genera el folio usando el ID de Stripe para que sea único
            dto.setFolio("BOL-" + stripeId.substring(stripeId.length() - 8).toUpperCase());

            //Se guarda la venta
            Venta entidad = VentaMapper.toEntity(dto);
            return ventaDAO.guardarVenta(entidad);
        } else {
            dto.setEstadoPago("FALLIDO");
            return false;
        }
    }

    /**
     * Recupera el historial de ventas de un cliente específico.
     */
    public List<VentaDTO<?>> obtenerHistorialCliente(String idCliente) {
        // Obtenemos las entidades del DAO
        List<Venta> ventasEntidad = ventaDAO.listarVentasPorCliente(idCliente);

        // Convertimos la lista de entidades a DTOs usando el Mapper
        return ventasEntidad.stream()
                .map(VentaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<VentaDTO<?>> generarReporteUltimoMes() {
        //Calculamos el rango: desde hace 1 mes hasta ahora
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime haceUnMes = ahora.minusMonths(1);

        //Pedimos las entidades al DAO
        List<Venta> ventas = ventaDAO.obtenerVentasMesPasado(haceUnMes, ahora);

        //Convertimos a DTO para la vista
        return ventas.stream()
                .map(VentaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    
    
    /**
     * Busca una venta específica por su folio o ID.
     */
    public VentaDTO<?> consultarVenta(String idVenta) {
        Venta entidad = ventaDAO.buscarVentaPorId(idVenta);
        return VentaMapper.toDTO(entidad);
    }
}
