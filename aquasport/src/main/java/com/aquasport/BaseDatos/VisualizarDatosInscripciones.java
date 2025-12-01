package com.aquasport.BaseDatos; 

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VisualizarDatosInscripciones {
    public JScrollPane crearTablaInscripciones() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        model.addColumn("Codigo");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");
        model.addColumn("Profesor");
        model.addColumn("Días");
        model.addColumn("Horario");

        String sql = "SELECT "
                   + "  a.idAlumno, "
                   + "  a.nombreCompleto, "
                   + "  a.telefono, "
                   + "  m.profesor, "
                   + "  m.dias, "     
                   + "  m.horario "
                   + "FROM alumnos a "
                   + "LEFT JOIN membresias m ON a.idAlumno = m.idAlumno ";

        try (Connection conn = ConexionBaseDatos.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6]; 
                
                fila[0] = rs.getInt("idAlumno");
                fila[1] = rs.getString("nombreCompleto");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("profesor");
                fila[4] = rs.getString("dias");
                fila[5] = rs.getString("horario");
                
                model.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al consultar inscripciones: " + e.getMessage(), 
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