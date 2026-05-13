/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.recursos;

/**
 *
 * @author josed
 */

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class Fuentes {
    public static Font ROBOTO_REGULAR;
    
    public static void cargarFuentes() {
        try {
            // Ajusta el nombre exacto del archivo que tienes en la carpeta
            InputStream is = Fuentes.class.getResourceAsStream("/ui/recursos/Roboto-VariableFont_wdth,wght.ttf");
            ROBOTO_REGULAR = Font.createFont(Font.TRUETYPE_FONT, is);
            
            // Registramos en el sistema
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(ROBOTO_REGULAR);
            
            System.out.println("Fuente Roboto cargada con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            // Fuente de respaldo si falla
            ROBOTO_REGULAR = new Font("SansSerif", Font.PLAIN, 14);
        }
    }
}
