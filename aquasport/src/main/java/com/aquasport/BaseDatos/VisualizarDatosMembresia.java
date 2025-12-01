package com.aquasport.BaseDatos;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class VisualizarDatosMembresia {
    
    public JScrollPane crearTablaMembresias() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        model.addColumn("ID");
        model.addColumn("Alumno");
        model.addColumn("Profesor");
        model.addColumn("Horario");
        model.addColumn("Días");
        model.addColumn("Total");
        model.addColumn("Fecha Inicio");
        model.addColumn("Estado");
        
        String sql = "SELECT "
                   + "  m.idMembresias, "
                   + "  a.nombreCompleto, "
                   + "  m.profesor, "
                   + "  m.horario, "
                   + "  m.dias, "
                   + "  m.total, "
                   + "  m.fechaInicio, "
                   + "  m.estado "
                   + "FROM membresias m "
                   + "INNER JOIN alumnos a ON m.idAlumno = a.idAlumno "
                   + "ORDER BY m.fechaInicio DESC";

        try (Connection conn = ConexionBaseDatos.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[8];
                
                fila[0] = rs.getInt("idMembresias");
                fila[1] = rs.getString("nombreCompleto");
                fila[2] = rs.getString("profesor");
                fila[3] = rs.getString("horario");
                fila[4] = rs.getString("dias");
                fila[5] = rs.getDouble("total");
                fila[6] = rs.getDate("fechaInicio");
                
                boolean estado = rs.getBoolean("estado");
                fila[7] = estado ? "Activo" : "Inactivo";
                
                model.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar las membresías: " + e.getMessage(), 
                "Error SQL", 
                JOptionPane.ERROR_MESSAGE);
        }

        JTable tabla = new JTable(model);
        
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(1, 1));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tabla);
        
        return scrollPane;
    }
}