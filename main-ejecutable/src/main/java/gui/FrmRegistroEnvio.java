/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author josed
 */
package gui;
 
import com.formdev.flatlaf.FlatClientProperties;
import dtos.EnvioDTO;
import dtos.PaqueteDTO;
import Mediadores.LogisticaMediador;
import dto.ClienteDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
 
/**
 * Vista de registro de envio.
 */
public class FrmRegistroEnvio extends JFrame {
 
    private JTextField txtPeso, txtAlto, txtAncho, txtLargo, txtTelefono;
    private JTextArea txtDireccionDestino;
    private JLabel lblNombreCliente, lblDireccionCliente, lblRfcCliente;
    private JButton btnOk;
    private JTextField txtNombreDestinatario, txtTelefonoDestinatario;
 
    private final LogisticaMediador mediador;
    private String idClienteEncontrado = null; // telefono = id
 
    public FrmRegistroEnvio(LogisticaMediador mediador) {
        this.mediador = mediador;
        setTitle("ObsExpress - Registro de Envio");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
 
        add(crearSidebar(), BorderLayout.WEST);
        add(crearPanelContenido(), BorderLayout.CENTER);
 
        configurarEventos();
    }
 
    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
 
        JLabel lblLogo = new JLabel("OBS EXPRESS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        sidebar.add(lblLogo);
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
 
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.35;
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
        JPanel panel = crearBaseTarjeta("Remitente");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
 
        // Fila 0: label telefono
        g.gridx = 0; g.gridy = 0; g.gridwidth = 1; g.weightx = 0;
        panel.add(new JLabel("Telefono:"), g);
 
        // Campo telefono
        g.gridx = 1; g.weightx = 1.0;
        txtTelefono = new JTextField();
        txtTelefono.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ej. 6441234567");
        panel.add(txtTelefono, g);
 
        // Fila 1: datos del cliente que aparecen al buscar
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        panel.add(new JLabel("Nombre:"), g);
        g.gridx = 1; g.weightx = 1.0;
        lblNombreCliente = new JLabel("—");
        lblNombreCliente.setForeground(Color.GRAY);
        panel.add(lblNombreCliente, g);
 
        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        panel.add(new JLabel("Direccion:"), g);
        g.gridx = 1; g.weightx = 1.0;
        lblDireccionCliente = new JLabel("—");
        lblDireccionCliente.setForeground(Color.GRAY);
        panel.add(lblDireccionCliente, g);
 
        g.gridx = 0; g.gridy = 3; g.weightx = 0;
        panel.add(new JLabel("RFC:"), g);
        g.gridx = 1; g.weightx = 1.0;
        lblRfcCliente = new JLabel("—");
        lblRfcCliente.setForeground(Color.GRAY);
        panel.add(lblRfcCliente, g);
 
        return panel;
    }
 
    private JPanel crearTarjetaPaquete() {
        JPanel panel = crearBaseTarjeta("Paquete");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
 
        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Peso (kg):"), g);
        g.gridx = 1;
        txtPeso = new JTextField(10);
        panel.add(txtPeso, g);
 
        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Medidas (Al x An x La):"), g);
        g.gridx = 1;
        JPanel dims = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dims.setOpaque(false);
        txtAlto = new JTextField(3);
        txtAncho = new JTextField(3);
        txtLargo = new JTextField(3);
        dims.add(txtAlto); dims.add(new JLabel("x"));
        dims.add(txtAncho); dims.add(new JLabel("x"));
        dims.add(txtLargo);
        panel.add(dims, g);
 
        return panel;
    }
 
    private JPanel crearTarjetaDestino() {
        JPanel panel = crearBaseTarjeta("Destino");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
 
        g.gridx = 0; g.gridy = 0; g.weightx = 0;
        panel.add(new JLabel("Nombre:"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtNombreDestinatario = new JTextField();
        panel.add(txtNombreDestinatario, g);
 
        g.gridx = 0; g.gridy = 1; g.weightx = 0;
        panel.add(new JLabel("Telefono:"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtTelefonoDestinatario = new JTextField();
        panel.add(txtTelefonoDestinatario, g);
 
        g.gridx = 0; g.gridy = 2; g.weightx = 0;
        panel.add(new JLabel("Direccion:"), g);
        g.gridx = 1; g.weightx = 1.0;
        txtDireccionDestino = new JTextArea(3, 20);
        txtDireccionDestino.setLineWrap(true);
        panel.add(new JScrollPane(txtDireccionDestino), g);
 
        return panel;
    }
 
    private JPanel crearPanelAcciones() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnl.setOpaque(false);
 
        JButton btnCan = new JButton("Limpiar");
        btnCan.addActionListener(e -> limpiarCampos());
 
        btnOk = new JButton("Generar Envio");
        btnOk.setBackground(new Color(39, 174, 96));
        btnOk.setForeground(Color.WHITE);
        btnOk.setPreferredSize(new Dimension(150, 40));
 
        pnl.add(btnCan);
        pnl.add(btnOk);
        return pnl;
    }
 
    private JPanel crearBaseTarjeta(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), titulo)
        ));
        return panel;
    }
 
    // Busca el cliente automaticamente al salir del campo telefono
    private void buscarCliente() {
        String telefono = txtTelefono.getText().trim();
        if (telefono.isEmpty()) return;
 
        ClienteDTO cliente = mediador.buscarClientePorTelefono(telefono);
 
        if (cliente != null) {
            idClienteEncontrado = telefono; // telefono = id
            lblNombreCliente.setText(cliente.getNombre());
            lblDireccionCliente.setText(cliente.getDireccion());
            lblRfcCliente.setText(cliente.getRfc());
            lblNombreCliente.setForeground(new Color(30, 130, 76));
            lblDireccionCliente.setForeground(new Color(30, 130, 76));
            lblRfcCliente.setForeground(new Color(30, 130, 76));
        } else {
            idClienteEncontrado = null;
            lblNombreCliente.setText("Cliente no encontrado");
            lblDireccionCliente.setText("—");
            lblRfcCliente.setText("—");
            lblNombreCliente.setForeground(Color.RED);
            lblDireccionCliente.setForeground(Color.GRAY);
            lblRfcCliente.setForeground(Color.GRAY);
        }
    }
 
    private void limpiarCampos() {
        txtTelefono.setText("");
        txtPeso.setText("");
        txtAlto.setText("");
        txtAncho.setText("");
        txtLargo.setText("");
        txtNombreDestinatario.setText("");
        txtTelefonoDestinatario.setText("");
        txtDireccionDestino.setText("");
        lblNombreCliente.setText("—");
        lblDireccionCliente.setText("—");
        lblRfcCliente.setText("—");
        lblNombreCliente.setForeground(Color.GRAY);
        lblDireccionCliente.setForeground(Color.GRAY);
        lblRfcCliente.setForeground(Color.GRAY);
        idClienteEncontrado = null;
    }
 
    private void configurarEventos() {
        // La busqueda se dispara al salir del campo (Tab o clic en otro lado)
        txtTelefono.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                buscarCliente();
            }
        });
        // También con Enter
        txtTelefono.addActionListener(e -> buscarCliente());
 
        btnOk.addActionListener(e -> {
            try {
                if (idClienteEncontrado == null) {
                    JOptionPane.showMessageDialog(this,
                        "Ingresa el telefono de un cliente registrado.",
                        "Cliente requerido", JOptionPane.WARNING_MESSAGE);
                    txtTelefono.requestFocus();
                    return;
                }
                if (txtPeso.getText().isEmpty() || txtAlto.getText().isEmpty()
                        || txtAncho.getText().isEmpty() || txtLargo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Completa los datos del paquete.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
 
                PaqueteDTO paquete = new PaqueteDTO();
                paquete.setPeso(Double.parseDouble(txtPeso.getText()));
                paquete.setAlto(Double.parseDouble(txtAlto.getText()));
                paquete.setAncho(Double.parseDouble(txtAncho.getText()));
                paquete.setLargo(Double.parseDouble(txtLargo.getText()));
                paquete.setDescripcion("Paquete estandar");
 
                EnvioDTO envio = new EnvioDTO();
                envio.setId_cliente(idClienteEncontrado); // telefono = id
                envio.setNombre_destinatario(txtNombreDestinatario.getText());
                envio.setDireccion_destino(txtDireccionDestino.getText());
                envio.setTelefono_destinatario(txtTelefonoDestinatario.getText());
                envio.setFecha_envio(java.time.LocalDate.now());
                envio.setPaquetes(java.util.List.of(paquete));
 
                boolean exito = mediador.registrarEnvioConGeocodificacion(envio);
 
                if (exito) {
                    // Armar el DTO de venta con el monto calculado y el id del envio
                    double total = mediador.calcularCostoTotal(envio);
                    dto.VentaDTO venta = new dto.VentaDTO();
                    venta.setMonto(total);
                    venta.setEnvios(java.util.List.of(envio.getCodigo_rastreo()));
 
                    // Abrir pantalla de pago pasando el mediador compartido
                    new FrmVenta(venta, mediador).setVisible(true);
                    limpiarCampos();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el envio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Ingresa numeros validos en peso y medidas.",
                    "Formato invalido", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}