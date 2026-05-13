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
import controladores.EnvioControlador;
import dtos.EnvioDTO;
import dtos.PaqueteDTO;
import javax.swing.*;
import java.awt.*;

public class FrmRegistroEnvio extends JFrame {

    // Variables globales (Atributos) - Estas DEBEN ser inicializadas sin repetir el tipo de dato
    private JTextField txtPeso, txtAlto, txtAncho, txtLargo, txtTelefono;
    private JTextArea txtDireccionDestino;
    private JLabel lblNombreCliente; 
    private JButton btnOk;
    private JTextField txtNombreDestinatario;
    private JTextField txtTelefonoDestinatario;
    private final EnvioControlador controlador = new EnvioControlador();

    public FrmRegistroEnvio() {
        // 1. Configuración de la Ventana
        setTitle("ObsExpress - Sistema de Paquetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Construcción de la UI
        add(crearSidebar(), BorderLayout.WEST);
        add(crearPanelContenido(), BorderLayout.CENTER);

        // 3. Importante: Configurar eventos AL FINAL, cuando los componentes ya existen
        configurarEventos();
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        JLabel lblLogo = new JLabel("OBS EXPRESS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Roboto", Font.BOLD, 20));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        sidebar.add(lblLogo);

        sidebar.add(crearBotonMenu("Dashboard", "iconDashbord.png"));
        sidebar.add(crearBotonMenu("Nuevo Envío", "newEnvio.png"));
        sidebar.add(crearBotonMenu("Rastreo", "rastreoEnv.png"));
        sidebar.add(crearBotonMenu("Historial", "historial.png"));

        return sidebar;
    }

    private JPanel crearPanelContenido() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(245, 246, 250));
        content.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.3;
        content.add(crearTarjetaRemitente(), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        content.add(crearTarjetaPaquete(), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 0.5;
        content.add(crearTarjetaDestino(), gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        content.add(crearPanelAcciones(), gbc);

        return content;
    }

    private JPanel crearTarjetaRemitente() {
        JPanel panel = crearBaseTarjeta("Datos del Remitente");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Teléfono/ID:"), g);

        g.gridx = 1;
        // CORRECCIÓN: Se usa la variable global txtTelefono
        txtTelefono = new JTextField(15);
        txtTelefono.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ej. 6441...");
        panel.add(txtTelefono, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Nombre:"), g);

        g.gridx = 1;
        lblNombreCliente = new JLabel("Pendiente de búsqueda...");
        lblNombreCliente.setForeground(Color.GRAY);
        panel.add(lblNombreCliente, g);

        return panel;
    }

    private JPanel crearTarjetaPaquete() {
        JPanel panel = crearBaseTarjeta("Detalles del Paquete");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 5, 8, 5);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Peso (kg):"), g);
        g.gridx = 1;
        // CORRECCIÓN: Se usa la variable global txtPeso
        txtPeso = new JTextField(10);
        panel.add(txtPeso, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Medidas (Al x An x Lar):"), g);

        g.gridx = 1;
        JPanel pnlDim = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlDim.setOpaque(false);
        // CORRECCIÓN: Se usan las variables globales de medidas
        txtAlto = new JTextField(3);
        txtAncho = new JTextField(3);
        txtLargo = new JTextField(3);
        
        pnlDim.add(txtAlto);
        pnlDim.add(new JLabel("x"));
        pnlDim.add(txtAncho);
        pnlDim.add(new JLabel("x"));
        pnlDim.add(txtLargo);
        panel.add(pnlDim, g);

        return panel;
    }

    private JPanel crearTarjetaDestino() {
        JPanel panel = crearBaseTarjeta("Destino Final");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Nombre Destinatario:"), g);
        g.gridx = 1;
        txtNombreDestinatario = new JTextField(20);
        panel.add(txtNombreDestinatario, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Teléfono:"), g);
        g.gridx = 1;
        txtTelefonoDestinatario = new JTextField(15);
        panel.add(txtTelefonoDestinatario, g);

        g.gridx = 0; g.gridy = 2;
        panel.add(new JLabel("Dirección:"), g);
        g.gridx = 1;
        // CORRECCIÓN: Inicialización de txtDireccionDestino
        txtDireccionDestino = new JTextArea(3, 20);
        txtDireccionDestino.setLineWrap(true);
        panel.add(new JScrollPane(txtDireccionDestino), g);

        return panel;
    }

    private JPanel crearPanelAcciones() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnl.setOpaque(false);

        JButton btnCan = new JButton("Cancelar");
        btnCan.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        btnCan.addActionListener(e -> limpiarCampos());

        btnOk = new JButton("Generar Envío");
        btnOk.setBackground(new Color(39, 174, 96));
        btnOk.setForeground(Color.WHITE);
        btnOk.setPreferredSize(new Dimension(150, 40));
        btnOk.putClientProperty("JButton.buttonType", "roundRect");

        pnl.add(btnCan);
        pnl.add(btnOk);
        return pnl;
    }

    private JPanel crearBaseTarjeta(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10,10,10,10), titulo)
        ));
        return panel;
    }

    private JButton crearBotonMenu(String texto, String icono) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(15);

        ImageIcon img = obtenerIconoRedimensionado("/ui/recursos/" + icono, 22, 22);
        if (img != null) btn.setIcon(img);

        return btn;
    }

    private ImageIcon obtenerIconoRedimensionado(String ruta, int w, int h) {
        try {
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private void limpiarCampos() {
        txtPeso.setText("");
        txtAlto.setText("");
        txtAncho.setText("");
        txtLargo.setText("");
        txtNombreDestinatario.setText("");
        txtTelefonoDestinatario.setText("");
        txtDireccionDestino.setText("");
        txtTelefono.setText("");
        lblNombreCliente.setText("Pendiente de búsqueda...");
        txtPeso.putClientProperty("JComponent.outline", null);
    }

    private void configurarEventos() {
        btnOk.addActionListener(e -> {
            try {
                // 1. Validar que los campos no estén vacíos antes de parsear
                if(txtPeso.getText().isEmpty() || txtAlto.getText().isEmpty()) {
                    throw new Exception("Los campos de paquete no pueden estar vacíos");
                }

                // 2. Empaquetar el Paquete en un DTO
                PaqueteDTO paqueteDTO = new PaqueteDTO();
                paqueteDTO.setPeso(Double.parseDouble(txtPeso.getText()));
                paqueteDTO.setAlto(Double.parseDouble(txtAlto.getText()));
                paqueteDTO.setAncho(Double.parseDouble(txtAncho.getText()));
                paqueteDTO.setLargo(Double.parseDouble(txtLargo.getText()));
                paqueteDTO.setDescripcion("Paquete estándar");

                // 3. Empaquetar el Envío en un DTO
                EnvioDTO envioDTO = new EnvioDTO();
                envioDTO.setNombre_destinatario(txtNombreDestinatario.getText());
                envioDTO.setDireccion_destino(txtDireccionDestino.getText());
                envioDTO.setTelefono_destinatario(txtTelefonoDestinatario.getText());
                envioDTO.setFecha_envio(java.time.LocalDate.now());
                envioDTO.setPaquetes(java.util.List.of(paqueteDTO));

                // 4. Llamar al controlador para persistencia
                boolean exito = controlador.guardarEnvio(envioDTO);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "¡Envío registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar el envío.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa números válidos en peso y medidas.", "Error de formato", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}