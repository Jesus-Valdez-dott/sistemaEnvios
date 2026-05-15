/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main.ejecutable;
 
/**
 *
 * @author josed
 */
import Mediadores.LogisticaMediador;
import com.formdev.flatlaf.FlatLightLaf;
import gui.FrmRegistroEnvio;
import javax.swing.SwingUtilities;
 
public class MainEjecutable {
 
    public static void main(String[] args) {
        // 1. Aplicar tema visual FlatLaf
        FlatLightLaf.setup();
 
        // 2. Crear el mediador (el unico punto que conecta todos los modulos)
        LogisticaMediador mediador = new LogisticaMediador();
 
        // 3. Abrir la ventana en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            FrmRegistroEnvio ventana = new FrmRegistroEnvio(mediador);
            ventana.setVisible(true);
        });
    }
}
 