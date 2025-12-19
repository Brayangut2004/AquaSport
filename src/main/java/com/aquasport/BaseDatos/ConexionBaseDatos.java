package com.aquasport.BaseDatos;

import java.sql.*;

public class ConexionBaseDatos {
    private static String url = "jdbc:h2:./albercaDB";
    private static String usuario = "sa";
    private static String contrasena = "";

    public static Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("org.h2.Driver");

            conexion = DriverManager.getConnection(url, usuario, contrasena);
            
            if (conexion != null) {
                System.out.println("¡Conexión establecida exitosamente!");
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
        return conexion;
    }
}