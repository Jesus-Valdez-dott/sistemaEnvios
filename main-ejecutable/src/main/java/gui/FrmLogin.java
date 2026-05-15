/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
 

/**
 *
 * @author josed
 */

import Mediadores.LogisticaMediador;
import javax.swing.*;
import java.awt.*;
 
/**
 * Pantalla de Login.
 * Al autenticarse correctamente abre el PanelCentral.
 */
public class FrmLogin extends JFrame {
 
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
 
    private final LogisticaMediador mediador;
 
    public FrmLogin(LogisticaMediador mediador) {
        this.mediador = mediador;
        setTitle("ObsExpress - Inicio de Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
 
        add(crearPanel(), BorderLayout.CENTER);
        configurarEventos();
    }
 
    private JPanel crearPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 15, 10, 15);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
 
        // Titulo
        JLabel lblTitulo = new JLabel("Inicio de Sesion", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(lblTitulo, g);
 
        // Usuario
        g.gridwidth = 1; g.gridy = 1; g.gridx = 0;
        panel.add(new JLabel("Usuario:"), g);
        g.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, g);
 
        // Contrasena
        g.gridy = 2; g.gridx = 0;
        panel.add(new JLabel("Contrasena:"), g);
        g.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena, g);
 
        // Boton
        g.gridy = 3; g.gridx = 0; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setPreferredSize(new Dimension(120, 35));
        panel.add(btnIngresar, g);
 
        return panel;
    }
 
    private void configurarEventos() {
        // Enter en contrasena tambien dispara el login
        txtContrasena.addActionListener(e -> intentarLogin());
        btnIngresar.addActionListener(e -> intentarLogin());
    }
 
    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
 
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // Por ahora validacion simple: admin/admin
        // Cuando el modulo de empleados este listo, se reemplaza por:
        // mediador.autenticarEmpleado(usuario, contrasena)
        if (usuario.equals("admin") && contrasena.equals("admin")) {
            // Login correcto: abrir panel central y cerrar login
            FrmPanelCentral panel = new FrmPanelCentral(mediador);
            panel.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contrasena incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
        }
    }
}