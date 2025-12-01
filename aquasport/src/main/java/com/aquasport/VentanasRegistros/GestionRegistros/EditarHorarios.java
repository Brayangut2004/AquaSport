package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.Entidades.Horario;
import com.aquasport.VentanasRegistros.RegistroHorarios;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditarHorarios extends JFrame {
    private ImageIcon icon = new ImageIcon(getClass().getResource("/Logo.png"));
    private JPanel panelPrincipal = new JPanel();
    
    private JLabel tituloLabel = new JLabel("Seleccione el horario a editar");
    
    private JComboBox<Horario> horariosComboBox;
    private JButton botonAbrir = new JButton("Cargar Datos");

    // Estilos
    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBoton = new Color(4, 1, 36);

    public EditarHorarios() {
        initComponents();
        cargarHorariosDesdeDB(); 
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Editar horario");
        setPreferredSize(new Dimension(500, 300));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(icon.getImage());
        }

        horariosComboBox = new JComboBox<>();
        
        tituloLabel.setFont(fuentePrincipal);
        tituloLabel.setForeground(Color.WHITE);

        botonAbrir.setFont(fuentePrincipal);
        botonAbrir.setBackground(colorBoton);
        botonAbrir.setForeground(Color.WHITE);
        
        botonAbrir.addActionListener(evt -> abrirVentanaRegistro());

        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(400, 200)); 

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(tituloLabel)
            .addComponent(horariosComboBox, 0, 300, Short.MAX_VALUE)
            .addComponent(botonAbrir, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(30)
            .addComponent(tituloLabel)
            .addGap(20)
            .addComponent(horariosComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
            .addGap(30)
            .addComponent(botonAbrir, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(20, Short.MAX_VALUE)
        );

        getContentPane().add(panelPrincipal, new GridBagConstraints());
        pack();
        setLocationRelativeTo(null);
    }

    private void cargarHorariosDesdeDB() {
        String sql = "SELECT * FROM horarios";

        try (Connection conexion = ConexionBaseDatos.getConnection();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Horario h = new Horario(
                    rs.getInt("idHorario"),
                    rs.getString("maestroTitular"),
                    rs.getString("nivel"),
                    rs.getInt("cuposDisponibles"),
                    rs.getString("horarioClase"),
                    rs.getString("dias")
                );
                horariosComboBox.addItem(h);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirVentanaRegistro() {
        Horario seleccion = (Horario) horariosComboBox.getSelectedItem();

        if (seleccion != null) {
            RegistroHorarios ventana = new RegistroHorarios(seleccion);
            ventana.setVisible(true);
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un horario.");
        }
    }
}