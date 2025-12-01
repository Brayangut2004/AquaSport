package com.aquasport.BaseDatos;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class VisualizarDatosPagos {
    
    public JScrollPane crearTablaPagos() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        model.addColumn("ID Pago");
        model.addColumn("Alumno");
        model.addColumn("Concepto");   
        model.addColumn("Monto");
        model.addColumn("Referencia"); 
        model.addColumn("Fecha");
        
        String sql = "SELECT "
                   + "  p.idPago, "
                   + "  a.nombreCompleto, "
                   + "  p.Concepto, "
                   + "  p.monto, "
                   + "  p.referencia, "
                   + "  p.fechaPago "
                   + "FROM pagos p "
                   + "INNER JOIN alumnos a ON p.idAlumno = a.idAlumno "
                   + "ORDER BY p.fechaPago DESC"; 

        try (Connection conn = ConexionBaseDatos.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                
                fila[0] = rs.getInt("idPago");
                fila[1] = rs.getString("nombreCompleto"); 
                fila[2] = rs.getString("Concepto");
                fila[3] = rs.getDouble("monto");
                fila[4] = rs.getString("referencia"); 
                fila[5] = rs.getDate("fechaPago");
                
                model.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los datos de pagos: " + e.getMessage(), 
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