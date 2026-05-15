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
import dto.ClienteDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
 
/**
 * Pantalla de registro y consulta de clientes.
 * Permite dar de alta un cliente nuevo y listar los existentes.
 */
public class FrmRegistroCliente extends JFrame {
 
    // Campos del formulario
    private JTextField txtNombre, txtTelefono, txtDireccion, txtRfc;
    private JButton btnGuardar, btnLimpiar, btnCerrar;
 
    // Tabla de clientes existentes
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
 
    private final LogisticaMediador mediador;
 
    public FrmRegistroCliente(LogisticaMediador mediador) {
        this.mediador = mediador;
        setTitle("ObsExpress - Registro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No cierra toda la app
        setSize(850, 580);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
 
        add(crearPanelFormulario(), BorderLayout.WEST);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
 
        cargarClientes(); // Llenar tabla al abrir
    }
 
    // ─── PANEL IZQUIERDO: Formulario ──────────────────────────────────────────
 
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Cliente"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(310, 0));
 
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 5, 8, 5);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
 
        // Nombre
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(new JLabel("Nombre completo:"), g);
        g.gridy = 1;
        txtNombre = new JTextField();
        panel.add(txtNombre, g);
 
        // Telefono
        g.gridy = 2;
        panel.add(new JLabel("Telefono:"), g);
        g.gridy = 3;
        txtTelefono = new JTextField();
        panel.add(txtTelefono, g);
 
        // RFC
        g.gridy = 4;
        panel.add(new JLabel("RFC:"), g);
        g.gridy = 5;
        txtRfc = new JTextField();
        txtRfc.setDocument(new JTextFieldLimit(13)); // RFC maximo 13 chars
        panel.add(txtRfc, g);
 
        // Direccion
        g.gridy = 6;
        panel.add(new JLabel("Direccion:"), g);
        g.gridy = 7;
        txtDireccion = new JTextField();
        panel.add(txtDireccion, g);
 
        // Separador visual
        g.gridy = 8;
        panel.add(new JSeparator(), g);
 
        // Nota RFC
        g.gridy = 9;
        JLabel lblNota = new JLabel("<html><i>El RFC se usa para facturacion.<br>Persona fisica: 13 chars<br>Persona moral: 12 chars</i></html>");
        lblNota.setForeground(Color.GRAY);
        lblNota.setFont(new Font("SansSerif", Font.PLAIN, 10));
        panel.add(lblNota, g);
 
        // Relleno para empujar todo arriba
        g.gridy = 10; g.weighty = 1.0;
        panel.add(new JPanel(), g);
 
        return panel;
    }
 
    // ─── PANEL DERECHO: Tabla de clientes ────────────────────────────────────
 
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Clientes Registrados"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
 
        String[] columnas = {"ID", "Nombre", "Telefono", "RFC", "Direccion"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Tabla de solo lectura
            }
        };
 
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);   // Ocultar columna ID
        tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaClientes.getColumnModel().getColumn(0).setWidth(0);
 
        JScrollPane scroll = new JScrollPane(tablaClientes);
        panel.add(scroll, BorderLayout.CENTER);
 
        // Boton para recargar tabla
        JButton btnRecargar = new JButton("Recargar lista");
        btnRecargar.addActionListener(e -> cargarClientes());
        JPanel panelRecarga = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRecarga.add(btnRecargar);
        panel.add(panelRecarga, BorderLayout.SOUTH);
 
        return panel;
    }
 
    // ─── PANEL INFERIOR: Botones ──────────────────────────────────────────────
 
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
 
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
 
        btnGuardar = new JButton("Guardar Cliente");
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        btnGuardar.addActionListener(e -> guardarCliente());
 
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
 
        panel.add(btnCerrar);
        panel.add(btnLimpiar);
        panel.add(btnGuardar);
        return panel;
    }
 
    // ─── LOGICA ───────────────────────────────────────────────────────────────
 
    private void guardarCliente() {
        // Validacion basica
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rfc = txtRfc.getText().trim().toUpperCase();
        String direccion = txtDireccion.getText().trim();
 
        if (nombre.isEmpty() || telefono.isEmpty() || rfc.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios.",
                "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        if (rfc.length() < 12 || rfc.length() > 13) {
            JOptionPane.showMessageDialog(this,
                "El RFC debe tener 12 o 13 caracteres.",
                "RFC invalido", JOptionPane.WARNING_MESSAGE);
            txtRfc.requestFocus();
            return;
        }
 
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setRfc(rfc);
        cliente.setDireccion(direccion);
 
        boolean exito = mediador.darDeAltaCliente(cliente);
 
        if (exito) {
            JOptionPane.showMessageDialog(this,
                "Cliente registrado correctamente.",
                "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarClientes(); // Refrescar tabla
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo registrar el cliente. Intenta de nuevo.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void cargarClientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<ClienteDTO> lista = mediador.obtenerTodosLosClientes();
        if (lista == null) return;
        for (ClienteDTO c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getId_Cliente(),
                c.getNombre(),
                c.getTelefono(),
                c.getRfc(),
                c.getDireccion()
            });
        }
    }
 
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtRfc.setText("");
        txtDireccion.setText("");
        txtNombre.requestFocus();
    }
 
    // ─── CLASE INTERNA: Limite de caracteres para JTextField ─────────────────
 
    private static class JTextFieldLimit extends javax.swing.text.PlainDocument {
        private final int limite;
        JTextFieldLimit(int limite) { this.limite = limite; }
 
        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limite) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
