package com.aquasport.BaseDatos;

import java.sql.*;

public class ManejoDatosAdmin {
    ConsultaBaseDatos consulta = new ConsultaBaseDatos();
    private String sql = "";

    public ManejoDatosAdmin() {
    }

    // Funcion que obtiene el usuario (Admin)
    // de la base de datos
    public String[] getDatos() {
        String[] datosAdmin = new String[2];
        sql = "SELECT * FROM datosAdmin";

        try(Connection conexion = ConexionBaseDatos.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();) {
                if(rs.next()) {
                    datosAdmin[0] = rs.getString("usuarioAdmin");
                    datosAdmin[1] = rs.getString("contrasenaAdmin");
                }
            } catch(SQLException e) {
                System.err.println("Error al obtener el usuario:");
                e.printStackTrace();
            }

        return datosAdmin;
    }

    // Funcion que cambia el usuario 
    public void setUsuario(String nuevoUsuario) {
        sql = "UPDATE datosAdmin SET usuarioAdmin = ? WHERE id=1";

        try(Connection conexion = ConexionBaseDatos.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nuevoUsuario);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("Usuario actualizado con exito");
            } else {
                System.out.println("No se encontro al usuario");
            }
        } catch(SQLException e) {
            System.err.println("Error al actualizar usuario:");
            e.printStackTrace();
        }
    }

    // Funcion que cambia la contraseña
    public void setContrasena(String nuevaContrasena) {
        sql = "UPDATE datosAdmin SET contrasenaAdmin = ? WHERE id = 1";

        try(Connection conexion = ConexionBaseDatos.getConnection();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nuevaContrasena);

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0) {
                System.out.println("Contraseña actualizada con exito");
            } else {
                System.out.println("No se encontro al usuario");
            }
        } catch(SQLException e) {
            System.err.println("Error al actualizar contraseña:");
            e.printStackTrace();
        }
    }
}
