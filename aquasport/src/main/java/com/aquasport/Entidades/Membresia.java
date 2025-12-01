package com.aquasport.Entidades;

import java.util.Date;

public class Membresia {
    private int idMembresias; 
    private int idAlumno;
    private String nombreAlumno; 
    private int numDias; 
    private String diasTexto;
    private String profesor;
    private String horario;
    private double monto;
    private double cargoExtra;
    private double total;
    private Date fechaInicio;

    public Membresia(int idMembresias, int idAlumno, String nombreAlumno, int numDias, String diasTexto, 
                     String profesor, String horario, double monto, double cargoExtra, double total, Date fechaInicio) {
        this.idMembresias = idMembresias;
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.numDias = numDias;
        this.diasTexto = diasTexto;
        this.profesor = profesor;
        this.horario = horario;
        this.monto = monto;
        this.cargoExtra = cargoExtra;
        this.total = total;
        this.fechaInicio = fechaInicio;
    }

    public int getIdMembresias() { return idMembresias; } 
    public int getIdAlumno() { return idAlumno; }
    public String getNombreAlumno() { return nombreAlumno; }
    public int getNumDias() { return numDias; }
    public String getDiasTexto() { return diasTexto; }
    public String getProfesor() { return profesor; }
    public String getHorario() { return horario; }
    public double getMonto() { return monto; }
    public double getCargoExtra() { return cargoExtra; }
    public double getTotal() { return total; }
    public Date getFechaInicio() { return fechaInicio; }

    @Override
    public String toString() {
        return "Folio " + idMembresias + " - " + nombreAlumno + " (" + diasTexto + ")";
    }
}