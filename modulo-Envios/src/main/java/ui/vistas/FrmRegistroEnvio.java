/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.vistas;

/**
 *
 * @author josed
 */

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import ui.recursos.Fuentes;

public class FrmRegistroEnvio extends JFrame {

    public FrmRegistroEnvio() {
        // Configuraciones básicas de la ventana
        setTitle("ObsExpress - Sistema de Paquetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. MENU LATERAL (Sidebar)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80)); // Azul oscuro profesional
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new GridLayout(10, 1, 0, 5));
        
        // Botones del menú
        sidebar.add(crearBotonMenu("Dashboard"));
        sidebar.add(crearBotonMenu("Nuevo Envío"));
        sidebar.add(crearBotonMenu("Rastreo"));
        sidebar.add(crearBotonMenu("Historial"));

        // 2. PANEL PRINCIPAL (Contenido)
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(245, 246, 250)); // Gris muy claro
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // --- TARJETA 1: DATOS DEL CLIENTE ---
        JPanel cardCliente = crearTarjeta("Datos del Remitente");
        cardCliente.setLayout(new GridLayout(3, 2, 10, 10));
        
        cardCliente.add(new JLabel("Teléfono/ID:"));
        JTextField txtBuscar = new JTextField();
        txtBuscar.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Buscar cliente...");
        cardCliente.add(txtBuscar);
        
        cardCliente.add(new JLabel("Nombre:"));
        cardCliente.add(new JLabel("Pepito Gonzalez (Auto-completado)"));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5; gbc.weighty = 0.3;
        content.add(cardCliente, gbc);

        // --- TARJETA 2: DATOS DEL PAQUETE ---
        JPanel cardPaquete = crearTarjeta("Detalles del Paquete");
        cardPaquete.setLayout(new GridLayout(4, 2, 10, 10));
        
        cardPaquete.add(new JLabel("Peso (kg):"));
        cardPaquete.add(new JTextField());
        cardPaquete.add(new JLabel("Dimensiones (Al x An x Lar):"));
        JPanel dimPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        dimPanel.add(new JTextField()); dimPanel.add(new JTextField()); dimPanel.add(new JTextField());
        cardPaquete.add(dimPanel);
        
        gbc.gridx = 1; gbc.gridy = 0;
        content.add(cardPaquete, gbc);

        // --- TARJETA 3: DESTINO Y ACCIONES ---
        JPanel cardDestino = crearTarjeta("Destino Final");
        cardDestino.setLayout(new BorderLayout(10, 10));
        JTextField txtDestino = new JTextField();
        txtDestino.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Calle, número, colonia...");
        cardDestino.add(txtDestino, BorderLayout.CENTER);
        
        JButton btnGuardar = new JButton("Generar Envío");
        btnGuardar.setBackground(new Color(39, 174, 96)); // Verde éxito
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 0.7;
        content.add(cardDestino, gbc);
        
        // Agregar paneles al Frame
        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
        
        // Aplicar fuente Roboto (si ya la cargaste en tu Main)
        aplicarFuentes(this);
    }

    private JPanel crearTarjeta(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createTitledBorder(titulo)
        ));
        return panel;
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void aplicarFuentes(Component comp) {
        // Aquí podrías usar tu clase Fuentes para iterar y aplicar Roboto
        // comp.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(14f));
    }
}