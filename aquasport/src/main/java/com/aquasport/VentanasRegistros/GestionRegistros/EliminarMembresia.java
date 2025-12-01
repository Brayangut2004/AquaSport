package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.Entidades.Membresia;
import com.aquasport.BaseDatos.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EliminarMembresia extends JFrame {

    private JPanel panelPrincipal = new JPanel();
    private JLabel tituloLabel = new JLabel("Seleccione la membresía a eliminar");
    private JComboBox<Membresia> membresiasComboBox = new JComboBox<>();
    private JButton botonEliminar = new JButton("Eliminar");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBotonPeligro = new Color(180, 0, 0);

    public EliminarMembresia() {
        initComponents();
        cargarMembresiasDB();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Eliminar membresia");
        setPreferredSize(new Dimension(500, 250));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(600, 250));

        tituloLabel.setFont(fuentePrincipal);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        membresiasComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        membresiasComboBox.setBackground(Color.WHITE);

        botonEliminar.setFont(fuentePrincipal);
        botonEliminar.setBackground(colorBotonPeligro);
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.addActionListener(e -> eliminarRegistro());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tituloLabel)
                .addComponent(membresiasComboBox, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(40)
                .addComponent(tituloLabel)
                .addGap(20)
                .addComponent(membresiasComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE)
        );

        getContentPane().add(panelPrincipal, new GridBagConstraints());
        pack();
        setLocationRelativeTo(null);
    }

    private void cargarMembresiasDB() {
        membresiasComboBox.removeAllItems();
        String sql = "SELECT m.*, a.nombreCompleto FROM membresias m " +
                     "JOIN alumnos a ON m.idAlumno = a.idAlumno " +
                     "ORDER BY m.idMembresias DESC";

        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Membresia m = new Membresia(
                    rs.getInt("idMembresias"), 
                    rs.getInt("idAlumno"),
                    rs.getString("nombreCompleto"),
                    rs.getInt("numDias"),
                    rs.getString("dias"),
                    rs.getString("profesor"),
                    rs.getString("horario"),
                    rs.getDouble("monto"),
                    0, 0, null 
                );
                membresiasComboBox.addItem(m);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }

    private void eliminarRegistro() {
        Membresia seleccionado = (Membresia) membresiasComboBox.getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay selección.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "<html>¿Estás seguro de eliminar la membresía de: <b>" + seleccionado.getNombreAlumno() + "</b>?<br/>Esta acción no se puede deshacer.</html>",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM membresias WHERE idMembresias = ?";

            try (Connection conn = ConexionBaseDatos.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, seleccionado.getIdMembresias()); 
                int filas = pstmt.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                    cargarMembresiasDB(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar.");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error SQL: " + e.getMessage());
            }
        }
    }
}