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
import gui.FrmLogin;
import javax.swing.SwingUtilities;
 
public class MainEjecutable {
 
    public static void main(String[] args) {
        // 1. Tema visual
        FlatLightLaf.setup();
 
        // 2. Un solo mediador para toda la sesion
        LogisticaMediador mediador = new LogisticaMediador();
 
        // 3. Arrancar en el Login
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin(mediador);
            login.setVisible(true);
        });
    }
}
 