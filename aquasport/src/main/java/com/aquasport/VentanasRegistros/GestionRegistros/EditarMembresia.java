package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.Entidades.Membresia;
import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.VentanasRegistros.RegistroMembresias;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditarMembresia extends JFrame {

    private JPanel panelPrincipal = new JPanel();
    private JLabel tituloLabel = new JLabel("Seleccione la membresía a editar");
    private JComboBox<Membresia> membresiasComboBox = new JComboBox<>();
    private JButton botonCargar = new JButton("Cargar Datos");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBoton = new Color(4, 1, 36);

    public EditarMembresia() {
        initComponents();
        cargarMembresiasDB();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Editar membresia");
        setPreferredSize(new Dimension(500, 300));
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

        botonCargar.setFont(fuentePrincipal);
        botonCargar.setBackground(colorBoton);
        botonCargar.setForeground(Color.WHITE);
        botonCargar.addActionListener(e -> abrirEdicion());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tituloLabel)
                .addComponent(membresiasComboBox, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(botonCargar, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(40)
                .addComponent(tituloLabel)
                .addGap(20)
                .addComponent(membresiasComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(botonCargar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
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
                    rs.getDouble("cargoExtra"),
                    rs.getDouble("total"),
                    rs.getDate("fechaInicio")
                );
                membresiasComboBox.addItem(m);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }

    private void abrirEdicion() {
        Membresia seleccionado = (Membresia) membresiasComboBox.getSelectedItem();
        if (seleccionado != null) {
            RegistroMembresias ventana = new RegistroMembresias(seleccionado);
            ventana.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una opción.");
        }
    }
}