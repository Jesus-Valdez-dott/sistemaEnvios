/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moduloCliente.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import moduloCliente.control.ClienteControlador;
import moduloCliente.dto.ClienteDTO;

public class PantallaRegistroCliente extends JFrame {

    private JTextField txtIdCliente;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtRFC;

    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnSalir;

    private ClienteControlador controlador;

    public PantallaRegistroCliente() {

        controlador = new ClienteControlador();

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        setTitle("Registro de Clientes");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTitulo = new JPanel();

        JLabel lblTitulo = new JLabel("SISTEMA DE CLIENTES");

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(40, 40, 120));

        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel();

        panelFormulario.setLayout(new GridLayout(5, 2, 15, 15));

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        JLabel lblId = new JLabel("ID Cliente:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblTelefono = new JLabel("Teléfono:");
        JLabel lblDireccion = new JLabel("Dirección:");
        JLabel lblRFC = new JLabel("RFC:");

        txtIdCliente = new JTextField();
        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        txtRFC = new JTextField();

        panelFormulario.add(lblId);
        panelFormulario.add(txtIdCliente);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);

        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);

        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);

        panelFormulario.add(lblRFC);
        panelFormulario.add(txtRFC);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");

        btnRegistrar.setBackground(new Color(70, 130, 180));
        btnRegistrar.setForeground(Color.WHITE);

        btnLimpiar.setBackground(new Color(100, 149, 237));
        btnLimpiar.setForeground(Color.WHITE);

        btnSalir.setBackground(new Color(178, 34, 34));
        btnSalir.setForeground(Color.WHITE);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        eventos();
    }

    private void eventos() {

        btnRegistrar.addActionListener(e -> registrarCliente());

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void registrarCliente() {

        try {

            long idCliente = Long.parseLong(
                    txtIdCliente.getText().trim()
            );

            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String rfc = txtRFC.getText().trim();

            /*
             VALIDACIONES
             */

            if (nombre.isEmpty()
                    || telefono.isEmpty()
                    || direccion.isEmpty()
                    || rfc.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios");

                return;
            }

            /*
             VALIDAR NOMBRE
             Solo letras y espacios
             */

            if (!Pattern.matches(
                    "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
                    nombre)) {

                JOptionPane.showMessageDialog(this,
                        "El nombre solo debe contener letras");

                return;
            }

            /*
             VALIDAR TELÉFONO
             Exactamente 10 números
             */

            if (!Pattern.matches(
                    "^[0-9]{10}$",
                    telefono)) {

                JOptionPane.showMessageDialog(this,
                        "El teléfono debe tener 10 dígitos");

                return;
            }

            /*
             VALIDAR DIRECCIÓN
             */

            if (direccion.length() < 5) {

                JOptionPane.showMessageDialog(this,
                        "La dirección es demasiado corta");

                return;
            }

            /*
             VALIDAR RFC
             Persona Física México
             */

            if (!Pattern.matches(
                    "^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$",
                    rfc.toUpperCase())) {

                JOptionPane.showMessageDialog(this,
                        "RFC inválido");

                return;
            }

            ClienteDTO cliente = new ClienteDTO(
                    idCliente,
                    nombre,
                    telefono,
                    direccion,
                    rfc.toUpperCase()
            );

            controlador.registrarCliente(cliente);

            JOptionPane.showMessageDialog(this,
                    "Cliente registrado correctamente");

            limpiarCampos();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "El ID debe ser numérico");
        }
    }

    private void limpiarCampos() {

        txtIdCliente.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtRFC.setText("");
    }
}