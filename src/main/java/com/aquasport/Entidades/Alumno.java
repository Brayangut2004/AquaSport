package com.aquasport.Entidades;

import java.sql.Date;

public class Alumno {
    private int idAlumno;
    private String nombreCompleto;
    private String alergias;
    private Date fechaNacimiento;
    private String telefono;
    private String telefonoEmergencia;
    private String direccion;

    public Alumno(int idAlumno, String nombreCompleto, String alergias, Date fechaNacimiento, String telefono, String telefonoEmergencia, String direccion) {
        this.idAlumno = idAlumno;
        this.nombreCompleto = nombreCompleto;
        this.alergias = alergias;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.telefonoEmergencia = telefonoEmergencia;
        this.direccion = direccion;
    }

    public int getIdAlumno() { return idAlumno; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getAlergias() { return alergias; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public String getTelefonoEmergencia() { return telefonoEmergencia; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return nombreCompleto; 
    }
}