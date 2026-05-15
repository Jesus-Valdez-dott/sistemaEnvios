/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import Mediadores.LogisticaMediador;
import com.formdev.flatlaf.FlatClientProperties;
import Mediadores.LogisticaMediador;
import dtos.EnvioDTO;
import ui.recursos.Fuentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Jesús
 */
public class FrmHistoCliente extends JFrame{
    private final LogisticaMediador mediador;
    private JTextField txtBusqueda;
    private JTable tablaHistorial;
    private JLabel lblConteo;

    public FrmHistoCliente(LogisticaMediador mediador) {
        this.mediador = mediador;
        Fuentes.cargarFuentes();
        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("ObsExpress - Historial por Cliente");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        // --- SIDEBAR ---
        add(crearSidebar(), BorderLayout.WEST);

        // --- PANEL PRINCIPAL ---
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Cabecera: Título e Icono
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("Historial de Envíos", 
            new ImageIcon(getClass().getResource("/ui/recursos/historial.png")), SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 26f));
        lblTitulo.setIconTextGap(15);
        header.add(lblTitulo, BorderLayout.WEST);

        // Buscador
        JPanel pnlBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBusqueda.setOpaque(false);
        
        txtBusqueda = new JTextField(25);
        txtBusqueda.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nombre o Teléfono del cliente...");
        
        JButton btnBuscar = new JButton("Consultar");
        btnBuscar.setBackground(new Color(30, 136, 229));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarHistorial());

        pnlBusqueda.add(new JLabel("Cliente: "));
        pnlBusqueda.add(txtBusqueda);
        pnlBusqueda.add(btnBuscar);
        header.add(pnlBusqueda, BorderLayout.SOUTH);

        main.add(header, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Código Rastreo", "Destinatario", "Destino", "Fecha", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaHistorial = new JTable(modelo);
        tablaHistorial.setRowHeight(35);
        tablaHistorial.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(14f));
        
        JScrollPane scroll = new JScrollPane(tablaHistorial);
        scroll.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        main.add(scroll, BorderLayout.CENTER);

        // Footer de la tabla
        lblConteo = new JLabel("Envíos encontrados: 0");
        lblConteo.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.ITALIC, 13f));
        main.add(lblConteo, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);
    }

    private void buscarHistorial() {
        String id_cliente = txtBusqueda.getText().trim();
        if (id_cliente.isEmpty()) return;

        List<EnvioDTO> envios = mediador.consultarEnviosPorCliente(id_cliente);
        DefaultTableModel modelo = (DefaultTableModel) tablaHistorial.getModel();
        modelo.setRowCount(0);

        for (EnvioDTO e : envios) {
            modelo.addRow(new Object[]{
                e.getCodigo_rastreo(),
                e.getNombre_destinatario(),
                e.getDireccion_destino(),
                e.getFecha_envio(),
                e.getEstado()
            });
        }
        lblConteo.setText("Envíos encontrados: " + envios.size());
    }

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(33, 33, 33));

        JButton btnVolver = new JButton("Volver al Panel");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(e -> this.dispose());
        
        sidebar.add(btnVolver, BorderLayout.SOUTH);
        return sidebar;
    }
}
