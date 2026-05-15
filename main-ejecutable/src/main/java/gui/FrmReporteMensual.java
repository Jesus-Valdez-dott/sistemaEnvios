/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatClientProperties;
import Mediadores.LogisticaMediador;
import dto.VentaDTO;
import ui.recursos.Fuentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Jesús
 */
public class FrmReporteMensual extends JFrame{
    private final LogisticaMediador mediador;
    private JTable tablaVentas;
    private JLabel lblTotalVendido, lblCantidadVentas;

    public FrmReporteMensual(LogisticaMediador mediador) {
        this.mediador = mediador;
        Fuentes.cargarFuentes();
        configurarVentana();
        initUI();
        cargarDatosReporte();
    }

    private void configurarVentana() {
        setTitle("ObsExpress - Reporte de Ventas Mensual");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // 1. Sidebar (Coherencia visual)
        add(crearSidebar(), BorderLayout.WEST);

        // 2. Panel Principal
        JPanel panelContenido = new JPanel(new BorderLayout(20, 20));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- ENCABEZADO ---
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(Color.WHITE);
        
        JLabel lblTitulo = new JLabel("Reporte de Ventas - Últimos 30 días", 
            new ImageIcon(getClass().getResource("/ui/recursos/iconDashbord.png")), SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 26f));
        header.add(lblTitulo);

        // --- PANEL DE RESUMEN (Tarjetas) ---
        JPanel panelResumen = new JPanel(new GridLayout(1, 2, 20, 0));
        panelResumen.setBackground(Color.WHITE);

        lblTotalVendido = crearTarjetaResumen("Ingresos Totales", "$0.00", new Color(40, 167, 69));
        lblCantidadVentas = crearTarjetaResumen("Total Operaciones", "0", new Color(30, 136, 229));

        panelResumen.add(lblTotalVendido.getParent());
        panelResumen.add(lblCantidadVentas.getParent());
        header.add(panelResumen);

        panelContenido.add(header, BorderLayout.NORTH);

        // --- TABLA DE VENTAS ---
        String[] columnas = {"ID Venta", "Concepto", "Fecha", "Monto", "Método"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaVentas = new JTable(modelo);
        tablaVentas.setRowHeight(35);
        tablaVentas.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(14f));
        
        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        panelContenido.add(scroll, BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);
    }

    private JLabel crearTarjetaResumen(String titulo, String valor, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblT = new JLabel(titulo);
        lblT.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(14f));
        lblT.setForeground(Color.GRAY);

        JLabel lblV = new JLabel(valor);
        lblV.setFont(Fuentes.ROBOTO_REGULAR.deriveFont(Font.BOLD, 22f));
        lblV.setForeground(color);

        card.add(lblT, BorderLayout.NORTH);
        card.add(lblV, BorderLayout.CENTER);
        
        return lblV; // Devolvemos el label para actualizarlo luego
    }

    private void cargarDatosReporte() {
        // Llamada al mediador
        List<VentaDTO<?>> ventas = mediador.obtenerVentasUltimoMes();
        
        double sumaTotal = 0;
        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        modelo.setRowCount(0);

        for (VentaDTO v : ventas) {
            sumaTotal += v.getMonto();
            modelo.addRow(new Object[]{
                v.getId_venta(),
                v.getFecha(), // Asegúrate que el DTO tenga fecha
                "$" + v.getMonto(),
                v.getMetodo_pago()
            });
        }

        lblTotalVendido.setText("$" + String.format("%.2f", sumaTotal) + " MXN");
        lblCantidadVentas.setText(String.valueOf(ventas.size()));
    }

    private JPanel crearSidebar() {
        // Reutiliza tu lógica de Sidebar aquí
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(33, 33, 33));
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> this.dispose());
        sidebar.add(btnVolver);
        
        return sidebar;
    }
}
