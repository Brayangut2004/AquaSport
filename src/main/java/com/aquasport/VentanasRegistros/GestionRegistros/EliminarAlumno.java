package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.Entidades.Alumno;
import com.aquasport.BaseDatos.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EliminarAlumno extends JFrame {

    private JPanel panelPrincipal = new JPanel();
    private JLabel tituloLabel = new JLabel("Seleccione el alumno a eliminar");
    private JComboBox<Alumno> alumnosComboBox = new JComboBox<>();
    private JButton botonEliminar = new JButton("Eliminar");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBotonPeligro = new Color(180, 0, 0);

    public EliminarAlumno() {
        initComponents();
        cargarAlumnosDB();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Eliminar alumno");
        setPreferredSize(new Dimension(450, 250));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(500, 250));

        tituloLabel.setFont(fuentePrincipal);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        alumnosComboBox.setBackground(Color.WHITE);

        botonEliminar.setFont(fuentePrincipal);
        botonEliminar.setBackground(colorBotonPeligro);
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.addActionListener(e -> eliminarRegistro());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tituloLabel)
                .addComponent(alumnosComboBox, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(40)
                .addComponent(tituloLabel)
                .addGap(20)
                .addComponent(alumnosComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE)
        );

        getContentPane().add(panelPrincipal, new GridBagConstraints());
        pack();
        setLocationRelativeTo(null);
    }

    private void cargarAlumnosDB() {
        alumnosComboBox.removeAllItems();
        String sql = "SELECT * FROM alumnos ORDER BY nombreCompleto ASC";

        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alumno a = new Alumno(
                    rs.getInt("idAlumno"),
                    rs.getString("nombreCompleto"),
                    rs.getString("alergias"),
                    rs.getDate("fechaNacimiento"),
                    rs.getString("telefono"),
                    rs.getString("telefonoEmergencia"),
                    rs.getString("direccion")
                );
                alumnosComboBox.addItem(a);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar alumnos: " + e.getMessage());
        }
    }

    private void eliminarRegistro() {
        Alumno seleccionado = (Alumno) alumnosComboBox.getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún alumno seleccionado.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "<html>¿Estás seguro de eliminar a: <b>" + seleccionado.getNombreCompleto() + "</b>?<br/>Esta acción no se puede deshacer.</html>",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM alumnos WHERE idAlumno = ?";

            try (Connection conn = ConexionBaseDatos.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, seleccionado.getIdAlumno());
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente.");
                    cargarAlumnosDB(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de base de datos (Posiblemente el alumno tenga registros asociados).");
            }
        }
        dispose();
    }
}