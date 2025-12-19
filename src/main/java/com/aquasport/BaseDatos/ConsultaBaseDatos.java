package com.aquasport.BaseDatos;

import java.sql.*;

public class ConsultaBaseDatos {
    // Verifica la existencia de una tabla 
    // mediante los metadatos de la base de datos
    public boolean verificarTabla(String nombreTabla) {
        try (Connection conexion = ConexionBaseDatos.getConnection()) {
            DatabaseMetaData meta = conexion.getMetaData();
            ResultSet tabla = meta.getTables(null, null, nombreTabla.toUpperCase(), new String[]{"TABLE"});
            return tabla.next();
        } catch (SQLException e) {
            return false;
        }
    } 
}
