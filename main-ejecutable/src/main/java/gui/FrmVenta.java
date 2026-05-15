/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatClientProperties;
import Mediadores.LogisticaMediador;
import dto.VentaDTO;
import ui.recursos.Fuentes;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Jesús
 */
public class FrmVenta extends JFrame{
    private final LogisticaMediador mediador = new LogisticaMediador();
    private final VentaDTO<?> ventaActual; // Recibe la venta que se va a pagar

    private JTextField txtTarjeta, txtExpiracion, txtCVC;
    private JLabel lblTotal;
    private JButton btnPagar, btnCancelar;

    public FrmVenta(VentaDTO<?> venta) {
        this.ventaActual = venta;
        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("Pasarela de Pago Segura - Stripe Test");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        // No cerramos la app, solo esta ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        // --- CABECERA ---
        JLabel lblTitulo = new JLabel("Finalizar Pago");
        lblTitulo.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 24f));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // --- DETALLES DEL MONTO ---
        JPanel panelMonto = new JPanel(new GridLayout(2, 1));
        panelMonto.setBackground(new Color(245, 247, 250));
        panelMonto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTxt = new JLabel("Total a cobrar:");
        lblTxt.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(14f));
        
        lblTotal = new JLabel("$" + ventaActual.getMonto()+ " MXN");
        lblTotal.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 28f));
        lblTotal.setForeground(new Color(40, 167, 69)); // Verde éxito

        panelMonto.add(lblTxt);
        panelMonto.add(lblTotal);

        // --- FORMULARIO DE TARJETA (Simulado para Stripe) ---
        JPanel form = new JPanel(new GridLayout(5, 1, 10, 10));
        form.setBackground(Color.WHITE);

        txtTarjeta = new JTextField();
        txtTarjeta.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "4242 4242 4242 4242");
        
        JPanel filaInfo = new JPanel(new GridLayout(1, 2, 10, 10));
        filaInfo.setBackground(Color.WHITE);
        txtExpiracion = new JTextField();
        txtExpiracion.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "MM/AA");
        txtCVC = new JTextField();
        txtCVC.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CVC");
        
        filaInfo.add(txtExpiracion);
        filaInfo.add(txtCVC);

        form.add(new JLabel("Número de Tarjeta:"));
        form.add(txtTarjeta);
        form.add(new JLabel("Detalles:"));
        form.add(filaInfo);

        // --- BOTONES ---
        btnPagar = new JButton("Confirmar Pago");
        btnPagar.setBackground(new Color(103, 114, 229)); // Color Stripe Purple
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 16f));
        btnPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPagar.addActionListener(e -> procesarPago());

        // Ensamblado
        mainPanel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel centro = new JPanel(new BorderLayout(20, 20));
        centro.setBackground(Color.WHITE);
        centro.add(panelMonto, BorderLayout.NORTH);
        centro.add(form, BorderLayout.CENTER);
        
        mainPanel.add(centro, BorderLayout.CENTER);
        mainPanel.add(btnPagar, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void procesarPago() {
        // En una integración real, aquí convertirías los datos en un Token de Stripe
        // Pero para tu proyecto, llamamos al mediador:
        
        btnPagar.setEnabled(false);
        btnPagar.setText("Procesando...");

        boolean exito = mediador.procesarVentaFinal(ventaActual);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Pago aprobado por Stripe!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); 
            // Aquí podrías abrir el ticket o volver al dashboard
        } else {
            JOptionPane.showMessageDialog(this, "El pago fue rechazado. Revisa los datos.", "Error de Pago", JOptionPane.ERROR_MESSAGE);
            btnPagar.setEnabled(true);
            btnPagar.setText("Confirmar Pago");
        }
    }
}
