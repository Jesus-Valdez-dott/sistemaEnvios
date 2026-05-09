/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.modulo.envios;

import javax.swing.UIManager;


/**
 *
 * @author Jesús
 */
public class ModuloEnvios {

public static void main(String[] args) {
    // 1. Configuramos FlatLaf
    com.formdev.flatlaf.FlatLightLaf.setup();

    // 2. Cargamos tu archivo de fuente (Asegúrate que el nombre sea igual al de tu carpeta)
    try {
        java.io.InputStream is = ModuloEnvios.class.getResourceAsStream("/ui/recursos/Roboto-VariableFont_wdth,wght.ttf");
        java.awt.Font roboto = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        
        // 3. LA MAGIA: Esto le dice a Java que use Roboto para TODO el sistema
        UIManager.put("defaultFont", roboto.deriveFont(14f)); 
        
    } catch (Exception e) {
        System.out.println("No se pudo cargar Roboto, usando fuente del sistema.");
    }

    // 4. Abrimos tu nueva interfaz
    java.awt.EventQueue.invokeLater(() -> {
        new ui.vistas.FrmRegistroEnvio().setVisible(true);
    });
}
}
