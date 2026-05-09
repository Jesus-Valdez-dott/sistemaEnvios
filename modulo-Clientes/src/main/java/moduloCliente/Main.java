/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package moduloCliente;

import moduloCliente.vistas.PantallaRegistroCliente;

/**
 *
 * @author saidr
 */
public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new PantallaRegistroCliente().setVisible(true);
        });
    }
}
