package com.aquasport.VentanasRegistros.GestionRegistros;

import com.aquasport.Entidades.Horario;
import com.aquasport.BaseDatos.ConexionBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EliminarHorario extends JFrame {
    private JPanel panelPrincipal = new JPanel();
    private JLabel tituloLabel = new JLabel("Seleccione el horario a eliminar");
    private JComboBox<Horario> horariosComboBox = new JComboBox<>();
    private JButton botonEliminar = new JButton("Eliminar");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);
    private Color colorBotonPeligro = new Color(180, 0, 0); 

    public EliminarHorario() {
        initComponents();
        cargarHorariosDesdeDB();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Eliminar horario");
        setPreferredSize(new Dimension(500, 300));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(500, 250)); 

        tituloLabel.setFont(fuentePrincipal);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        horariosComboBox.setBackground(Color.WHITE);

        botonEliminar.setFont(fuentePrincipal);
        botonEliminar.setBackground(colorBotonPeligro);
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFocusPainted(false);
        botonEliminar.addActionListener(e -> eliminarRegistro());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tituloLabel)
                .addComponent(horariosComboBox, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(40)
                .addComponent(tituloLabel)
                .addGap(20)
                .addComponent(horariosComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(30)
                .addComponent(botonEliminar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE)
        );

        getContentPane().add(panelPrincipal, new GridBagConstraints());
        pack(); 
        setLocationRelativeTo(null); 
    }

    private void cargarHorariosDesdeDB() {
        horariosComboBox.removeAllItems(); 
        String sql = "SELECT * FROM horarios";

        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
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
            JOptionPane.showMessageDialog(this, "Error al cargar lista: " + e.getMessage());
        }
    }

    private void eliminarRegistro() {
        Horario seleccionado = (Horario) horariosComboBox.getSelectedItem();

        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún horario seleccionado.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "<html>¿Estás seguro de que deseas eliminar permanentemente el horario de:<br/>" 
            + "<b>" + seleccionado.getMaestroTitular() + "</b> (" + seleccionado.getHorarioClase() + ")?</html>",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM horarios WHERE idHorario = ?";

            try (Connection conn = ConexionBaseDatos.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, seleccionado.getIdHorario());
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                    cargarHorariosDesdeDB(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro (tal vez ya no existe).");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage());
            }
        }
    }
}