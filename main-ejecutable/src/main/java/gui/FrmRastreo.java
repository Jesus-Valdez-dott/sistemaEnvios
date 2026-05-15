/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatClientProperties;
import Mediadores.LogisticaMediador;
import dtos.EnvioDTO;
import dtos.RegistroEnvioDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Jesús
 */
public class FrmRastreo extends JFrame{
    private JTextField txtCodigoBusqueda;
    private JButton btnBuscar, btnVerMapa;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado, lblDestinatario, lblOrigen;
    
    // Usamos el Mediador como puente único
    private final LogisticaMediador mediador;

    public FrmRastreo(LogisticaMediador mediador) {
        configurarVentana();
        initUI();
        this.mediador = mediador;
    }

    private void configurarVentana() {
        setTitle("ObsExpress - Rastreo de Paquetes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Reutilizamos tu Sidebar del FrmRegistroEnvio para mantener coherencia
        add(crearSidebar(), BorderLayout.WEST);

        JPanel panelCentral = new JPanel(new BorderLayout(20, 20));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- PARTE SUPERIOR: BUSQUEDA ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCodigoBusqueda = new JTextField(20);
        txtCodigoBusqueda.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Introduce código de rastreo (ej: PKG-123)");
        
        btnBuscar = new JButton("Rastrear");
        btnBuscar.setBackground(new Color(30, 136, 229)); // Azul coherente
        btnBuscar.setForeground(Color.WHITE);
        
        panelBusqueda.add(new JLabel("Código:"));
        panelBusqueda.add(txtCodigoBusqueda);
        panelBusqueda.add(btnBuscar);
        
        // --- PARTE CENTRAL: INFORMACIÓN Y TABLA ---
        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 10));
        lblEstado = new JLabel("Estado: -");
        lblDestinatario = new JLabel("Destinatario: -");
        lblOrigen = new JLabel("Ubicación Actual: -");
        
        panelInfo.add(lblEstado);
        panelInfo.add(lblDestinatario);
        panelInfo.add(lblOrigen);

        // Tabla de Historial (RegistroEnvio)
        String[] columnas = {"Fecha", "Ubicación", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        
        // --- PARTE INFERIOR: ACCIONES ---
        btnVerMapa = new JButton("Ver Ubicación en Mapa Tiempo Real");
        btnVerMapa.setEnabled(false);

        // Layout Final
        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.add(panelInfo, BorderLayout.NORTH);
        panelContenido.add(scrollTabla, BorderLayout.CENTER);
        panelContenido.add(btnVerMapa, BorderLayout.SOUTH);

        panelCentral.add(panelBusqueda, BorderLayout.NORTH);
        panelCentral.add(panelContenido, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // EVENTOS
        btnBuscar.addActionListener(e -> buscarPaquete());
        btnVerMapa.addActionListener(e -> abrirMapa());
    }

    private void buscarPaquete() {
        String codigo = txtCodigoBusqueda.getText().trim();
        if (codigo.isEmpty()) return;

        // Llamada al Mediador

        
       EnvioDTO envio = mediador.rastrearEnvio(codigo);

        if (envio != null) {
            actualizarInterfaz(envio);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el paquete.");
        }
    }

    private void actualizarInterfaz(EnvioDTO envio) {
        lblEstado.setText("Estado: " + envio.getEstado());
        lblDestinatario.setText("Para: " + envio.getNombre_destinatario());
        
        // Limpiar y llenar tabla con el historial de RegistroEnvio
        modeloTabla.setRowCount(0);
        List<RegistroEnvioDTO> historial = envio.getHistorial_envio();
        
        for (RegistroEnvioDTO reg : historial) {
            modeloTabla.addRow(new Object[]{
                reg.getFecha(), 
                reg.getDireccion()
            });
        }

        if (!historial.isEmpty()) {
            lblOrigen.setText("Última parada: " + historial.get(0).getDireccion());
            btnVerMapa.setEnabled(true);
        }
    }

    private void abrirMapa() {
        String codigo = txtCodigoBusqueda.getText().trim();
        // El mediador usa la lógica de OpenStreetMap que creamos
        mediador.abrirMapaUbicacionActual(codigo);
    }

    // Método Sidebar similar al que ya tienes para consistencia visual
    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setPreferredSize(new Dimension(250, 0));
        // Aquí agregarías los mismos botones de Dashboard, Envíos, etc.
        return sidebar;
    }
}
