/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import com.formdev.flatlaf.FlatLightLaf;
import daos.EnvioDAO;
import entidades.Envio;
import entidades.RegistroEnvio;
import ui.recursos.Fuentes;
import gui.FrmRastreo;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author Jesús
 */
public class pruebaRastreo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 1. Configurar Look And Feel (Coherencia con tu proyecto)
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("Error al iniciar FlatLaf");
        }

        // 2. Cargar tus fuentes personalizadas
        Fuentes.cargarFuentes();

        // 3. Crear un dato de prueba en MongoDB para poder rastrearlo
        // Solo lo hacemos una vez para que tengas algo que buscar
        prepararDatoPrueba();

        // 4. Lanzar la pantalla
        java.awt.EventQueue.invokeLater(() -> {
            new FrmRastreo(null).setVisible(true);
        });
    }

    private static void prepararDatoPrueba() {
        EnvioDAO dao = new EnvioDAO();
        
        // Creamos un código de rastreo fácil de recordar
        String codigoTest = "TEST-123";
        
        // Verificamos si ya existe para no duplicar cada vez que corras la prueba
        if (dao.obtenerDetalles(codigoTest) == null) {
            Envio envio = new Envio();
            envio.setCodigo_rastreo(codigoTest);
            envio.setNombre_destinatario("Juan Pérez");
            envio.setDireccion_destino("Calle Falsa 123, Ciudad Obregón");
            envio.setEstado(Enums.EstadoEnvio.EN_TRANSITO);
            envio.setHistorial_envio(new ArrayList<>());

            // Agregamos un hito inicial (Origen)
            RegistroEnvio hito1 = new RegistroEnvio();
            hito1.setFecha(LocalDateTime.now());
            hito1.setDireccion("Sucursal Centro");
            hito1.setLatitud("27.4828"); // Coordenadas de Cd. Obregón
            hito1.setLongitud("-109.9304");
            
            envio.getHistorial_envio().add(hito1);

            // Insertamos en MongoDB
            dao.registrarEnvio(envio);
            System.out.println(">>> Dato de prueba '" + codigoTest + "' creado en MongoDB.");
        } else {
            System.out.println(">>> El dato de prueba ya existe en la base de datos.");
        }
    }
}
 
