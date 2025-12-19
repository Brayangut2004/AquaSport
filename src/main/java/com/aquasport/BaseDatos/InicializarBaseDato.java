package com.aquasport.BaseDatos;

import java.sql.*;

public class InicializarBaseDato {
    private String sql = "";
    private ConsultaBaseDatos consulta = new ConsultaBaseDatos();

    public InicializarBaseDato() {
    }

    // Verifica si existen tablas en la base de datos
    // si no las crea automaticamente
    public void crearTablas() {
        if(!consulta.verificarTabla("datosAdmin")) {
            sql = "CREATE TABLE datosAdmin ("
                + "id               INT          PRIMARY KEY, "
                + "usuarioAdmin     VARCHAR(30), "
                + "contrasenaAdmin  VARCHAR(30))";

            String sqlInsert = "INSERT INTO datosAdmin(id, usuarioAdmin, contrasenaAdmin) "
                             + "VALUES (1, 'user', '1234')";
            
            try(Connection conexion = ConexionBaseDatos.getConnection();
                Statement stmt = conexion.createStatement()) {
                
                stmt.execute(sql);
                stmt.execute(sqlInsert);
                
            } catch(SQLException e) {
                System.err.println("Error al crear datosAdmin: " + e.getMessage());
            } 
        } else {
            return;
        }

        if(!consulta.verificarTabla("alumnos")) {
            sql = "CREATE TABLE alumnos ("
                + "idAlumno            INT           PRIMARY KEY AUTO_INCREMENT, "
                + "nombreCompleto      VARCHAR(100)  NOT NULL, "
                + "alergias            VARCHAR(255)  DEFAULT '', "
                + "fechaNacimiento     DATE          NOT NULL, "
                + "telefono            VARCHAR(20)   NOT NULL, "
                + "telefonoEmergencia  VARCHAR(20)   NOT NULL, "
                + "direccion           VARCHAR(255)  NOT NULL)";

            ejecutarSql(sql);
        }

        if(!consulta.verificarTabla("horarios")) {
            sql = "CREATE TABLE horarios ("
                + "idHorario         INT           PRIMARY KEY AUTO_INCREMENT, "
                + "maestroTitular    VARCHAR(100)  NOT NULL, "
                + "nivel             VARCHAR(50)   NOT NULL, "
                + "cuposDisponibles  INT           NOT NULL, "
                + "horarioClase      VARCHAR(50)   NOT NULL, "
                + "dias              VARCHAR(50)   NOT NULL)";

            ejecutarSql(sql);
        }

        if(!consulta.verificarTabla("membresias")) {
            sql = "CREATE TABLE membresias ("
                + "idMembresias  INT             AUTO_INCREMENT PRIMARY KEY, "
                + "idAlumno      INT             NOT NULL, " 
                + "numDias       INT             NOT NULL, "
                + "dias          VARCHAR(100)    NOT NULL, "
                + "profesor      VARCHAR(100)    NOT NULL, "
                + "horario       VARCHAR(50)     NOT NULL, "
                + "monto         DECIMAL(10, 2)  NOT NULL, "
                + "cargoExtra    DECIMAL(10, 2)  DEFAULT 0.00, "
                + "total         DECIMAL(10, 2)  NOT NULL, "
                + "fechaInicio   DATE            NOT NULL, "
                + "fechaPago     DATE            NOT NULL, "
                + "estado        BOOLEAN         NOT NULL, "
                + "FOREIGN KEY (idAlumno) REFERENCES alumnos(idAlumno) ON DELETE CASCADE)";
            
            ejecutarSql(sql);
        }

        if(!consulta.verificarTabla("pagos")) {
            sql = "CREATE TABLE pagos ("
                + "idPago      INT             AUTO_INCREMENT PRIMARY KEY, "
                + "idAlumno    INT             NOT NULL, "
                + "Concepto    VARCHAR(100)    NOT NULL, "
                + "monto       DECIMAL(10, 2)  NOT NULL, "
                + "referencia  VARCHAR(100)    NOT NULL, "
                + "fechaPago   DATE, " 
                + "FOREIGN KEY (idAlumno) REFERENCES alumnos(idAlumno) ON DELETE CASCADE)";

            ejecutarSql(sql);
        }
    }

    private void ejecutarSql(String sql) {
        try (Connection conexion = ConexionBaseDatos.getConnection();
             Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}