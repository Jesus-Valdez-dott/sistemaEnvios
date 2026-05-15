/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;

import com.formdev.flatlaf.FlatLightLaf;
import dto.VentaDTO;
import ui.recursos.Fuentes;
import gui.FrmVenta;
/**
 *
 * @author Jesús
 */
public class pruebaVenta {
    public static void main(String[] args) {
        // 1. Configurar el estilo visual (FlatLaf)
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Error al inicializar FlatLaf");
        }

        // 2. Cargar tus fuentes de Roboto
        Fuentes.cargarFuentes();

        // 3. Crear un DTO de Venta de prueba
        // Nota: Uso VentaDTO<String> asumiendo que el ID es String, 
        // ajústalo según tu implementación.
        VentaDTO<String> ventaPrueba = new VentaDTO<>();
        ventaPrueba.setMonto(850.50); // Un monto para probar
//        ventaPrueba.setConcepto("Envío Express - Guía TEST-999");
        ventaPrueba.setMetodo_pago(Enums.MetodoPago.TARJETA);

        // 4. Ejecutar la interfaz de pago
        java.awt.EventQueue.invokeLater(() -> {
            // Pasamos la venta creada a la pantalla de pago
            FrmVenta pantallaPago = new FrmVenta(ventaPrueba);
            pantallaPago.setVisible(true);
            
            System.out.println(">>> Pasarela de pago abierta para la venta de: $" + ventaPrueba.getMonto());
        });
    }
}
