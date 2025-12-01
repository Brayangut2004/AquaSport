package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.Entidades.Alumno;
import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.VentanasRegistros.RegistroAlumno;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditarAlumno extends JFrame {

    private JPanel panelPrincipal = new JPanel();
    private JLabel tituloLabel = new JLabel("Seleccione el alumno a editar");
    private JComboBox<Alumno> alumnosComboBox = new JComboBox<>();
    private JButton botonEditar = new JButton("Cargar datos");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBoton = new Color(4, 1, 36);

    public EditarAlumno() {
        initComponents();
        cargarAlumnosDB();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Editar alumno");
        setPreferredSize(new Dimension(450, 300));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(500, 250));

        tituloLabel.setFont(fuentePrincipal);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        alumnosComboBox.setBackground(Color.WHITE);

        botonEditar.setFont(fuentePrincipal);
        botonEditar.setBackground(colorBoton);
        botonEditar.setForeground(Color.WHITE);
        botonEditar.addActionListener(e -> abrirVentanaEdicion());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tituloLabel)
                .addComponent(alumnosComboBox, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                .addComponent(botonEditar, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(40)
                .addComponent(tituloLabel)
                .addGap(20)
                .addComponent(alumnosComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(botonEditar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
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

    private void abrirVentanaEdicion() {
        Alumno seleccionado = (Alumno) alumnosComboBox.getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún alumno seleccionado.");
            return;
        }

        RegistroAlumno ventanaEdicion = new RegistroAlumno(seleccionado);
        ventanaEdicion.setVisible(true);
        
        this.dispose();
    }
}