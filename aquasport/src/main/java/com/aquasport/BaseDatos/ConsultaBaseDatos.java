package com.aquasport.BaseDatos;

import java.sql.*;

public class ConsultaBaseDatos {
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
