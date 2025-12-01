package com.aquasport.BaseDatos; 

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VisualizarDatosHorarios {
    public JScrollPane crearTablaHorarios() {
        
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        model.addColumn("Horario");
        model.addColumn("Maestro");
        model.addColumn("Cupo Total");     
        model.addColumn("Nivel");
        model.addColumn("Cupos Disponibles"); 
        
        String sql = "SELECT "
                   + "  h.maestroTitular, "  
                   + "  h.nivel, "
                   + "  h.horarioClase, "    
                   + "  h.cuposDisponibles, " 
                   + "  COUNT(m.idMembresias) AS inscritos, "    
                   + "  (h.cuposDisponibles - COUNT(m.idMembresias)) AS cuposRestantes " 
                   + "FROM horarios h " 
                   + "LEFT JOIN membresias m ON h.maestroTitular = m.profesor "
                   + "                      AND h.horarioClase = m.horario "
                   + "                      AND m.estado = TRUE " 
                   + "GROUP BY h.idHorario, h.maestroTitular, h.nivel, h.horarioClase, h.cuposDisponibles "
                   + "ORDER BY h.maestroTitular, h.horarioClase"; 

        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[5]; 
                fila[0] = rs.getString("horarioClase");
                fila[1] = rs.getString("maestroTitular");
                fila[2] = rs.getInt("cuposDisponibles"); 
                fila[3] = rs.getString("nivel");
                fila[4] = rs.getInt("cuposRestantes");  
                
                model.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los datos de horarios: " + e.getMessage(), 
                "Error de SQL", 
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