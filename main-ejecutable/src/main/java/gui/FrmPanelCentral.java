/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josed
 */

package gui;
 
import Mediadores.LogisticaMediador;
import javax.swing.*;
import java.awt.*;
 
/**
 * Panel central del sistema.
 * Muestra las 7 opciones del sistema como tarjetas clicables.
 * Cada boton abre la pantalla correspondiente.
 */
public class FrmPanelCentral extends JFrame {
 
    private final LogisticaMediador mediador;
 
    public FrmPanelCentral(LogisticaMediador mediador) {
        this.mediador = mediador;
        setTitle("ObsExpress - Panel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
 
        add(crearHeader(), BorderLayout.NORTH);
        add(crearGridOpciones(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }
 
    private JPanel crearHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel lblTitulo = new JLabel("ObsExpress - Sistema de Paqueteria");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(lblTitulo);
        return header;
    }
 
    private JPanel crearGridOpciones() {
        JPanel grid = new JPanel(new GridLayout(3, 3, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
 
        // Las 7 tarjetas del sistema segun los casos de uso del proyecto
        grid.add(crearTarjeta("Registrar Envio",     "Capturar nuevo envio",          e -> abrirRegistroEnvio()));
        grid.add(crearTarjeta("Registrar Cliente",   "Dar de alta un cliente",         e -> abrirProximamente()));
        grid.add(crearTarjeta("Pagar Envio",         "Procesar pago con Stripe",       e -> abrirProximamente()));
        grid.add(crearTarjeta("Rastrear Paquete",    "Seguimiento por codigo",         e -> abrirRastreo()));
        grid.add(crearTarjeta("Consultar Historial", "Envios de un cliente",           e -> abrirHistorial()));
        grid.add(crearTarjeta("Gestion Sucursales",  "Administrar sucursales",         e -> abrirProximamente()));
        grid.add(crearTarjeta("Generar Reporte",     "Reporte mensual por sucursal",   e -> abrirReporte()));
 
        // Dos celdas vacias para completar el grid 3x3
        grid.add(new JPanel());
        grid.add(new JPanel());
 
        return grid;
    }
 
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20));
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(this, "Deseas cerrar sesion?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                // Volver al login
                FrmLogin login = new FrmLogin(mediador);
                login.setVisible(true);
                this.dispose();
            }
        });
        footer.add(btnSalir);
        return footer;
    }
 
    /**
     * Crea una tarjeta con titulo, descripcion y accion al hacer clic.
     */
    private JPanel crearTarjeta(String titulo, String descripcion, java.awt.event.ActionListener accion) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
 
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
 
        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblDesc.setForeground(Color.GRAY);
 
        JButton btnAbrir = new JButton("Abrir");
        btnAbrir.setPreferredSize(new Dimension(70, 28));
        btnAbrir.addActionListener(accion);
 
        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setOpaque(false);
        panelTexto.add(lblTitulo);
        panelTexto.add(lblDesc);
 
        tarjeta.add(panelTexto, BorderLayout.CENTER);
        tarjeta.add(btnAbrir, BorderLayout.SOUTH);
 
        return tarjeta;
    }
 
    // --- Metodos de navegacion ---
 
    private void abrirRegistroEnvio() {
        FrmRegistroEnvio ventana = new FrmRegistroEnvio(mediador);
        ventana.setVisible(true);
        // No cerramos el panel para poder volver
    }
 
    private void abrirRastreo() {
        FrmRastreo ventana = new FrmRastreo(mediador);
        ventana.setVisible(true);
    }
    
    private void abrirReporte() {
        FrmReporteMensual ventana = new FrmReporteMensual(mediador);
        ventana.setVisible(true);
    }
    
    private void abrirHistorial() {
        new FrmHistoCliente(mediador).setVisible(true);
    }
 
    private void abrirProximamente() {
        JOptionPane.showMessageDialog(this,
            "Esta pantalla aun no esta implementada.\nEsta disponible cuando el modulo correspondiente este listo.",
            "Proximamente",
            JOptionPane.INFORMATION_MESSAGE);
    }
}